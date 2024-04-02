CREATE TABLE IF NOT EXISTS difficulty_levels
(
    difficulty_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    difficulty    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS completion_feedbacks
(
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text        TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS type_collaborators
(
    type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS media_types
(
    type_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS question_type
(
    type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);


CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role_id  BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE IF NOT EXISTS multi_medias
(
    media_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_path   VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  DATETIME     NOT NULL,
    type_id     BIGINT,
    FOREIGN KEY (type_id) REFERENCES media_types (type_id)
);

CREATE TABLE IF NOT EXISTS answers
(
    answer_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    answer_text TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS questions
(
    question_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_name VARCHAR(255) NOT NULL,
    question_text TEXT         NOT NULL,
    explanations  TEXT,
    is_public     BOOLEAN      NOT NULL DEFAULT TRUE,
    type_id       BIGINT,
    difficulty_id BIGINT,
    media_id      BIGINT,
    question_duration INT UNSIGNED,
    FOREIGN KEY (type_id) REFERENCES question_type (type_id),
    FOREIGN KEY (difficulty_id) REFERENCES difficulty_levels (difficulty_id),
    FOREIGN KEY (media_id) REFERENCES multi_medias (media_id)
);

CREATE TABLE IF NOT EXISTS tags
(
    tag_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag     VARCHAR(255) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS templates
(
    template_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    filepath    VARCHAR(255) NOT NULL,
    created_at  DATETIME     NOT NULL,
    user_id     BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS feedbacks
(
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    created_at  DATETIME     NOT NULL,
    user_id     BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS quiz
(
    quiz_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    is_public   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  DATETIME     NOT NULL,
    template_id BIGINT,
    category_id BIGINT,
    media_id BIGINT,
    FOREIGN KEY (template_id) REFERENCES templates (template_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id),
    FOREIGN KEY (media_id) REFERENCES multi_medias(media_id)
);

CREATE TABLE IF NOT EXISTS quiz_questions
(
    quiz_id     BIGINT,
    question_id BIGINT,
    PRIMARY KEY (quiz_id, question_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);

CREATE TABLE IF NOT EXISTS history
(
    history_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    completed_at  DATETIME NOT NULL,
    score         DECIMAL(5, 2),
    rating        INT,
    feedback_text TEXT,
    user_id       BIGINT,
    quiz_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS collaborators
(
    user_id BIGINT,
    quiz_id BIGINT,
    type_id BIGINT,
    PRIMARY KEY (user_id, quiz_id, type_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id),
    FOREIGN KEY (type_id) REFERENCES type_collaborators (type_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text       TEXT     NOT NULL,
    created_at DATETIME NOT NULL,
    user_id    BIGINT,
    quiz_id    BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS quiz_completion_feedbacks
(
    quiz_id           BIGINT,
    feedback_id       BIGINT,
    score_lower_bound INT,
    score_upper_bound INT,
    PRIMARY KEY (quiz_id, feedback_id),
    FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id),
    FOREIGN KEY (feedback_id) REFERENCES completion_feedbacks (feedback_id)
);

CREATE TABLE IF NOT EXISTS answers_questions
(
    question_id BIGINT,
    answer_id   BIGINT,
    is_correct  BOOLEAN NOT NULL,
    PRIMARY KEY (question_id, answer_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id),
    FOREIGN KEY (answer_id) REFERENCES answers (answer_id)
);

CREATE TABLE IF NOT EXISTS questions_tags
(
    tag_id      BIGINT,
    question_id BIGINT,
    PRIMARY KEY (tag_id, question_id),
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id),
    FOREIGN KEY (question_id) REFERENCES questions (question_id)
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    refresh_token_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    refreshtoken     TEXT NOT NULL,
    revoked          BOOLEAN DEFAULT FALSE,
    user_id          BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
