import { TaskPriority, TaskStatus } from "./enums";

export interface TaskItem{
    id?: number;
    taskPriority: TaskPriority;
    title: string;
    description: string;
    taskStatus: TaskStatus;
    dueDateString: Date;
    createdDate?: Date;
    updatedDate?: Date;
}