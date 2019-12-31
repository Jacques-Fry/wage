package com.transeesoft.wage.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.transeesoft.wage.pojo.Action;
import com.transeesoft.wage.service.ActionService;

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
@RequestMapping("/action")
@Api(tags = "系统功能")
public class ActionController {

  @Autowired
  private ActionService actionService;


  /**
   * 分页+多条件查询
   *
   * @param searchMap 查询条件封装
   * @param page      页码
   * @param size      页大小
   * @return 分页结果
   */
  @ApiOperation("查询功能")
  @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
  public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
    return actionService.findSearch(searchMap, page, size);
  }


  /**
   * 添加功能
   *
   * @param action
   */
  @ApiOperation("添加功能")
  @PostMapping
  public Result add(@RequestBody Action action) {
    return actionService.add(action);
  }

  /**
   * 修改功能
   *
   * @param action
   */
  @ApiOperation("修改功能")
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Result update(@RequestBody Action action, @PathVariable Integer id) {
    action.setId(id);
    return actionService.update(action);
  }

  /**
   * 删除功能
   *
   * @param id
   */
  @ApiOperation("删除功能")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Result delete(@PathVariable Integer id) {
    return actionService.deleteById(id);
  }

}
