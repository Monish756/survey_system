CREATE DATABASE survey_db;
USE survey_db;

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text VARCHAR(255)
);

CREATE TABLE responses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT,
    answer VARCHAR(255)
);