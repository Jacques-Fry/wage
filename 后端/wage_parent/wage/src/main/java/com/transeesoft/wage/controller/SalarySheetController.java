package com.transeesoft.wage.controller;

import java.util.Map;

import com.transeesoft.wage.pojo.SalarySheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.transeesoft.wage.service.SalarySheetService;

import entity.Result;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/salarySheet")
@Api(tags = "工资条")
public class SalarySheetController {

  @Autowired
  private SalarySheetService sheetService;


  /**
   * 分页+多条件查询
   *
   * @param searchMap 查询条件封装
   * @param page      页码
   * @param size      页大小
   * @return 分页结果
   */
  @ApiOperation("分页查询工资条")
  @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
  public Result findSearch(@RequestBody(required = false) Map searchMap, @PathVariable int page, @PathVariable int size) {

    return sheetService.findSearch(searchMap, page, size);
  }

  /**
   * 创建工资条
   *
   * @param sheet
   */
  @ApiOperation("创建工资条")
  @RequestMapping(method = RequestMethod.POST)
  public Result add(@RequestBody SalarySheet sheet) {
    return sheetService.add(sheet);
  }

  /**
   * 修改工资条
   *
   * @param sheet
   */
  @ApiOperation("修改工资条")
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Result update(@RequestBody SalarySheet sheet, @PathVariable Integer id) {
    System.out.println(sheet);
    sheet.setId(id);
    return sheetService.update(sheet);
  }

  /**
   * 删除
   *
   * @param id
   */
  @ApiOperation("删除工资条")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Result delete(@PathVariable Integer id) {
    return sheetService.deleteById(id);
  }


  /**
   * 根据ID查询工资条
   */
  @ApiOperation("根据ID查询工资条")
  @GetMapping("/detail/{id}")
  public Result detailById(@PathVariable Integer id) {
    return sheetService.detailById(id);
  }

}
