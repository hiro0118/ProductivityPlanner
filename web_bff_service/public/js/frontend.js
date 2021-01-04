function getTasks() {
    let request = new XMLHttpRequest();
    request.open('GET', 'http://localhost:49160/tasks/');
    request.onload = function () {
        console.log(this.response);
    }
    request.send(null);
}