<template>
  <div class="nav-bar" :style="activeStyle" @click="itemClick">
    <div>
      <slot name="icon"></slot>
    </div>
    <div>
      <slot name="name"></slot>
    </div>
  </div>
</template>

<script type="text/javascript">
export default {
  name: "NavBar",
  props: {
    path: String,
    activeColor: {
      type: String,
      default() {
        return "#fff";
      }
    },
    activeBackgroundColor: {
      type: String,
      default() {
        return "#5f6f97";
      }
    }
  },
  computed: {
    isActive() {
      return this.$route.path.indexOf(this.path) !== -1;
    },
    activeStyle() {
      return this.isActive
        ? {
            color: this.activeColor,
            backgroundColor: this.activeBackgroundColor
          }
        : {};
    }
  },
  methods: {
    itemClick() {
      if (this.isActive) return;
      this.$router.replace(this.path);
    }
  }
};
</script>

<style scoped>
</style>
