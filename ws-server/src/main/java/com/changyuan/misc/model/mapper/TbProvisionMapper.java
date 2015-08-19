package com.changyuan.misc.model.mapper;

import com.changyuan.misc.model.pojo.TbProvision;
import com.changyuan.misc.model.pojo.TbProvisionExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface TbProvisionMapper {
    int countByExample(TbProvisionExample example);

    int deleteByExample(TbProvisionExample example);

    @Delete({
        "delete from tb_provision",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_provision (platUserId, MsgType, ",
        "MSISDN_U, MSISDN_U_UserIDType, ",
        "MSISDN_U_PseudoCode, MSISDN_D, ",
        "MSISDN_D_UserIDType, MSISDN_D_PseudoCode, ",
        "ActionID, ActionReasonID, ",
        "Pno, SPServiceID, ",
        "SPID, Version, createtime, ",
        "status, TransactionID, ",
        "LinkID, FeatureStr, ",
        "Send_DeviceType, Send_DeviceID, ",
        "Dest_DeviceType, Dest_DeviceID)",
        "values (#{platuserid,jdbcType=INTEGER}, #{msgtype,jdbcType=VARCHAR}, ",
        "#{msisdnU,jdbcType=VARCHAR}, #{msisdnUUseridtype,jdbcType=INTEGER}, ",
        "#{msisdnUPseudocode,jdbcType=VARCHAR}, #{msisdnD,jdbcType=VARCHAR}, ",
        "#{msisdnDUseridtype,jdbcType=INTEGER}, #{msisdnDPseudocode,jdbcType=VARCHAR}, ",
        "#{actionid,jdbcType=INTEGER}, #{actionreasonid,jdbcType=INTEGER}, ",
        "#{pno,jdbcType=VARCHAR}, #{spserviceid,jdbcType=VARCHAR}, ",
        "#{spid,jdbcType=VARCHAR}, #{version,jdbcType=VARCHAR}, #{createtime,jdbcType=TIMESTAMP}, ",
        "#{status,jdbcType=INTEGER}, #{transactionid,jdbcType=VARCHAR}, ",
        "#{linkid,jdbcType=VARCHAR}, #{featurestr,jdbcType=VARCHAR}, ",
        "#{sendDevicetype,jdbcType=INTEGER}, #{sendDeviceid,jdbcType=VARCHAR}, ",
        "#{destDevicetype,jdbcType=INTEGER}, #{destDeviceid,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(TbProvision record);

    int insertSelective(TbProvision record);

    List<TbProvision> selectByExample(TbProvisionExample example);

    @Select({
        "select",
        "id, platUserId, MsgType, MSISDN_U, MSISDN_U_UserIDType, MSISDN_U_PseudoCode, ",
        "MSISDN_D, MSISDN_D_UserIDType, MSISDN_D_PseudoCode, ActionID, ActionReasonID, ",
        "Pno, SPServiceID, SPID, Version, createtime, status, TransactionID, LinkID, ",
        "FeatureStr, Send_DeviceType, Send_DeviceID, Dest_DeviceType, Dest_DeviceID",
        "from tb_provision",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbProvision selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProvision record, @Param("example") TbProvisionExample example);

    int updateByExample(@Param("record") TbProvision record, @Param("example") TbProvisionExample example);

    int updateByPrimaryKeySelective(TbProvision record);

    @Update({
        "update tb_provision",
        "set platUserId = #{platuserid,jdbcType=INTEGER},",
          "MsgType = #{msgtype,jdbcType=VARCHAR},",
          "MSISDN_U = #{msisdnU,jdbcType=VARCHAR},",
          "MSISDN_U_UserIDType = #{msisdnUUseridtype,jdbcType=INTEGER},",
          "MSISDN_U_PseudoCode = #{msisdnUPseudocode,jdbcType=VARCHAR},",
          "MSISDN_D = #{msisdnD,jdbcType=VARCHAR},",
          "MSISDN_D_UserIDType = #{msisdnDUseridtype,jdbcType=INTEGER},",
          "MSISDN_D_PseudoCode = #{msisdnDPseudocode,jdbcType=VARCHAR},",
          "ActionID = #{actionid,jdbcType=INTEGER},",
          "ActionReasonID = #{actionreasonid,jdbcType=INTEGER},",
          "Pno = #{pno,jdbcType=VARCHAR},",
          "SPServiceID = #{spserviceid,jdbcType=VARCHAR},",
          "SPID = #{spid,jdbcType=VARCHAR},",
          "Version = #{version,jdbcType=VARCHAR},",
          "createtime = #{createtime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER},",
          "TransactionID = #{transactionid,jdbcType=VARCHAR},",
          "LinkID = #{linkid,jdbcType=VARCHAR},",
          "FeatureStr = #{featurestr,jdbcType=VARCHAR},",
          "Send_DeviceType = #{sendDevicetype,jdbcType=INTEGER},",
          "Send_DeviceID = #{sendDeviceid,jdbcType=VARCHAR},",
          "Dest_DeviceType = #{destDevicetype,jdbcType=INTEGER},",
          "Dest_DeviceID = #{destDeviceid,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbProvision record);
}