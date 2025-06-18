-- V3__Seed_admin_user.sql

-- Insert the default admin user
INSERT INTO users (name, password_hash, email, status, created_at, updated_at)
VALUES ('Admin User',
        '$2a$10$Tr08hGqIwOd.ML5P12tm9uTQjJF3R4HRJM0HpznkilrlU/W9EYUQO',
        'denzylibe6@gmail.com',
        'ACTIVE',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
       );

-- Grant ROLE_ADMIN to the new user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'denzylibe6@gmail.com'
  AND NOT EXISTS (
    SELECT 1 FROM user_roles ur
    WHERE ur.user_id = u.id
      AND ur.role_id = r.id
  );

