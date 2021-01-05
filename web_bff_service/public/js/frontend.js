function getTasks() {
    change();
    let request = new XMLHttpRequest();
    request.open('GET', 'http://localhost:49160/tasks/');
    request.onload = function () {
        console.log(this.response);
    }
    request.send(null);
}

function change() {
    var elem = document.getElementById("card1");
    elem.innerHTML = "<h5 class='card-title' id='card1'>Task title2</h5>";
}