import { defineStore } from 'pinia'
import type { TaskList } from '@/types/tasklist.ts'
import api from '@/services/api'
import { useUiStore } from './uiStore'

let nextTaskListId = 4
let nextTaskId = 100

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
        // const { data } = await api.get<TaskList[]>('/boards/current/task-lists')
        // this.taskLists = data

        if (this.taskLists.length === 0) {
          this.taskLists = [
            {
              id: 1,
              title: 'To do',
              tasks: [
                { id: 11, title: 'Study Vue', completed: false },
                { id: 12, title: 'Mount board layout', completed: false },
              ],
            },
            {
              id: 2,
              title: 'In progress',
              tasks: [{ id: 21, title: 'Refactor API', completed: false }],
            },
            {
              id: 3,
              title: 'Done',
              tasks: [{ id: 31, title: 'Project skeleton', completed: false }],
            },
          ]
        }
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
        // const { data } = await api.post<TaskList>('/task-lists', { title: finalTitle })
        // this.taskLists.push(data)

        this.taskLists.push({
          id: nextTaskListId++,
          title: finalTitle,
          tasks: [],
        })

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
        // const { data } = await api.post<Task>(`/task-lists/${taskListId}/tasks`, { title })
        // list.tasks.push(data)

        list.tasks.push({
          id: nextTaskId++,
          title,
          description: '',
          completed: false,
        })

        ui.showSuccess('Tarefa criada')
      } catch (error: any) {
        ui.showError('Erro ao criar tarefa')
      } finally {
        this.isSaving = false
      }
    },

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
        // await api.put(`/task-lists/${taskListId}`, { title })
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
        // await api.patch(`/tasks/${taskId}`, { completed: task.completed })
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
        // await api.put(`/tasks/${taskId}`, { title })
      } catch (error: any) {
        task.title = oldTitle
        ui.showError('Erro ao atualizar título da tarefa')
      }
    },
  },
})
