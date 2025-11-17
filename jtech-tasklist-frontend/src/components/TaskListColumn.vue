<script setup lang="ts">
import { ref, watch } from 'vue'
import TaskCard from './TaskCard.vue'
import type { TaskList } from '@/types/tasklist.ts'

import { BButton, BCard, BFormInput } from 'bootstrap-vue-next'

const props = defineProps<{
  taskList: TaskList
}>()

const emit = defineEmits<{
  (e: 'add-task', taskListId: number, taskTitle: string): void
  (e: 'update-title', taskListId: number, newTitle: string): void
  (e: 'toggle-completed', taskListId: number, taskId: number): void
  (e: 'update-task-title', taskListId: number, taskId: number, newTitle: string): void
  (e: 'delete-task', taskListId: number, taskId: number): void
  (e: 'delete-task-list', taskListId: number): void
}>()

const isAdding = ref(false)
const newTaskTitle = ref('')

const editableTitle = ref(props.taskList.title)

watch(
  () => props.taskList.title,
  newVal => {
    editableTitle.value = newVal
  }
)

function confirmAddTask(): void {
  const title = newTaskTitle.value.trim()
  if (!title) return

  emit('add-task', props.taskList.id, title)
  newTaskTitle.value = ''
  isAdding.value = false
}

function cancelAddTask(): void {
  newTaskTitle.value = ''
  isAdding.value = false
}

function emitTitleChange(): void {
  const trimmed = editableTitle.value.trim()
  if (!trimmed) {
    editableTitle.value = props.taskList.title
    return
  }
  if (trimmed !== props.taskList.title) {
    emit('update-title', props.taskList.id, trimmed)
  }
}

function onDeleteList() {
  emit('delete-task-list', props.taskList.id)
}
</script>

<template>
  <b-card
    class="tasklist-card flex-shrink-0 d-flex flex-column"
    style="min-width: 260px; max-height: 80vh;"
    body-class="p-2 d-flex flex-column"
  >
    <div class="d-flex align-items-center mb-2">

      <b-form-input
        v-model="editableTitle"
        @blur="emitTitleChange"
        size="sm"
        class="fw-semibold border-0 bg-transparent px-1 flex-grow-1"
      />

      <b-button class="btn-icon btn-sm  delete-list-btn" variant="danger" @click.stop="emit('delete-task-list', taskList.id)">
        <i class="bi bi-trash-fill"></i>
      </b-button>
    </div>


    <div class="d-flex flex-column gap-2 overflow-auto pe-1 mb-2">
      <TaskCard
        v-for="task in taskList.tasks"
        :key="task.id"
        :task="task"
        @toggle-completed="emit('toggle-completed', taskList.id, task.id)"
        @update-title="title => emit('update-task-title', taskList.id, task.id, title)"
        @delete="emit('delete-task', taskList.id, task.id)"
      />
    </div>

    <div class="mt-auto">
      <template v-if="isAdding">
        <b-form-input
          v-model="newTaskTitle"
          size="sm"
          class="mb-2"
          placeholder="Título da tarefa"
          @keyup.enter="confirmAddTask"
        />
        <div class="d-flex gap-2">
          <b-button size="sm" variant="primary" @click="confirmAddTask">
            Add
          </b-button>
          <b-button size="sm" variant="outline-secondary" @click="cancelAddTask">
            Cancel
          </b-button>
        </div>
      </template>

      <b-button
        v-else
        size="sm"
        variant="light"
        class="w-100 text-start"
        @click="isAdding = true"
      >
        + Add task
      </b-button>
    </div>
  </b-card>
</template>

<style scoped>
.tasklist-card {
  position: relative;
}

.delete-list-btn {
  opacity: 0;
  transition: opacity 0.15s ease-in-out;
}

.tasklist-card:hover .delete-list-btn {
  opacity: 1;
}
</style>
