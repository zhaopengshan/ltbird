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
 * 输入处理线程，从输入消息队列中获取消息完成数据持久化或状态更新操作
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
	 * 从输入队列中捕获Deliver消息插入CMPPDeliver表中， 如果为用户上行信息则根据用户发送上行内容调用相应下发逻辑，
	 * 如果为状态报告则更新对应的Submit表中的下发记录的最终状态final_result, 并作数据的备份转移操作
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

		// 如果registered_delivery=0表示为用户上行
		if (deliver.registered_delivery == 0) {

			// 如果deliver下行开关开启则将收到的deliver消息插入CMPPDeliver表,
			// 如果deliver下行开关关闭则将收到的deliver消息插入CMPPDeliverBackup表
			if (CMPPDeliverDatabase.m_output_switch) {
				// 记录用户上行信息到CMPPDeliver表，等待轮循回复response
				/*
				 * m_deliver.open(); m_deliver.insert(deliver);
				 * m_deliver.close();
				 */

				// 处理上行长短信问题，屏蔽以上代码。20120920
				m_deliver.open();
				Log.log("===========================0: result" +deliver.msg_content[0],0x4000000000000L);
				Log.log("===========================1: result" +deliver.msg_content[1],0x4000000000000L);
				// 长短信的首3位字符16进制为【05 00 03】，单字节为【5 0 3】，固判断首3位
				if (deliver.msg_content[0] == 5 
						&& deliver.msg_content[1] == 0
						&& deliver.msg_content[2] == 3) {
					// 合并长短信
					MasCmpp2SmsReceive.longSmsHandler(deliver);
					// 入库
					deliverToInsert();
				} else {
					// 普通短信直接入库,根据上行通知规则，将入库字符串改为明文显示，所以，此处将字符集改为GB2312。modified by chenguoliang
					//ucs2格式才转换。
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
				// 记录用户上行信息到CMPPDeliver表
				/*
				 * m_deliver.open(); m_deliver.insert(deliver);
				 * m_deliver.close();
				 */

				// 处理上行长短信问题，屏蔽以上代码。20120920
				m_deliver.open();
				Log.log("===========================0: result"+deliver.msg_content[0],0x4000000000000L);
				Log.log("===========================1: result"+deliver.msg_content[1],0x4000000000000L);
				// 长短信的首3位字符16进制为【05 00 03】，单字节为【5 0 3】，固判断首3位
				if (deliver.msg_content[0] == 5 
						&& deliver.msg_content[1] == 0 
						&& deliver.msg_content[2] == 3) {

					// 合并长短信
					MasCmpp2SmsReceive.longSmsHandler(deliver);
					// 入库
					deliverToInsert();
				} else {
					// 普通短信直接入库
					//ucs2格式才转换。
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

				// 备份deliver消息到CMPPDeliverBackup表
				m_deliver_backup.open();
				m_deliver_backup.backup(deliver);
				m_deliver_backup.close();

				// 删除CMPPDeliver表deliver消息
				m_deliver.open();
				m_deliver.deletenow(deliver);
				m_deliver.close();
			}

			// 如果处理用户上行机制开启 则进入处理逻辑
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
		// 否则为状态报告,继续处理更新cmpp_submit表final_result工作
		else {
			// 如果更新submit_cmpp表最终状态开关开启则执行更新操作
			if (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH
					.equalsIgnoreCase("true")) {
				Log.log("CMPPDatabaseInput.handleDeliver : to update submit table's final_result feild !",
						0x4000000000000L);
				m_submit.open();
				m_submit.update(deliver);
				m_submit.close();
			}

			// 如果动态备份开关开启则执行备份操作
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
	 * 从输入队列中捕获Submit消息并插入CMPPSubmit表中
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
	 * 从输入队列中捕获SubmitResponse消息，并更新CMPPSubmit表中对应的状态(process)字段
	 * 
	 * @param response
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	public void handleSubmitResponse(CMPPSubmitResponse response)
			throws SQLException, UnsupportedEncodingException {

		// 如果submit response的发送结果为8或111，表示ISMG消息队列满，此条消息进行重发
		if (response.result == 8 || response.result == 111) {
			/***************************** 宫宇伟按福建问题修改队列满的处理逻辑： 此条消息进行重发3次，如果仍为队列满，则将此条短信置为发送失败，不再进行重发 开始 ***********************************/
			// 查找出该消息id对应的消息的发送次数，判断重试次数再选择操作
			m_submit.open();
			int ih_retry = m_submit.selectIh_retry(response);
			m_submit.close();
			// ih_retry初始值是：3，重试的次数
			if (ih_retry > 0) {
				m_submit.open();
				m_submit.updateResend(response);// 更新成初始状态
				m_submit.close();
				m_nCount++;
			} else {
				m_submit.open();
				m_submit.update(response);// 更新成失败
				m_submit.close();
				m_nCount++;
				// 对于响应失败的数据进行备份---------如果更新submit_cmpp表最终状态开关关闭则在收到Submit
				// Response之后就直接备份表消息
				if (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH
						.equalsIgnoreCase("false")) {
					// 如果动态备份开关开启则执行备份操作
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
			/***************************** 宫宇伟按福建问题修改队列满的处理逻辑： 此条消息进行重发3次，如果仍为队列满，则将此条短信置为发送失败，不再进行重发 结束 ***********************************/
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
			// 对于响应成功的数据进行备份---------如果更新submit_cmpp表最终状态开关关闭则在收到Submit
			// Response之后就直接备份表消息
			if (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH
					.equalsIgnoreCase("false")) {
				// 如果动态备份开关开启则执行备份操作
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
	 * 从输入队列中捕获Query消息，并插入CMPPQuery表中
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
	 * 从输入队列中捕获QueryResponse消息，并更新CMPPQuery表的状态(ih_process)字段
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
	 * 不断伦循从输入队列中获取消息体，并根据消息头中相应的command_id调用对应的处理方法
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