import Toast from "./Toast.vue"

const obj = {}

obj.install = (Vue) => {
  //创建组件构造器
  const toastContrustor = Vue.extend(Toast)
  //通过new的方式创建构造器对象
  const toast = new toastContrustor()
  //挂载对象
  toast.$mount(document.createElement('div'))
  //添加toast模板
  document.body.appendChild(toast.$el)

  Vue.prototype.$toast = toast
}

export default obj