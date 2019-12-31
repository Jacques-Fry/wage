package com.transeesoft.wage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.transeesoft.wage.pojo.Action;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ActionDao extends JpaRepository<Action,Integer>,JpaSpecificationExecutor<Action>{

  Action findByName(String name);

  List<Action> findByIdIn(Integer[] ids);

  Integer countById(Integer id);

}
