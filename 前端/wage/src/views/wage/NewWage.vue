<template>
  <div class="new-wage">
    <div class="black" @click="$router.go(-1)">
      <span class="el-icon-arrow-left"></span> 返回
    </div>
    <div class="wage-title">
      <span>新建工资条</span>
    </div>
    <div class="wage-form">
      <vue-scroll class="vue-scroll">
        <el-form
          label-position="right"
          size="mini"
          :model="wage"
          :rules="rules"
          ref="wage"
          label-width="120px"
          class="demo-ruleForm"
        >
          <el-form-item label="所属年月份" prop="wageTime">
            <el-date-picker
              format="yyyy年MM月"
              value-format="yyyy-MM-dd"
              v-model="wage.wageTime"
              type="month"
              placeholder="月份"
            ></el-date-picker>
          </el-form-item>

          <el-form-item label="所属用户" prop="userId">
            <el-select v-model="wage.userId" placeholder="请选择">
              <el-option
                v-for="item in users"
                :key="item.value"
                :label="item.username"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="员工姓名" prop="staffName">
            <el-input v-model="wage.staffName" autocomplete="off" clearable></el-input>
          </el-form-item>

          <el-form-item label="基本工资" prop="base">
            <el-input v-model="wage.base" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="上一月度等级" prop="lastLevel">
            <el-input v-model="wage.lastLevel" autocomplete="off" clearable></el-input>
          </el-form-item>

          <el-form-item label="岗位系数" prop="postCoefficient">
            <el-input v-model="wage.postCoefficient" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="实习" prop="internship">
            <el-input v-model="wage.internship" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="级别系数" prop="classificationCoefficient">
            <el-input v-model="wage.classificationCoefficient" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="绩效" prop="performance">
            <el-input v-model="wage.performance" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="应到天数" prop="daysToCome">
            <el-input v-model="wage.daysToCome" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="事假天数" prop="affairLeave">
            <el-input v-model="wage.affairLeave" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="病假天数" prop="sickLeave">
            <el-input v-model="wage.sickLeave" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="迟到天数" prop="late">
            <el-input v-model="wage.late" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="缺勤天数" prop="absenteeismDays">
            <el-input v-model="wage.absenteeismDays" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="出差天数" prop="evection">
            <el-input v-model="wage.evection" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="报销费用" prop="claimExpense">
            <el-input v-model="wage.claimExpense" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="补贴费用" prop="subsidies">
            <el-input v-model="wage.subsidies" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="奖金" prop="bonus">
            <el-input v-model="wage.bonus" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="社保" prop="socialSecurity">
            <el-input v-model="wage.socialSecurity" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="其他" prop="other">
            <el-input v-model="wage.other" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="应发工资" prop="wagesPayable">
            <el-input v-model="wage.wagesPayable" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="日工资" prop="dailyWages">
            <el-input v-model="wage.dailyWages" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="实发工资" prop="realWages">
            <el-input v-model="wage.realWages" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="实发补贴" prop="realSubsidy">
            <el-input v-model="wage.realSubsidy" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="个税" prop="incomeTax">
            <el-input v-model="wage.incomeTax" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="PAID IN" prop="paidIn">
            <el-input v-model="wage.paidIn" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item class="form-option">
            <el-button size="small" type="primary" :loading="loading" @click="submitForm">立即提交</el-button>
            <el-button size="small" @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </vue-scroll>
    </div>
  </div>
</template>

<script type="text/javascript">
import {findAll} from "network/user.js"
import { createWage } from "network/wage.js";
export default {
  name: "NewWage",
  data() {
    let reg1 = /^(([12]?[0-9](\.[0-9]{1})?)|30)$/;
    let math1Verify = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("不允许为空"));
      } else if (!reg1.test(value)) {
        callback(new Error("天数范围0-30,只允许至多一位小数"));
      } else {
        callback();
      }
    };

    let reg2 = /^([1-9]\d*(\.\d{1,2})?|0\.[1-9]\d?|0\.0[1-9]|0|0.0|0.00)$/;
    let math2Verify = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("不允许为空"));
      } else if (!reg2.test(value)) {
        callback(new Error("数值不规范,只允许至多两位小数,整数前请不要有0"));
      } else {
        callback();
      }
    };

    return {
      loading: false,
      wage: {
        wageTime: "", //工资所属年月份
        userId: "", //所属员工ID
        staffName: "", //员工姓名
        base: 0.0, //基本工资
        lastLevel: "", //上一月度等级
        postCoefficient: 0.0, //岗位系数
        internship: 1, //实习
        classificationCoefficient: 0.0, //级别系数
        performance: 0.0, //绩效
        daysToCome: 0.0, //应到天数
        affairLeave: 0.0, //事假天数
        sickLeave: 0.0, //病假天数
        late: 0.0, //迟到天数
        absenteeismDays: 0.0, //缺勤天数
        evection: 0.0, //出差天数
        claimExpense: 0.0, //报销费用
        subsidies: 0.0, //补贴费用
        bonus: 0.0, //奖金
        socialSecurity: 0.0, //社保
        other: 0.0, //其他
        wagesPayable: 0.0, //应发工资
        dailyWages: 0.0, //日工资
        realWages: 0.0, //实发工资
        realSubsidy: 0.0, //实发补贴
        incomeTax: 0.0, //个税
        paidIn: 0.0 //实际到手工资
      },
      rules: {
        wageTime: [
          {
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ], //所属年月
        userId: [
          { required: true, message: "所属员工必填", trigger: "blur" }
        ], //所属员工
        staffName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
        base: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //基本工资
        postCoefficient: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //岗位系数
        classificationCoefficient: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //级别系数
        performance: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //绩效
        daysToCome: [
          { validator: math1Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //应到天数
        affairLeave: [
          { validator: math1Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //事假天数
        sickLeave: [
          { validator: math1Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //病假天数
        late: [
          { validator: math1Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //迟到天数
        absenteeismDays: [
          { validator: math1Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //缺勤天数
        evection: [
          { validator: math1Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //出差天数
        claimExpense: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //报销费用
        subsidies: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //补贴费用
        bonus: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //奖金
        socialSecurity: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //社保
        other: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //其他
        wagesPayable: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //应发工资
        dailyWages: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //日工资
        realWages: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //实发工资
        realSubsidy: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //实发工资
        incomeTax: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ], //个税
        paidIn: [
          { validator: math2Verify, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ] //PAID IN
      },
      users: []
    };
  },
  created () {
    findAll().then(res => {
      if (res && res.code === 200) {
        this.users = res.data;
      }
    });
  },
  methods: {
    submitForm(formName) {
      this.loading = true;
      this.$refs["wage"].validate(valid => {
        if (valid) {
          createWage(this.wage).then(res => {
            this.loading = false;
            if (res && res.code === 200) {
              this.$notify({
                showClose: true,
                title: "成功",
                message: "创建成功",
                type: "success"
              });
              this.$confirm("创建成功，是否继续创建工资条?", "提示", {
                confirmButtonText: "继续创建",
                cancelButtonText: "返回列表",
                type: "warning"
              })
                .then(() => {
                  this.resetForm();
                })
                .catch(() => {
                  this.$router.push("/wagelist");
                });
            }
          });
        } else {
          this.loading = false;
          this.$message({
            showClose: true,
            message: "信息填写有误,请检查",
            type: "error"
          });
          return false;
        }
      });
    },
    resetForm() {
      this.$refs["wage"].resetFields();
    }
  }
};
</script>

<style scoped>
.new-wage {
  position: relative;
  overflow: hidden;

  text-align: center;
  width: 100%;
  height: 100%;
}
.wage-title {
  font-size: 30px;
  font-weight: 600;
  line-height: 60px;
  height: 60px;

  background-color: #fff;
}
.black {
  position: absolute;
  margin-left: 20px;
  cursor: pointer;

  font-size: 14px;
  line-height: 60px;

  color: #959595;
}
.black:hover {
  color: #66b1ff;
}
.wage-form {
  position: relative;
  display: inline-block;

  width: 500px;
  height: calc(100% - 60px);

  background-color: #fff;
}

.vue-scroll {
  position: relative;
  width: 100%;
}
.demo-ruleForm {
  text-align: left;
}
.el-form-item {
  width: 450px;

  margin: 15px 0;
}
</style>
