package LeadTone.longMsg;

import java.util.Date;

public class LongSmsBean
{
  private String moId;
  private Integer biz;
  private Date loadtime;
  private Date recieveTime;
  private Integer seq1;
  private Integer seq2;
  private Integer seq3;
  private String caller;
  private String called;
  private Integer msgformat;
  private String msgcontent;
  private Integer tppid;
  private Integer tpuid;
  private String reserve;
  private int referNum;
  private int total;
  private int current;

  public String getMoId() {
	return moId;
}
public void setMoId(String moId) {
	this.moId = moId;
}
public Integer getBiz() {
    return this.biz;
  }
  public void setBiz(Integer biz) {
    this.biz = biz;
  }
  public Date getLoadtime() {
    return this.loadtime;
  }
  public void setLoadtime(Date loadtime) {
    this.loadtime = loadtime;
  }
  public Date getRecieveTime() {
    return this.recieveTime;
  }
  public void setRecieveTime(Date recieveTime) {
    this.recieveTime = recieveTime;
  }
  public Integer getSeq1() {
    return this.seq1;
  }
  public void setSeq1(Integer seq1) {
    this.seq1 = seq1;
  }
  public Integer getSeq2() {
    return this.seq2;
  }
  public void setSeq2(Integer seq2) {
    this.seq2 = seq2;
  }
  public Integer getSeq3() {
    return this.seq3;
  }
  public void setSeq3(Integer seq3) {
    this.seq3 = seq3;
  }
  public String getCaller() {
    return this.caller;
  }
  public void setCaller(String caller) {
    this.caller = caller;
  }
  public String getCalled() {
    return this.called;
  }
  public void setCalled(String called) {
    this.called = called;
  }
  public Integer getMsgformat() {
    return this.msgformat;
  }
  public void setMsgformat(Integer msgformat) {
    this.msgformat = msgformat;
  }
  public String getMsgcontent() {
    return this.msgcontent;
  }
  public void setMsgcontent(String msgcontent) {
    this.msgcontent = msgcontent;
  }
  public Integer getTppid() {
    return this.tppid;
  }
  public void setTppid(Integer tppid) {
    this.tppid = tppid;
  }
  public Integer getTpuid() {
    return this.tpuid;
  }
  public void setTpuid(Integer tpuid) {
    this.tpuid = tpuid;
  }
  public String getReserve() {
    return this.reserve;
  }
  public void setReserve(String reserve) {
    this.reserve = reserve;
  }
  public int getReferNum() {
    return this.referNum;
  }
  public void setReferNum(int referNum) {
    this.referNum = referNum;
  }
  public int getTotal() {
    return this.total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getCurrent() {
    return this.current;
  }
  public void setCurrent(int current) {
    this.current = current;
  }
}