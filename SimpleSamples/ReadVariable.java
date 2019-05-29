/*
    Sample program
	This program reads the value of I[1] and writes a random value.
*/

import orin2.bcap.BCAPClient;
import java.util.Random;

public class ReadVariable {
	public static void main(String[] args) {
		try {
			new ReadVariable("tcp:192.168.0.1", 3000, 3);
		} catch(Throwable cause) {
			System.out.println(cause.toString());
		}
	}
	
	private BCAPClient m_client = null;
	
	public ReadVariable(String strConn, int timeout, int retry) throws Throwable {
		m_client = new BCAPClient(strConn, timeout, retry);
		m_client.Service_Start("WDT=400");
		
		/* Get controller handle */
		int hCtrl = 0;
		try {
			hCtrl = m_client.Controller_Connect("Ctrl", "CaoProv.DENSO.VRC", "localhost", "");
			
			/* Get variable(I[1]) handle */
            int hIval = 0;
			Object ret= 0;
			Random rand = new Random();
			int num = rand.nextInt(100);
			try {
                hIval = m_client.Controller_GetVariable(hCtrl,"I1","");
				ret = m_client.Variable_GetValue(hIval);
				System.out.println("I[1] = " + ret);
				m_client.Variable_PutValue(hIval,num);
				ret = m_client.Variable_GetValue(hIval);
				System.out.println("I[1] = " +ret);
			} catch(Throwable cause) {
				System.out.println(cause.toString());
			} finally {
				if(hIval != 0) {
					/* Release variable handle */
					m_client.Variable_Release(hIval);
				}
			}
		} catch(Throwable cause) {
			System.out.println(cause.toString());
		} finally {
			if(hCtrl != 0) {
				/* Disconnect controller */
				m_client.Controller_Disconnect(hCtrl);
			}
		}
		m_client.Service_Stop();
		m_client.Release();
	}
}
