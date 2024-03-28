CREATE TABLE IF NOT EXISTS Roles
(
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS CompletionFeedbacks
(
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text        TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS TypeCollaborators
(
    type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS MediaTypes
(
    type_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS QuestionType
(
    type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Categories
(
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS DifficultyLevels
(
    difficulty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    difficulty    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Users
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role_id  BIGINT,
    FOREIGN KEY (role_id) REFERENCES Roles (role_id)
);

CREATE TABLE IF NOT EXISTS MultiMedias
(
    media_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_path   VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  DATETIME     NOT NULL,
    type_id     BIGINT,
    FOREIGN KEY (type_id) REFERENCES MediaTypes (type_id)
);

CREATE TABLE IF NOT EXISTS Answers
(
    answer_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    answer_text TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Questions
(
    question_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_name VARCHAR(255) NOT NULL,
    question_text TEXT         NOT NULL,
    explanations  TEXT,
    is_public     BOOLEAN      NOT NULL DEFAULT TRUE,
    type_id       BIGINT,
    difficulty_id BIGINT,
    media_id      BIGINT,
    FOREIGN KEY (type_id) REFERENCES QuestionType (type_id),
    FOREIGN KEY (difficulty_id) REFERENCES DifficultyLevels (difficulty_id),
    FOREIGN KEY (media_id) REFERENCES MultiMedias (media_id)
);

CREATE TABLE IF NOT EXISTS Tags
(
    tag_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag     VARCHAR(255) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE IF NOT EXISTS Templates
(
    template_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    filepath    VARCHAR(255) NOT NULL,
    created_at  DATETIME     NOT NULL,
    user_id     BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE IF NOT EXISTS Feedbacks
(
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    created_at  DATETIME     NOT NULL,
    user_id     BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE IF NOT EXISTS Quiz
(
    quiz_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    time_left   INT,
    is_public   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  DATETIME     NOT NULL,
    template_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (template_id) REFERENCES Templates (template_id),
    FOREIGN KEY (category_id) REFERENCES Categories (category_id)
);

CREATE TABLE IF NOT EXISTS QuizQuestions
(
    quiz_id     BIGINT,
    question_id BIGINT,
    PRIMARY KEY (quiz_id, question_id),
    FOREIGN KEY (quiz_id) REFERENCES Quiz (quiz_id),
    FOREIGN KEY (question_id) REFERENCES Questions (question_id)
);

CREATE TABLE IF NOT EXISTS History
(
    history_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    completed_at  DATETIME NOT NULL,
    score         DECIMAL(5, 2),
    rating        INT,
    feedback_text TEXT,
    user_id       BIGINT,
    quiz_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (quiz_id) REFERENCES Quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS Collaborators
(
    user_id BIGINT,
    quiz_id BIGINT,
    type_id BIGINT,
    PRIMARY KEY (user_id, quiz_id, type_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (quiz_id) REFERENCES Quiz (quiz_id),
    FOREIGN KEY (type_id) REFERENCES TypeCollaborators (type_id)
);

CREATE TABLE IF NOT EXISTS Comments
(
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text       TEXT     NOT NULL,
    created_at DATETIME NOT NULL,
    user_id    BIGINT,
    quiz_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (quiz_id) REFERENCES Quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS QuizCompletionFeedbacks
(
    quiz_id           BIGINT,
    feedback_id       BIGINT,
    score_lower_bound INT,
    score_upper_bound INT,
    PRIMARY KEY (quiz_id, feedback_id),
    FOREIGN KEY (quiz_id) REFERENCES Quiz (quiz_id),
    FOREIGN KEY (feedback_id) REFERENCES CompletionFeedbacks (feedback_id)
);

CREATE TABLE IF NOT EXISTS AnswersQuestions
(
    question_id BIGINT,
    answer_id   BIGINT,
    is_correct  BOOLEAN NOT NULL,
    PRIMARY KEY (question_id, answer_id),
    FOREIGN KEY (question_id) REFERENCES Questions (question_id),
    FOREIGN KEY (answer_id) REFERENCES Answers (answer_id)
);

CREATE TABLE IF NOT EXISTS QuestionsTags
(
    tag_id      BIGINT,
    question_id BIGINT,
    PRIMARY KEY (tag_id, question_id),
    FOREIGN KEY (tag_id) REFERENCES Tags (tag_id),
    FOREIGN KEY (question_id) REFERENCES Questions (question_id)
);