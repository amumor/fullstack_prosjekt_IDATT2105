import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/Login/LoginView.vue'
import ProfileView from '@/views/Profile/ProfileView.vue'
import ListingView from '@/views/Listing/ListingView.vue'
import NewListingView from '@/views/Listing/NewListingView.vue'
import MyListingsView from '@/views/Profile/MyListingsView.vue'
import FavoritesView from '@/views/Profile/FavoritesView.vue'
import InboxView from '@/views/Inbox/InboxView.vue'
import EditProfileView from '@/views/Profile/EditProfileView.vue'
import MapView from '@/views/MapView.vue'

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
      path: '/listing/:id',
      name: 'listing',
      component: ListingView,
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
    },
    {
      path: '/profile/favorites',
      name: 'favorites',
      component: FavoritesView
    },
    {
      path: '/profile/edit',
      name: 'editProfile',
      component: EditProfileView
    },
    {
      path: '/inbox',
      name: 'inbox',
      component: InboxView
    },
    {
      path: '/map',
      name: 'map',
      component: MapView
    }
  ],
})

export default router


