import Vue from 'vue'
import Router from 'vue-router'

const Home = () => import('views/home/Home.vue')
const User = () => import('views/user/User.vue')
const WageList = () => import('views/wage/WageList.vue')
const NewWage = () => import('views/wage/NewWage.vue')
const UpdWage = () => import('views/wage/UpdWage.vue')
const Login = () => import('views/login/Login.vue')
Vue.use(Router)

const routes = [
  {
    path: '*', // 页面不存在的情况下会跳到404页面
    redirect: '/',
    name: 'notFound',
    hidden: true
  },
  {
    path: '',
    redirect: '/user'
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/home',
    component: Home
  },
  {
    path: '/user',
    component: User
  },
  {
    path: '/wagelist',
    component: WageList
  },
  {
    path: '/newwage',
    component: NewWage
  },
  {
    path: '/updwage/:id',
    name: "updwage",
    component: UpdWage
  }

]

var router = new Router({
  routes,
  mode: 'history'
})

export default router;
