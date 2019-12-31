<template>
  <div class="layout-one">
    <div class="layout-one-title" :style="titleStyle">
      <slot name="layout-one-title">页面头部</slot>
    </div>
    <div class="layout-one-content" :style="contentStyle">
      <div class="layout-one-left" :style="leftStyle">
        <slot name="layout-one-left">页面左边</slot>
      </div>
      <div class="layout-one-right" :style="rightStyle">
        <slot name="layout-one-right">页面右边</slot>
      </div>
    </div>
    <div class="layout-one-foot" :style="footStyle">
      <slot name="layout-one-foot">页面尾部</slot>
    </div>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "LayoutOne",
  props: {
    titleHeight: {
      type: Number,
      default() {
        return 60;
      }
    },
    footHeight: {
      type: Number,
      default() {
        return 30;
      }
    },
    leftWidth: {
      type: Number,
      default() {
        return 250;
      }
    }
  },
  computed: {
    contentStyle() {
      return {
        height: "calc(100% - " + (this.titleHeight + this.footHeight) + "px)"
      };
    },
    titleStyle() {
      return {
        height: this.titleHeight + "px",
        lineHeight: this.titleHeight + "px"
      };
    },
    leftStyle() {
      return {
        width: this.leftWidth + "px"
      };
    },
    rightStyle() {
      return {
        width: "calc(100% - " + this.leftWidth + "px)"
      };
    },
    footStyle() {
      return {
        height: this.footHeight + "px",
        lineHeight: this.footHeight + "px"
      };
    }
  }
};
</script>

<style scoped>
.layout-one {
  overflow: hidden;
  position: relative;

  width: 100vw;
  height: 100vh;
}
.layout-one-title {
  position: relative;
  width: 100%;
  box-shadow: 0 2px 0 rgba(0, 0, 0, 0.05);
}
.layout-one-content {
  position: relative;
  display: flex;

  width: 100%;
}
.layout-one-left {
  position: relative;
  box-shadow: 2px 0 0 rgba(0, 0, 0, 0.05);
}
.layout-one-right {
  position: relative;
  height: 100%;
}
.layout-one-foot {
  position: relative;
  width: 100%;

  box-shadow: 0 -2px 0 rgba(0, 0, 0, 0.05);
}
</style>
