package com.transeesoft.wage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "salary_sheet")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class SalarySheet implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;//工资条ID
  private String staffName;//员工姓名
  private Double base;//基本工资
  private String lastLevel;//上一月度等级
  private Double postCoefficient;//岗位系数
  private String internship;//实习
  private Double classificationCoefficient;//级别系数
  private Double performance;//绩效
  private Double affairLeave;//事假天数
  private Double sickLeave;//病假天数
  private Double late;//迟到天数
  private Double evection;//出差天数
  private Double claimExpense;//报销费用
  private Double subsidies;//补贴费用
  private Double bonus;//奖金
  private Double socialSecurity;//社保
  private Double other;//其他
  private Double daysToCome;//应到天数
  private Double absenteeismDays;//缺勤天数
  private Double wagesPayable;//应发工资
  private Double dailyWages;//日工资
  private Double realWages;//实发工资
  private Double realSubsidy;//实发补贴
  private Double incomeTax;//个税
  private Double paidIn;//实际到手工资
  private Integer userId;//所属员工ID
  private Date wageTime;//工资所属年月份
  private Date createTime;//工资条创建时间
  private Integer createUserId;//创建人
  private Date updateTime;//工资条修改时间
  private Integer updateUserId;//修改人

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(Integer updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Integer getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Integer createUserId) {
    this.createUserId = createUserId;
  }

  public SalarySheet() {
  }

  public String getStaffName() {
    return staffName;
  }

  public void setStaffName(String staffName) {
    this.staffName = staffName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getBase() {
    return base;
  }

  public void setBase(Double base) {
    this.base = base;
  }

  public String getLastLevel() {
    return lastLevel;
  }

  public void setLastLevel(String lastLevel) {
    this.lastLevel = lastLevel;
  }

  public Double getPostCoefficient() {
    return postCoefficient;
  }

  public void setPostCoefficient(Double postCoefficient) {
    this.postCoefficient = postCoefficient;
  }

  public String getInternship() {
    return internship;
  }

  public void setInternship(String internship) {
    this.internship = internship;
  }

  public Double getClassificationCoefficient() {
    return classificationCoefficient;
  }

  public void setClassificationCoefficient(Double classificationCoefficient) {
    this.classificationCoefficient = classificationCoefficient;
  }

  public Double getPerformance() {
    return performance;
  }

  public void setPerformance(Double performance) {
    this.performance = performance;
  }


  public Double getClaimExpense() {
    return claimExpense;
  }

  public void setClaimExpense(Double claimExpense) {
    this.claimExpense = claimExpense;
  }

  public Double getSubsidies() {
    return subsidies;
  }

  public void setSubsidies(Double subsidies) {
    this.subsidies = subsidies;
  }

  public Double getBonus() {
    return bonus;
  }

  public void setBonus(Double bonus) {
    this.bonus = bonus;
  }

  public Double getSocialSecurity() {
    return socialSecurity;
  }

  public void setSocialSecurity(Double socialSecurity) {
    this.socialSecurity = socialSecurity;
  }

  public Double getOther() {
    return other;
  }

  public void setOther(Double other) {
    this.other = other;
  }

  public Double getAffairLeave() {
    return affairLeave;
  }

  public void setAffairLeave(Double affairLeave) {
    this.affairLeave = affairLeave;
  }

  public Double getSickLeave() {
    return sickLeave;
  }

  public void setSickLeave(Double sickLeave) {
    this.sickLeave = sickLeave;
  }

  public Double getLate() {
    return late;
  }

  public void setLate(Double late) {
    this.late = late;
  }

  public Double getEvection() {
    return evection;
  }

  public void setEvection(Double evection) {
    this.evection = evection;
  }

  public Double getDaysToCome() {
    return daysToCome;
  }

  public void setDaysToCome(Double daysToCome) {
    this.daysToCome = daysToCome;
  }

  public Double getAbsenteeismDays() {
    return absenteeismDays;
  }

  public void setAbsenteeismDays(Double absenteeismDays) {
    this.absenteeismDays = absenteeismDays;
  }

  public Double getWagesPayable() {
    return wagesPayable;
  }

  public void setWagesPayable(Double wagesPayable) {
    this.wagesPayable = wagesPayable;
  }

  public Double getDailyWages() {
    return dailyWages;
  }

  public void setDailyWages(Double dailyWages) {
    this.dailyWages = dailyWages;
  }

  public Double getRealWages() {
    return realWages;
  }

  public void setRealWages(Double realWages) {
    this.realWages = realWages;
  }

  public Double getRealSubsidy() {
    return realSubsidy;
  }

  public void setRealSubsidy(Double realSubsidy) {
    this.realSubsidy = realSubsidy;
  }

  public Double getIncomeTax() {
    return incomeTax;
  }

  public void setIncomeTax(Double incomeTax) {
    this.incomeTax = incomeTax;
  }

  public Double getPaidIn() {
    return paidIn;
  }

  public void setPaidIn(Double paidIn) {
    this.paidIn = paidIn;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Date getWageTime() {
    return wageTime;
  }

  public void setWageTime(Date wageTime) {
    this.wageTime = wageTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "{\"SalarySheet\":{"
        + "\"id\":"
        + id
        + ",\"staffName\":\""
        + staffName + '\"'
        + ",\"base\":"
        + base
        + ",\"lastLevel\":\""
        + lastLevel + '\"'
        + ",\"postCoefficient\":"
        + postCoefficient
        + ",\"internship\":\""
        + internship + '\"'
        + ",\"classificationCoefficient\":"
        + classificationCoefficient
        + ",\"performance\":"
        + performance
        + ",\"affairLeave\":"
        + affairLeave
        + ",\"sickLeave\":"
        + sickLeave
        + ",\"late\":"
        + late
        + ",\"evection\":"
        + evection
        + ",\"claimExpense\":"
        + claimExpense
        + ",\"subsidies\":"
        + subsidies
        + ",\"bonus\":"
        + bonus
        + ",\"socialSecurity\":"
        + socialSecurity
        + ",\"other\":"
        + other
        + ",\"daysToCome\":"
        + daysToCome
        + ",\"absenteeismDays\":"
        + absenteeismDays
        + ",\"wagesPayable\":"
        + wagesPayable
        + ",\"dailyWages\":"
        + dailyWages
        + ",\"realWages\":"
        + realWages
        + ",\"realSubsidy\":"
        + realSubsidy
        + ",\"incomeTax\":"
        + incomeTax
        + ",\"paidIn\":"
        + paidIn
        + ",\"userId\":"
        + userId
        + ",\"wageTime\":\""
        + wageTime + '\"'
        + ",\"createTime\":\""
        + createTime + '\"'
        + ",\"createUserId\":"
        + createUserId
        + ",\"updateTime\":\""
        + updateTime + '\"'
        + ",\"updateUserId\":"
        + updateUserId
        + "}}";

  }
}
