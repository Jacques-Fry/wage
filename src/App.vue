<template>
  <div id="app">
    <Layout :left-width="110" v-if="isLogin">
      <div class="layout-title" slot="layout-one-title">
        <MainTabBar />
      </div>
      <div class="layout-left" slot="layout-one-left">
        <MainNavBar />
      </div>
      <div class="layout-right" slot="layout-one-right">
        <router-view />
      </div>
      <div class="layout-foot" slot="layout-one-foot">© 最终解释权归创羿有限公司所有</div>
    </Layout>
    <div v-else>
      <router-view />
    </div>
  </div>
</template>

<script>
import Layout from "components/common/layout/LayoutOne";

import MainTabBar from "components/content/tabbar/MainTabBar";
import MainNavBar from "components/content/navbar/MainNavBar";

import { detail } from "network/user.js";

import { mapGetters } from "vuex";

export default {
  name: "app",
  computed: {
    ...mapGetters(["isLogin"])
  },
  mounted() {
    this.$bus.$on("toLogin", () => {
      this.$router.push("/login");
    });
  },
  components: {
    Layout,
    MainTabBar,
    MainNavBar
  },
  created() {
    let token = localStorage.getItem("token");
    if (!token) {
      this.$message.error("未登录,请先登录");
      this.$router.push("/login");
    } else {
      this.$store.commit("setToken", token);

      detail().then(res => {
        if (res&&res.code === 200) {
          this.$store.commit("setUser", res.data);
        }
      });
    }
  }
};
</script>

<style>
@import "assets/css/normalize.css";
</style>
<style scoped>
.layout-title,
.layout-left,
.layout-right,
.layout-foot {
  position: relative;
  height: 100%;
}
.layout-right {
  width: 100%;
  background-color: #eee;
}

.layout-title {
  background-color: rgb(52, 55, 60);
  color: #fff;
}
.layout-left {
  width: 110px;
  background-color: rgb(77, 95, 133);
}

.layout-foot {
  text-align: center;
  font-size: 12px;
}
</style>
