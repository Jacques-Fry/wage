import { request } from "./request"

export function detailById(id) {
  return request({
    url: '/salarySheet/detail/' + id,
  })
}

export function salarySheetSearch(page, size, staffName, wageTime) {
  return request({
    method: 'post',
    url: '/salarySheet/search/' + page + '/' + size,
    data: {
      staffName,
      wageTime
    }

  })
}

export function createWage(wage) {
  return request({
    method: 'post',
    url: '/salarySheet',
    data: {
      wageTime: wage.wageTime, //工资所属年月份
      userId: wage.userId, //所属员工ID
      staffName: wage.staffName, //员工姓名
      base: wage.base, //基本工资
      lastLevel: wage.lastLevel, //上一月度等级
      postCoefficient: wage.postCoefficient, //岗位系数
      internship: wage.internship, //实习
      classificationCoefficient: wage.classificationCoefficient, //级别系数
      performance: wage.performance, //绩效
      daysToCome: wage.daysToCome, //应到天数
      affairLeave: wage.affairLeave, //事假天数
      sickLeave: wage.sickLeave, //病假天数
      late: wage.late, //迟到天数
      absenteeismDays: wage.absenteeismDays, //缺勤天数
      evection: wage.evection, //出差天数
      claimExpense: wage.claimExpense, //报销费用
      subsidies: wage.subsidies, //补贴费用
      bonus: wage.bonus, //奖金
      socialSecurity: wage.socialSecurity, //社保
      other: wage.other, //其他
      wagesPayable: wage.wagesPayable, //应发工资
      dailyWages: wage.dailyWages, //日工资
      realWages: wage.realWages, //实发工资
      realSubsidy: wage.realSubsidy, //实发补贴
      incomeTax: wage.incomeTax, //个税
      paidIn: wage.paidIn, //实际到手工资
    }

  })
}


export function updWage(id, wage) {
  return request({
    method: 'put',
    url: '/salarySheet/' + id,
    data: {
      wageTime: wage.wageTime, //工资所属年月份
      userId: wage.userId, //所属员工ID
      staffName: wage.staffName, //员工姓名
      base: wage.base, //基本工资
      lastLevel: wage.lastLevel, //上一月度等级
      postCoefficient: wage.postCoefficient, //岗位系数
      internship: wage.internship, //实习
      classificationCoefficient: wage.classificationCoefficient, //级别系数
      performance: wage.performance, //绩效
      daysToCome: wage.daysToCome, //应到天数
      affairLeave: wage.affairLeave, //事假天数
      sickLeave: wage.sickLeave, //病假天数
      late: wage.late, //迟到天数
      absenteeismDays: wage.absenteeismDays, //缺勤天数
      evection: wage.evection, //出差天数
      claimExpense: wage.claimExpense, //报销费用
      subsidies: wage.subsidies, //补贴费用
      bonus: wage.bonus, //奖金
      socialSecurity: wage.socialSecurity, //社保
      other: wage.other, //其他
      wagesPayable: wage.wagesPayable, //应发工资
      dailyWages: wage.dailyWages, //日工资
      realWages: wage.realWages, //实发工资
      realSubsidy: wage.realSubsidy, //实发补贴
      incomeTax: wage.incomeTax, //个税
      paidIn: wage.paidIn, //实际到手工资
      createUserId: wage.createUserId,//创建人
      createTime: wage.createTime//创建时间
    }

  })
}