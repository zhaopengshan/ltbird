package com.changyuan.misc.model.mapper;

import com.changyuan.misc.model.pojo.TbUser;
import com.changyuan.misc.model.pojo.TbUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbUserMapper {
    int countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    @Delete({
        "delete from tb_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into tb_user (user_id, user_name, ",
        "receive_name, mobile, ",
        "province_name, address, ",
        "email)",
        "values (#{userId,jdbcType=INTEGER}, #{userName,jdbcType=VARCHAR}, ",
        "#{receiveName,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{provinceName,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR})"
    })
    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    @Select({
        "select",
        "user_id, user_name, receive_name, mobile, province_name, address, email",
        "from tb_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    @Update({
        "update tb_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "receive_name = #{receiveName,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "province_name = #{provinceName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbUser record);
}