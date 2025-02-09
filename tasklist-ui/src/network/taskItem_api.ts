import { TaskItem } from "../models/taskItem";

async function fetchData(input: RequestInfo, init?: RequestInit) {
    const response = await fetch(input, init);
    if (response.ok) {
        return response;
    } else {
        const errorBody = await response.json();
        const errorMessage = errorBody.error;
        throw Error(errorMessage || "Unknown Error Occurred");
    }
}

export async function fetchTaskItems(): Promise<TaskItem[]> {
    const response = await fetchData("http://localhost:8080/api/v1/taskItems", { method: "GET"}); 
    return response.json();
}

export async function createTaskItem(taskItem: TaskItem): Promise<TaskItem> {
    const response = await fetchData("http://localhost:8080/api/v1/taskItems",
    {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(taskItem),
    }); 
    return response.json()
}

export async function deleteTaskItem(taskId: number) {
    const response = await fetchData(`http://localhost:8080/api/v1/taskItems/${taskId}` , {method: "DELETE"});
}

export async function updateTaskItem(taskId: number, task: TaskItem): Promise<TaskItem> {
    const response = await fetchData(`http://localhost:8080/api/v1/taskItems/${taskId}` , 
    {
        method: "PUT",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(task),
    }); 
    return response.json();
}