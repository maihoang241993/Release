import 'core-js/stable'
import Vue from 'vue'
import App from './App'
import { router } from './router'
import CoreuiVue from '@coreui/vue'
import { iconsSet as icons } from './assets/icons/icons.js'
import store from './store'
import 'babel-polyfill'
import VueSimpleAlert from "vue-simple-alert";
import VueLoading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';

Vue.config.performance = true
Vue.use(CoreuiVue)
Vue.use(VueSimpleAlert)
Vue.use(VueLoading)

new Vue({
  el: '#app',
  store,
  router,
  icons,
  template: '<App/>',
  components: {
    App
  }
})
