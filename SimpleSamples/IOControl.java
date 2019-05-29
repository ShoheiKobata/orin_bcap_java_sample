/*
    Sample program
    This program flip the state of IO[128].
*/

import orin2.bcap.BCAPClient;
import java.util.Random;

public class IOControl {
	public static void main(String[] args) {
		try {
			new IOControl("tcp:192.168.0.1", 3000, 3);
		} catch(Throwable cause) {
			System.out.println(cause.toString());
		}
	}
	
	private BCAPClient m_client = null;
	
	public IOControl(String strConn, int timeout, int retry) throws Throwable {
		m_client = new BCAPClient(strConn, timeout, retry);
		m_client.Service_Start("WDT=400");
		
		/* Get controller handle */
		int hCtrl = 0;
		try {
			hCtrl = m_client.Controller_Connect("Ctrl", "CaoProv.DENSO.VRC", "localhost", "");
			
			/* Get variable(I[1]) handle */
            int hIval = 0;
			Object ret= 0;
            Boolean input_state = true;
            Boolean get_state = false;
			
			try {
                hIval = m_client.Controller_GetVariable(hCtrl,"IO128","");
				ret = m_client.Variable_GetValue(hIval);
                System.out.println("IO[128] = " + ret);
                get_state = (Boolean)ret;
                input_state =! get_state;
				m_client.Variable_PutValue(hIval,input_state);
				ret = m_client.Variable_GetValue(hIval);
				System.out.println("IO[128] = " +ret);
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
