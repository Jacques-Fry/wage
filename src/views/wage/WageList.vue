<template>
  <div class="wage">
    <div class="wage-header">
      <div class="wage-title">
        <span>工资条</span>
        <el-button
          style="margin-left: 20px"
          size="mini"
          icon="el-icon-plus"
          @click="$router.push('/newwage')"
        >新建工资条</el-button>
      </div>
      <div class="wage-option">
        <div class="option-name">
          <el-input size="mini" v-model="staffName" clearable placeholder="员工姓名"></el-input>
        </div>

        <div class="option-date">
          <el-date-picker
            size="mini"
            value-f
            v-model="wageTime"
            format="yyyy年MM月"
            value-format="yyyy年MM月"
            type="month"
            placeholder="工资所属月份"
            :loading="loading"
          ></el-date-picker>
        </div>

        <div class="option-search">
          <el-button size="mini" type="primary" icon="el-icon-search" @click="salarySheetSearch">搜索</el-button>
        </div>
      </div>
    </div>

    <div class="wage-content">
      <vue-scroll v-if="wageList.length!==0">
        <SalarySheet
          @look="lookWage"
          v-for="(item,index) in wageList"
          :key="'wageList'+index"
          :wage="item"
        />
      </vue-scroll>
      <div v-else class="no-data">抱歉，暂无数据</div>
    </div>

    <div class="wage-footer">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>

    <el-dialog title="工资条详情" :visible.sync="centerDialogVisible" width="30%" center>
      <el-form
        label-position="right"
        size="mini"
        :model="wage"
        label-width="185px"
        class="demo-ruleForm"
      >
        <el-form-item label="所属年月份: ">{{wage.wageTime}}</el-form-item>

        <el-form-item label="员工姓名: ">{{wage.staffName}}</el-form-item>

        <el-form-item label="基本工资: ">{{wage.base}}</el-form-item>

        <el-form-item label="上一月度等级: ">{{wage.lastLevel}}</el-form-item>

        <el-form-item label="岗位系数: ">{{wage.postCoefficient}}</el-form-item>

        <el-form-item label="实习: ">{{wage.internship}}</el-form-item>

        <el-form-item label="级别系数: ">{{wage.classificationCoefficient}}</el-form-item>

        <el-form-item label="绩效: ">{{wage.performance}}</el-form-item>
        <el-form-item label="应到天数: ">{{wage.daysToCome}}</el-form-item>

        <el-form-item label="事假天数: ">{{wage.affairLeave}}</el-form-item>

        <el-form-item label="病假天数: ">{{wage.sickLeave}}</el-form-item>

        <el-form-item label="迟到天数: ">{{wage.late}}</el-form-item>

        <el-form-item label="缺勤天数: ">{{wage.absenteeismDays}}</el-form-item>

        <el-form-item label="出差天数: ">{{wage.evection}}</el-form-item>

        <el-form-item label="报销费用: ">{{wage.claimExpense}}</el-form-item>

        <el-form-item label="补贴费用: ">{{wage.subsidies}}</el-form-item>

        <el-form-item label="奖金: ">{{wage.bonus}}</el-form-item>

        <el-form-item label="社保: ">{{wage.socialSecurity}}</el-form-item>

        <el-form-item label="其他: ">{{wage.other}}</el-form-item>

        <el-form-item label="应发工资: ">{{wage.wagesPayable}}</el-form-item>

        <el-form-item label="日工资: ">{{wage.dailyWages}}</el-form-item>

        <el-form-item label="实发工资: ">{{wage.realWages}}</el-form-item>

        <el-form-item label="实发补贴: ">{{wage.realSubsidy}}</el-form-item>

        <el-form-item label="个税: ">{{wage.incomeTax}}</el-form-item>

        <el-form-item label="PAID IN: ">{{wage.paidIn}}</el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="warning" @click="$router.push('/updwage/'+wage.id)">修 改</el-button>
        <el-button size="small" @click="centerDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script type="text/javascript">
import { salarySheetSearch, detailById } from "network/wage.js";

import { Loading } from "element-ui";

import SalarySheet from "components/content/salarysheet/SalarySheet";
export default {
  name: "WageList",
  data() {
    return {
      wageList: [],
      staffName: "",
      wageTime: "",
      currentPage: 1,
      pageSizes: [20, 30, 50],
      pageSize: 20,
      total: 0,
      centerDialogVisible: false,
      wage: {},
      loading: false
    };
  },
  components: {
    SalarySheet
  },
  created() {
    this.salarySheetSearch();
  },
  mounted() {},
  methods: {
    lookWage(id) {
      let loadingInstance = Loading.service();
      detailById(id).then(res => {
        this.$nextTick(() => {
          // 以服务的方式调用的 Loading 需要异步关闭
          loadingInstance.close();
        });
        if (res && res.code === 4000) {
          this.salarySheetSearch();
        }
        if (res && res.code === 200) {
          this.wage = res.data;
          this.centerDialogVisible = true;
        }
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.salarySheetSearch();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.salarySheetSearch();
    },
    salarySheetSearch() {
      let loadingInstance = Loading.service();
      this.loading = true;
      salarySheetSearch(
        this.currentPage,
        this.pageSize,
        this.staffName,
        this.wageTime
      ).then(res => {
        this.$nextTick(() => {
          // 以服务的方式调用的 Loading 需要异步关闭
          loadingInstance.close();
        });
        this.loading = false;
        if (res && res.code === 200) {
          this.wageList = res.data.rows;
          this.total = res.data.total;
        }
      });
    }
  }
};
</script>

<style scoped>
.wage {
  position: relative;
  overflow: hidden;
  width: calc(100% - 10px);
  height: 100%;
}
.wage-header {
  position: relative;
  margin-left: 10px;
  margin-top: 10px;
  width: 100%;
  line-height: 50px;
  background-color: #fff;
  padding-bottom: 10px;
}

.wage-title {
  width: 100%;
  margin-left: 10px;
  font-size: 30px;
  font-weight: 600;
}
.wage-option {
  position: relative;
  width: 100%;
  display: flex;
}
.option-name,
.option-date,
.option-search {
  margin-left: 10px;
}

.wage-content {
  position: relative;
  margin-left: 10px;

  width: calc(100% - 10px);
  height: calc(100% - 200px);
  background-color: #fff;
}
.no-data {
  text-align: center;

  line-height: 100px;
  font-size: 30px;
  font-weight: 600;
}
.wage-footer {
  text-align: center;
  margin-left: 10px;
  padding-top: 20px;

  width: 100%;
  height: 50px;
  background-color: #fff;
}
</style>
