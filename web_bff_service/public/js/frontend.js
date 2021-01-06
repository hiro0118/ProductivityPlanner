function onChangeDatePressed() {
    const date = document.getElementById("date").value;
    if (!date) {
        alert('Please enter date!');
        return;
    }
    updateTaskBoard(date);
}

function updateTaskBoard(date) {

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


function getToday() {
    const date = new Date();
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
    const dd = String(date.getDate()).padStart(2, '0');
    return yyyy + '-' + mm + '-' + dd;
}

function clearNewTaskModal() {
    document.getElementById("newTaskForm").reset();

    var forms = document.getElementsByClassName('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.classList.remove('was-validated');
    });
}

function setDateForNewTaskModal() {
    const currentDate = document.getElementById("taskboardtitle").innerHTML;
    if (!currentDate) {
        document.getElementById("inputDate").value = '';
    } else {
        document.getElementById("inputDate").value = currentDate;
    }
}

function checkTimeInput(input) {
    document.getElementById("currentTime").innerHTML = input * 0.5;
}

function onCreateNewTask() {
    var forms = document.querySelectorAll('.needs-validation')
    Array.prototype.slice.call(forms).forEach(function (form) {
        if (form.checkValidity()) {
            sendPostTask();
        }
        form.classList.add('was-validated');
    });
}

function sendPostTask() {
    const title = document.getElementById("inputTitle").value;
    const date = document.getElementById("inputDate").value;
    const targetTime = document.getElementById("inputTargetTime").value;
    const priority = document.getElementById("inputPriority").value;
    const note = document.getElementById("inputNote").value;

    const newTaskJson = {};
    newTaskJson.id = '';
    newTaskJson.title = title;
    newTaskJson.dateString = date;
    newTaskJson.completed = false;
    newTaskJson.note = note;
    newTaskJson.priority = 'NOT_SET';
    newTaskJson.targetTime = targetTime;
    newTaskJson.actualTime = 0;

    const request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:49160/tasks/');
    request.setRequestHeader('Content-type', 'application/json');
    request.onload = function () {
        const task = JSON.parse(this.responseText);
        const date = document.getElementById('taskboardtitle').innerText;
        if (date) {
            const taskBoard = document.getElementById('taskboard');
            const originalHTML = taskBoard.innerHTML;
            let result = '';
            result += [
                '<task-card title="',
                task.title,
                '" date="',
                task.dateString,
                '"></task-card> '
            ].join("");
            taskBoard.innerHTML += result;
        }
    }
    request.send(JSON.stringify(newTaskJson));
}