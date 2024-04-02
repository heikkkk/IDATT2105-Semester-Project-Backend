-- Inserting roles
INSERT INTO roles (type)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_GUEST');

INSERT INTO completion_feedbacks (text)
VALUES ('Excellent job! You scored very high on the quiz.'),
       ('Good effort! You did well, but there is room for improvement.'),
       ('Fair performance. You may want to review the material and try again.'),
       ('Poor performance. Consider studying more before attempting the quiz again.');

-- Inserting users
-- Password = password123
INSERT INTO users (username, email, password, role_id)
VALUES ('adminUser', 'admin@example.com', '$2a$10$4tzOHGPWBcHNoZvAammZLOldKMRP7.vdQsNBKFuGYAqVW1Tg3OIEu', 1),
       ('regularUser', 'user@example.com','$2a$10$qdHievWCF6SbISQMVA2smOtR0uQJBd6KRfeMN6gB/MZntmw133SGS', 2),
       ('guestUser', 'guest@example.com', '$2a$10$YT4Zniy6huusodZO919A1OCl4pu/9bobszAGN.tspDHLWQB.Nd87W', 3);

-- Inserting categories
INSERT INTO categories (name)
VALUES ('Science'),
       ('Math'),
       ('History');

-- Inserting difficulty levels
INSERT INTO difficulty_levels (difficulty)
VALUES ('Easy'),
       ('Medium'),
       ('Hard');

-- Inserting into media_types
INSERT INTO media_types (description)
VALUES ('Image'),
       ('Video');

-- Inserting into type_collaborators
INSERT INTO type_collaborators (name)
VALUES ('Author'),
       ('Co-Author'),
       ('Tester');

-- Inserting into question_type
INSERT INTO question_type (type)
VALUES ('Multiple Choice'),
       ('True/False'),
       ('Short Answer');

-- Inserting into multi_medias
INSERT INTO multi_medias (file_path, description, created_at, type_id)
VALUES ('path/to/image.jpg', 'An image file', NOW(), 1),
       ('path/to/video.mp4', 'A video file', NOW(), 2);

-- Inserting into templates
INSERT INTO templates (name, description, filepath, created_at, user_id)
VALUES ('Template A', 'Template for science quizzes', 'path/to/template_a.docx', NOW(), 1),
       ('Template B', 'Template for math quizzes', 'path/to/template_b.docx', NOW(), 2),
       ('Template C', 'General purpose template', 'path/to/template_c.docx', NOW(), 3);

-- Inserting into questions
INSERT INTO questions (question_name, question_text, explanations, is_public, type_id,
                       difficulty_id, media_id)
VALUES ('What is 2+2?', 'Calculate the sum of 2 and 2.', 'The sum of 2 and 2 is 4.', TRUE, 1, 1,
        NULL),
       ('Is the Earth round?', 'Answer if the Earth is round or flat.', 'The Earth is round.', TRUE,
        1, 2, NULL),
       ('Who discovered America?', 'Name the explorer who discovered America.',
        'America was discovered by Columbus.', TRUE, 1, 3, NULL);

-- Inserting into answers
INSERT INTO answers (answer_text)
VALUES ('4'),
       ('True'),
       ('Columbus');

-- Inserting into answers_questions
INSERT INTO answers_questions (question_id, answer_id, is_correct)
VALUES (1, 1, TRUE),
       (1, 2, FALSE),
       (1, 3, FALSE),
       (2, 2, TRUE),
       (3, 3, TRUE);

-- Inserting into tags
INSERT INTO tags (tag, user_id)
VALUES ('Science', 1),
       ('History', 2),
       ('Math', 3);

-- Inserting into quiz
INSERT INTO quiz (title, description, is_public, created_at, template_id, category_id)
VALUES ('Basic Math Quiz', 'A quiz covering basic math principles.', TRUE, NOW(), 1, 2),
       ('World History Quiz', 'A quiz about world history.', TRUE, NOW(), 2, 3),
       ('General Science Quiz', 'A quiz testing general science knowledge.', TRUE, NOW(), 3, 1);

-- Inserting into feedbacks
INSERT INTO feedbacks (title, content, created_at, user_id)
VALUES ('Great Quiz', 'This was a very informative quiz.', NOW(), 1),
       ('Challenging Quiz', 'Some questions were really challenging.', NOW(), 2),
       ('Enjoyable Experience', 'Enjoyed taking this quiz.', NOW(), 3);

-- Inserting into history
INSERT INTO history (completed_at, score, rating, feedback_text, user_id, quiz_id)
VALUES (NOW(), 85.0, 4, 'Good quiz, well done.', 1, 1),
       (NOW(), 92.5, 5, 'Perfect, loved the questions.', 2, 2),
       (NOW(), 76.0, 3, 'Challenging but fun.', 3, 3);

-- Inserting into collaborators
INSERT INTO collaborators (user_id, quiz_id, type_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3);

-- Inserting into comments
INSERT INTO comments (text, created_at, user_id, quiz_id)
VALUES ('Really makes you think!', NOW(), 1, 1),
       ('I learned something new today.', NOW(), 2, 2),
       ('Looking forward to more quizzes like this.', NOW(), 3, 3);

-- Inserting into quiz_completion_feedbacks
INSERT INTO quiz_completion_feedbacks (quiz_id, feedback_id, score_lower_bound, score_upper_bound)
VALUES (1, 1, 0, 50),
       (2, 2, 51, 75),
       (3, 3, 76, 100);

-- Inserting into quiz_questions
INSERT INTO quiz_questions (quiz_id, question_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);

-- Inserting into questions_tags
INSERT INTO questions_tags (tag_id, question_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);