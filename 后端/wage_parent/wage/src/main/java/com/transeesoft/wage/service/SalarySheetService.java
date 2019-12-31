package com.transeesoft.wage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import com.transeesoft.wage.dao.SalarySheetDao;
import com.transeesoft.wage.exception.CommonException;
import com.transeesoft.wage.pojo.SalarySheet;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class SalarySheetService {

  @Autowired
  private SalarySheetDao sheetDao;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private RoleActionService roleActionService;

  /**
   * 根据ID查询工资条
   */
  public Result detailById(Integer id) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("查询工资条", new Integer(claims.getId()));

    if(sheetDao.countById(id)!=1){
      return new Result(false, StatusCode.PARAMERROR, "该工资条不存在");
    }

    return new Result(true, StatusCode.OK, "查询成功", sheetDao.getOne(id));
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
    roleActionService.verifyAction("查询工资条", new Integer(claims.getId()));

    if (page <= 0) return new Result(true, StatusCode.PARAMERROR, "页数必须大于0");
    if (size <= 0) return new Result(true, StatusCode.PARAMERROR, "每页个数必须大于0");

    Specification<SalarySheet> specification = createSpecification(whereMap);
    PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createTime");
    Page<SalarySheet> pageList = sheetDao.findAll(specification, pageRequest);

    return new Result(true, StatusCode.OK, "查询成功", new PageResult<SalarySheet>(pageList.getTotalElements(), pageList.getContent()));
  }

  /**
   * 创建工资条
   *
   * @param sheet
   */
  public Result add(SalarySheet sheet) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("创建工资条", new Integer(claims.getId()));

    if (StringUtils.isBlank(sheet.getStaffName())) {
      throw new CommonException(StatusCode.PARAMERROR, "员工姓名不可为空");
    }

    if (sheet.getWageTime()==null) {
      throw new CommonException(StatusCode.PARAMERROR, "工资所属年月份不可为空");
    }

    //验证是否创建过该员工工资条
    if (sheetDao.countByStaffNameAndWageTime(sheet.getStaffName(), sheet.getWageTime()) != 0) {
      throw new CommonException(StatusCode.PARAMERROR, "该员工 " + sheet.getWageTime() + " 的工资条已经创建过了");
    }

    if (StringUtils.isBlank(sheet.getLastLevel())) {
      sheet.setLastLevel("暂无");
    }

    sheet.setCreateUserId(new Integer(claims.getId()));
    sheet.setCreateTime(new Date());

    sheetDao.save(sheet);

    return new Result(true, StatusCode.OK, "创建成功");
  }

  /**
   * 修改工资条
   *
   * @param sheet
   */
  public Result update(SalarySheet sheet) {
    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("修改工资条", new Integer(claims.getId()));

    if (StringUtils.isBlank(sheet.getStaffName())) {
      throw new CommonException(StatusCode.PARAMERROR, "员工名称不可为空");
    }
    if (StringUtils.isBlank(sheet.getStaffName())) {
      throw new CommonException(StatusCode.PARAMERROR, "员工名称不可为空");
    }
    if (sheet.getCreateUserId()==null||sheet.getCreateUserId()==0) {
      throw new CommonException(StatusCode.PARAMERROR, "创建人不可为空");
    }
    if (sheet.getCreateTime()==null) {
      throw new CommonException(StatusCode.PARAMERROR, "创建时间不可为空");
    }

    sheet.setUpdateUserId(new Integer(claims.getId()));
    sheet.setUpdateTime(new Date());
    sheetDao.save(sheet);

    return new Result(true, StatusCode.OK, "修改成功");
  }

  /**
   * 删除
   *
   * @param id
   */
  public Result deleteById(Integer id) {

    Claims claims = (Claims) httpServletRequest.getAttribute("claims");
    if (claims == null) {
      return new Result(false, StatusCode.TOKENERROR, "未登录");
    }

    //验证权限
    roleActionService.verifyAction("删除工资条", new Integer(claims.getId()));

    sheetDao.delById(id);

    return new Result(true, StatusCode.OK, "删除成功");
  }


  /**
   * 动态条件构建
   *
   * @param searchMap
   * @return
   */
  private Specification<SalarySheet> createSpecification(Map searchMap) {

    return new Specification<SalarySheet>() {

      @Override
      public Predicate toPredicate(Root<SalarySheet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        if (searchMap.get("staffName") != null && !"".equals(searchMap.get("staffName"))) {
          predicateList.add(cb.like(root.get("staffName").as(String.class), "%" + (String) searchMap.get("staffName") + "%"));
        }
        if (searchMap.get("wageTime") != null && !"".equals(searchMap.get("wageTime"))) {
          predicateList.add(cb.like(root.get("wageTime").as(String.class), "%" + (String) searchMap.get("wageTime") + "%"));
        }

        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

      }
    };

  }

}
