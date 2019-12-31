package com.transeesoft.wage.controller;


import com.alibaba.fastjson.JSONObject;
import com.transeesoft.wage.service.RoleActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.transeesoft.wage.service.RoleService;

import entity.Result;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/role")
@Api(tags = "角色")
public class RoleController {

  @Autowired
  private RoleService roleService;


  /**
   * 查询全部角色数据
   *
   * @return
   */
  @ApiOperation("查询全部角色数据")
  @GetMapping("searchAllRole")
  public Result findAll() {
    return roleService.searchAll();

  }

//
//	/**
//	 * 增加
//	 * @param role
//	 */
//	@RequestMapping(method=RequestMethod.POST)
//	public Result add(@RequestBody Role role  ){
//		roleService.add(role);
//		return new Result(true,StatusCode.OK,"增加成功");
//	}
//
//	/**
//	 * 修改
//	 * @param role
//	 */
//	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
//	public Result update(@RequestBody Role role, @PathVariable Integer id ){
//		role.setId(id);
//		roleService.update(role);
//		return new Result(true,StatusCode.OK,"修改成功");
//	}
//
//	/**
//	 * 删除
//	 * @param id
//	 */
//	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
//	public Result delete(@PathVariable Integer id ){
//		roleService.deleteById(id);
//		return new Result(true,StatusCode.OK,"删除成功");
//	}


  @Autowired
  private RoleActionService roleActionService;

  /**
   * 查询全部角色绑定的功能
   */
  @ApiOperation("查询全部角色绑定的功能")
  @GetMapping("searchAllRoleAction")
  public Result searchAll() {
    return roleActionService.searchAll();
  }

  /**
   * 查询单个角色全部权限
   */
  @ApiOperation("查询单个角色全部权限")
  @GetMapping("searchAllRoleAction/{id}")
  public Result searchAllRoleAction(@PathVariable Integer id) {
    return roleActionService.searchOneRoleAction(id);

  }

  /**
   * 单个授权
   */
  @ApiOperation("给单个角色添加一个功能")
  @PostMapping("addActionOne")
  public Result addActionOne(@RequestBody JSONObject obj) {
    return roleActionService.roleAddActionOne(obj);
  }

  /**
   * 取消单个授权
   */
  @ApiOperation("取消单个角色单个功能")
  @DeleteMapping("delActionOne")
  public Result delActionOne(@RequestBody JSONObject obj) {
    return roleActionService.roleDelActionOne(obj);
  }

  /**
   * 将某个用户升级为管理员
   */
  @ApiOperation("用户升级为管理员")
  @PutMapping("userUpgradeAdmin/{id}")
  public Result userUpgradeAdmin(@PathVariable Integer id) {
    return roleService.userUpgradeAdmin(id);

  }

  /**
   * 将某个管理员降级为用户
   */
  @ApiOperation("管理员降级为用户")
  @PutMapping("adminDemotionUser/{id}")
  public Result adminDemotionUser(@PathVariable Integer id) {
    return roleService.adminDemotionUser(id);
  }

}
