package com.transeesoft.wage.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.transeesoft.wage.dao.RoleDao;
import com.transeesoft.wage.dao.UserDao;
import com.transeesoft.wage.pojo.Action;
import com.transeesoft.wage.pojo.Role;
import com.transeesoft.wage.pojo.User;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.Encrypt;
import util.JwtUtil;
import util.VerifyUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService implements Serializable {

  @Autowired
  private UserDao userDao;

  @Autowired
  private RoleService roleService;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private RoleActionService roleActionService;


  /**
   * 登陆
   */
  public Result login(String username, String password) {

    if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
      return new Result(false, StatusCode.PARAMERROR, "用户名或密码不允许为空");
    }

    //登陆
    User user = userDao.findByPasswordAndUsername(Encrypt.getInstance().MD5(password), username);
    if (user == null) {
      user = userDao.findByPasswordAndTel(Encrypt.getInstance().MD5(password), username);
      if (user == null) {
        return new Result(false, StatusCode.USERERROR, "用户名或密码错误");
      }
    }

    if (roleService.isUser(user.getRoleId()))
      return new Result(false, StatusCode.SYSERROR, "抱歉,您没有权限登陆后台");


    if (StringUtils.equals("1", user.getStatus()))
      return new Result(false, StatusCode.FREEZE, "账号已被冻结");


    Role role = roleService.getOne(user.getRoleId());

    String jwt = jwtUtil.createJWT(user.getId() + "", !roleService.isExists(user.getRoleId()) ? "user" : StringUtils.isBlank(role.getTag()) ? "user" : role.getTag(), null);

    return new Result(true, StatusCode.OK, "登陆成功", JSON.parse("{\"token\":\"" + jwt + "\"}"));
  }

  /**
   * 管理员创建用户
   */
  public Result createUser(JSONObject obj) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    Integer roleId = null;
    String type = obj.getString("type");

    if (StringUtils.equals("admin", type)) {
      //验证权限
      roleActionService.verifyAction("创建管理员", new Integer(claims.getId()));

      Role role = roleService.findByTag("admin");
      if (role == null) {
        return new Result(false, StatusCode.ERROR, "数据库没有管理员角色");
      } else {
        roleId = role.getId();
      }
    } else {
      //验证权限
      roleActionService.verifyAction("创建用户", new Integer(claims.getId()));

      Role role = roleService.findByTag("user");
      if (role == null) {
        return new Result(false, StatusCode.ERROR, "数据库没有用户角色");
      } else {
        roleId = role.getId();
      }
    }


    String username = obj.getString("username");
    if (StringUtils.isBlank(username)) {
      return new Result(false, StatusCode.PARAMERROR, "用户名不能为空");
    }
    if (userDao.countByUsername(username) > 0) {
      return new Result(false, StatusCode.PARAMERROR, "用户名已被注册");
    }
    String telRegex = "^1\\d{10}$";
    if (username.matches(telRegex)) {
      return new Result(false, StatusCode.PARAMERROR, "用户名不允许为手机号格式");
    }

    String tel = obj.getString("tel");

    if (!tel.matches(telRegex)) {
      return new Result(false, StatusCode.PARAMERROR, "手机号必须是11位数字,且必须以1开头");
    }
    if (userDao.countByTel(tel) > 0) {
      return new Result(false, StatusCode.PARAMERROR, "手机号已被注册");
    }

    String password = obj.getString("password");
    if (!VerifyUtil.password(password)) {
      return new Result(false, StatusCode.PARAMERROR, "密码必须为6-16位,且密码中至少包含一个字母和一个数字");
    }


    User user = new User(username, tel, Encrypt.getInstance().MD5(password), roleId, "0", new Date());
    user = userDao.save(user);

    String jwt = jwtUtil.createJWT(user.getId() + "", type, null);

    return new Result(true, StatusCode.OK, "注册成功", JSON.parse("{\"token\":\"" + jwt + "\"}"));
  }

  /**
   * 获取个人详情
   */
  public Result detail() {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    Integer id = new Integer(claims.getId());
    if (countById(id) != 1) {
      return new Result(false, StatusCode.PARAMERROR, "个人数据异常");
    }
    User user = getOne(id);
    user.setRoleName(roleService.getOne(user.getRoleId()).getName());
    user.setPassword(null);

    return new Result(true, StatusCode.OK, "查询成功", user);
  }

  /**
   * 根据ID获取用户详情
   */
  public Result detailById(Integer id) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("查询用户", new Integer(claims.getId()));

    if (countById(id) != 1) {
      return new Result(false, StatusCode.PARAMERROR, "不存在该用户数据");
    }
    User user = getOne(id);
    user.setPassword(null);

    return new Result(true, StatusCode.OK, "查询成功", user);
  }

  /**
   * 用户修改自己的个人信息-手机号
   */
  public Result updateTel(JSONObject obj) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    String tel = obj.getString("tel");
    String telRegex = "^1\\d{10}$";
    if (!tel.matches(telRegex)) {
      return new Result(false, StatusCode.PARAMERROR, "手机号必须11位数字,且必须以1开头");
    }
    if (userDao.countByTel(tel) > 0) {
      return new Result(false, StatusCode.PARAMERROR, "手机号已被注册");
    }
    Integer id = new Integer(claims.getId());

    userDao.updateTel(tel, id);

    return new Result(true, StatusCode.OK, "修改成功");

  }


  /**
   * 用户修改自己的个人信息-用户名
   */
  public Result updateUsername(JSONObject obj) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }
    String username = obj.getString("username");
    if (StringUtils.isBlank(username)) {
      return new Result(false, StatusCode.PARAMERROR, "用户名不能为空");
    }
    if (userDao.countByUsername(username) > 0) {
      return new Result(false, StatusCode.PARAMERROR, "用户名已存在");
    }
    String telRegex = "^1\\d{10}$";
    if (username.matches(telRegex)) {
      return new Result(false, StatusCode.PARAMERROR, "用户名不允许为手机号格式");
    }

    Integer id = new Integer(claims.getId());

    userDao.updateUserName(username, id);

    return new Result(true, StatusCode.OK, "修改成功");

  }

  /**
   * 用户修改自己的个人信息-密码
   */
  public Result updatePassword(JSONObject obj) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    String password = obj.getString("password");
    if (!VerifyUtil.password(password)) {
      return new Result(false, StatusCode.PARAMERROR, "新密码必须为6-16位,且密码中至少包含一个字母和一个数字");
    }

    Integer id = new Integer(claims.getId());

    String oldPassword = obj.getString("oldPassword");
    if (!VerifyUtil.password(oldPassword)) {
      return new Result(false, StatusCode.PARAMERROR, "旧密码格式错误");
    }
    if (userDao.countByPasswordAndId(Encrypt.getInstance().MD5(oldPassword), id) == 0) {
      return new Result(false, StatusCode.PARAMERROR, "旧密码输入错误");
    }
    if (StringUtils.equals(password, oldPassword)) {
      return new Result(false, StatusCode.PARAMERROR, "新旧密码一致无需修改");
    }

    userDao.updatePassword(Encrypt.getInstance().MD5(password), id);

    return new Result(true, StatusCode.OK, "修改成功");

  }


  /**
   * 条件查询+分页
   * 查询用户
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
    roleActionService.verifyAction("查询用户", new Integer(claims.getId()));

    String username = (String) whereMap.get("username");
    if (StringUtils.isBlank(username))
      username = "%";
    else
      username = "%" + username + "%";

    String tel = (String) whereMap.get("tel");
    if (StringUtils.isBlank(tel))
      tel = "%";
    else
      tel = "%" + tel + "%";

    Integer roleId = (Integer) whereMap.get("roleId");
    Integer roleIds[] = new Integer[1];
    if (roleId == null || roleId == 0)
      roleIds = roleService.findAllRoleId();
    else
      roleIds[0] = roleId;

    if (page <= 0) return new Result(true, StatusCode.PARAMERROR, "页数必须大于0");
    if (size <= 0) return new Result(true, StatusCode.PARAMERROR, "每页个数必须大于0");

    PageRequest of = PageRequest.of(page - 1, size/*, Sort.Direction.DESC, "createTime"*/);

    Page<User> users = userDao.findByUsernameLikeAndTelLikeAndRoleIdIn(username, tel, roleIds, of);
    for (User user : users.getContent()) {
      user.setPassword(null);
    }

    return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(users.getTotalElements(), users.getContent()));

  }


  /**
   * 查询全部用户
   */
  public Result findAll() {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("查询用户", new Integer(claims.getId()));
    return new Result(true, StatusCode.OK, "查询成功", userDao.findAll());

  }


  /**
   * 删除用户
   *
   * @param id
   */
  public Result deleteById(Integer id) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    if (countById(id) != 1) {
      return new Result(false, StatusCode.PARAMERROR, "用户数据不存在");
    }

    User user = getOne(id);

    if (roleService.isSystem(user.getRoleId())) {
      return new Result(false, StatusCode.SYSERROR, "任何人都没有权限删除超级管理员");
    } else if (roleService.isAdmin(user.getRoleId())) {
      //验证权限
      roleActionService.verifyAction("删除管理员", new Integer(claims.getId()));
    } else {
      //验证权限
      roleActionService.verifyAction("删除普通用户", new Integer(claims.getId()));
    }

    userDao.delById(id);

    return new Result(true, StatusCode.OK, "删除成功");
  }

  public User getOne(Integer id) {
    return userDao.getOne(id);
  }

  public Integer countById(Integer id) {
    return userDao.countById(id);
  }

  public Integer countByIdAndStatus(Integer id) {
    return userDao.countByIdAndStatus(id, "0");
  }

  /**
   * 更改权限
   */
  public void updateRoleId(Integer userId, Integer roleId) {
    userDao.updateRoleId(roleId, userId);
  }

  /**
   * 账号冻结
   */
  public Result freeze(Integer id) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    if (id == null || id == 0)
      return new Result(false, StatusCode.PARAMERROR, "参数错误");

    if (countById(id) != 1)
      return new Result(false, StatusCode.PARAMERROR, "用户数据不存在");

    User user = getOne(id);

    if (roleService.isSystem(user.getRoleId())) {
      return new Result(false, StatusCode.SYSERROR, "任何人都没有权限冻结超级管理员");
    } else if (roleService.isAdmin(user.getRoleId())) {
      //验证权限
      roleActionService.verifyAction("冻结管理员", new Integer(claims.getId()));
    } else {
      //验证权限
      roleActionService.verifyAction("冻结普通用户", new Integer(claims.getId()));
    }

    user.setStatus("1");

    userDao.save(user);

    return new Result(true, StatusCode.OK, "操作成功");
  }

  /**
   * 账号解冻
   */
  public Result unfreeze(Integer id) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    if (id == null || id == 0)
      return new Result(false, StatusCode.PARAMERROR, "参数错误");

    if (countById(id) != 1)
      return new Result(false, StatusCode.PARAMERROR, "用户数据不存在");

    User user = getOne(id);

    if (roleService.isAdmin(user.getRoleId())) {
      //验证权限
      roleActionService.verifyAction("冻结管理员", new Integer(claims.getId()));
    } else {
      //验证权限
      roleActionService.verifyAction("冻结普通用户", new Integer(claims.getId()));
    }

    user.setStatus("0");

    userDao.save(user);

    return new Result(true, StatusCode.OK, "操作成功");
  }

  /**
   * 修改账号信息
   */
  public Result updUser(Integer id, JSONObject obj) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    if (id == null || id == 0)
      return new Result(false, StatusCode.PARAMERROR, "参数错误");

    if (countById(id) != 1)
      return new Result(false, StatusCode.PARAMERROR, "用户数据不存在");

    User user = getOne(id);


    if (roleService.isSystem(user.getRoleId())) {
      return new Result(false, StatusCode.SYSERROR, "任何人都没有权限修改超级管理员");
    } else if (roleService.isAdmin(user.getRoleId())) {
      //验证权限
      roleActionService.verifyAction("修改管理员", new Integer(claims.getId()));
    } else {
      //验证权限
      roleActionService.verifyAction("修改普通用户", new Integer(claims.getId()));

    }

    String username = obj.getString("username");
    String telRegex = "^1\\d{10}$";
    if (!StringUtils.equals(username, user.getUsername())) {
      if (StringUtils.isBlank(username))
        return new Result(false, StatusCode.PARAMERROR, "用户名不能为空");

      if (userDao.countByUsername(username) > 0)
        return new Result(false, StatusCode.PARAMERROR, "用户名已存在");

      if (username.matches(telRegex))
        return new Result(false, StatusCode.PARAMERROR, "用户名不允许为手机号格式");

    }

    String password = obj.getString("password");
    if (!VerifyUtil.password(password))
      return new Result(false, StatusCode.PARAMERROR, "新密码必须为6-16位,且密码中至少包含一个字母和一个数字");


    String tel = obj.getString("tel");
    if (!tel.matches(telRegex))
      return new Result(false, StatusCode.PARAMERROR, "手机号必须11位数字,且必须以1开头");

    if (userDao.countByTel(tel) > 0)
      return new Result(false, StatusCode.PARAMERROR, "手机号已被注册");


    user.setUsername(username);
    user.setTel(tel);
    user.setPassword(Encrypt.getInstance().MD5(password));

    userDao.save(user);

    return new Result(true, StatusCode.OK, "修改成功");
  }


}
