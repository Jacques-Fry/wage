package com.transeesoft.wage.dao;

import com.transeesoft.wage.pojo.SalarySheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface SalarySheetDao extends JpaRepository<SalarySheet, Integer>, JpaSpecificationExecutor<SalarySheet> {

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM salary_sheet WHERE id=?1")
  void delById(Integer id);

  Integer countByStaffNameAndWageTime(String staffName, Date wageTime);

  Integer countById(Integer id);
}
