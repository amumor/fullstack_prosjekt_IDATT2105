import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import ProfileView from '@/views/ProfileView.vue'
import ListingView from '@/views/ListingView.vue'
import NewListingView from '@/views/NewListingView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView
    },
    {
      path: '/listing',
      name: 'listing',
      component: ListingView
    },
    {
      path: '/newListing',
      name: 'newListing',
      component: NewListingView
    }
  ],
})

export default router
