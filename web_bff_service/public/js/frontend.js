function getTasks() {
    var date = document.getElementById("date").value;
    console.log(date);

    let request = new XMLHttpRequest();
    request.open('GET', 'http://localhost:49160/tasks/');
    request.onload = function () {
        var tasks = JSON.parse(this.responseText);
        updateTasks(tasks);
    }
    request.send(null);
}

function updateTasks(tasks) {
    var taskboard = document.getElementById('taskboard');
    var result = '';
    for (task of tasks) {
        result += [
            '<task-element title="',
            task.title,
            '" date="',
            task.dateString,
            '"></task-element> '
        ].join("");
    }
    taskboard.innerHTML = result;
}