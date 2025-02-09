import React, { useState } from 'react';
import { TaskItem } from '../models/taskItem';
import { Card } from 'react-bootstrap';
import styles from "../styles/Task.module.css";
import styleUtils from "../styles/utils.module.css"
import { formatDate } from "../utils/formatDate";
import { FaMinus, FaPlus } from 'react-icons/fa';
import { MdDelete } from "react-icons/md";
import { AiFillEdit } from 'react-icons/ai';
import { TaskPriority } from '../models/enums';

type TaskProps = {
  taskItem: TaskItem,
  onTaskClicked: (task: TaskItem) => void,
  // isExpand: boolean,
  onDeleteTaskClicked: (task: TaskItem) => void,
}

const TaskSubmitted = ({ taskItem, onTaskClicked, onDeleteTaskClicked }: TaskProps) => {
  const {
    taskPriority,
    title,
    description,
    dueDateString,
  } = taskItem;

  const [isExpand, setIsExpand] = useState(false);
  const toggleExpand = (event: React.MouseEvent) => {
    event.stopPropagation();
    setIsExpand(!isExpand);
  };

  let dueDateText: string;
  dueDateText = formatDate(dueDateString);

  function formatTaskPriority(priority: TaskPriority): string {
    switch(priority) {
      case TaskPriority.HIGH:
        return "High 🔥";
      case TaskPriority.MEDIUM:
        return "Medium";
      case TaskPriority.LOW:
        return "Low";
      default:
        return priority;
    }
  }

  function getPriorityClassName(priority: TaskPriority): string {
    switch(priority) {
      case TaskPriority.HIGH:
        return styles.priorityHigh;
      case TaskPriority.MEDIUM:
        return styles.priorityMedium;
      case TaskPriority.LOW:
        return styles.priorityLow;
      default:
        return ""; // Default class if needed
    }
  }

  // explain (small message) the logos in the task item

  return (
    <Card 
      className={styles.taskCard}
    >
      <div className={styles.taskCardHeader}>
        <span onClick={toggleExpand} className={`${styles.expandIcon} text-muted`}>
          {isExpand ? <FaMinus/> : <FaPlus/>}
        </span>
        <AiFillEdit 
          className='text-muted'
          onClick={() => onTaskClicked(taskItem)}
        />
        <MdDelete
          className='text-muted'
          onClick={(e) => {
            onDeleteTaskClicked(taskItem);
            e.stopPropagation();
          }}
        />
      </div>
      <Card.Body>
        <Card.Title className={styleUtils.flexCenter}>
          {title}
        </Card.Title>
        {isExpand && (
          <>
            <Card.Text className={styles.cardText}> 
              {description}
            </Card.Text>
          </>
        )}
        <Card.Text className={`${styles.priorityText} ${getPriorityClassName(taskPriority)}`}> 
          Priority: {formatTaskPriority(taskPriority)}
        </Card.Text>
        <Card.Text className={styles.dueDateText}> 
          {dueDateText}
        </Card.Text>
      </Card.Body>
    </Card>
  )
}

export default TaskSubmitted;