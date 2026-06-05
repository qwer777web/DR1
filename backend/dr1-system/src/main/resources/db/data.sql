INSERT INTO sys_user (username, nickname, email, phone, status, create_time, update_time)
VALUES
    ('admin', '系统管理员', 'admin@dr1.local', '18800000001', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('teacher', '课程教师', 'teacher@dr1.local', '18800000002', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('student', '作业学生', 'student@dr1.local', '18800000003', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

