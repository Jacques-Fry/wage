package com.transeesoft.wage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.transeesoft.wage.exception.CommonException;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.transeesoft.wage.dao.RoleDao;
import com.transeesoft.wage.pojo.Role;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class RoleService {

  @Autowired
  private RoleDao roleDao;
  @Autowired
  private UserService userService;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private RoleActionService roleActionService;

  /**
   * 查询所有角色的ID
   */
  public Integer[] findAllRoleId() {
    return roleDao.findAllRoleId();
  }

  /**
   * 根据标记查询角色
   */
  public Role findByTag(String tag) {
    return roleDao.findByTag(tag);
  }

  /**
   * 是否存在该角色
   */
  public boolean isExists(Integer id) {
    return roleDao.countById(id) > 0;
  }

  /**
   * 验证是否是超级管理员
   */
  public boolean isSystem(Integer id) {
    return roleDao.countByIdAndTag(id, "system") > 0;
  }

  /**
   * 验证是否是管理员
   */
  public boolean isAdmin(Integer id) {
    return roleDao.countByIdAndTag(id, "admin") > 0;
  }
  /**
   * 验证是否是普通用户
   */
  public boolean isUser(Integer id) {
    return roleDao.countByIdAndTag(id, "user") > 0;
  }



  /**
   * 查询全部角色
   *
   * @return
   */
  public Result searchAll() {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    return new Result(true, StatusCode.OK, "查询成功", findAll());
  }

  public List<Role> findAll() {
    return roleDao.findAll();
  }


  /**
   * 条件查询+分页
   *
   * @param whereMap
   * @param page
   * @param size
   * @return
   */
  public Page<Role> findSearch(Map whereMap, int page, int size) {
    Specification<Role> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(page - 1, size);
    return roleDao.findAll(specification, pageRequest);
  }


  /**
   * 根据ID查询实体
   *
   * @param id
   * @return
   */
  public Role getOne(Integer id) {
    return roleDao.getOne(id);
  }

  /**
   * 将某个用户升级为管理员
   */
  public Result userUpgradeAdmin( Integer userId ) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }
    roleActionService.verifyAction("授权用户为管理员", new Integer(claims.getId()));

    if (userId == null || userId == 0)
      return new Result(false, StatusCode.PARAMERROR, "参数错误");

    if (userService.countById(userId) == 0)
      return new Result(false, StatusCode.PARAMERROR, "此用户不存在");

    Role role = findByTag("admin");
    if (role == null)
      throw new CommonException(StatusCode.ERROR, "管理员角色不存在");

    userService.updateRoleId(userId, role.getId());
    return new Result(true, StatusCode.OK, "此用户已被升级为管理员");

  }

  /**
   * 将某个管理员降级为用户
   */
  public Result adminDemotionUser(Integer userId) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    roleActionService.verifyAction("取消管理员授权", new Integer(claims.getId()));

    if (userId == null || userId == 0)
      return new Result(false, StatusCode.PARAMERROR, "参数错误");

    if (userService.countById(userId) == 0)
      return new Result(false, StatusCode.PARAMERROR, "此用户不存在");

    Role role = findByTag("user");
    if (role == null)
      throw new CommonException(StatusCode.ERROR, "用户角色不存在");

    userService.updateRoleId(userId, role.getId());

    return new Result(true, StatusCode.OK, "此管理员已被降级为用户");

  }


  /**
   * 动态条件构建
   *
   * @param searchMap
   * @return
   */
  private Specification<Role> createSpecification(Map searchMap) {

    return new Specification<Role>() {

      @Override
      public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        // 角色名称
        if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
          predicateList.add(cb.like(root.get("name").as(String.class), "%" + (String) searchMap.get("name") + "%"));
        }
        // 角色标记
        if (searchMap.get("tag") != null && !"".equals(searchMap.get("tag"))) {
          predicateList.add(cb.like(root.get("tag").as(String.class), "%" + (String) searchMap.get("tag") + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

      }
    };

  }

}
