package com.transeesoft.wage.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_action")
public class RoleAction implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "role_id")
  private Integer roleId;

  @Column(name = "action_id")
  private Integer actionId;

  public RoleAction() {
  }

  public RoleAction(Integer roleId, Integer actionId) {
    this.roleId = roleId;
    this.actionId = actionId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Integer getActionId() {
    return actionId;
  }

  public void setActionId(Integer actionId) {
    this.actionId = actionId;
  }
}
