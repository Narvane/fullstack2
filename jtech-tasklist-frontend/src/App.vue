<script setup lang="ts">
import { RouterView } from 'vue-router'
import { BToast } from 'bootstrap-vue-next'
import { useUiStore } from '@/stores/uiStore'

const ui = useUiStore()
</script>

<template>
  <div class="app-root d-flex flex-column h-100">
    <RouterView />

    <b-toaster name="app-toaster" class="toaster-fixed p-3" placement="bottom-right">
      <b-toast
        v-for="toast in ui.toasts"
        :key="toast.id"
        :title="toast.title"
        :variant="toast.variant"
        :progress-props="{ variant: toast.variant }"
        :model-value="3000"
      @hidden="ui.removeToast(toast.id)"
      class="mb-2"
      >
      {{ toast.message }}
      </b-toast>
    </b-toaster>


  </div>
</template>

<style>
  html,
  body,
  #app {
    height: 100%;
    margin: 0;
  }
  .app-root {
    height: 100%;
  }
  .toaster-fixed {
    position: fixed !important;
    bottom: 20px;
    right: 20px;
    z-index: 9999;
  }

  .toaster-fixed  .progress-bar {
    opacity: 0.5;
  }

</style>
