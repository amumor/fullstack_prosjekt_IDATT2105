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

const app = createApp(App)

app.use(createPinia())
app.use(router)

const store = userStore();
store.restoreUser();


app.mount('#app')
