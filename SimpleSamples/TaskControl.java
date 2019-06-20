/*
    Sample program
    This program executes pro1.pcs .
*/

import orin2.bcap.BCAPClient;
import java.util.Arrays;

public class TaskControl {
	public static void main(String[] args) {
		try {
			new TaskControl("tcp:192.168.0.1", 3000, 3);
		} catch(Throwable cause) {
			System.out.println(cause.toString());
		}
	}
	
	private BCAPClient m_client = null;
	
	public TaskControl(String strConn, int timeout, int retry) throws Throwable {
		m_client = new BCAPClient(strConn, timeout, retry);
		m_client.Service_Start("WDT=400");
		
		/* Get controller handle */
		int hCtrl = 0;
		try {
			hCtrl = m_client.Controller_Connect("Ctrl", "CaoProv.DENSO.VRC", "localhost", "");
			
			/* Get robot handle */
			Object ret;
			int htask = 0;
			int htaskstate = 0;
			try {
                /* Get task(pro1.pcs) handle */
				htask = m_client.Controller_GetTask(hCtrl,"Pro1","");
				/* Get task STATUS (pro1.pcs) handle */
				htaskstate = m_client.Task_GetVariable(htask, "@STATUS", "");
				/* Task Start One cycle */
                m_client.Task_Start(htask,1,"");
				System.out.println("Task Start");
				Thread.sleep(100);
				/* get Task State  */
				Object tmp=-1;
				System.out.println(tmp.toString() );
				while((int)tmp!=2){
					tmp = m_client.Variable_GetValue(htaskstate);
					System.out.println("Task State = " +tmp);
				}
			} catch(Throwable cause) {
				System.out.println(cause.toString());
            } finally {
				if(htaskstate != 0) {
					/* Release robot handle */
					m_client.Variable_Release(htaskstate);
				}
				if(htask != 0) {
					/* Release robot handle */
					m_client.Task_Release(htask);
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
