package com.transeesoft.wage.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.transeesoft.wage.pojo.User;
import com.transeesoft.wage.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * 登陆
   */
  @ApiOperation("登陆")
  @GetMapping("/login")
  public Result login(String username, String password) {
    return userService.login(username, password);
  }

  /**
   * 注册
   */
  @ApiOperation("创建用户")
  @PostMapping("/createUser")
  public Result createUser(@RequestBody JSONObject obj) {
    return userService.createUser(obj);
  }

  /**
   * 获取个人详情
   */
  @ApiOperation("获取个人详情")
  @GetMapping("/detail")
  public Result detail() {
    return userService.detail();
  }

  /**
   * 根据Id获取用户详情
   */
  @ApiOperation("根据Id获取用户详情")
  @GetMapping("/detail/{id}")
  public Result detailById(@PathVariable Integer id) {
    return userService.detailById(id);
  }

  /**
   * 修改手机号
   */
  @ApiOperation("修改手机号")
  @PutMapping("/updateTel")
  public Result updateTel(@RequestBody JSONObject obj) {
    return userService.updateTel(obj);
  }


  /**
   * 修改用户名
   */
  @ApiOperation("修改用户名")
  @PutMapping("/updateUsername")
  public Result updateUsername(@RequestBody JSONObject obj) {
    return userService.updateUsername(obj);
  }

  /**
   * 修改密码
   */
  @ApiOperation("修改密码")
  @PutMapping("/updatePassword")
  public Result updatePassword(@RequestBody JSONObject obj) {
    return userService.updatePassword(obj);
  }

  /**
   * 分页+多条件查询
   *
   * @param searchMap 查询条件封装
   * @param page      页码
   * @param size      页大小
   * @return 分页结果
   */
  @ApiOperation(value = "分页+多条件查询")
  @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
  public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
    return userService.findSearch(searchMap, page, size);
  }

  /**
   * 删除
   *
   * @param id
   */
  @ApiOperation("删除用户")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Result delete(@PathVariable Integer id) {
    return userService.deleteById(id);
  }

  /**
   * 账号冻结
   */
  @ApiOperation("账号冻结")
  @PutMapping("freeze/{id}")
  public Result freeze(@PathVariable Integer id) {
    return userService.freeze(id);
  }

  /**
   * 账号解冻
   */
  @ApiOperation("账号解冻")
  @PutMapping("unfreeze/{id}")
  public Result unfreeze(@PathVariable Integer id) {
    return userService.unfreeze(id);
  }

  /**
   * 修改账号信息
   */
  @ApiOperation("修改账号信息")
  @PutMapping("updUser/{id}")
  public Result updUser(@PathVariable Integer id, @RequestBody JSONObject obj) {
    return userService.updUser(id, obj);
  }

  @GetMapping("findAll")
  public Result findAll(){
    return userService.findAll();
  }
}
