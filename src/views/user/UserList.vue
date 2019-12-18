<template>
  <div class="user">
    <div class="user-header">
      <div class="user-title">
        <span>用户</span>
        <el-button
          style="margin-left: 20px"
          size="mini"
          icon="el-icon-plus"
          @click="$router.push('/newuser')"
        >创建用户</el-button>
      </div>
      <div class="user-option">
        <div class="option-name">
          <el-input size="mini" v-model="username" clearable placeholder="用户名"></el-input>
        </div>

        <div class="option-tel">
          <el-input size="mini" v-model="tel" clearable placeholder="手机号"></el-input>
        </div>

        <div class="option-type">
          <el-select size="mini" v-model="roleId" placeholder="用户类型">
            <el-option label="全部" :value="0"></el-option>
            <el-option
              v-for="(item,index) in roles"
              :key="'roles'+index"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </div>

        <div class="option-search">
          <el-button size="mini" type="primary" icon="el-icon-search" @click="salarySheetSearch">搜索</el-button>
        </div>
      </div>
    </div>

    <div class="user-content">
      <vue-scroll v-if="userList.length!==0"></vue-scroll>
      <div v-else class="no-data">抱歉，暂无数据</div>
    </div>

    <div class="user-footer">
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
import { searchAllRole } from "network/role.js";

import { Loading } from "element-ui";

import SalarySheet from "components/content/salarysheet/SalarySheet";
export default {
  name: "userList",
  data() {
    return {
      userList: [],
      roles: [],
      username: "",
      tel: "",
      roleId: 0,
      currentPage: 1,
      pageSizes: [20, 30, 50],
      pageSize: 20,
      total: 0,
      centerDialogVisible: false,
      user: {},
      loading: false
    };
  },
  components: {
    SalarySheet
  },
  created() {
    this.salarySheetSearch();

    searchAllRole().then(res => {
      if (res && res.code === 200) {
        this.roles = res.data;
      }
    });
  },
  mounted() {},
  methods: {
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
      // let loadingInstance = Loading.service();
      // this.loading = true;
      // salarySheetSearch(
      //   this.currentPage,
      //   this.pageSize,
      //   this.staffName,
      //   this.userTime
      // ).then(res => {
      //   this.$nextTick(() => {
      //     // 以服务的方式调用的 Loading 需要异步关闭
      //     loadingInstance.close();
      //   });
      //   this.loading = false;
      //   if (res && res.code === 200) {
      //     this.userList = res.data.rows;
      //     this.total = res.data.total;
      //   }
      // });
    }
  },
  filters: {
    toFixed2(data) {
      if (data === undefined) return data;
      return data.toFixed(2);
    }
  }
};
</script>

<style scoped>
.user {
  position: relative;
  overflow: hidden;
  width: calc(100% - 10px);
  height: 100%;
}
.user-header {
  position: relative;
  margin-left: 10px;
  margin-top: 10px;
  width: 100%;
  line-height: 50px;
  background-color: #fff;
  padding-bottom: 10px;
}

.user-title {
  width: 100%;
  margin-left: 10px;
  font-size: 30px;
  font-weight: 600;
}
.user-option {
  position: relative;
  width: 100%;
  display: flex;
}
.option-name,
.option-tel,
.option-type,
.option-search {
  margin-left: 10px;
}

.user-content {
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
.user-footer {
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
</style>
