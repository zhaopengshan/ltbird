package LeadTone.CMPPDatabase;

import java.io.PrintStream;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Database.DatabasePool;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;

/**
 * ��������������̣�������߳�״��
 */
public class CMPPSubmitDatabase extends Engine {
	public static int m_nCount = 0;
	public static boolean m_input_switch = true;
	public static boolean m_output_switch = true;
	public static boolean m_clean_switch = true;
	int m_nID;
	CMPPPacketQueue m_input_queue;
	CMPPPacketQueue m_output_queue;
	CMPPSubmitDatabaseOutput m_output;
	CMPPSubmitDatabaseInput m_input;

	 CMPPSubmitDatabaseClean m_clean;

	public CMPPSubmitDatabase(int nID, CMPPPacketQueue input_queue,
			CMPPPacketQueue output_queue, DatabasePool pool) {
		super("CMPPSubmitDatabase(" + nID + ")");
		m_input_queue = null;
		m_output_queue = null;
		m_output = null;
		m_input = null;
		m_nID = nID;
		m_input_queue = input_queue;
		m_output_queue = output_queue;

		m_output = new CMPPSubmitDatabaseOutput(m_nID, m_output_queue, pool);
		m_input = new CMPPSubmitDatabaseInput(m_nID, m_input_queue, pool);
		// m_clean = new CMPPSubmitDatabaseClean(m_nID, pool);
	}

	public void dump(PrintStream ps) {
		/*ps.print("\tsubmitdb(" + m_nID + "," + (isRunning() ? "+" : "-")
				+ ") : " + "input(" + m_input.m_nCount + ")," + "output("
				+ m_output.m_nCount + ")," + "clean(" + m_clean.m_nCount
				+ ")\r\n");*/
	}

	public void empty() {
		m_input_queue = null;
		m_output_queue = null;

		m_output = null;
		m_input = null;
		// m_clean = null;
	}

	public void run() {
		try {
			if (m_input_switch) {
				Log.log("CMPPSubmitDatabase(" + m_nID
						+ ").run : submit database input begin startup !",
						0x1000000000L);
				m_input.startup();
				Engine.wait(m_input);
			}

			if (m_output_switch) {
				Log.log("CMPPSubmitDatabase(" + m_nID
						+ ").run : submit database output begin startup !",
						0x1000000000L);
				m_output.startup();
				Engine.wait(m_output);
			}

			if (m_clean_switch) {
				Log.log("CMPPSubmitDatabase(" + m_nID
						+ ").run : submit database clean begin startup !",
						0x1000000000L);
				// m_clean.startup();
				// Engine.wait(m_clean);
			}

			Log.log("CMPPSubmitDatabase(" + m_nID
					+ ").run : submit database startup !", 0x1000000000L);
			m_nStatus = 1;
			for (; isRunning(); Engine.sleep()) {
				if (m_input_switch) {
					if (!m_input.isRunning()) {
						Log.log("CMPPSubmitDatabase(" + m_nID
								+ ").run : database input unexpected stopped !",
								0x2000001000000000L);
						break;
					}
				}

				if (m_output_switch) {
					if (!m_output.isRunning()) {
						Log.log("CMPPSubmitDatabase("
								+ m_nID
								+ ").run : database output unexpected stopped !",
								0x2000001000000000L);
						break;
					}
				}
				
				//����빬��ΰ��ͨ��m_clean�߳�δ�������¼���ã����Խ��ö������Σ�ͬʱ�����������á���� 20120628
				/*if (m_clean_switch) {
					if (!m_clean.isRunning()) {
						Log.log("CMPPSubmitDatabase(" + m_nID
								+ ").run : database clean unexpected stopped !",
								0x2000001000000000L);
						break;
					}
				}*/
			}

		} catch (Exception e) {
			Log.log(e);
			Log.log("CMPPSubmitDatabase(" + m_nID + ").run : unexpected exit !",
					0x2000001000000000L);
		}
		Log.log("CMPPSubmitDatabase(" + m_nID
				+ ").run : submit database input begin shutdown !",
				0x1000000000L);
		m_input.shutdown();
		Engine.wait(m_input);
		Log.log("CMPPSubmitDatabase(" + m_nID
				+ ").run : submit database output begin shutdown !",
				0x1000000000L);
		m_output.shutdown();
		Engine.wait(m_output);
		Log.log("CMPPSubmitDatabase(" + m_nID
				+ ").run : submit database clean begin shutdown !",
				0x1000000000L);
		//m_clean.shutdown();
		//Engine.wait(m_clean);
		empty();
		m_nStatus = 3;
		Log.log("CMPPSubmitDatabase(" + m_nID + ").run : thread stopped !",
				0x1000000000L);
	}

}