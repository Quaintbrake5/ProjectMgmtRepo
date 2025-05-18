-- ensure the permission exists
INSERT INTO permissions (name, description)
  VALUES ('EDIT_USER_PROFILE','Permission to edit user profiles')
ON CONFLICT (name) DO NOTHING;

-- grant it to ROLE_ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.name = 'EDIT_USER_PROFILE'
WHERE r.name = 'ROLE_ADMIN'
  AND NOT EXISTS (
    SELECT 1 FROM role_permissions rp
    WHERE rp.role_id = r.id
      AND rp.permission_id = p.id
  );
