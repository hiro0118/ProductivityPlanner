'use strict';

const axios = require('axios');
const express = require('express');
const fs = require('fs');

// Constants
const PORT = 49160;
const HOST = '0.0.0.0';
const TASK_SERVICE_ADDRESS = 'http://task-app:8080/tasks/';

// App
const app = express();

app.use(express.static("public"));
app.use(express.json());

app.get('/', (req, res) => {
    fs.readFile("./index.html", "UTF-8", function (err, html) {
        res.writeHead(200, { "Content-Type": "text/html" });
        res.end(html);
    })
});

app.get('/tasks', (clientReq, clientRes) => {

    axios.get(TASK_SERVICE_ADDRESS)
        .then(res => {
            let received = JSON.stringify(res.data);
            clientRes.writeHead(200, { "Content-Type": "application/json" });
            clientRes.end(received);
        }).catch(error => {
            console.error(error);
        })
});

app.post('/tasks', (clientReq, clientRes) => {
    const data = JSON.stringify(clientReq.body);
    axios.post(TASK_SERVICE_ADDRESS, data, {
        headers: {
            // Overwrite Axios's automatically set Content-Type
            'Content-Type': 'application/json'
        }
    }).then(res => {
        let received = JSON.stringify(res.data);
        console.error('received ' + received);
        clientRes.writeHead(200, { "Content-Type": "application/json" });
        clientRes.end(received);
    }).catch(error => {
        console.error(error);
    });
});

app.listen(PORT, HOST, () => {
    console.log(`Running on http://${HOST}:${PORT}`);
});