import { defineStore } from 'pinia'
import type { TaskList } from '@/types/tasklist.ts'

let nextTaskListId = 4
let nextTaskId = 100

export const useTaskStore = defineStore('taskStore', {
  state: () => ({
    taskLists: [] as TaskList[],
  }),

  actions: {
    initDemoData() {
      if (this.taskLists.length > 0) return

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
    },

    addTaskList(title?: string) {
      const finalTitle = title?.trim() || `New list ${nextTaskListId}`

      this.taskLists.push({
        id: nextTaskListId++,
        title: finalTitle,
        tasks: [],
      })
    },

    addTask(taskListId: number, taskTitle: string) {
      const title = taskTitle.trim()
      if (!title) return

      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) return

      list.tasks.push({
        id: nextTaskId++,
        title,
        description: '',
        completed: false
      })
    },

    updateTaskListTitle(taskListId: number, newTitle: string) {
      const title = newTitle.trim()
      if (!title) return

      const list = this.taskLists.find((l) => l.id === taskListId)
      if (!list) return

      list.title = title
    },

    toggleTaskCompleted(taskListId: number, taskId: number) {
      const list = this.taskLists.find(l => l.id === taskListId)
      if (!list) return

      const task = list.tasks.find(t => t.id === taskId)
      if (!task) return

      task.completed = !task.completed
    },

    updateTaskTitle(taskListId: number, taskId: number, newTitle: string) {
      const title = newTitle.trim()
      if (!title) return

      const list = this.taskLists.find(l => l.id === taskListId)
      if (!list) return

      const task = list.tasks.find(t => t.id === taskId)
      if (!task) return

      task.title = title
    }
  },
})
