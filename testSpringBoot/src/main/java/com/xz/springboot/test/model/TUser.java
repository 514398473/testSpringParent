package com.xz.springboot.test.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 
 * @since 2018-07-24
 */
@TableName("t_user")
public class TUser extends Model<TUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userid", type = IdType.AUTO)
    private Integer userid;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String userpwd;
    /**
     * 电话号
     */
    private String telphone;
    /**
     * 0 管理员 1普通用户
     */
    private Integer usertype;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    @Override
    protected Serializable pkVal() {
        return this.userid;
    }

    @Override
    public String toString() {
        return "TUser{" +
        ", userid=" + userid +
        ", realname=" + realname +
        ", idcard=" + idcard +
        ", username=" + username +
        ", userpwd=" + userpwd +
        ", telphone=" + telphone +
        ", usertype=" + usertype +
        "}";
    }
}
