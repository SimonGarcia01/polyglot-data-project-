-- 1. Countries
INSERT INTO COUNTRIES (code, name) VALUES (1, 'Colombia');

-- 2. Departments
INSERT INTO DEPARTMENTS (code, name, country_code) VALUES
                                                       (1, 'Valle del Cauca', 1),
                                                       (2, 'Cundinamarca', 1),
                                                       (5, 'Antioquia', 1),
                                                       (8, 'Atlántico', 1),
                                                       (11, 'Bogotá D.C.', 1);

-- 3. Cities
INSERT INTO CITIES (code, name, dept_code) VALUES
                                               (101, 'Cali', 1),
                                               (102, 'Bogotá', 11),
                                               (103, 'Medellín', 5),
                                               (104, 'Barranquilla', 8),
                                               (105, 'Soledad', 8);

-- 4. Employee Types
INSERT INTO EMPLOYEE_TYPES (name) VALUES
                                      ('Docente'),
                                      ('Administrativo'),
                                      ('Instructor');

-- 5. Contract Types
INSERT INTO CONTRACT_TYPES (name) VALUES
                                      ('Planta'),
                                      ('Cátedra');

-- 6. Faculties (dean_id temporarily NULL)
INSERT INTO FACULTIES (code, name, location, phone_number, dean_id) VALUES
                                                                        (1, 'Facultad de Ciencias', 'Call3', '555-1234', NULL),
                                                                        (2, 'Facultad de Ingeniería', 'Call4', '555-5678', NULL);

-- 7. Campuses
INSERT INTO CAMPUSES (code, name, city_code) VALUES
                                                 (1, 'Campus Cali', 101),
                                                 (2, 'Campus Bogotá', 102),
                                                 (3, 'Campus Medellín', 103),
                                                 (4, 'Campus Barranquilla', 104);

-- 8. Employees
INSERT INTO EMPLOYEES (id, first_name, last_name, email, contract_type, employee_type, faculty_code, campus_code, birth_place_code) VALUES
                                                                                                                                        ('1001', 'Juan', 'Pérez', 'juan.perez@univcali.edu.co', 'Planta', 'Docente', 1, 1, 101),
                                                                                                                                        ('1002', 'María', 'Gómez', 'maria.gomez@univcali.edu.co', 'Planta', 'Administrativo', 1, 2, 102),
                                                                                                                                        ('1003', 'Carlos', 'López', 'carlos.lopez@univcali.edu.co', 'Cátedra', 'Docente', 2, 1, 103),
                                                                                                                                        ('1004', 'Carlos', 'Mejía', 'carlos.mejia@univcali.edu.co', 'Planta', 'Docente', 1, 3, 103),
                                                                                                                                        ('1005', 'Sandra', 'Ortiz', 'sandra.ortiz@univcali.edu.co', 'Cátedra', 'Docente', 2, 4, 104),
                                                                                                                                        ('1006', 'Julián', 'Reyes', 'julian.reyes@univcali.edu.co', 'Planta', 'Administrativo', 2, 1, 105),
                                                                                                                                        ('1007', 'Paula', 'Ramírez', 'paula.ramirez@univcali.edu.co', 'Planta', 'Instructor', 1, 1, 101),
                                                                                                                                        ('1008', 'Andrés', 'Castro', 'andres.castro@univcali.edu.co', 'Cátedra', 'Instructor', 1, 3, 103);

-- 9. Update Faculties dean_id
UPDATE FACULTIES SET dean_id = '1001' WHERE code = 1;
UPDATE FACULTIES SET dean_id = '1002' WHERE code = 2;

-- 10. Areas
INSERT INTO AREAS (code, name, faculty_code, coordinator_id) VALUES
                                                                 (1, 'Área de Sociales', 1, '1001'),
                                                                 (2, 'Área de Ingeniería', 2, '1003'),
                                                                 (3, 'Área de Bienestar', 1, '1007');

-- 11. Programs
INSERT INTO PROGRAMS (code, name, area_code) VALUES
                                                 (1, 'Psicología', 1),
                                                 (2, 'Ingeniería de Sistemas', 2);

-- 12. Subjects
INSERT INTO SUBJECTS (code, name, program_code) VALUES
                                                    ('S101', 'Psicología General', 1),
                                                    ('S102', 'Cálculo I', 2),
                                                    ('S103', 'Programación', 2),
                                                    ('S104', 'Estructuras de Datos', 2),
                                                    ('S105', 'Bases de Datos', 2),
                                                    ('S106', 'Redes de Computadores', 2),
                                                    ('S107', 'Sistemas Operativos', 2),
                                                    ('S108', 'Algoritmos Avanzados', 2);

-- 13. Groups
INSERT INTO GROUPS (NRC, number, semester, subject_code, professor_id) VALUES
                                                                           ('G101', 1, '2023-2', 'S101', '1001'),
                                                                           ('G102', 2, '2023-2', 'S102', '1003'),
                                                                           ('G103', 3, '2023-2', 'S103', '1004'),
                                                                           ('G104', 4, '2023-2', 'S105', '1005'),
                                                                           ('G105', 5, '2023-2', 'S106', '1004');

-- 14. Students
INSERT INTO STUDENTS (id, first_name, last_name, email, birth_date, birth_place_code, campus_code) VALUES
                                                                                                       ('2001', 'Laura', 'Hernández', 'laura.hernandez@univcali.edu.co', '2000-03-15', 101, 1),
                                                                                                       ('2002', 'Pedro', 'Martínez', 'pedro.martinez@univcali.edu.co', '1999-07-22', 103, 1),
                                                                                                       ('2003', 'Ana', 'Suárez', 'ana.suarez@univcali.edu.co', '2001-01-05', 102, 2),
                                                                                                       ('2004', 'Luis', 'Ramírez', 'luis.ramirez@univcali.edu.co', '1998-11-30', 104, 3),
                                                                                                       ('2005', 'Sofía', 'García', 'sofia.garcia@univcali.edu.co', '2000-09-12', 105, 2);

-- 15. Enrollments
INSERT INTO ENROLLMENTS (student_id, NRC, enrollment_date, status) VALUES
                                                                       ('2001', 'G101', '2023-08-01', 'Active'),
                                                                       ('2001', 'G102', '2023-08-01', 'Active'),
                                                                       ('2002', 'G103', '2023-08-02', 'Active'),
                                                                       ('2003', 'G103', '2023-08-02', 'Active'),
                                                                       ('2004', 'G104', '2023-08-03', 'Withdrawn'),
                                                                       ('2005', 'G105', '2023-08-03', 'Active');

-- 16. Users (Estudiantes, Empleados y Entrenadores)
-- IMPORTANTE: Los Instructores (Paula y Andrés) son ENTRENADORES certificados
INSERT INTO USERS (username, password_hash, role, student_id, employee_id, is_active, created_at) VALUES
    -- Estudiantes
    ('laura.h', 'password123', 'STUDENT', '2001', NULL, TRUE, CURRENT_TIMESTAMP),
    ('pedro.m', 'password123', 'STUDENT', '2002', NULL, TRUE, CURRENT_TIMESTAMP),
    ('ana.s', 'password123', 'STUDENT', '2003', NULL, TRUE, CURRENT_TIMESTAMP),
    ('luis.r', 'password123', 'STUDENT', '2004', NULL, TRUE, CURRENT_TIMESTAMP),
    ('sofia.g', 'password123', 'STUDENT', '2005', NULL, TRUE, CURRENT_TIMESTAMP),
    -- Administrador
    ('admin', 'adminpass', 'ADMIN', NULL, NULL, TRUE, CURRENT_TIMESTAMP),
    -- Empleados Administrativos/Docentes
    ('juan.p', 'password123', 'EMPLOYEE', NULL, '1001', TRUE, CURRENT_TIMESTAMP),
    ('maria.g', 'password123', 'EMPLOYEE', NULL, '1002', TRUE, CURRENT_TIMESTAMP),
    ('carlos.l', 'password123', 'EMPLOYEE', NULL, '1003', TRUE, CURRENT_TIMESTAMP),
    ('carlos.m', 'password123', 'EMPLOYEE', NULL, '1004', TRUE, CURRENT_TIMESTAMP),
    ('sandra.o', 'password123', 'EMPLOYEE', NULL, '1005', TRUE, CURRENT_TIMESTAMP),
    -- Entrenadores Certificados (Instructores de Bienestar)
    ('paula.r', 'password123', 'TRAINER', NULL, '1007', TRUE, CURRENT_TIMESTAMP),
    ('andres.c', 'password123', 'TRAINER', NULL, '1008', TRUE, CURRENT_TIMESTAMP);