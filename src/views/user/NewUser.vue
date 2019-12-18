<template>
  <div class="new-user">
    <div class="black" @click="$router.go(-1)">
      <span class="el-icon-arrow-left"></span> 返回
    </div>
    <div class="user-title">
      <span>创建用户</span>
    </div>
    <div class="user-content">
      <el-form
        :model="user"
        status-icon
        :rules="rules"
        ref="user"
        label-width="100px"
        class="user-from"
      >
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="user.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="tel">
          <el-input type="text" v-model="user.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="user.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="user.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select v-model="user.type" placeholder="请选择用户类型">
            <el-option
              v-for="(item,index) in userTypes"
              :key="'userTypes'+index"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm()" :loading="loading">立即提交</el-button>
          <el-button @click="resetForm()">重置数据</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { Loading } from "element-ui";

import { createUser } from "network/user.js";
export default {
  data() {
    let reg1 = /^1\d{10}$/;
    let validateTel = (rule, value, callback) => {
      if (value !== "") {
        if (!reg1.test(value)) {
          callback("手机号必须为11位");
        } else {
          callback();
        }
      } else {
        callback();
      }
    };

    let reg2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
    let validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.user.checkPass !== "") {
          this.$refs.user.validateField("checkPass");
        }
        if (!reg2.test(value)) {
          callback("密码必须为6-16位,且密码中至少包含一个字母");
        } else {
          callback();
        }
      }
    };
    let validatePassword2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.user.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      userTypes: [
        { name: "普通用户", value: "user" },
        { name: "管理员", value: "admin" }
      ],
      user: {
        username: "",
        tel: "",
        password: "",
        checkPass: "",
        type: "user"
      },
      rules: {
        username: [
          { required: true, message: "用户名不允许为空", trigger: "blur" }
        ],
        tel: [{ validator: validateTel, trigger: "blur" }],
        password: [
          { validator: validatePassword, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ],
        checkPass: [
          { validator: validatePassword2, trigger: "blur" },
          { required: true, message: "不允许为空", trigger: "blur" }
        ]
      },
      loading: false
    };
  },
  methods: {
    submitForm() {
      this.$refs["user"].validate(valid => {
        if (valid) {
          this.loading = true;

          let loadingInstance = Loading.service();

          createUser(this.user).then(res => {
            this.loading = false;

            this.$nextTick(() => {
              // 以服务的方式调用的 Loading 需要异步关闭
              loadingInstance.close();
            });

            if (res && res.code === 200) {
              this.$message({
                showClose: true,
                message: "创建成功",
                type: "success"
              });
              this.$confirm("创建成功，是否继续创建用户?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              })
                .then(() => {
                  this.resetForm();
                })
                .catch(() => {
                  this.$router.push("/userlist");
                });
            }
          });
        } else {
          return false;
        }
      });
    },
    resetForm() {
      this.$refs["user"].resetFields();
    }
  }
};
</script>
<style scoped>
.new-user {
  position: relative;
  overflow: hidden;

  text-align: center;
  width: 100%;
  height: 100%;
}
.user-title {
  font-size: 30px;
  font-weight: 600;
  line-height: 60px;
  height: 60px;

  background-color: #fff;
}
.black {
  position: absolute;
  margin-left: 20px;
  cursor: pointer;

  font-size: 14px;
  line-height: 60px;

  color: #959595;
}
.black:hover {
  color: #66b1ff;
}
.user-content {
  display: inline-block;
  margin-top: 20px;

  width: 400px;
}
.user-from {
  text-align: left;
}
</style>