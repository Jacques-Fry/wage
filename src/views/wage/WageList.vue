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

    <div class="wage-detail">
      <el-dialog title="工资条详情" :visible.sync="centerDialogVisible" width="30%" center>
        <table class="wage-table" width="100%">
          <tr>
            <th>所属年月份:</th>
            <td>{{wage.wageTime}}</td>
          </tr>

          <tr>
            <th>员工姓名:</th>
            <td>{{wage.staffName}}</td>
          </tr>

          <tr>
            <th>基本工资:</th>
            <td>{{wage.base | toFixed2}}</td>
          </tr>

          <tr>
            <th>上一月度等级:</th>
            <td>{{wage.lastLevel}}</td>
          </tr>

          <tr>
            <th>岗位系数:</th>
            <td>{{wage.postCoefficient}}</td>
          </tr>

          <tr>
            <th>实习:</th>
            <td>{{wage.internship}}</td>
          </tr>

          <tr>
            <th>级别系数:</th>
            <td>{{wage.classificationCoefficient}}</td>
          </tr>

          <tr>
            <th>绩效:</th>
            <td>{{wage.performance}}</td>
          </tr>
          <tr>
            <th>应到天数:</th>
            <td>{{wage.daysToCome}}</td>
          </tr>

          <tr>
            <th>事假天数:</th>
            <td>{{wage.affairLeave}}</td>
          </tr>

          <tr>
            <th>病假天数:</th>
            <td>{{wage.sickLeave}}</td>
          </tr>

          <tr>
            <th>迟到天数:</th>
            <td>{{wage.late}}</td>
          </tr>

          <tr>
            <th>缺勤天数:</th>
            <td>{{wage.absenteeismDays}}</td>
          </tr>

          <tr>
            <th>出差天数:</th>
            <td>{{wage.evection}}</td>
          </tr>

          <tr>
            <th>报销费用:</th>
            <td>{{wage.claimExpense | toFixed2}}</td>
          </tr>

          <tr>
            <th>补贴费用:</th>
            <td>{{wage.subsidies | toFixed2}}</td>
          </tr>

          <tr>
            <th>奖金:</th>
            <td>{{wage.bonus | toFixed2}}</td>
          </tr>

          <tr>
            <th>社保:</th>
            <td>{{wage.socialSecurity | toFixed2}}</td>
          </tr>

          <tr>
            <th>其他:</th>
            <td>{{wage.other | toFixed2}}</td>
          </tr>

          <tr>
            <th>应发工资:</th>
            <td>{{wage.wagesPayable | toFixed2}}</td>
          </tr>

          <tr>
            <th>日工资:</th>
            <td>{{wage.dailyWages | toFixed2}}</td>
          </tr>

          <tr>
            <th>实发工资:</th>
            <td>{{wage.realWages | toFixed2}}</td>
          </tr>

          <tr>
            <th>实发补贴:</th>
            <td>{{wage.realSubsidy | toFixed2}}</td>
          </tr>

          <tr>
            <th>个税:</th>
            <td>{{wage.incomeTax | toFixed2}}</td>
          </tr>

          <tr>
            <th>PAID IN:</th>
            <td>{{wage.paidIn}}</td>
          </tr>
        </table>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" type="warning" @click="$router.push('/updwage/'+wage.id)">修 改</el-button>
          <el-button size="small" @click="centerDialogVisible = false">关闭</el-button>
        </span>
      </el-dialog>
    </div>
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
  },
  filters: {
    toFixed2(data) {
      if(data===undefined) return data
      return data.toFixed(2);
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
.demo-ruleForm {
  width: 300px;
}

.wage-table tr {
  line-height: 20px;
}
.wage-table tr th {
  width: 50%;
  text-align: right;
  padding-right: 10px;
}
.wage-table tr td {
  text-align: left;
  padding-left: 10px;
}
</style>
