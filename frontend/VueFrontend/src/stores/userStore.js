// stores/user.js
import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
  state: () => ({
    id: null,
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    isLoggedIn: false,
  }),
  actions: {
    setUser(user) {
        this.id = user.id
        this.firstName = user.firstName
        this.lastName = user.lastName
        this.email = user.email
        this.phoneNumber = user.phoneNumber
    },
    clearUser() {
        this.id = null
        this.firstName = ''
        this.lastName = ''
        this.email = ''
        this.phoneNumber = ''
    },
    login(email) {
        this.isLoggedIn = true
        this.email = email
    },
    logout() {
        this.isLoggedIn = false
        this.clearUser()
    }
  }
})
