-- Inserting roles
INSERT INTO role (type)
VALUES 
  ('ROLE_ADMIN'),
  ('ROLE_USER'),
  ('ROLE_GUEST');

-- Inserting users 
-- Password = password123
INSERT INTO user (username, email, password, role_id)
VALUES 
  ('adminUser', 'admin@example.com', '$2a$10$4tzOHGPWBcHNoZvAammZLOldKMRP7.vdQsNBKFuGYAqVW1Tg3OIEu', 1),
  ('regularUser', 'user@example.com', '$2a$10$qdHievWCF6SbISQMVA2smOtR0uQJBd6KRfeMN6gB/MZntmw133SGS', 2),
  ('guestUser', 'guest@example.com', '$2a$10$YT4Zniy6huusodZO919A1OCl4pu/9bobszAGN.tspDHLWQB.Nd87W', 3),
  ('testUser', 'test@example.com', '$2a$10$YT4Zniy6huusodZO919A1OCl4pu/9bobszAGN.tspDHLWQB.Nd87W', 1);


-- Inserting categories
INSERT INTO category (name)
VALUES 
  ('Science'),
  ('Math'),
  ('History'),
  ('Sport'),
  ('Film'),
  ('Food');

-- Inserting difficulty levels
INSERT INTO difficulty_level (difficulty)
VALUES 
  ('Easy'),
  ('Medium'),
  ('Hard');

-- Inserting into type_collaborator
INSERT INTO type_collaborator (name)
VALUES ('Author');

-- Inserting question types
INSERT INTO question_type (type)
VALUES 
  ('Multiple Choice'),
  ('True/False'),
  ('Short Answer');

  -- Inserting templates
INSERT INTO template (name, description, filepath, created_at, user_id)
VALUES 
  ('Default Template', 'Default template for all quizzes', 'path/to/template_a.docx', NOW(), 1);

-- Inserting media types
INSERT INTO media_type (description)
VALUES 
  ('Image'),
  ('Video');

-- Inserting multi_medias
INSERT INTO multi_media (file_path, description, created_at, type_id)
VALUES 
  ('path/to/image.jpg', 'An image file', NOW(), 1),
  ('path/to/video.mp4', 'A video file', NOW(), 2);