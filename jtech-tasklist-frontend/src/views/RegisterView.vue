<template>
  <b-container
    fluid
    class="h-100 d-flex align-items-center justify-content-center bg-light"
  >
    <b-card style="max-width: 400px; width: 100%;">
      <h1 class="h4 mb-3 text-center">Register</h1>

      <b-form @submit.prevent="onSubmit">
        <b-form-group label="Name" label-for="name">
          <b-form-input
            id="name"
            v-model="name"
            type="text"
            autocomplete="name"
            required
            :disabled="auth.isLoading"
          />
        </b-form-group>

        <b-form-group label="Email" label-for="email" class="mt-2">
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
            autocomplete="new-password"
            required
            :disabled="auth.isLoading"
          />
        </b-form-group>

        <b-form-group label="Confirm Password" label-for="confirmPassword" class="mt-2">
          <b-form-input
            id="confirmPassword"
            v-model="confirmPassword"
            type="password"
            autocomplete="new-password"
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
            Register
          </b-button>
        </div>
      </b-form>

      <div class="text-center mt-3">
        <p class="mb-0 text-muted">
          Already have an account?
          <router-link to="/login" class="text-decoration-none">
            Login here
          </router-link>
        </p>
      </div>
    </b-card>
  </b-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
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
const auth = useAuthStore()
const ui = useUiStore()

const name = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')

async function onSubmit() {
  if (!name.value || !email.value || !password.value || !confirmPassword.value) {
    ui.showError('Please fill in all fields')
    return
  }

  if (password.value !== confirmPassword.value) {
    ui.showError('Passwords do not match')
    return
  }

  if (password.value.length < 6) {
    ui.showError('Password must be at least 6 characters')
    return
  }

  try {
    await auth.register(name.value, email.value, password.value)
    await router.push('/board')
  } catch {}
}
</script>

