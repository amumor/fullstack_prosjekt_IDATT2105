import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import ProfileView from '@/views/ProfileView.vue'
import ListingView from '@/views/ListingView.vue'
import NewListingView from '@/views/NewListingView.vue'
import MyListingsView from '@/views/MyListingsView.vue'

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
    },
    {
      path: '/profile/myListings',
      name: 'myListings',
      component: MyListingsView
    }
  ],
})

export default router
