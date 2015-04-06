/*
 * Created on 2004-3-22
 */
package LeadTone.longMsg;

import java.util.ArrayList;

import java.util.LinkedList;

import LeadTone.Packet.CMPPPacket.CMPPDeliver;

/**
 * 
 */
public class CMPPDeliverCache
{

	
	private static CMPPDeliverCache instance = null;
	private final static LinkedList<CMPPDeliver> totalCache = new LinkedList<CMPPDeliver>();



	public synchronized static CMPPDeliverCache getInstance()
	{
		if (instance == null)
			instance = new CMPPDeliverCache();

		return instance;
	}

	
	public synchronized int add(CMPPDeliver deliverMsg)
	{
		if (deliverMsg != null)
		{
			totalCache.addLast(deliverMsg);
			return 1;
		}
		return 0;
	}


	public synchronized int batchAdd(ArrayList<CMPPDeliver> deliverMsgs)
	{

		int deliverMsgsCount = deliverMsgs.size();
		int j = 0;

		for (int i = 0; i < deliverMsgsCount; i++)
		{
			CMPPDeliver deliverMsg = deliverMsgs.get(i);

			if (deliverMsg != null)
			{
				totalCache.addLast(deliverMsg);
				j++;
			}
		}

		return j;
	}

	
	public synchronized CMPPDeliver get()
	{
		if (totalCache.size() > 0)
			return totalCache.removeFirst();
		else		    
			return null;
	}
	

	public int getCount()
	{
		return totalCache.size();
	}
}
