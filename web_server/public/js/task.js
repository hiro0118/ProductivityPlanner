// change date -> update task board
// page load -> update task board
// create new task

const taskUrl = 'http://localhost:8080/tasks/'

// --------------- Show tasks on a specified date --------------- //

function onChangeDatePressed() {
    const date = document.getElementById('date').value;
    if (!date) {
        alert('Please enter date!');
        return;
    }
    updateTaskBoard(date);
}

function updateTaskBoard(date) {
    // Update the displayed date.
    const taskBoardTitle = document.getElementById('taskboardtitle');
    taskBoardTitle.innerHTML = date;

    // const url = taskUrl + '?date=' + date
    sendGet(taskUrl, function (tasks) {
        let result = '';
        for (task of tasks) {
            result += buildTaskCardHtml(task);
        }
        const taskBoard = document.getElementById('taskboard');
        taskBoard.innerHTML = result;
    });
}

function buildTaskCardHtml(task) {
    return [
        '<task-card title="',
        task.title,
        '" date="',
        task.dateString,
        '"></task-card> '
    ].join('');
}

function getToday() {
    const date = new Date();
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
    const dd = String(date.getDate()).padStart(2, '0');
    return yyyy + '-' + mm + '-' + dd;
}


// --------------- Create a new task --------------- //

function resetNewTaskForm() {
    document.getElementById('newTaskForm').reset();

    var forms = document.getElementsByClassName('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.classList.remove('was-validated');
    });
}

function setDateForNewTaskForm() {
    const currentDate = document.getElementById('taskboardtitle').innerHTML;
    if (!currentDate) {
        document.getElementById('inputDate').value = '';
    } else {
        document.getElementById('inputDate').value = currentDate;
    }
}

function checkTimeInput(input) {
    document.getElementById('currentTime').innerHTML = input * 0.5;
}

function onCreateNewTask() {
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function (form) {
        if (form.checkValidity()) {
            sendPostTask();
        }
        form.classList.add('was-validated');
    });
}

function sendPostTask() {
    const title = document.getElementById('inputTitle').value;
    const date = document.getElementById('inputDate').value;
    const targetTime = document.getElementById('inputTargetTime').value;
    const priority = document.getElementById('inputPriority').value;
    const note = document.getElementById('inputNote').value;

    const newTaskJson = {};
    newTaskJson.id = '';
    newTaskJson.title = title;
    newTaskJson.dateString = date;
    newTaskJson.completed = false;
    newTaskJson.note = note;
    newTaskJson.priority = 'NOT_SET';
    newTaskJson.targetTime = targetTime;
    newTaskJson.actualTime = 0;

    sendPost(taskUrl, newTaskJson, function (task) {
        
    });
}