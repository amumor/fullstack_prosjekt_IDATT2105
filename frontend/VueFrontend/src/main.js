import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router/index.js'
import 'leaflet/dist/leaflet.css';
import 'vue-map-ui/dist/normalize.css';
import 'vue-map-ui/dist/style.css';
import 'vue-map-ui/dist/theme-all.css';
import './assets/main.css'
import {userStore} from "@/stores/user.js";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import i18n from './i18n';

const app = createApp(App)

app.use(i18n)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

app.use(router)
const store = userStore();
store.restoreUser();


app.mount('#app')
