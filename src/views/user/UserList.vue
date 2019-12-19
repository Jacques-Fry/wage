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
          <el-button
            :loading="loading"
            size="mini"
            type="primary"
            icon="el-icon-search"
            @click="queryData"
          >搜索</el-button>
        </div>
      </div>
    </div>

    <div class="user-content">
      <vue-scroll v-if="userList.length!==0">
        <el-table ref="filterTable" :data="userList" style="width: 100%">
          <el-table-column prop="id" label="ID" width="50"></el-table-column>
          <el-table-column
            :formatter="dateFormatter"
            prop="createTime"
            label="注册时间"
            width="130"
            column-key="createTime"
          ></el-table-column>
          <el-table-column prop="username" label="用户名" width="120"></el-table-column>
          <el-table-column :formatter="telFormatter" label="手机号" width="120"></el-table-column>
          <el-table-column label="账号状态" width="80">
            <template slot-scope="scope">
              <el-tag
                :type="statusTag(scope.row.status)"
                disable-transitions
                effect="plain"
              >{{statusFormatter(scope.row.status)}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="角色/权限" width="110" filter-placement="bottom-end">
            <template slot-scope="scope">
              <el-tag :type="roleTag(scope.row.roleId)" disable-transitions>{{scope.row.roleId}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
              <div v-if="scope.row.roleId!=='超级管理员'&&me.id!==scope.row.id">
                <el-button size="mini" type="danger" @click="delUser(scope.row.id)">删除</el-button>
                <el-button
                  v-if="scope.row.status==='0'"
                  size="mini"
                  type="info"
                  @click="freeze(scope.row.id)"
                >冻结</el-button>
                <el-button
                  v-if="scope.row.status==='1'"
                  size="mini"
                  type="danger"
                  @click="unfreeze(scope.row.id)"
                >解冻</el-button>
                <el-button
                  v-if="scope.row.roleId==='管理员'"
                  size="mini"
                  type="danger"
                  @click="adminDemotionUser(scope.row.id)"
                >降级为普通用户</el-button>
                <el-button
                  v-if="scope.row.roleId==='普通用户'"
                  size="mini"
                  type="primary"
                  @click="userUpgradeAdmin(scope.row.id)"
                >升级为管理员</el-button>
                <el-button
                  size="mini"
                  type="warning"
                  @click="$router.push('/upduser/'+scope.row.id)"
                >编辑</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </vue-scroll>

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
import {
  searchUser,
  detailById,
  delUser,
  freeze,
  unfreeze,
  adminDemotionUser,
  userUpgradeAdmin
} from "network/user.js";

import { formatDate } from "common/utils.js";

import { Loading } from "element-ui";

import { mapGetters } from "vuex";

import SalarySheet from "components/content/salarysheet/SalarySheet";
export default {
  name: "userList",
  data() {
    return {
      userList: [],
      roles: [],
      roleFarmatter: {},
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
  computed: {
    ...mapGetters({
      me: "getUser"
    })
  },
  components: {
    SalarySheet
  },
  created() {
    searchAllRole().then(res => {
      if (res && res.code === 200) {
        this.roles = res.data;
        this.roles.forEach(item => {
          this.roleFarmatter[item.id] = item.name;
        });
        this.searchUser();
      }
    });
  },
  mounted() {},
  methods: {
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.currentPage = 1;
      this.searchUser();
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.searchUser();
    },
    dateFormatter(row, column) {
      return formatDate(new Date(row.createTime), "yyyy年MM月dd日");
    },
    telFormatter(row, column) {
      return row.tel || "暂无";
    },
    statusFormatter(status) {
      if (status === "0") return "正常";
      if (status === "1") return "已冻结";
    },
    statusTag(status) {
      if (status === "1") return "danger";
      return "success";
    },
    roleTag(roleName) {
      if (roleName === "超级管理员") return "warning";
      if (roleName === "管理员") return "";
      return "info";
    },
    queryData() {
      this.currentPage = 1;
      this.searchUser();
    },
    searchUser() {
      this.currentPage = 1;
      let loadingInstance = this.pageLoading();
      searchUser(
        this.currentPage,
        this.pageSize,
        this.username,
        this.tel,
        this.roleId
      ).then(res => {
        this.pageStopLoading(loadingInstance);
        if (res && res.code === 200) {
          this.userList = res.data.rows;
          this.userList.forEach(item => {
            item.roleId = this.roleFarmatter[item.roleId];
          });
          this.total = res.data.total;
        }
      });
    },
    delUser(id) {
      this.$confirm("删除后数据将不可恢复,确定删除?", "提示", {
        confirmButtonText: "确认删除",
        cancelButtonText: "我点错了",
        type: "warning"
      })
        .then(() => {
          let loadingInstance = this.pageLoading();
          delUser(id).then(res => {
            this.pageStopLoading(loadingInstance);
            if (res && res.code === 200) {
              this.$notify({
                showClose: true,
                title: "成功",
                message: "用户已删除",
                type: "success"
              });
            }
            this.searchUser();
          });
        })
        .catch(() => {});
    },
    freeze(id) {
      this.$confirm("确定冻结此用户吗,冻结后此用户将被限制登陆?", "提示", {
        confirmButtonText: "确认冻结",
        cancelButtonText: "我点错了",
        type: "warning"
      })
        .then(() => {
          let loadingInstance = this.pageLoading();
          freeze(id).then(res => {
            this.pageStopLoading(loadingInstance);
            if (res && res.code === 200) {
              this.$notify({
                showClose: true,
                title: "成功",
                message: "此用户已被冻结",
                type: "success"
              });
            }
            this.searchUser();
          });
        })
        .catch(() => {});
    },
    unfreeze(id) {
      this.$confirm("确定解冻此用户吗,解冻后此用户账户恢复正常?", "提示", {
        confirmButtonText: "确认解冻",
        cancelButtonText: "我点错了",
        type: "warning"
      })
        .then(() => {
          let loadingInstance = this.pageLoading();
          unfreeze(id).then(res => {
            this.pageStopLoading(loadingInstance);
            if (res && res.code === 200) {
              this.$notify({
                showClose: true,
                title: "成功",
                message: "此用户已解冻",
                type: "success"
              });
            }
            this.searchUser();
          });
        })
        .catch(() => {});
    },
    userUpgradeAdmin(id) {
      this.$confirm(
        "确定将此用户升级为管理员吗,升级后此用户将获得管理员全部权限?",
        "提示",
        {
          confirmButtonText: "确认升级",
          cancelButtonText: "我点错了",
          type: "warning"
        }
      )
        .then(() => {
          let loadingInstance = this.pageLoading();
          userUpgradeAdmin(id).then(res => {
            this.pageStopLoading(loadingInstance);
            if (res && res.code === 200) {
              this.$notify({
                showClose: true,
                title: "成功",
                message: "此用户已升级为管理员",
                type: "success"
              });
            }
            this.searchUser();
          });
        })
        .catch(() => {});
    },
    adminDemotionUser(id) {
      this.$confirm(
        "确定将此管理员降级为用户吗,降级后此用户将失去所有管理员权限?",
        "提示",
        {
          confirmButtonText: "确认降级",
          cancelButtonText: "我点错了",
          type: "warning"
        }
      )
        .then(() => {
          let loadingInstance = this.pageLoading();
          adminDemotionUser(id).then(res => {
            this.pageStopLoading(loadingInstance);
            if (res && res.code === 200) {
              this.$notify({
                showClose: true,
                title: "成功",
                message: "此管理员已降级为用户",
                type: "success"
              });
            }
            this.searchUser();
          });
        })
        .catch(() => {});
    },
    pageLoading() {
      this.loading = true;
      return Loading.service();
    },
    pageStopLoading(loadingInstance) {
      this.loading = false;
      this.$nextTick(() => {
        // 以服务的方式调用的 Loading 需要异步关闭
        loadingInstance.close();
      });
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
