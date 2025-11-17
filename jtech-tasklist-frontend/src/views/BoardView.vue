<script setup lang="ts">
import { onMounted } from 'vue'
import { BButton, BCard, BContainer, BSpinner } from 'bootstrap-vue-next'
import TaskListColumn from '@/components/TaskListColumn.vue'
import { useTaskStore } from '@/stores/taskStore'

const store = useTaskStore()

onMounted(() => {
  store.fetchTaskLists()
})
</script>

<template>
  <b-container fluid class="py-3 h-100 bg-light d-flex flex-column">
    <header class="d-flex align-items-center mb-3">
      <h1 class="h4 mb-0">My board</h1>
    </header>

    <div
      v-if="store.isLoadingBoard"
      class="flex-grow-1 d-flex align-items-center justify-content-center"
    >
      <b-spinner />
    </div>

    <div
      v-else
      class="d-flex align-items-start gap-3 overflow-auto pb-2 flex-grow-1"
    >
      <TaskListColumn
        v-for="list in store.taskLists"
        :key="list.id"
        :task-list="list"
        @add-task="store.addTask"
        @update-title="store.updateTaskListTitle"
        @toggle-completed="store.toggleTaskCompleted"
        @update-task-title="store.updateTaskTitle"
        @delete-task="store.deleteTask"
        @delete-task-list="store.deleteTaskList"
      />


      <b-card
        class="flex-shrink-0 d-flex align-items-center justify-content-center text-center"
        style="min-width: 260px; max-height: 80vh; border-style: dashed;"
        body-class="p-3"
      >
        <b-button
          variant="outline-primary"
          :disabled="store.isSaving"
          @click="() => store.addTaskList()"
        >
          <span
            v-if="store.isSaving"
            class="spinner-border spinner-border-sm me-2"
          />
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
