<template>
  <div>
    <div class="login-container">
      <el-form
        :model="user"
        :rules="rules"
        status-icon
        ref="user"
        label-position="left"
        label-width="0px"
        class="demo-ruleForm login-page"
      >
        <h3 class="title">创羿OA后台系统登录</h3>
        <el-form-item prop="username">
          <el-input type="text" v-model="user.username" auto-complete="off" placeholder="用户名/手机号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="user.password" auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item style="width:100%;">
          <el-button type="primary" style="width:100%;" @click="handleSubmit" :loading="logining">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { login, detail } from "network/user.js";

import { mapGetters } from "vuex";

export default {
  data() {
    return {
      logining: false,
      user: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },
  computed: {
    ...mapGetters(["isLogin"])
  },
  mounted() {
    if (this.isLogin) this.$router.push("/");
  },
  methods: {
    handleSubmit(event) {
      this.$refs.user.validate(valid => {
        if (valid) {
          this.logining = true;
          login(this.user.username, this.user.password).then(res => {
            this.logining = false;
            if (res.code == 200) {
              localStorage.setItem("token", res.data.token);
              this.$store.commit("setToken", res.data.token);
              detail().then(res => {
                if (res.code === 200) {
                  this.$store.commit("setUser", res.data);
                  this.$notify({
                    showClose: true,
                    title: "成功",
                    message: "欢迎来到创羿OA后台",
                    type: "success"
                  });
                }
              });
            }
          });
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.login-container {
  position: relative;
  width: 100vw;
  height: 100vh;
}
.login-page {
  position: absolute;
  top: 40%;
  left: 50%;
  margin: auto;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  -webkit-border-radius: 5px;
  border-radius: 5px;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
label.el-checkbox.rememberme {
  margin: 0px 0px 15px;
  text-align: left;
}
</style>