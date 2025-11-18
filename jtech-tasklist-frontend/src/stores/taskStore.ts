import { defineStore } from 'pinia'
import type { TaskList } from '@/types/tasklist.ts'
import type { Task } from '@/types/task.ts'
import api from '@/services/api'
import { useUiStore } from './uiStore'

let nextTaskListId = 4

export const useTaskStore = defineStore('taskStore', {
  state: () => ({
    taskLists: [] as TaskList[],
    isLoadingBoard: false,
    isSaving: false,
  }),

  actions: {
    async fetchTaskLists() {
      const ui = useUiStore()
      this.isLoadingBoard = true
      try {
        const { data } = await api.get<TaskList[]>('/api/v1/tasklists')
        this.taskLists = data
      } catch (error: any) {
        ui.showError('Erro ao carregar listas de tarefas')
      } finally {
        this.isLoadingBoard = false
      }
    },

    async addTaskList(title?: string) {
      const ui = useUiStore()
      const finalTitle = title?.trim() || `New list ${nextTaskListId}`

      if (!finalTitle) {
        ui.showError('O título da lista é obrigatório')
        return
      }

      this.isSaving = true
      try {
        const { data } = await api.post('/api/v1/tasklists', { title: finalTitle })
        this.taskLists.push(data)

        nextTaskListId++
        ui.showSuccess('Lista criada com sucesso')
      } catch (error: any) {
        ui.showError('Erro ao criar lista')
      } finally {
        this.isSaving = false
      }
    },

    async addTask(taskListId: number, taskTitle: string) {
      const ui = useUiStore()
      const title = taskTitle.trim()
      if (!title) {
        ui.showError('O título da tarefa é obrigatório')
        return
      }

      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) {
        ui.showError('Lista não encontrada')
        return
      }

      this.isSaving = true
      try {
        const { data } = await api.post('/api/v1/tasks', { title, taskListId })
        list.tasks.push(data);
        ui.showSuccess('Tarefa criada')
      } catch (error: any) {
        ui.showError('Erro ao criar tarefa')
      } finally {
        this.isSaving = false
      }
    }
    ,

    async updateTaskListTitle(taskListId: number, newTitle: string) {
      const ui = useUiStore()
      const title = newTitle.trim()
      if (!title) {
        ui.showError('O título da lista não pode ser vazio')
        return
      }

      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) {
        ui.showError('Lista não encontrada')
        return
      }

      const oldTitle = list.title
      list.title = title

      try {
        await api.put(`/api/v1/tasklists/${taskListId}`, { title })
      } catch (error: any) {
        list.title = oldTitle
        ui.showError('Erro ao atualizar título da lista')
      }
    },

    async toggleTaskCompleted(taskListId: number, taskId: number) {
      const ui = useUiStore()
      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) return

      const task = list.tasks.find((t) => t.id === taskId)
      if (!task) return

      const oldValue = task.completed ?? false
      task.completed = !oldValue

      try {
        await api.patch(`/tasks/${taskId}`, { completed: task.completed })
      } catch (error: any) {
        task.completed = oldValue
        ui.showError('Erro ao atualizar status da tarefa')
      }
    },

    async updateTaskTitle(taskListId: number, taskId: number, newTitle: string) {
      const ui = useUiStore()
      const title = newTitle.trim()
      if (!title) {
        ui.showError('O título da tarefa não pode ser vazio')
        return
      }

      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) {
        ui.showError('Lista não encontrada')
        return
      }

      const task = list.tasks.find((t) => t.id === taskId)
      if (!task) {
        ui.showError('Tarefa não encontrada')
        return
      }

      const oldTitle = task.title
      task.title = title

      try {
        await api.put(`/tasks/${taskId}`, { title })
      } catch (error: any) {
        task.title = oldTitle
        ui.showError('Erro ao atualizar título da tarefa')
      }
    },

    async deleteTask(taskListId: number, taskId: number) {
      const ui = useUiStore()

      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) {
        ui.showError('Lista não encontrada')
        return
      }

      const originalTasks = [...list.tasks]
      list.tasks = list.tasks.filter((t) => t.id !== taskId)

      try {
        await api.delete(`/tasks/${taskId}`)
        ui.showSuccess('Tarefa removida')
      } catch (error: any) {
        list.tasks = originalTasks
        ui.showError('Erro ao remover tarefa')
      }
    },

    async deleteTaskList(taskListId: number) {
      const ui = useUiStore()

      const originalLists = [...this.taskLists]
      this.taskLists = this.taskLists.filter((l) => l.id !== taskListId)

      try {
        await api.delete(`/api/v1/tasklists/${taskListId}`)
        ui.showSuccess('Lista removida')
      } catch (error: any) {
        this.taskLists = originalLists
        ui.showError('Erro ao remover lista')
      }
    },
  },
})
