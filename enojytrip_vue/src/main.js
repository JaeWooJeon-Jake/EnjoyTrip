import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

/* API 호출 */
import "@/api/lib/bootstrap";

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
