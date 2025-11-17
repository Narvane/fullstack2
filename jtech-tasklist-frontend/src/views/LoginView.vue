<template>
  <b-container
    fluid
    class="h-100 d-flex align-items-center justify-content-center bg-light"
  >
    <b-card style="max-width: 400px; width: 100%;">
      <h1 class="h4 mb-3 text-center">Login</h1>

      <b-form @submit.prevent="onSubmit">
        <b-form-group label="Email" label-for="email">
          <b-form-input
            id="email"
            v-model="email"
            type="email"
            autocomplete="email"
            required
            :disabled="auth.isLoading"
          />
        </b-form-group>

        <b-form-group label="Password" label-for="password" class="mt-2">
          <b-form-input
            id="password"
            v-model="password"
            type="password"
            autocomplete="current-password"
            required
            :disabled="auth.isLoading"
          />
        </b-form-group>

        <div class="d-grid gap-2 mt-3">
          <b-button
            type="submit"
            variant="primary"
            :disabled="auth.isLoading"
          >
            <span v-if="auth.isLoading" class="spinner-border spinner-border-sm me-2" />
            Login
          </b-button>
        </div>
      </b-form>
    </b-card>
  </b-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { useUiStore } from '@/stores/uiStore'
import {
  BButton,
  BCard,
  BContainer,
  BForm,
  BFormGroup,
  BFormInput,
} from 'bootstrap-vue-next'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()
const ui = useUiStore()

const email = ref('')
const password = ref('')

async function onSubmit() {
  if (!email.value || !password.value) {
    ui.showError('Preencha email e senha')
    return
  }

  try {
    await auth.login(email.value, password.value)
    const redirect = (route.query.redirect as string) || '/board'
    await router.push(redirect)
  } catch {}
}
</script>
