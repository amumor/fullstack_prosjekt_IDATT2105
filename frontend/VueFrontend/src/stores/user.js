// stores/user.js
import {defineStore} from 'pinia'

export const userStore = defineStore('user', {
    state: () => ({
        token: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        isLoggedIn: false,
    }),
    actions: {
        setUser(user) {
            this.token = user.token;
            this.firstName = user.firstName
            this.lastName = user.lastName
            this.email = user.email
            this.phoneNumber = user.phoneNumber
        },
        clearUser() {
            this.token = ''
            this.firstName = ''
            this.lastName = ''
            this.email = ''
            this.phoneNumber = ''
        },
        login(user) {
            this.token = user.token;
            this.isLoggedIn = true
            this.setUser(user) // TODO remove? set user manually on login?
        },
        logout() {
            this.isLoggedIn = false
            this.clearUser()
        }
    }
})
