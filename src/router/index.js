import Vue from 'vue'
import Router from 'vue-router'

const Home = () => import('views/home/Home.vue')
const UserList = () => import('views/user/UserList.vue')
const NewUser = () => import('views/user/NewUser.vue')
const UpdUser = () => import('views/user/UpdUser.vue')
const WageList = () => import('views/wage/WageList.vue')
const NewWage = () => import('views/wage/NewWage.vue')
const UpdWage = () => import('views/wage/UpdWage.vue')
const Login = () => import('views/login/Login.vue')
const Detail = () => import('views/detail/Detail.vue')
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
    redirect: '/userlist'
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
    path: '/userlist',
    component: UserList
  }, {
    path: '/newuser',
    component: NewUser
  },
  {
    path: '/upduser/:id',
    name: "upduser",
    component: UpdUser
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
  },
  {
    path: '/detail',
    name: "detail",
    component: Detail
  }

]

var router = new Router({
  routes,
  mode: 'history'
})

export default router;
