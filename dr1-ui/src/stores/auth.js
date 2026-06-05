import { defineStore } from 'pinia'
import { login as loginApi, getCurrentUser } from '../api/auth'

const TOKEN_KEY = 'dr1_token'
const USER_KEY = 'dr1_user'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem(TOKEN_KEY) || '',
    user: JSON.parse(localStorage.getItem(USER_KEY) || 'null')
  }),
  actions: {
    async login(form) {
      const tokenInfo = await loginApi(form)
      this.token = tokenInfo.token
      localStorage.setItem(TOKEN_KEY, tokenInfo.token)
      await this.loadUser()
      return tokenInfo
    },
    async loadUser() {
      const user = await getCurrentUser()
      this.user = user
      localStorage.setItem(USER_KEY, JSON.stringify(user))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(USER_KEY)
    }
  }
})

