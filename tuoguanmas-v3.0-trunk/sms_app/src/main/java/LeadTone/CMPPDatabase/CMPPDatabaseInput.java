package LeadTone.CMPPDatabase;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Utility;
import LeadTone.CMPPDatabase.CMPPTable.CMPPDeliverBackupTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPDeliverTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPQueryTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitBackupTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitTable;
import LeadTone.Database.DatabasePool;
import LeadTone.LeadToneLogic.BackupTableConfig;
import LeadTone.LeadToneLogic.LeadToneLogic;
import LeadTone.LeadToneLogic.LeadToneLogicConfig;
import LeadTone.LeadToneLogic.UpdateFinalResultConfig;
import LeadTone.Packet.CMPPPacket.CMPPDeliver;
import LeadTone.Packet.CMPPPacket.CMPPPacket;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import LeadTone.Packet.CMPPPacket.CMPPQuery;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Packet.CMPPPacket.CMPPSubmit;
import LeadTone.Packet.CMPPPacket.CMPPSubmitResponse;
import LeadTone.longMsg.CMPPDeliverCache;
import LeadTone.longMsg.MasCmpp2SmsReceive;

/**
 * ���봦���̣߳���������Ϣ�����л�ȡ��Ϣ������ݳ־û���״̬���²���
 */
public class CMPPDatabaseInput extends Engine {
	int m_nID;
	CMPPSubmitTable m_submit;
	CMPPDeliverTable m_deliver;
	CMPPSubmitBackupTable m_submit_backup;
	CMPPDeliverBackupTable m_deliver_backup;
	CMPPQueryTable m_query;
	CMPPPacketQueue m_queue;
	public int m_nCount;
	private static CMPPDeliverCache dmc = CMPPDeliverCache.getInstance();

	public CMPPDatabaseInput(int nID, CMPPPacketQueue queue, DatabasePool pool) {
		super("CMPPDatabaseInput(" + nID + ")");
		m_submit = null;
		m_deliver = null;
		m_query = null;
		m_queue = null;
		m_nCount = 0;
		m_nID = nID;
		m_queue = queue;
		m_submit = new CMPPSubmitTable();
		m_submit.m_pool = pool;
		m_deliver = new CMPPDeliverTable();
		m_deliver.m_pool = pool;
		m_query = new CMPPQueryTable();
		m_query.m_pool = pool;
		m_submit_backup = new CMPPSubmitBackupTable();
		m_submit_backup.m_pool = pool;
		m_deliver_backup = new CMPPDeliverBackupTable();
		m_deliver_backup.m_pool = pool;
	}

	public void empty() {
		m_submit.empty();
		m_submit = null;
		m_submit_backup.empty();
		m_submit_backup = null;
		m_deliver.empty();
		m_deliver = null;
		m_deliver_backup.empty();
		m_deliver_backup = null;
		m_query.empty();
		m_query = null;
		m_queue = null;
	}

	/**
	 * ����������в���Deliver��Ϣ����CMPPDeliver���У� ���Ϊ�û�������Ϣ������û������������ݵ�����Ӧ�·��߼���
	 * ���Ϊ״̬��������¶�Ӧ��Submit���е��·���¼������״̬final_result, �������ݵı���ת�Ʋ���
	 * 
	 * @param deliver
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public void handleDeliver(CMPPDeliver deliver) throws SQLException,
			UnsupportedEncodingException {
		m_nCount++;
		
		//TODO add why
	     if (deliver.src_terminal_id != null
				&& deliver.src_terminal_id.startsWith("86")) {
			deliver.src_terminal_id = deliver.src_terminal_id.substring(2);
	     }

		// ���registered_delivery=0��ʾΪ�û�����
		if (deliver.registered_delivery == 0) {

			// ���deliver���п��ؿ������յ���deliver��Ϣ����CMPPDeliver��,
			// ���deliver���п��عر����յ���deliver��Ϣ����CMPPDeliverBackup��
			if (CMPPDeliverDatabase.m_output_switch) {
				// ��¼�û�������Ϣ��CMPPDeliver���ȴ���ѭ�ظ�response
				/*
				 * m_deliver.open(); m_deliver.insert(deliver);
				 * m_deliver.close();
				 */

				// �������г��������⣬�������ϴ��롣20120920
				m_deliver.open();
				Log.log("===========================0: result" +deliver.msg_content[0],0x4000000000000L);
				Log.log("===========================1: result" +deliver.msg_content[1],0x4000000000000L);
				// �����ŵ���3λ�ַ�16����Ϊ��05 00 03�������ֽ�Ϊ��5 0 3�������ж���3λ
				if (deliver.msg_content[0] == 5 
						&& deliver.msg_content[1] == 0
						&& deliver.msg_content[2] == 3) {
					// �ϲ�������
					MasCmpp2SmsReceive.longSmsHandler(deliver);
					// ���
					deliverToInsert();
				} else {
					// ��ͨ����ֱ�����,��������֪ͨ���򣬽�����ַ�����Ϊ������ʾ�����ԣ��˴����ַ�����ΪGB2312��modified by chenguoliang
					//ucs2��ʽ��ת����
					//deliver.msg_content = Utility.ucs2_to_gb2312(deliver.msg_content).getBytes();
					if(deliver.msg_fmt==8) {
						//remove 1 line
//						deliver.msg_content = Utility.ucs2_to_gb2312(deliver.msg_content).getBytes();
						//add follow 3 line
						//TODO
//			        	   String msg_content = new String(deliver.msg_content, "UnicodeBigUnmarked");
			        	   LeadTone.Log.log("===========================msg_content:" + new String(deliver.msg_content, "UnicodeBigUnmarked"), 1125899906842624L);
//			        	 deliver.msg_content = LeadTone.Utility.ucs2_to_gb2312(deliver.msg_content).getBytes();
					}
					
					m_deliver.insert(deliver);
				}
				m_deliver.close();
			} else {
				// ��¼�û�������Ϣ��CMPPDeliver��
				/*
				 * m_deliver.open(); m_deliver.insert(deliver);
				 * m_deliver.close();
				 */

				// �������г��������⣬�������ϴ��롣20120920
				m_deliver.open();
				Log.log("===========================0: result"+deliver.msg_content[0],0x4000000000000L);
				Log.log("===========================1: result"+deliver.msg_content[1],0x4000000000000L);
				// �����ŵ���3λ�ַ�16����Ϊ��05 00 03�������ֽ�Ϊ��5 0 3�������ж���3λ
				if (deliver.msg_content[0] == 5 
						&& deliver.msg_content[1] == 0 
						&& deliver.msg_content[2] == 3) {

					// �ϲ�������
					MasCmpp2SmsReceive.longSmsHandler(deliver);
					// ���
					deliverToInsert();
				} else {
					// ��ͨ����ֱ�����
					//ucs2��ʽ��ת����
					//deliver.msg_content = Utility.ucs2_to_gb2312(deliver.msg_content).getBytes();
					if(deliver.msg_fmt==8) {
						//remove 1 line 
//						deliver.msg_content = Utility.ucs2_to_gb2312(deliver.msg_content).getBytes();
						//TODO 
						//add follow 3 line
			        	   LeadTone.Log.log("===========================msg_content:" + new String(deliver.msg_content, "UnicodeBigUnmarked"), 1125899906842624L);
//			        	   deliver.msg_content = LeadTone.Utility.ucs2_to_gb2312(deliver.msg_content).getBytes();
					}
					
					m_deliver.insert(deliver);
				}
				m_deliver.close();

				// ����deliver��Ϣ��CMPPDeliverBackup��
				m_deliver_backup.open();
				m_deliver_backup.backup(deliver);
				m_deliver_backup.close();

				// ɾ��CMPPDeliver��deliver��Ϣ
				m_deliver.open();
				m_deliver.deletenow(deliver);
				m_deliver.close();
			}

			// ��������û����л��ƿ��� ����봦���߼�
			if (LeadToneLogicConfig.LOGICSWITCH.equalsIgnoreCase("true")) {
				Log.log("CMPPDatabaseInput.handleDeliver : to invokem LeadTone logic !",
						0x4000000000000L);

				if (deliver.src_terminal_id != null
						&& deliver.msg_content != null) {
					String phonenumber = null;
					String msg_content = null;
					phonenumber = LeadToneLogic
							.dealPhoneNumber(deliver.src_terminal_id);
					msg_content = new String(deliver.msg_content, "GB2312");
					Log.log("CMPPDatabaseInput.handleDeliver : phonenumber "
							+ phonenumber, 0x4000000000000L);
					Log.log("CMPPDatabaseInput.handleDeliver : msg_content "
							+ msg_content, 0x4000000000000L);
					if (phonenumber != null
							&& LeadToneLogic.isPhoneNumber(phonenumber)
							&& msg_content != null) {
						String result = null;
						result = (new LeadToneLogic()).callSend(phonenumber,
								msg_content);
						Log.log("CMPPDatabaseInput.handleDeliver : result "
								+ result, 0x4000000000000L);
					}
				}
			}

		}
		// ����Ϊ״̬����,�����������cmpp_submit��final_result����
		else {
			// �������submit_cmpp������״̬���ؿ�����ִ�и��²���
			if (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH
					.equalsIgnoreCase("true")) {
				Log.log("CMPPDatabaseInput.handleDeliver : to update submit table's final_result feild !",
						0x4000000000000L);
				m_submit.open();
				m_submit.update(deliver);
				m_submit.close();
			}

			// �����̬���ݿ��ؿ�����ִ�б��ݲ���
			if (BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true")) {
				Log.log("CMPPDatabaseInput.handleDeliver : backup submit record !",
						0x4000000000000L);
				m_submit_backup.open();
				m_submit_backup.backup(deliver);
				m_submit_backup.close();
				Log.log("CMPPDatabaseInput.handleDeliver : delete submit record !",
						0x4000000000000L);
				m_submit.open();
				m_submit.deletenow(deliver);
				m_submit.close();
			}

		}

		deliver.empty();
		deliver = null;
	}

	public static final String toHex(byte b) {
		int str = "0123456789ABCDEF".charAt(0xF & b >> 4)
				+ "0123456789ABCDEF".charAt(b & 0xF);
		return String.valueOf(str);
	}

	/**
	 * ����������в���Submit��Ϣ������CMPPSubmit����
	 * 
	 * @param submit
	 * @throws SQLException
	 */
	public void handleSubmit(CMPPSubmit submit) throws SQLException {
		m_submit.open();
		m_submit.insert(submit);
		m_submit.close();
		m_nCount++;
		submit.empty();
		submit = null;
	}

	/**
	 * ����������в���SubmitResponse��Ϣ��������CMPPSubmit���ж�Ӧ��״̬(process)�ֶ�
	 * 
	 * @param response
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	public void handleSubmitResponse(CMPPSubmitResponse response)
			throws SQLException, UnsupportedEncodingException {

		// ���submit response�ķ��ͽ��Ϊ8��111����ʾISMG��Ϣ��������������Ϣ�����ط�
		if (response.result == 8 || response.result == 111) {
			/***************************** ����ΰ�����������޸Ķ������Ĵ����߼��� ������Ϣ�����ط�3�Σ������Ϊ���������򽫴���������Ϊ����ʧ�ܣ����ٽ����ط� ��ʼ ***********************************/
			// ���ҳ�����Ϣid��Ӧ����Ϣ�ķ��ʹ������ж����Դ�����ѡ�����
			m_submit.open();
			int ih_retry = m_submit.selectIh_retry(response);
			m_submit.close();
			// ih_retry��ʼֵ�ǣ�3�����ԵĴ���
			if (ih_retry > 0) {
				m_submit.open();
				m_submit.updateResend(response);// ���³ɳ�ʼ״̬
				m_submit.close();
				m_nCount++;
			} else {
				m_submit.open();
				m_submit.update(response);// ���³�ʧ��
				m_submit.close();
				m_nCount++;
				// ������Ӧʧ�ܵ����ݽ��б���---------�������submit_cmpp������״̬���عر������յ�Submit
				// Response֮���ֱ�ӱ��ݱ���Ϣ
				if (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH
						.equalsIgnoreCase("false")) {
					// �����̬���ݿ��ؿ�����ִ�б��ݲ���
					if (BackupTableConfig.DYNAMICBACKUPTABLE
							.equalsIgnoreCase("true")) {
						Log.log("CMPPDatabaseInput.handleSubmitResponse : backup submit record !",
								0x4000000000000L);
						m_submit_backup.open();
						m_submit_backup.backup(response);
						m_submit_backup.close();
						Log.log("CMPPDatabaseInput.handleSubmitResponse : delete submit record !",
								0x4000000000000L);
						m_submit.open();
						m_submit.deletenow(response);
						m_submit.close();
					}
				}
			}
			/***************************** ����ΰ�����������޸Ķ������Ĵ����߼��� ������Ϣ�����ط�3�Σ������Ϊ���������򽫴���������Ϊ����ʧ�ܣ����ٽ����ط� ���� ***********************************/
		} else {
			m_submit.open();
			m_submit.update(response);
			if(response.result!=0){
				try{
					String access = m_submit.selectAccess(response);
					String userId = m_submit.selectUserId(access.substring(0,access.length()-2),access.substring(access.length()-2));
					
					m_submit.updateLimit(userId);
				} catch(Exception e){
					Log.log("CMPPDatabaseInput.handleSubmitResponse : update limit count !",
							0x4000000000000L);
				}
			}
			
			m_submit.close();
			m_nCount++;
			// ������Ӧ�ɹ������ݽ��б���---------�������submit_cmpp������״̬���عر������յ�Submit
			// Response֮���ֱ�ӱ��ݱ���Ϣ
			if (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH
					.equalsIgnoreCase("false")) {
				// �����̬���ݿ��ؿ�����ִ�б��ݲ���
				if (BackupTableConfig.DYNAMICBACKUPTABLE
						.equalsIgnoreCase("true")) {
					Log.log("CMPPDatabaseInput.handleSubmitResponse : backup submit record !",
							0x4000000000000L);
					m_submit_backup.open();
					m_submit_backup.backup(response);
					m_submit_backup.close();
					Log.log("CMPPDatabaseInput.handleSubmitResponse : delete submit record !",
							0x4000000000000L);
					m_submit.open();
					m_submit.deletenow(response);
					m_submit.close();
				}
			}
		}

		response.empty();
		response = null;
	}

	/**
	 * ����������в���Query��Ϣ��������CMPPQuery����
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public void handleQuery(CMPPQuery query) throws SQLException {
		m_query.open();
		m_query.insert(query);
		m_query.close();
		m_nCount++;
		query.empty();
		query = null;
	}

	/**
	 * ����������в���QueryResponse��Ϣ��������CMPPQuery���״̬(ih_process)�ֶ�
	 * 
	 * @param response
	 * @throws SQLException
	 */
	public void handleQueryResponse(CMPPQueryResponse response)
			throws SQLException {
		m_query.open();
		m_query.update(response);
		m_query.close();
		m_nCount++;
		response.empty();
		response = null;
	}

	/**
	 * ������ѭ����������л�ȡ��Ϣ�壬��������Ϣͷ����Ӧ��command_id���ö�Ӧ�Ĵ�����
	 */
	public void run() {
		try {
			Log.log("CMPPDatabaseInput(" + m_nID + ").run : thread startup !",
					0x8000000000L);
			m_nStatus = 1;
			while (isRunning()) {
				CMPPPacket packet = (CMPPPacket) m_queue.pop();
				if (packet == null) {
					Engine.nap();
					continue;
				}
				if (packet.command_id == 5)
					handleDeliver((CMPPDeliver) packet);
				// longSmsTranslator();
				else if (packet.command_id == 4) {
					handleSubmit((CMPPSubmit) packet);
				} else {
					if (packet.command_id == 0x80000004) {
						handleSubmitResponse((CMPPSubmitResponse) packet);
						continue;
					}
					if (packet.command_id == 6)
						handleQuery((CMPPQuery) packet);
					else if (packet.command_id == 0x80000006)
						handleQueryResponse((CMPPQueryResponse) packet);
				}
				packet.empty();
				packet = null;
				Engine.nap();
			}
		} catch (Exception e) {
			Log.log(e);
			Log.log("CMPPDatabaseInput(" + m_nID + ").run : unexpected exit !",
					0x2000008000000000L);
		}
		empty();
		m_nStatus = 3;
		Log.log("CMPPDatabaseInput(" + m_nID + ").run : thread stopped !",
				0x8000000000L);
	}

	public void deliverToInsert() {

		for (int j = 0; j < dmc.getCount(); j++) {
			CMPPDeliver deliverMsg = dmc.get();
			if (deliverMsg != null)
				try {
					m_deliver.insert(deliverMsg);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			else
				break;
		}
	}

}