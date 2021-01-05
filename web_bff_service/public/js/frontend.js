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


function getToday() {
    const date = new Date();
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
    const dd = String(date.getDate()).padStart(2, '0');
    return yyyy + '-' + mm + '-' + dd;
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

function checkTimeInput(input) {
    document.getElementById("currentTime").innerHTML = input * 0.5;
}

function registerNewTaskFormSubmitChecker() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            console.log(form);
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
}