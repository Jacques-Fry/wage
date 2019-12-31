<template>
  <div class="detail">
    <div class="detail-title">个人中心</div>
    <el-divider></el-divider>

    <div class="detail-content">
      <table class="user-table" width="100%">
        <tr>
          <th>ID</th>
          <td>{{user.id}}</td>
        </tr>
        <tr>
          <th>用户名</th>
          <td>{{user.username}}</td>
        </tr>
        <tr>
          <th>手机号</th>
          <td>{{user.tel | telFilter}}</td>
        </tr>
        <tr>
          <th>身份</th>
          <td>{{user.roleName}}</td>
        </tr>
        <tr>
          <th>账号状态</th>
          <td>{{user.status | statusFilter}}</td>
        </tr>
        <tr>
          <th>注册时间</th>
          <td>{{user.createTime | timeFilter}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script type="text/javascript">
import { detail } from "network/user.js";
import { formatDate } from "common/utils.js";
export default {
  name: "Detail",
  data() {
    return {
      user: {}
    };
  },
  components: {},
  created() {
    detail().then(res => {
      if (res && res.code === 200) {
        this.user = res.data;
      }
    });
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  },
  filters: {
    timeFilter(value) {
      return formatDate(new Date(value), "yyyy年MM月dd日");
    },
    statusFilter(value) {
      if (value === "0") return "正常";
      if (value === "1") return "已冻结";
    },
    telFilter(value) {
      return value || "暂无";
    }
  }
};
</script>

<style scoped>
.detail {
  position: relative;
  height: 100%;
  width: 100%;
  background-color: #fff;
}
.detail-title {
  text-align: center;

  font-size: 30px;
  font-weight: 600;
  height: 60px;
  line-height: 60px;
  width: 100%;
}
.user-table tr {
  line-height: 30px;
}
.user-table tr th {
  text-align: right;
  padding-right: 20px;

  width: 50%;

  color: #437fff;
}
.user-table tr td {
  text-align: left;
  padding-left: 20px;

  width: 50%;
}
</style>
