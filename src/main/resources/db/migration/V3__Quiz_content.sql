-- Inserting quizzes
INSERT INTO quiz (quiz_id, title, description, is_public, created_at, template_id, category_id, media_id)
VALUES 
  (1, 'Basic Math Quiz', 'A quiz covering basic math principles.', TRUE, NOW(), 1, 2, 1),
  (2, 'World History Quiz', 'A quiz about world history.', TRUE, NOW(), 1, 3, 1),
  (3, 'Advanced Physics Quiz', 'Explore the fundamental concepts of physics with challenging questions.', TRUE, NOW(), 1, 1, 1),
  (4, 'Sport Quiz', 'QUIZ SPORTS.', TRUE, NOW(), 1, 4, 1);


-- Questions for Basic Math Quiz (quiz_id=1)
INSERT INTO question (question_id, question_name, question_text, explanations, is_public, type_id, difficulty_id, media_id, question_duration)
VALUES 
  (1, 'Simple Addition', 'What is the result of 5 + 3?', 'The result of 5 + 3 is 8.', TRUE, 1, 1, NULL, 30),
  (2, 'Basic Subtraction', 'What is 10 - 6?', '10 - 6 equals 4.', TRUE, 1, 1, NULL, 30),
  (3, 'Multiplication', 'Calculate 4 * 5.', '4 multiplied by 5 is 20.', TRUE, 1, 1, NULL, 30),
  (4, 'Division', 'What is 15 / 3?', '15 divided by 3 equals 5.', TRUE, 1, 1, NULL, 30),
  (5, 'Square Root', 'What is the square root of 16?', 'The square root of 16 is 4.', TRUE, 1, 1, NULL, 30);

-- Connecting questions to the "Basic Math Quiz"
INSERT INTO quiz_question (quiz_id, question_id) VALUES 
  (1, 1), 
  (1, 2), 
  (1, 3), 
  (1, 4), 
  (1, 5);

-- Questions for World History Quiz (quiz_id=2)
INSERT INTO question (question_id, question_name, question_text, explanations, is_public, type_id, difficulty_id, media_id, question_duration)
VALUES 
  (6, 'Ancient Civilizations', 'Which civilization is considered the oldest?', 'The Sumerian civilization is considered the oldest.', TRUE, 1, 2, NULL, 30),
  (7, 'Great Conquerors', 'Who was known as the king of the Macedonian Empire?', 'Alexander the Great was the king of the Macedonian Empire.', TRUE, 1, 2, NULL, 30),
  (8, 'Renaissance', 'What period followed the Middle Ages in Europe?', 'The Renaissance followed the Middle Ages in Europe.', TRUE, 1, 2, NULL, 30),
  (9, 'World Wars', 'In what year did World War I begin?', 'World War I began in 1914.', TRUE, 1, 2, NULL, 30),
  (10, 'The Cold War', 'Which two superpowers were primarily involved in the Cold War?', 'The United States and the Soviet Union were primarily involved in the Cold War.', TRUE, 1, 2, NULL, 30);

-- Connecting questions to the "World History Quiz"
INSERT INTO quiz_question (quiz_id, question_id) VALUES 
  (2, 6), 
  (2, 7), 
  (2, 8), 
  (2, 9), 
  (2, 10);

-- Questions for Advanced Physics Quiz (quiz_id=3)
INSERT INTO question (question_id, question_name, question_text, explanations, is_public, type_id, difficulty_id, media_id, question_duration)
VALUES 
  (11, 'Newton\'s Second Law', 'What does Newton\'s Second Law of Motion state?', 'It states that the force acting on an object is equal to the mass of that object times its acceleration.', TRUE, 1, 2, NULL, 30),
  (12, 'The Speed of Light', 'What is the speed of light in a vacuum?', 'The speed of light in a vacuum is approximately 299,792 kilometers per second.', TRUE, 1, 2, NULL, 30),
  (13, 'Quantum Mechanics', 'Which principle is a fundamental concept in quantum mechanics?', 'The uncertainty principle is a key part of quantum mechanics.', TRUE, 1, 3, NULL, 30),
  (14, 'Thermodynamics', 'What does the first law of thermodynamics state?', 'The first law, also known as Law of Conservation of Energy, states that energy cannot be created or destroyed in an isolated system.', TRUE, 1, 2, NULL, 30),
  (15, 'Electromagnetism', 'What does the electromagnetic force do?', 'The electromagnetic force is responsible for electric and magnetic forces and interactions.', TRUE, 1, 2, NULL, 30);

-- Connecting questions to the "World History Quiz"
INSERT INTO quiz_question (quiz_id, question_id) VALUES 
  (3, 11), 
  (3, 12), 
  (3, 13), 
  (3, 14), 
  (3, 15);

-- Questions for Sport Quiz (quiz_id=4)
INSERT INTO question (question_id, question_name, question_text, explanations, is_public, type_id, difficulty_id, media_id, question_duration)
VALUES 
  (16, 'Olympic Hosts', 'Which city hosted the 2016 Summer Olympics?', 'The 2016 Summer Olympics were held in Rio de Janeiro, Brazil.', TRUE, 1, 2, NULL, 30),
  (17, 'World Cup Winner', 'Which country won the 2018 FIFA World Cup?', 'France won the 2018 FIFA World Cup.', TRUE, 1, 2, NULL, 30),
  (18, 'Fastest Man', 'Who is known as the fastest man in the world?', 'Usain Bolt is known as the fastest man in the world.', TRUE, 1, 2, NULL, 30),
  (19, 'NBA Championships', 'Which team has won the most NBA Championships?', 'The Boston Celtics have won the most NBA Championships.', TRUE, 1, 2, NULL, 30),
  (20, 'Grand Slam Titles', 'Who has won the most Grand Slam titles in tennis?', 'Margaret Court has won the most Grand Slam titles in tennis.', TRUE, 1, 2, NULL, 30);

-- Connecting questions to the "Sport Quiz"
INSERT INTO quiz_question (quiz_id, question_id) VALUES 
  (4, 16), 
  (4, 17), 
  (4, 18), 
  (4, 19), 
  (4, 20);

-- Answers for Basic Math Quiz Questions
INSERT INTO answer (answer_id, answer_text) VALUES 
  (1, '8'), 
  (2, '6'), 
  (3, '10'),
  (4, '4'), 
  (5, '5'), 
  (6, '3'), 
  (7, '6'),
  (8, '20'), 
  (9, '25'), 
  (10, '15'),
  (11, '5'), 
  (12, '4');

-- Connecting answers to Basic Math Quiz Questions
INSERT INTO answer_question (question_id, answer_id, is_correct) VALUES 
  (1, 1, TRUE), 
  (1, 2, FALSE), 
  (1, 3, FALSE),
  (2, 4, TRUE), 
  (2, 5, FALSE), 
  (2, 6, FALSE), 
  (2, 7, FALSE),
  (3, 8, TRUE), 
  (3, 9, FALSE), 
  (3, 10, FALSE),
  (4, 11, TRUE), 
  (4, 12, FALSE);

-- Answers for World History Quiz Questions
INSERT INTO answer (answer_id, answer_text) VALUES 
  (13, 'Sumerian'), 
  (14, 'Egyptian'), 
  (15, 'Indus Valley'), 
  (16, 'Minoan'),
  (17, 'Alexander the Great'), 
  (18, 'Julius Caesar'), 
  (19, 'Genghis Khan'), 
  (20, 'Napoleon Bonaparte'),
  (21, 'Renaissance'), 
  (22, 'Industrial Revolution'), 
  (23, 'Victorian Era'),
  (24, '1914'), 
  (25, '1918'), 
  (26, '1939'), 
  (27, '1945'),
  (28, 'United States and Soviet Union'), 
  (29, 'United States and China'), 
  (30, 'Germany and Russia'), 
  (31, 'United Kingdom and France');

-- Connecting answers to World History Quiz Questions
INSERT INTO answer_question (question_id, answer_id, is_correct) VALUES 
  (6, 13, TRUE), 
  (6, 14, FALSE), 
  (6, 15, FALSE), 
  (6, 16, FALSE),
  (7, 17, TRUE), 
  (7, 18, FALSE), 
  (7, 19, FALSE), 
  (7, 20, FALSE),
  (8, 21, TRUE), 
  (8, 22, FALSE), 
  (8, 23, FALSE),
  (9, 24, TRUE), 
  (9, 25, FALSE), 
  (9, 26, FALSE), 
  (9, 27, FALSE),
  (10, 28, TRUE), 
  (10, 29, FALSE), 
  (10, 30, FALSE), 
  (10, 31, FALSE);

-- Answers for Advanced Physics Quiz Questions
INSERT INTO answer (answer_id, answer_text) VALUES 
  (32, 'F=ma'), 
  (33, 'F=mv'), 
  (34, 'F=mg'),
  (35, '299,792 km/s'), 
  (36, '150,000 km/s'), 
  (37, '1,080 million km/h'),
  (38, 'Uncertainty Principle'), 
  (39, 'Theory of Relativity'), 
  (40, 'Hookes Law'),
  (41, 'Energy is conserved'), 
  (42, 'Energy is infinite'), 
  (43, 'Energy can be created'),
  (44, 'Generates light'), 
  (45, 'Causes gravity'), 
  (46, 'Responsible for chemical bonds'), 
  (47, 'Transmits sound');

-- Connecting answers to Advanced Physics Quiz Questions
INSERT INTO answer_question (question_id, answer_id, is_correct) VALUES 
  (11, 32, TRUE), 
  (11, 33, FALSE), 
  (11, 34, FALSE),
  (12, 35, TRUE), 
  (12, 36, FALSE), 
  (12, 37, FALSE),
  (13, 38, TRUE), 
  (13, 39, FALSE), 
  (13, 40, FALSE),
  (14, 41, TRUE), 
  (14, 42, FALSE), 
  (14, 43, FALSE),
  (15, 44, FALSE), 
  (15, 45, FALSE), 
  (15, 46, TRUE), 
  (15, 47, FALSE);

-- Answers for Sport Quiz Questions
INSERT INTO answer (answer_id, answer_text) VALUES 
  (48, 'Rio de Janeiro'), 
  (49, 'London'), 
  (50, 'Tokyo'),
  (51, 'France'), 
  (52, 'Croatia'), 
  (53, 'Germany'),
  (54, 'Usain Bolt'), 
  (55, 'Tyson Gay'), 
  (56, 'Yohan Blake'),
  (57, 'Boston Celtics'), 
  (58, 'Los Angeles Lakers'), 
  (59, 'Chicago Bulls'),
  (60, 'Margaret Court'), 
  (61, 'Serena Williams'), 
  (62, 'Steffi Graf');

-- Connecting answers to Sport Quiz Questions
INSERT INTO answer_question (question_id, answer_id, is_correct) VALUES 
  (16, 48, TRUE), 
  (16, 49, FALSE), 
  (16, 50, FALSE),
  (17, 51, TRUE), 
  (17, 52, FALSE), 
  (17, 53, FALSE),
  (18, 54, TRUE), 
  (18, 55, FALSE), 
  (18, 56, FALSE),
  (19, 57, TRUE), 
  (19, 58, FALSE), 
  (19, 59, FALSE),
  (20, 60, TRUE), 
  (20, 61, FALSE), 
  (20, 62, FALSE);

-- Inserting into collaborator (adminUser is set as author)
INSERT INTO collaborator (user_id, quiz_id, type_id) VALUES
  (1, 1, 1),
  (1, 2, 1),
  (1, 3, 1),
  (1, 4, 1);
