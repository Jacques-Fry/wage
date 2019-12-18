<template>
  <div class="main-nav-bar">
    <NavBar class="nav-bar-item" path="/home">
      <i slot="icon" class="el-icon-s-home"></i>
      <span slot="name">首页</span>
    </NavBar>

    <NavBar class="nav-bar-item" path="/wagelist">
      <i slot="icon" class="el-icon-notebook-2"></i>
      <span slot="name">工资条</span>
    </NavBar>

    <NavBar class="nav-bar-item" path="/user">
      <i slot="icon" class="el-icon-user"></i>
      <span slot="name">用户</span>
    </NavBar>
  </div>
</template>

<script type="text/javascript">
import NavBar from "components/common/navbar/NavBar";
export default {
  name: "MainNavBar",
  data() {
    return {};
  },
  components: { NavBar }
};
</script>

<style scoped>
.nav-bar {
  text-align: center;

  color: rgb(187, 206, 246);
}
.nav-bar-item {
  cursor: pointer;
  padding: 10px 0;
}
.nav-bar-item:hover {
  color: #fff;
  background-color: rgb(95, 111, 145);
}
.nav-bar-item i {
  margin-bottom: 5px;
  font-size: 40px;
}
</style>
