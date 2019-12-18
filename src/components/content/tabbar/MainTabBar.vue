<template>
  <TabBar>
    <div class="tab-bar-left" slot="tab-bar-left">创羿企业OA系统</div>
    <div slot="tab-bar-center"></div>
    <div class="tab-bar-right" slot="tab-bar-right">
      <div>
        <el-avatar class="user-head-portrait" icon="el-icon-user-solid"></el-avatar>
      </div>
      <div class="user-name">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{user.username}}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu class="el-dropdown-item" slot="dropdown">
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="loginOut">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </TabBar>
</template>

<script type="text/javascript">
import TabBar from "components/common/tabbar/TabBar";

import { mapGetters } from "vuex";

export default {
  name: "MainTabBar",
  data() {
    return {};
  },
  computed: {
    ...mapGetters({
      user: "getUser"
    })
  },
  components: {
    TabBar
  },
  methods: {
    loginOut() {
      localStorage.clear();
      this.$store.commit("setToken", "");
      this.$bus.$emit("toLogin");
    }
  }
};
</script>

<style scoped>
.tab-bar-left {
  padding: 0 20px;
  font-size: 30px;
  font-weight: 600;
}
.tab-bar-right {
  overflow: hidden;
  display: flex;
  padding: 0 20px;

  vertical-align: middle;

  line-height: 60px;
  font-size: 20px;
  font-weight: 600;
}
.tab-bar-right:hover {
  background-color: rgb(30, 35, 39);
}

.user-head-portrait {
  vertical-align: middle;
}

.user-name {
  cursor: pointer;

  margin-left: 10px;
}
.el-dropdown-link {
  cursor: pointer;
  color: #fff;
}
.el-dropdown-item {
  color: #fff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
