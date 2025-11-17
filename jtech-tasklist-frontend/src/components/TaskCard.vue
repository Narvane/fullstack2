<script setup lang="ts">
import { ref, watch } from 'vue'
import type { Task } from '@/types/task.ts'

import {BButton, BCard, BFormCheckbox, BFormInput} from 'bootstrap-vue-next'

const props = defineProps<{
  task: Task
}>()

const emit = defineEmits<{
  (e: 'toggle-completed'): void
  (e: 'update-title', newTitle: string): void
  (e: 'delete'): void
}>()

const isEditing = ref(false)
const editedTitle = ref(props.task.title)

watch(() => props.task.title, newVal => editedTitle.value = newVal)

function onToggle() {
  emit('toggle-completed')
}

function startEdit() {
  isEditing.value = true
  editedTitle.value = props.task.title
}

function confirmEdit() {
  const title = editedTitle.value.trim()

  if (!title || title === props.task.title) {
    isEditing.value = false
    editedTitle.value = props.task.title
    return
  }

  emit('update-title', title)
  isEditing.value = false
}

function onKeyupEnter() {
  confirmEdit()
}

function onDelete() {
  emit('delete')
}
</script>

<template>
  <b-card class="task-card" body-class="p-2">
    <div class="d-flex align-items-center gap-2">

      <b-form-checkbox
        :model-value="task.completed ?? false"
        @change="onToggle"
        class="mt-1"
      />

      <div class="flex-grow-1">
        <b-form-input
          v-if="isEditing"
          v-model="editedTitle"
          size="sm"
          @blur="confirmEdit"
          @keyup.enter="onKeyupEnter"
          autofocus
        />
        <div
          v-else
          :class="[
            'small mb-0 fw-semibold',
            { 'text-decoration-line-through text-muted': task.completed }
          ]"
          @dblclick="startEdit"
        >
          {{ task.title }}
        </div>
      </div>

      <b-button class="btn-icon btn-sm delete-btn" variant="danger" @click.stop="onDelete">
        <i class="bi bi-trash-fill"></i>
      </b-button>

    </div>
  </b-card>
</template>

<style scoped>
.task-card {
  cursor: pointer;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.15);
  max-width: 238px;
  position: relative;
}

.delete-btn {
  opacity: 0;
  transition: opacity 0.15s ease-in-out;
}

.task-card:hover .delete-btn {
  opacity: 1;
}

</style>
