package com.leadtone.mas.admin.openaccount.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantSmsMmsLimit;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVipVO;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.openaccount.service.OpenAccountIService;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.security.service.UserService;

/**
 * OpenAccountAction
 *	1:省系统管理员开销户；2：地市管理员开销户3、企业管理员开销户4、企业开销户
 * @author wangyu1
 */
@ParentPackage("json-default")
@Namespace(value = "/openAccountAction")
public class OpenAccountAction extends BaseAction {
	
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(OpenAccountAction.class);
    @Resource
    private OpenAccountIService openAccountIService;

    private Set<String> parasSet = new HashSet<String>();
    private Map<String, Object> parasMap = new HashMap<String, Object>();
    private MbnMerchantVip mbnMerchantVip;
    private UserVO userVO;
    @Resource 
    private UserService userService;
    @Resource
    private MbnMerchantVipIService mbnMerchantVipIService;
    @Resource
    private UsersDao usersDao;
    private MbnMerchantVipVO mbnMerchantVipVO;
    private String merchantIds;
    /**
     * 本方法仅用于测试使用
     */
    private void initParaForTest() {
        /*Long id = Long.parseLong(this.getRequest().getParameter("userId"));//请输入ID
        Users users = new Users();
        users = usersDao.findById(id);
        users.setUserType(2);//1:省系统管理员；2：地市管理员3、企业管理员4、企业用户',
        List list=new ArrayList();
        list.add(users);
        this.getSession().setAttribute("loginUser", list);
        mbnMerchantVip.setCreateTime(new Date());
        String limitPerSecond = this.getRequest().getParameter("limitPerSecond");
        String merchantSign = this.getRequest().getParameter("merchantSign");
        this.getRequest().setAttribute("limitPerSecond", limitPerSecond);
        this.getRequest().setAttribute("merchantSign", merchantSign);*/
    }
    /**
     * 验证开户参数合法性
     * @return
     */
    private boolean validatePara(int type){
    	entityMap=new HashMap<String,Object>();
    	if(mbnMerchantVip ==null ||mbnMerchantVip.getName()==null){
    		entityMap.put("flag",false);
    		entityMap.put("resultMsg","企业信息添加失败");
    		return false;
    	}
    	if(userVO==null||userVO.getAccount()==null||userVO.getMobile()==null){
    		entityMap.put("flag",false);
    		entityMap.put("resultMsg","企业管理员信息添加失败");
    		return false;
    	}
    	if(type==1){
    		UserVO uv=this.userService.getUserByAccount(userVO.getAccount());
    		if(uv!=null){
    			entityMap.put("flag",false);
        		entityMap.put("resultMsg","企业开户失败，该企业管理员账号已被使用");
        		return false;
    		}
    		MbnMerchantVip mmv=this.mbnMerchantVipIService.loadByName(mbnMerchantVip.getName());
    		if(mmv!=null){
    			entityMap.put("flag",false);
        		entityMap.put("resultMsg","企业开户失败，该企业名称已被使用");
        		return false;
    		}
    		return true;
    	}else if(type==2){
    		if(mbnMerchantVip.getMerchantPin()==null){
    			entityMap.put("flag",false);
        		entityMap.put("resultMsg","企业变更失败，该企业不存在");
        		return false;
    		}
    		MbnMerchantVip mmv=this.mbnMerchantVipIService.loadByMerchantPin(mbnMerchantVip.getMerchantPin());
    		if(mmv==null){
    			entityMap.put("flag",false);
        		entityMap.put("resultMsg","企业变更失败，该企业不存在");
        		return false;
    		}
    		UserVO uv=this.userService.getUserByAccount(userVO.getAccount());
    		if(uv==null){
    			entityMap.put("flag",false);
        		entityMap.put("resultMsg","企业变更失败，该企业管理员账号不存在");
        		return false;
    		}else{
    			if(uv.getMerchantPin().longValue()!=mbnMerchantVip.getMerchantPin().longValue()){
    				entityMap.put("flag",false);
            		entityMap.put("resultMsg","企业信变更失败，该企业管理员账号与企业信息不匹配");
            		return false;
    			}
    		}
    		mmv=this.mbnMerchantVipIService.loadByName(mbnMerchantVip.getName());
    		if(mmv!=null ){
    			if(mmv.getMerchantPin().longValue()!=mbnMerchantVip.getMerchantPin().longValue()){
    				entityMap.put("flag",false);
            		entityMap.put("resultMsg","企业信变更失败，该企业名称已被使用");
            		return false;
    			}
    		}
    		return true;
    	}else{
    		return false;
    	}
    }

    /**
     * 开户
     *
     * @return
     */
    @Action(value = "createMerchant", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String createMerchant() {
        if( !validatePara(1)){
            return SUCCESS;
        }
        operationMerchant(1);
        return SUCCESS;
    }

    /**
     * 变更
     *
     * @return
     */
    @Action(value = "updateMerchant", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String updateMerchant() {
         if( !validatePara(2)){
            return SUCCESS;
        }
        operationMerchant(2);
        return SUCCESS;
    }

    /**
     * 暂停
     *
     * @return
     */
    @Action(value = "delayMerchant", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String delayMerchant() {
         if( !validatePara(3)){
            return SUCCESS;
        }
        operationMerchant(3);
        return SUCCESS;
    }

    /**
     * 销户
     *
     * @return
     */
    @Action(value = "logoutMerchant", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String logoutMerchant() {
    	entityMap=new HashMap<String,Object>();
    	String [] temp=merchantIds.split(",");
    	List<Long> pins=new ArrayList<Long>();
    	for(int i=0;i<temp.length;i++){
    		pins.add(Long.valueOf(temp[i]));
    	}
    	if(pins.size()>0){
    		boolean flag=this.userService.logoutMerchants(pins);
    		if(flag){
    			entityMap.put("flag", true);
    			entityMap.put("resultMsg", "销户成功");
    			return SUCCESS;
    		}else{
    			entityMap.put("flag", false);
    			entityMap.put("resultMsg", "销户失败");
    			return SUCCESS;
    		}
    	}else{
    		entityMap.put("flag", false);
			entityMap.put("resultMsg", "没有要注销的企业");
			return SUCCESS;
    	}
    }

    /**
     * 从配置文件组读取参数列表
     */
    private void initParasSet() {
        parasSet.add("mbnMerchantVip");
        parasSet.add("merchantLimit");
        parasSet.add("users");
        parasSet.add("limitPerSecond");
        parasSet.add("merchantSign");
        parasSet.add("operater");
    }

    /**
     * 初始化开销户所需要的参数
     */
    private void initParasMap(int sign) {
		//    	List listOpe =((ArrayList) this.getSession().getAttribute("loginUser"));
		//    	Users operater=null;
		//    	if(listOpe!=null){
		//    		operater=(Users)listOpe.get(0);
		//    	}
    	Users operater = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
        //留言特殊内容的特殊处理
        Long merchantPin = null;
        if (sign == 1) {
            merchantPin = PinGen.getMerchantPin();
        }else if(sign==2){
             merchantPin=mbnMerchantVip.getMerchantPin();
        }
        for (Iterator<String> it = parasSet.iterator(); it.hasNext();) {
            String key = it.next();
            if("operater".equals(key)){
            	parasMap.put(key, operater);
            }else if ("users".equals(key)) {
                if (sign == 1) {
                    userVO.setMerchantPin(merchantPin);
                    parasMap.put("users", userVO);
                }else if(sign ==2){
                    userVO.setMerchantPin(merchantPin);
                    parasMap.put("users", userVO);
                }
            } else if ("mbnMerchantVip".equals(key)) {
                if (sign == 1) {
                    mbnMerchantVip.setMerchantPin(merchantPin);
                    mbnMerchantVip.setGroupCode(String.valueOf(merchantPin));
                    mbnMerchantVip.setSmsState("B");
                    mbnMerchantVip.setMmsState("B");
                    mbnMerchantVip.setCreateTime(Calendar.getInstance().getTime());
                    
                    //暂时给出默认值
                    mbnMerchantVip.setProvince("未知");
                    mbnMerchantVip.setPlatform("未知");
                    
                    parasMap.put("mbnMerchantVip", mbnMerchantVip);
                } else if (sign == 2) {
                    mbnMerchantVip.setMerchantPin(merchantPin);
                    mbnMerchantVip.setGroupCode(String.valueOf(merchantPin));
                    mbnMerchantVip.setLastUpdateTime(Calendar.getInstance().getTime());       
                    
                    //暂时给出默认值
                    mbnMerchantVip.setProvince("未知");
                    mbnMerchantVip.setPlatform("未知");
                    
                    parasMap.put("mbnMerchantVip", mbnMerchantVip);
                }
            } else {
                parasMap.put(key, this.getRequest().getParameter(key));
            }
        }
    }

    /**
     * 补全没有初始化到的信息
     */
    private void fillParasMap() {
        //补全必须有map中为空的值
        long pr = 5L;
        try {
            pr = Long.parseLong((String) parasMap.get("limitPerSecond"));
        } catch (NumberFormatException e) {
            pr = 5L;
        } finally {
            parasMap.put("limitPerSecond", String.valueOf(pr).trim());//每秒发送速度默认为100条每秒
        }
        if (parasMap.get("merchantLimit") == null) {
            MbnMerchantSmsMmsLimit mmsl = new MbnMerchantSmsMmsLimit();
            mmsl.setMerchantPin(mbnMerchantVip.getMerchantPin());
            mmsl.setModifyTime(Calendar.getInstance().getTime());
            int postRate = 5;
            int speedPerSecond = 5;
            try {
                postRate = Integer.parseInt((String) parasMap.get("limitPerSecond"));
                speedPerSecond = postRate;
            } catch (NumberFormatException e) {
                speedPerSecond = 5;
            }
            int speedPerHour = speedPerSecond * 60 * 60;
            int speedPerDay = speedPerHour * 24;
            int speedPerMon = speedPerDay * 30;
            mmsl.setSmsGatewayDaily(speedPerDay);
            mmsl.setSmsGatewayMonth(speedPerMon);
            if (speedPerHour > 100) {
                speedPerHour = 100;
            }
            mmsl.setSmsTdHour(speedPerHour);
            if (speedPerDay > 1200) {
                speedPerDay = 1200;
            }
            mmsl.setSmsTdDaily(speedPerDay);
            parasMap.put("merchantLimit", mmsl);
        }
        if (parasMap.get("merchantSign") == null || parasMap.get("merchantSign") == "") {
            parasMap.put("merchantSign", mbnMerchantVip.getName());//企业强制签名默认为了企业名称
        }
        /*if (mbnMerchantVip.getSmsAccessNumber() == null || mbnMerchantVip.getSmsAccessNumber() == "") {
            mbnMerchantVip.setSmsAccessNumber("10000");//短信特服号默认为了10000
        }*/
    }

    /**
     * 具体的实现开销户方法
     *
     * @param sign
     * @return
     */
    private void operationMerchant(int sign) {
        //用于测试开始
        this.initParaForTest();
       /* Users users = (Users) ((ArrayList) this.getSession().getAttribute("loginUser")).get(0);
        boolean flag=false;
        if(sign==1){
            flag=openAccountIService.checkMerchantExist(users);
            if(flag){
                log.info("添加企业已存在");
                
            }
        }else if(sign==2){
            flag=openAccountIService.checkMerchantExist(users);
            if(!flag){
                log.info("更新企业不存在");
                
            }
        }*/
       //用于测试结束
        initParasSet();
        initParasMap(sign);
        fillParasMap();
        openAccountIService.openAccount(sign, parasMap, entityMap);
    }

    /*
     * 准备更新企业及企业参数	showParas
     //1\查出商户 merchantPin
     * 2查出users merchantPin 
     * 3查出企业签名 merchantPin,企业强制签名 ok
     * 4查出企业发送速度 merchantPin
     @Action(value = "preUpdateCorpPara", results = { @Result(name = SUCCESS, location = "/RegionAdmin/city/preUpdateCorpPara.jsp") })
     public String preUpdateCorpPara() {
     long i = masCorp.getId();
     masCorp = masCorpAdminService.getMasCorpById(i);//企业对象
     findCorpParaList();
     for(Iterator<String> it=corpParaList.iterator();it.hasNext();){
     String str=it.next();  
     MasCorpPara mcp=masCorpAdminService.getCorpPara(i, str);
     if(mcp !=null){
     request.setAttribute(str,mcp.getValue());
     }else{
     request.setAttribute(str,"");
     }
     }
     return SUCCESS;
     }*/
    public OpenAccountIService getOpenAccountIService() {
        return openAccountIService;
    }

    public MbnMerchantVip getMbnMerchantVip() {
        return mbnMerchantVip;
    }

    public void setMbnMerchantVip(MbnMerchantVip mbnMerchantVip) {
        this.mbnMerchantVip = mbnMerchantVip;
    }

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	public UserService getUserService() {
		return userService;
	}
	public MbnMerchantVipIService getMbnMerchantVipIService() {
		return mbnMerchantVipIService;
	}
	public MbnMerchantVipVO getMbnMerchantVipVO() {
		return mbnMerchantVipVO;
	}
	public String getMerchantIds() {
		return merchantIds;
	}
	public void setMerchantIds(String merchantIds) {
		this.merchantIds = merchantIds;
	}
	public UsersDao getUsersDao() {
		return usersDao;
	}
	public void setMbnMerchantVipVO(MbnMerchantVipVO mbnMerchantVipVO) {
		this.mbnMerchantVipVO = mbnMerchantVipVO;
	}
    
}
