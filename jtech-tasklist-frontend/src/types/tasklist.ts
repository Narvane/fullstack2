import type { Task } from '@/types/task.ts'

export interface TaskList {
  id: number
  title: string
  tasks: Task[]
}
