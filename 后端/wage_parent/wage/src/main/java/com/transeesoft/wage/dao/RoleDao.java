package com.transeesoft.wage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.transeesoft.wage.pojo.Role;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RoleDao extends JpaRepository<Role,Integer>,JpaSpecificationExecutor<Role>{

  Integer countById(Integer id);

  Integer countByIdAndTag(Integer id,String tag);

  Role findByTag(String tag);

  @Query(nativeQuery = true,value = "select id from role")
  Integer[] findAllRoleId();
}
