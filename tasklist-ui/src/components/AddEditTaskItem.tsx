import { Button, Form, FormLabel, Modal, Row, Col } from "react-bootstrap";
import { TaskItem } from "../models/taskItem";
import { useForm } from "react-hook-form";
import * as TasksApi from "../network/taskItem_api";
import { TaskPriority, TaskStatus } from "../models/enums";

interface AddEditTaskItemProps {
    taskToEdit?: TaskItem,
    onDismiss: () => void,
    onTaskSaved: (task: TaskItem) => void,
}

const AddEditTaskItem = ({ taskToEdit, onDismiss, onTaskSaved }: AddEditTaskItemProps) => {
    const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<TaskItem>({
        defaultValues: {
            title: taskToEdit?.title || "",
            description: taskToEdit?.description || "",
            taskPriority: taskToEdit?.taskPriority || undefined,
            taskStatus: taskToEdit?.taskStatus || undefined,
            dueDateString: taskToEdit?.dueDateString || undefined, 
        }
    });

    async function onSubmit(input: TaskItem) {
        try {
            let taskItemResponse: TaskItem;
            if (taskToEdit) {
                taskItemResponse = await TasksApi.updateTaskItem(taskToEdit.id!, input)
            } else {
                taskItemResponse = await TasksApi.createTaskItem(input);
            }
            console.log("Task saved:", taskItemResponse);
            onTaskSaved(taskItemResponse);
        } catch (error) {
            console.error(error);
            alert(error);
        }
    }

    return ( 
        <Modal show onHide={onDismiss}>
            <Modal.Header closeButton>
                <Modal.Title>
                    {taskToEdit ? "Edit a task" : "Add new Task"}
                </Modal.Title>
            </Modal.Header>
            
            <Modal.Body>
                <Form id="addEditTaskForm" onSubmit={handleSubmit(onSubmit)}>
                    <Form.Group className="mb-3">
                        <Form.Label>Title</Form.Label>
                        <Form.Control
                        type="text"
                        placeholder="title"
                        isInvalid={!!errors.title}
                        {...register("title", {required: "Required"})}
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.title?.message}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                        as="textarea"
                        rows={5}
                        placeholder="Description"
                        {...register("description")}
                        />
                    </Form.Group>

                    <Form.Group as={Row} className="mb-3">
                        <Form.Label as="legend" column sm={2}>
                            Priority:
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Check
                                type="radio"
                                label="High"
                                {...register("taskPriority", { required: "Required" })}
                                id="priorityHigh"
                                value={TaskPriority.HIGH}
                            />
                            <Form.Check
                                type="radio"
                                label="Medium"
                                {...register("taskPriority", { required: "Required" })}
                                id="priorityMedium"
                                value={TaskPriority.MEDIUM}
                            />
                            <Form.Check
                                type="radio"
                                label="Low"
                                {...register("taskPriority", { required: "Required" })}
                                id="priorityLow"
                                value={TaskPriority.LOW}
                            />
                        </Col>
                        <Form.Control.Feedback type="invalid">
                            {errors.taskPriority?.message}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Row} className="mb-3">
                        <Form.Label column sm={2}>Status:</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                as='select'
                                isInvalid={!!errors.taskStatus}
                                {...register("taskStatus", {required: "Required"})}
                                >
                                <option value="" disabled selected>please select</option>
                                <option value={TaskStatus.ToDo}>To Do</option>
                                <option value={TaskStatus.DOING}>Doing</option>
                                <option value={TaskStatus.DONE}>Done</option>
                            </Form.Control>
                        </Col>
                        <Form.Control.Feedback type="invalid">
                            {errors.taskStatus?.message}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Due Date</Form.Label>
                        <Form.Control
                            type="date"
                            {...register("dueDateString")}/>
                    </Form.Group>
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button 
                    type="submit" 
                    form="addEditTaskForm"
                    disabled={isSubmitting}>
                    Save
                </Button>
            </Modal.Footer>
        </Modal>
     );
}
 
export default AddEditTaskItem;