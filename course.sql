CREATE DATABASE course_db;

CREATE TABLE Admin(
                      id SERIAL PRIMARY KEY ,
                      username varchar(50) UNIQUE NOT NULL ,
                      password varchar(225) NOT NULL
);

CREATE TABLE student (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         dob DATE NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         sex BOOLEAN NOT NULL,
                         phone VARCHAR(20),
                         password VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        duration INT NOT NULL CHECK (duration > 0),
                        instructor VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TYPE enrollment_status AS ENUM (
    'WAITING',
    'DENIED',
    'CANCEL',
    'CONFIRM'
    );

CREATE TABLE enrollment (
                            id SERIAL PRIMARY KEY,
                            student_id INT NOT NULL,
                            course_id INT NOT NULL,
                            registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            status enrollment_status DEFAULT 'WAITING',

                            CONSTRAINT fk_student
                                FOREIGN KEY (student_id)
                                    REFERENCES student(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_course
                                FOREIGN KEY (course_id)
                                    REFERENCES course(id)
                                    ON DELETE CASCADE
);

-- Performance indexes / constraints for common lookups
CREATE INDEX IF NOT EXISTS idx_enrollment_student_id ON enrollment(student_id);
CREATE INDEX IF NOT EXISTS idx_enrollment_course_id ON enrollment(course_id);
CREATE UNIQUE INDEX IF NOT EXISTS ux_enrollment_student_course ON enrollment(student_id, course_id);
CREATE INDEX IF NOT EXISTS idx_course_name ON course(name);

INSERT INTO student(name, dob, email, sex, phone, password)
VALUES
    ('Nguyen Van A','2002-05-10','f@gmail.com',true,'0900000001','$2a$10$testpass1'),
    ('Tran Thi B','2001-09-12','b@gmail.com',false,'0900000002','$2a$10$testpass2'),
    ('Le Van C','2000-03-18','c@gmail.com',true,'0900000003','$2a$10$testpass3'),
    ('Pham Thi D','2002-11-22','d@gmail.com',false,'0900000004','$2a$10$testpass4'),
    ('Hoang Van E','2003-01-30','e@gmail.com',true,'0900000005','$2a$10$testpass5');

INSERT INTO course(name, duration, instructor)
VALUES
    ('Java Backend',180,'Cunos'),
    ('Spring Boot',150,'Nguyen Van Dev'),
    ('Database PostgreSQL',120,'Tran DBA'),
    ('Web Development',200,'Le Frontend'),
    ('Data Structures',160,'Pham Algorithm');

