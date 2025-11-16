<script setup lang="ts">
import { RouterView } from 'vue-router'
import BoardView from './views/BoardView.vue'
</script>

<template>
    <div class="app-root h-100">
      <BoardView />
    </div>

  <RouterView />
</template>

<style>
  html, body, #app {
    height: 100%;
    margin: 0;
  }
  .app-root {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
</style>
