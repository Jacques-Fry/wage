package com.transeesoft.wage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;//用户ID
  private String username;//员工姓名
  private String tel;//员工电话
  private String password;//员工密码
  private Integer roleId;//角色ID
  private String status;//账号状态(0.正常,1.冻结)
  private Date createTime;//创建时间

  @Transient
  private String roleName;

  public User() {
  }


  @Override
  public String toString() {
    return "{\"User\":{"
        + "\"id\":"
        + id
        + ",\"username\":\""
        + username + '\"'
        + ",\"tel\":\""
        + tel + '\"'
        + ",\"password\":\""
        + password + '\"'
        + ",\"roleId\":"
        + roleId
        + ",\"status\":\""
        + status + '\"'
        + ",\"createTime\":\""
        + createTime + '\"'
        + ",\"roleName\":\""
        + roleName + '\"'
        + "}}";

  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public User(String username, String tel, String password, Integer roleId, String status, Date createTime) {
    this.username = username;
    this.tel = tel;
    this.password = password;
    this.roleId = roleId;
    this.status = status;
    this.createTime = createTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
