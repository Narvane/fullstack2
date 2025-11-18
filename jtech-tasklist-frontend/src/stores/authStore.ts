import { defineStore } from 'pinia'
import api from '@/services/api'
import { useUiStore } from './uiStore'

interface User {
  id: string
  name: string
  email: string
}

interface LoginResponse {
  id: string
  name: string
  email: string
  token: string
}

interface RegisterResponse {
  id: string
  name: string
  email: string
  token: string
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
        const { data } = await api.post<LoginResponse>('/api/v1/auth/login', {
          email,
          password,
        })

        this.token = data.token
        this.user = {
          id: data.id,
          name: data.name,
          email: data.email,
        }

        localStorage.setItem('auth_token', this.token)
        localStorage.setItem('auth_user', JSON.stringify(this.user))

        ui.showSuccess('Login successful')
      } catch (error: any) {
        ui.showError(
          error?.response?.data?.message ||
          'Invalid credentials or login error'
        )
        throw error
      } finally {
        this.isLoading = false
      }
    },

    async register(name: string, email: string, password: string) {
      const ui = useUiStore()
      this.isLoading = true

      try {
        const { data } = await api.post<RegisterResponse>('/api/v1/auth/register', {
          name,
          email,
          password,
        })

        this.token = data.token
        this.user = {
          id: data.id,
          name: data.name,
          email: data.email,
        }

        localStorage.setItem('auth_token', this.token)
        localStorage.setItem('auth_user', JSON.stringify(this.user))

        ui.showSuccess('Registration successful')
      } catch (error: any) {
        ui.showError(
          error?.response?.data?.message ||
          'Registration error'
        )
        throw error
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
