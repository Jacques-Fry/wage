package com.transeesoft.wage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import com.transeesoft.wage.exception.CommonException;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.transeesoft.wage.dao.ActionDao;
import com.transeesoft.wage.pojo.Action;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ActionService {

  @Autowired
  private ActionDao actionDao;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private RoleActionService roleActionService;


  /**
   * 根据名称查询
   */
  public Action findByName(String name) {
    return actionDao.findByName(name);
  }

  /**
   * 查询多个功能
   */
  public List<Action> findMore(Integer[] ids) {
    return actionDao.findByIdIn(ids);
  }

  /**
   * 是否存在该功能
   */
  public boolean isExists(Integer id) {
    return actionDao.countById(id) > 0;
  }


  /**
   * 条件查询+分页
   *
   * @param whereMap
   * @param page
   * @param size
   * @return
   */
  public Result findSearch(Map whereMap, int page, int size) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("查询功能", new Integer(claims.getId()));

    if (page <= 0) return new Result(true, StatusCode.PARAMERROR, "页数必须大于0");
    if (size <= 0) return new Result(true, StatusCode.PARAMERROR, "每页个数必须大于0");

    Specification<Action> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(page - 1, size);
    Page<Action> pageList = actionDao.findAll(specification, pageRequest);
    return new Result(true, StatusCode.OK, "查询成功", new PageResult<Action>(pageList.getTotalElements(), pageList.getContent()));

  }

  /**
   * 添加功能
   *
   * @param action
   */
  public Result add(Action action) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("添加功能", new Integer(claims.getId()));

    if (findByName(action.getName()) != null) {
      throw new CommonException(StatusCode.PARAMERROR, "该功能已存在");
    }

    actionDao.save(action);

    return new Result(true, StatusCode.OK, "添加成功");
  }

  /**
   * 修改功能
   *
   * @param action
   */
  public Result update(Action action) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("修改功能", new Integer(claims.getId()));

    if (findByName(action.getName()) != null) {
      throw new CommonException(StatusCode.PARAMERROR, "该功能已存在");
    }

    actionDao.save(action);
    return new Result(true, StatusCode.OK, "修改成功");
  }

  /**
   * 删除功能
   *
   * @param id
   */
  public Result deleteById(Integer id) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }
    //验证权限
    roleActionService.verifyAction("删除功能", new Integer(claims.getId()));
    actionDao.deleteById(id);
    return new Result(true, StatusCode.OK, "删除成功");
  }

  /**
   * 动态条件构建
   *
   * @param searchMap
   * @return
   */
  private Specification<Action> createSpecification(Map searchMap) {

    return new Specification<Action>() {

      @Override
      public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<Predicate>();

        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

      }
    };

  }

}
