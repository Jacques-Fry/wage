<template>
  <div class="tab-bar">
    <div class="tab-bar-slot">
      <slot name="tab-bar-left"></slot>
    </div>
    <div class="tab-bar-center tab-bar-slot">
      <slot name="tab-bar-center"></slot>
    </div>
    <div class="tab-bar-slot">
      <slot name="tab-bar-right"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "TabBar",
  props: {
    tabBarHeight: {
      type: Number,
      default() {
        return 60;
      }
    }
  }
};
</script>

<style scoped>
.tab-bar {
  position: relative;
  overflow: hidden;
  display: flex;

  height: 100%;
}
.tab-bar-slot {
  position: relative;
  height: 100%;
}
.tab-bar-center {
  flex: 1;
}
</style>