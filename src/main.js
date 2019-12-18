import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import store from './store'
import toast from 'components/common/toast'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import vuescroll from 'vuescroll';

Vue.prototype.$bus = new Vue()

Vue.use(vuescroll, {
  // 在这里设置全局默认配置
  ops: {
    vuescroll: {
      //选择一个模式, native 或者 slide
      mode: "native",
      //如果父容器不是固定高度，请设置为 number , 否则保持默认的percent即可
      sizeStrategy: "number",
      //是否检测内容尺寸发生变化
      detectResize: true
    },
    scrollPanel: {
      scrollingX: false
    },
    rail: {
      //轨道的背景色
      background: "#666666",
      //轨道的尺寸
      size: "5px",
      //轨道的透明度
      opacity: 0.2
    },
    bar: {
      //在鼠标离开容器后多长时间隐藏滚动条
      showDelay: 2000,
      //滚动条背景色
      background: "#666666",
      //滚动条透明度
      opacity: 0.5
    }
  },
  name: 'vue-scroll' // 在这里自定义组件名字，默认是vueScroll
});

Vue.use(toast)

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
