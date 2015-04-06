package LeadTone.longMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnFinishRecvBeanMap
{
  private static Map<Integer, List<LongSmsBean>> unFinishMap = new HashMap();

  public static LongSmsBean contentHandler(int referNum, LongSmsBean bean)
  {
    add(referNum, bean);

    String strContent = getRecvContect(referNum);

    if (strContent != null) {
      unFinishMap.remove(Integer.valueOf(referNum));
      bean.setMsgcontent(strContent);

      return bean;
    }

    return null;
  }

  /**
   * 根据短信的随机标志符，对应的加入到map集合
   * @param referNum
   * @param bean
   */
  public static void add(int referNum, LongSmsBean bean)
  {
    List beanList = (List)unFinishMap.get(Integer.valueOf(referNum));
    if (beanList == null)
    {
      beanList = new ArrayList();

      LongSmsBean recvBean = new LongSmsBean();
      recvBean = bean;
      beanList.add(recvBean);

      unFinishMap.put(Integer.valueOf(referNum), beanList);
    } else {
      for (int i = 0; i < beanList.size(); i++)
      {
        if (((LongSmsBean)beanList.get(i)).getCurrent() == bean.getCurrent())
          return;
      }
      beanList.add(bean);
    }
  }

  public static boolean finish(int referNum)
  {
    List beanList = (List)unFinishMap.get(Integer.valueOf(referNum));
    int nCount = beanList.size();

    return ((LongSmsBean)beanList.get(0)).getTotal() == nCount;
  }

  /**
   * 全部的子短信都收到，拼凑为长短信的内容
   * @param referNum
   * @return
   */
  public static String getRecvContect(int referNum)
  {
    List beanList = (List)unFinishMap.get(Integer.valueOf(referNum));

    if (beanList == null) {
      return null;
    }
    String strContent = null;
    int nCount = beanList.size();

    if (((LongSmsBean)beanList.get(0)).getTotal() == nCount)
    {
      strContent = "";
      int index = 1;

      while (index <= nCount)
      {
        for (int i = 0; i < beanList.size(); i++)
        {
          if (((LongSmsBean)beanList.get(i)).getCurrent() == index) {
            strContent = strContent + ((LongSmsBean)beanList.get(i)).getMsgcontent();
            index++;

            break;
          }
        }
      }

      unFinishMap.remove(Integer.valueOf(referNum));
    }

    return strContent;
  }

  public static void remove(int referNum)
  {
    unFinishMap.remove(Integer.valueOf(referNum));
  }
}