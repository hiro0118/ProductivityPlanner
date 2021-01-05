function getTasks() {
    const date = document.getElementById("date").value;
    if (!date) {
        alert('Please enter date!');
        return;
    }

    const taskBoardTitle = document.getElementById("taskboardtitle");
    taskBoardTitle.innerHTML = date;

    const request = new XMLHttpRequest();
    request.open('GET', 'http://localhost:49160/tasks/');
    request.onload = function () {
        const tasks = JSON.parse(this.responseText);
        updateTasks(tasks);
    }
    request.send(null);
}

function updateTasks(tasks) {
    const taskBoard = document.getElementById('taskboard');
    let result = '';
    for (task of tasks) {
        result += [
            '<task-card title="',
            task.title,
            '" date="',
            task.dateString,
            '"></task-card> '
        ].join("");
    }
    taskBoard.innerHTML = result;
}

function createNewTask() {
    // const title = document.getElementById("inputTitle");
    // const date = document.getElementById("inputDate");
    // const targetTime = document.getElementById("inputTargetTime");
    // const priority = document.getElementById("inputPriority");
    // const note = document.getElementById("inputNote");
}

function clearNewTaskModal() {
    document.getElementById("newTaskForm").reset();
}

function setDateForNewTaskModal() {
    const currentDate = document.getElementById("taskboardtitle").innerHTML;
    if (!currentDate) {
        document.getElementById("inputDate").value = '';
    } else {
        document.getElementById("inputDate").value = currentDate;
    }
}