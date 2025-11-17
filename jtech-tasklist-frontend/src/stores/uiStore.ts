import { defineStore } from 'pinia'

export interface UiToast {
  id: number
  title?: string
  message: string
  variant?: 'primary' | 'success' | 'danger' | 'warning' | 'info'
}

let nextToastId = 1

export const useUiStore = defineStore('uiStore', {
  state: () => ({
    toasts: [] as UiToast[],
  }),

  actions: {
    pushToast(toast: Omit<UiToast, 'id'>) {
      this.toasts.push({
        id: nextToastId++,
        variant: toast.variant ?? 'danger',
        ...toast,
      })
    },

    removeToast(id: number) {
      this.toasts = this.toasts.filter((t) => t.id !== id)
    },

    showError(message: string, title = 'Error') {
      this.pushToast({ message, title, variant: 'danger' })
    },

    showSuccess(message: string, title = 'Success') {
      this.pushToast({ message, title, variant: 'success' })
    },
  },
})
