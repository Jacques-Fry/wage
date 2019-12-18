<template>
  <div class="toast" v-show="isShow">{{message}}</div>
</template>

<script type="text/javascript">
export default {
  data() {
    return {
      message: "",
      isShow: false,
      timeOut: null
    };
  },
  methods: {
    show(message = "默认文字", time = 2000) {
      this.message = message;
      this.isShow = true;
      //解决点击过快的bug
      if (this.timeOut) clearTimeout(this.timeOut);
      this.timeOut = setTimeout(() => {
        this.message = "";
        this.isShow = false;
      }, time);
    }
  }
};
</script>

<style scoped>
.toast {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 999;

  padding: 16px 20px;
  border-radius: 10px;

  color: #fff;
  background-color: rgba(0, 0, 0, 0.75);

  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
}
</style>
