DROP TABLE IF EXISTS test_task_service;
CREATE DATABASE test_task_service;

USE test_task_service;

CREATE TABLE IF NOT EXISTS tasks (
    id VARCHAR(36) NOT NULL,
    title VARCHAR(30) NOT NULL,
    task_date DATE NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT 0,
    note VARCHAR(100) NOT NULL DEFAULT '',
    priority VARCHAR(20) DEFAULT NULL,
    target INT NOT NULL DEFAULT 0,
    actual INT NOT NULL DEFAULT 0
);