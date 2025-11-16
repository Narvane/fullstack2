<script setup lang="ts">
import { BButton, BCard, BContainer } from 'bootstrap-vue-next'
import TaskListColumn from '@/components/TaskListColumn.vue'
import { useTaskStore } from '@/stores/taskStore'

const store = useTaskStore()

store.initDemoData()
</script>

<template>
  <b-container fluid class="py-3 h-100 bg-light d-flex flex-column">
    <header class="d-flex align-items-center mb-3">
      <h1 class="h4 mb-0">My board</h1>
    </header>

    <div class="d-flex align-items-start gap-3 overflow-auto pb-2 flex-grow-1">
      <TaskListColumn
        v-for="list in store.taskLists"
        :key="list.id"
        :task-list="list"
        @add-task="store.addTask"
        @update-title="store.updateTaskListTitle"
        @toggle-completed="store.toggleTaskCompleted"
        @update-task-title="store.updateTaskTitle"
      />

      <b-card
        class="flex-shrink-0 d-flex align-items-center justify-content-center text-center"
        style="min-width: 260px; max-height: 80vh; border-style: dashed;"
        body-class="p-3"
      >
        <b-button variant="outline-primary" @click="() => store.addTaskList()">
          + New tasklist
        </b-button>
      </b-card>
    </div>
  </b-container>
</template>

<style scoped>
:deep(html, body, #app) {
  height: 100%;
}
</style>
