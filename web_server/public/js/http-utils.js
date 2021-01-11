
function sendGet(url, callbackFunction) {
    const request = new XMLHttpRequest();
    request.open('GET', url);
    request.onload = function () {
        status = this.status;
        if (status >= 200 && status < 300) {
            responseJson = JSON.parse(this.responseText);
            callbackFunction(responseJson);
        }
    }
    request.send(null);
}

function sendPost(url, reqJsonBody, callbackFunction) {
    const request = new XMLHttpRequest();
    request.open('POST', url);
    request.setRequestHeader('Content-type', 'application/json');
    request.onload = function () {
        if (status >= 200 && status < 300) {
            responseJson = JSON.parse(this.responseText);
            callbackFunction(responseJson);
        }
    }
    reqJsonString = JSON.stringify(reqJsonBody)
    request.send(reqJsonString);
}
