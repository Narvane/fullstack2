import { defineStore } from 'pinia'
import api from '@/services/api'
import { useUiStore } from './uiStore'

interface User {
  id: number
  name: string
  email: string
}

interface LoginResponse {
  token: string
  user: User
}

export const useAuthStore = defineStore('authStore', {
  state: () => ({
    token: '' as string,
    user: null as User | null,
    isLoading: false,
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
  },

  actions: {
    loadFromStorage() {
      const token = localStorage.getItem('auth_token')
      const user = localStorage.getItem('auth_user')
      if (token) this.token = token
      if (user) this.user = JSON.parse(user)
    },

    async login(email: string, password: string) {
      const ui = useUiStore()
      this.isLoading = true

      try {
        // const { data } = await api.post<LoginResponse>('/auth/login', {
        //   email,
        //   password,
        // })


        const data: LoginResponse = {
          token: 'token-fake-123',
          user: {
            id: 1,
            name: 'Usuário Mockado',
            email,
          },
        }

        this.token = data.token
        this.user = data.user

        localStorage.setItem('auth_token', this.token)
        localStorage.setItem('auth_user', JSON.stringify(this.user))

        ui.showSuccess('Login successful. (mock)')
      } catch (error: any) {
        ui.showError(
          error?.response?.data?.message ||
          'Invalid credentials or login error'
        )
        // throw error
      } finally {
        this.isLoading = false
      }
    },

    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('auth_token')
      localStorage.removeItem('auth_user')
    },
  },
})
