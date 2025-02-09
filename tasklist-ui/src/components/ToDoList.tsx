import React, { useState } from "react";
import { TaskItem } from "../models/taskItem";
import TaskSubmitted from "./TaskSubmitted";
import styles from "../styles/List.module.css";
import { Container } from "react-bootstrap";
import * as TaskItemsApi from "../network/taskItem_api";
import { TaskStatus } from '../models/enums';
import AddEditTaskItem from './AddEditTaskItem';

interface ListProps {
    taskItems: {
        ToDo: TaskItem[];
        DOING: TaskItem[];
        DONE: TaskItem[];
    };
    setTaskItems: React.Dispatch<React.SetStateAction<{
        ToDo: TaskItem[];
        DOING: TaskItem[];
        DONE: TaskItem[];
    }>>;
    showAddNewTaskItem: boolean;
    setShowAddNewTaskItem: React.Dispatch<React.SetStateAction<boolean>>;
}

const ToDoList: React.FC<ListProps> = ({ taskItems, setTaskItems, showAddNewTaskItem, setShowAddNewTaskItem } : ListProps) => {

    const [taskToEdit, setTaskToEdit] = useState<TaskItem | null>(null);

    async function deleteTask(task: TaskItem) {
        if (typeof task.id !== 'undefined') {
            try {
                await TaskItemsApi.deleteTaskItem(task.id);
                const remainingTaskItems = await TaskItemsApi.fetchTaskItems();
                const categorisedTasks = {
                    ToDo: remainingTaskItems.filter(item => item.taskStatus === TaskStatus.ToDo && item.id !== task.id),
                    DOING: remainingTaskItems.filter(item => item.taskStatus === TaskStatus.DOING && item.id !== task.id),
                    DONE: remainingTaskItems.filter(item => item.taskStatus === TaskStatus.DONE && item.id !== task.id),
                };
                setTaskItems(categorisedTasks);
            } catch (error) {
                console.error(error);
                alert(error);
            }
        } else {
            console.error('Attempted to delete a task without an ID')
        }
    }

    return (
        <Container className={styles.listContainer}>
            <Container className={styles.taskItems}>
                <span className="list_heading">To Do</span>
                {taskItems.ToDo.map((taskItem) => (
                    <TaskSubmitted 
                        taskItem={taskItem} 
                        key={taskItem.id} 
                        onDeleteTaskClicked={deleteTask}
                        onTaskClicked={setTaskToEdit}
                    />
                ))}
            </Container>
            <Container className={styles.taskItems}>
                <span className="list_heading">Doing</span>
                {taskItems.DOING.map((taskItem) => (
                    <TaskSubmitted 
                        taskItem={taskItem} 
                        key={taskItem.id} 
                        onDeleteTaskClicked={deleteTask}
                        onTaskClicked={(task) => setTaskToEdit(task)}
                    />
                ))}
            </Container>
            <Container className={styles.taskItems}>
                <span className="list_heading">Done</span>
                {taskItems.DONE.map((taskItem) => (
                    <TaskSubmitted 
                        taskItem={taskItem} 
                        key={taskItem.id} 
                        onDeleteTaskClicked={deleteTask}
                        onTaskClicked={setTaskToEdit}
                    />
                ))}
            </Container>
            { showAddNewTaskItem &&
                <AddEditTaskItem
                    onDismiss={() => setShowAddNewTaskItem(false)}
                    onTaskSaved={(taskItem : TaskItem) => {
                        setTaskItems((prevTasks) => ({
                            ...prevTasks,
                            [taskItem.taskStatus]: [...prevTasks[taskItem.taskStatus], taskItem],
                        })); 
                        setShowAddNewTaskItem(false)
                    }}
                />
            }
            {taskToEdit &&
            <AddEditTaskItem
                taskToEdit={taskToEdit}
                onDismiss={() => setTaskToEdit(null)}
                onTaskSaved={(updatedTask) => {
                    const updateList = (list: TaskItem[]) => list.map(
                        task => task.id === updatedTask.id? updatedTask : task
                    );

                    const newList: {[key in TaskStatus]: TaskItem[]} = {
                        [TaskStatus.ToDo]: updateList(taskItems[TaskStatus.ToDo]),
                        [TaskStatus.DOING]: updateList(taskItems[TaskStatus.DOING]),
                        [TaskStatus.DONE]: updateList(taskItems[TaskStatus.DONE]),
                    }
                    setTaskItems(newList);
                    setTaskToEdit(null);
                }}
            />
            }
        </Container>
    );
}

export default ToDoList;