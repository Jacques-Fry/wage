import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: {
      type: String,
      default() {
        return ''
      }
    },
    user: {}
  },
  mutations: {
    setToken(state, payLoad) {
      state.token = payLoad
    },
    setUser(state, payLoad) {
      state.user = payLoad
    },
  },
  getters: {
    getToken(state) {
      return state.token
    },
    isLogin(state) {
      return state.token != undefined && typeof state.token === "string" && state.token != "";
    },
    getUser(state) {
      return state.user
    }
  },
  actions: {
  },
  modules: {
  }
})
