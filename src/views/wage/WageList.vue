<template>
  <div class="wage">
    <div class="wage-header">
      <div class="wage-title">
        <span>工资条</span>
        <el-button style="margin-left: 20px" size="mini" icon="el-icon-plus" @click="newWage">新建工资条</el-button>
      </div>
      <div class="wage-option">
        <div class="option-name">
          <el-input size="mini" v-model="staffName" clearable placeholder="员工姓名"></el-input>
        </div>

        <div class="option-date">
          <el-date-picker size="mini" v-model="wageTime" type="month" placeholder="工资所属月份"></el-date-picker>
        </div>

        <div class="option-search">
          <el-button size="mini" type="primary" icon="el-icon-search">搜索</el-button>
        </div>
      </div>
    </div>

    <div class="wage-content">
      <vue-scroll v-if="wageList.length!==0">
        <SalarySheet v-for="(item,index) in wageList" :key="'wageList'+index" :wage="item" />
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

   
  </div>
</template>

<script type="text/javascript">
import { salarySheetSearch } from "network/wage.js";

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
      
    };
  },
  components: {
    SalarySheet
  },
  created() {
    this.salarySheetSearch();
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    salarySheetSearch() {
      salarySheetSearch(
        this.currentPage,
        this.pageSize,
        this.staffName,
        this.wageTime
      ).then(res => {
        if (res.code === 200) {
          this.wageList = res.data.rows;
          this.total = res.data.total;
        }
      });
    },
    newWage() {
      this.$router.push("/newwage");
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
