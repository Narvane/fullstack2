import type { Task } from '@/types/task.ts'

export interface TaskList {
  id: string
  title: string
  tasks: Task[]
}
