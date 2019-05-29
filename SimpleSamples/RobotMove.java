/*
    Sample program
	This program moves the robot to P [1] and then moves it 50 mm up and down.
*/

import orin2.bcap.BCAPClient;

public class RobotMove {
	public static void main(String[] args) {
		try {
			new RobotMove("tcp:192.168.0.1", 3000, 3);
		} catch(Throwable cause) {
			System.out.println(cause.toString());
		}
	}
		
	private BCAPClient m_client = null;
	
	public RobotMove(String strConn, int timeout, int retry) throws Throwable {
		m_client = new BCAPClient(strConn, timeout, retry);
		m_client.Service_Start("WDT=400");
		
		/* Get controller handle */
		int hCtrl = 0;
		try {
			hCtrl = m_client.Controller_Connect("Ctrl", "CaoProv.DENSO.VRC", "localhost", "");
			
			/* Get robot handle */
			int hRob = 0;
			try {
				hRob = m_client.Controller_GetRobot(hCtrl, "Rob", "");
				
				/* Get arm control authority */
				m_client.Robot_Execute(hRob, "Takearm", new int[] { 0, 1 });
				/* Motor on */
				m_client.Robot_Execute(hRob, "Motor", true);

                /* Move to first pose (P[1]) */
				m_client.Robot_Move(hRob, 1, "@E P1", "");
				
				/* Get current position */
                double[] dPosInit = (double[])m_client.Robot_Execute(hRob, "CurPos", "");
                dPosInit[2] += 50;
                
                /* Move to P[1]+P(0,0,50) */
                Object[] posedata ={dPosInit,"P","@E"};
                m_client.Robot_Move(hRob, 1, posedata, "");
                /* Move to P[1]+P(0,0,-50) */
                dPosInit[2] -= 100;
                posedata[0] =dPosInit; //Update value
                m_client.Robot_Move(hRob, 1, posedata, "");
				
				/* Motor off */
				m_client.Robot_Execute(hRob, "Motor", false);
				
				/* Release arm control authority */
				m_client.Robot_Execute(hRob, "Givearm", null);
			} catch(Throwable cause) {
				System.out.println(cause.toString());
			} finally {
				if(hRob != 0) {
					/* Release robot handle */
					m_client.Robot_Release(hRob);
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
