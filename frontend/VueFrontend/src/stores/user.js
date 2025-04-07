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
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.email = user.email;
            this.phoneNumber = user.phoneNumber;
        },
        clearUser() {
            this.token = ''
            this.firstName = ''
            this.lastName = ''
            this.email = ''
            this.phoneNumber = ''
        },
        login(user) {
            this.isLoggedIn = true
            localStorage.setItem("user", JSON.stringify(user));
            this.setUser(user)
        },
        logout() {
            this.isLoggedIn = false
            localStorage.removeItem('user');
            this.clearUser()
        },
        restoreUser() {
            const storedUser = localStorage.getItem('user');
            if (storedUser) {
                try {
                    const user = JSON.parse(storedUser);
                    console.log(user);
                    this.login(user);

                } catch (e) {
                    console.error('Failed to parse user from localStorage:', e);
                    localStorage.removeItem('user');
                }
            }
        }
    }
});