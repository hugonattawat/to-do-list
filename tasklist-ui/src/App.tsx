import React, { useEffect, useState } from 'react';
import './styles/App.css';
import ToDoList from "./components/ToDoList";
import { TaskItem } from './models/taskItem';
import * as TaskItemsApi from "./network/taskItem_api";
import AddEditTaskItem from './components/AddEditTaskItem';
import { Button } from 'react-bootstrap';
import { TaskStatus } from './models/enums';
import { FaPlus } from "react-icons/fa";
import styleUtils from "./styles/utils.module.css";

// Playwright - end to end testing (similar to UI unit test)

const App: React.FC = () => {

  const [taskItems, setTaskItems] = useState<{
    ToDo: TaskItem[];
    DOING: TaskItem[];
    DONE: TaskItem[]
  }>({
    ToDo: [],
    DOING: [],
    DONE: [],
  });

  const [showAddNewTaskItem, setShowAddNewTaskItem] = useState(false);

  useEffect(() => {
    async function loadTaskItems() {
      try {
        const fetchTaskItems = await TaskItemsApi.fetchTaskItems();
        const categorisedTasks = {
          ToDo: fetchTaskItems.filter(item => item.taskStatus === TaskStatus.ToDo),
          DOING: fetchTaskItems.filter(item => item.taskStatus === TaskStatus.DOING),
          DONE: fetchTaskItems.filter(item => item.taskStatus === TaskStatus.DONE),
        };
        setTaskItems(categorisedTasks);
      } catch (error) {
        console.error(error);
        alert(error);
        // state for error --> present to user in a way they understand
      }
    }
    loadTaskItems();
  }, []);

  return (
    <div className="App">
      <span className="heading">My To Do List</span> 
      <Button 
        className={`mb-4 ${styleUtils.blockCenter} ${styleUtils.flexCenter}`}
        onClick={() => setShowAddNewTaskItem(true)}>
        <FaPlus/>
        Add new task
      </Button>
      <ToDoList 
        taskItems={taskItems} 
        setTaskItems={setTaskItems}
        showAddNewTaskItem={showAddNewTaskItem}
        setShowAddNewTaskItem={setShowAddNewTaskItem}/>
      {/* {JSON.stringify(taskItems)} */}
    </div>
  );
};

export default App;
