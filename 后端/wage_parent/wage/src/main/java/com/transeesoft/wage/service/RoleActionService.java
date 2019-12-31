package com.transeesoft.wage.service;

import com.alibaba.fastjson.JSONObject;
import com.transeesoft.wage.dao.RoleActionDao;
import com.transeesoft.wage.exception.CommonException;
import com.transeesoft.wage.pojo.Action;
import com.transeesoft.wage.pojo.Role;
import com.transeesoft.wage.pojo.RoleAction;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack_YD
 * @create 2019/12/11 17:36
 */
@Service
public class RoleActionService {
  @Autowired
  private RoleActionDao roleActionDao;

  @Autowired
  private ActionService actionService;

  @Autowired
  private UserService userService;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private RoleService roleService;

  /**
   * 验证权限
   */
  public void verifyAction(String name, Integer userId) {
    if (userService.countByIdAndStatus(userId) != 1) {
      System.out.println("此用户数据条数: " + userService.countById(userId));
      throw new CommonException(StatusCode.ERROR, "账号数据异常,请尝试重新登陆");
    }
    Action action = actionService.findByName(name);
    if (action == null)
      throw new CommonException(StatusCode.ERROR, "当前没有该功能");
    if (!isExists(userService.getOne(userId).getRoleId(), action.getId()))
      throw new CommonException(StatusCode.SYSERROR, "权限不足");
  }

  /**
   * 角色是否存在此权限
   *
   * @param roleId
   * @param actionId
   * @return
   */
  public boolean isExists(Integer roleId, Integer actionId) {
    return roleActionDao.countByRoleIdAndActionId(roleId, actionId) > 0;
  }

  /**
   * 单个角色添加单个功能
   */
  public Result roleAddActionOne(JSONObject obj) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    verifyAction("角色授权", new Integer(claims.getId()));

    Integer roleId = obj.getInteger("roleId");
    Integer actionId = obj.getInteger("actionId");
    if (roleId == null || roleId == 0)
      return new Result(false, StatusCode.PARAMERROR, "未选择角色");
    if (actionId == null || actionId == 0)
      return new Result(false, StatusCode.PARAMERROR, "未选择功能");

    if (!roleService.isExists(roleId))
      return new Result(false, StatusCode.PARAMERROR, "该角色不存在");

    if (!actionService.isExists(actionId))
      return new Result(false, StatusCode.PARAMERROR, "该功能不存在");

    if (isExists(roleId, actionId))
      return new Result(false, StatusCode.PARAMERROR, "该角色已拥有此权限");

    roleActionDao.save(new RoleAction(roleId, actionId));

    return new Result(false, StatusCode.PARAMERROR, "授权成功");
  }

  /**
   * 取消单个角色单个功能
   */
  public Result roleDelActionOne(JSONObject obj) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    verifyAction("角色授权", new Integer(claims.getId()));

    Integer roleId = obj.getInteger("roleId");
    Integer actionId = obj.getInteger("actionId");
    if (roleId == null || roleId == 0)
      return new Result(false, StatusCode.PARAMERROR, "未选择角色");
    if (actionId == null || actionId == 0)
      return new Result(false, StatusCode.PARAMERROR, "未选择功能");

    if (!roleService.isExists(roleId))
      return new Result(false, StatusCode.PARAMERROR, "该角色不存在");

    if (!actionService.isExists(actionId))
      return new Result(false, StatusCode.PARAMERROR, "该功能不存在");

    if (roleService.isSystem(roleId)) {
      return new Result(false, StatusCode.PARAMERROR, "超级管理员的权限不可被取消");
    }

    delByRoleIdAndActionId(roleId, actionId);

    return new Result(false, StatusCode.PARAMERROR, "取消授权成功");
  }

  /**
   * 查询单个角色全部权限
   */
  public List<Action> findActionByRoleId(Integer roleId) {


    return actionService.findMore(roleActionDao.findActionByRoleIdIn(roleId));
  }

  /**
   * 查询单个角色绑定功能
   */
  public Result searchOneRoleAction(Integer roleId) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      throw new CommonException(StatusCode.TOKENERROR, "未登录");
    }
    verifyAction("查询功能", new Integer(claims.getId()));

    return new Result(true, StatusCode.OK, "查询成功", findActionByRoleId(roleId));
  }

  /**
   * 删除单个角色单个功能
   */
  public void delByRoleIdAndActionId(Integer roleId, Integer actionId) {
    roleActionDao.delByRoleIdAndActionId(roleId, actionId);
  }


  /**
   * 查询角色绑定功能
   */
  public Result searchAll() {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      throw new CommonException(StatusCode.TOKENERROR, "未登录");
    }
    verifyAction("查询功能", new Integer(claims.getId()));

    List<Role> roleList = roleService.findAll();

    Map<String, Object> map = new HashMap<>();

    for (Role role : roleList) {
      role.setActions(findActionByRoleId(role.getId()));
      map.put(role.getTag(), role);
    }

    return new Result(true, StatusCode.OK, "查询成功", map);
  }
}
