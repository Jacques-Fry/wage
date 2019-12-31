package com.transeesoft.wage.dao;

import com.transeesoft.wage.pojo.RoleAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Jack_YD
 * @create 2019/12/11 17:35
 */
public interface RoleActionDao extends JpaRepository<RoleAction, Integer>, JpaSpecificationExecutor<RoleAction> {

  Integer countByRoleIdAndActionId(Integer roleId, Integer actionId);

  @Query(nativeQuery = true, value = "SELECT action_id FROM role_action where role_id = ?1")
  Integer[] findActionByRoleIdIn(Integer roleIds);

  @Query(nativeQuery = true, value = "DELETE from role_action where role_id=?1 and action_id=?2")
  void delByRoleIdAndActionId(Integer roleId, Integer actionId);
}
