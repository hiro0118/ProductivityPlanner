'use strict';

const express = require('express');
const fs = require('fs');

// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

// App
const app = express();
app.get('/', (req, res) => {
    fs.readFile("./index.html", "UTF-8", function (err, html) {
        res.writeHead(200, { "Content-Type": "text/html" });
        res.end(html);
    })
});

app.get('/css/style.css', (req, res) => {
    fs.readFile("./css/style.css", "UTF-8", function (err, html) {
        res.writeHead(200, { "Content-Type": "text/css" });
        res.end(html);
    })
});

app.listen(PORT, HOST, () => {
    console.log(`Running on http://${HOST}:${PORT}`);
});