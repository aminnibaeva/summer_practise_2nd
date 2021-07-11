insert into student (first_name, second_name, group_number)
VALUES ('Станислав', 'Лихачёв', 1);
insert into student (first_name, second_name, group_number)
VALUES ('Гарри', 'лейников', 2);
insert into student (first_name, second_name, group_number)
VALUES ('Ольга', 'Кудрявцева', 3);
insert into student (first_name, second_name, group_number)
VALUES ('Юлиан', 'Чиркин', 3);
insert into student (first_name, second_name, group_number)
VALUES ('Варвара', 'Воронцова', 4);
insert into student (first_name, second_name, group_number)
VALUES ('Владислав', 'Рябов', 4);
insert into student (first_name, second_name, group_number)
VALUES ('Клара', 'Русакова', 4);

insert into teacher (first_name, second_name, work_experience)
VALUES ('Павел', 'Быков', 3);
insert into teacher (first_name, second_name, work_experience)
VALUES ('Анжела', 'Зиновьева', 15);
insert into teacher (first_name, second_name, work_experience)
VALUES ('Таисия', 'Шарапова', 7);

insert into course (name, start_date, end_date, teacher_id)
VALUES ('Английский', '2020-06-02', '2021-08-20', 1);
insert into course (name, start_date, end_date, teacher_id)
VALUES ('Немецкий', '2020-01-05', '2022-03-02', 1);
insert into course (name, start_date, end_date, teacher_id)
VALUES ('Информатика', '2018-06-02', '2021-10-15', 2);
insert into course (name, start_date, end_date, teacher_id)
VALUES ('Алгебра', '2020-11-07', '2022-05-30', 3);

insert into lesson (name, date, course_id)
VALUES ('Английский', '2020-06-03', 1);
insert into lesson (name, date, course_id)
VALUES ('Алгебра', '2020-01-06', 4);
insert into lesson (name, date, course_id)
VALUES ('Немецкий', '2020-06-02', 2);
insert into lesson (name, date, course_id)
VALUES ('Алгебра', '2020-11-07', 4);
insert into lesson (name, date, course_id)
VALUES ('Информатика', '2020-11-07', 3);
insert into lesson (name, date, course_id)
VALUES ('Информатика', '2020-11-07', 3);

insert into course_teachers (id_teacher, id_course)
VALUES (2, 1);
insert into course_teachers (id_teacher, id_course)
VALUES (2, 2);
insert into course_teachers (id_teacher, id_course)
VALUES (1, 3);
insert into course_teachers (id_teacher, id_course)
VALUES (3, 4);

insert into course_students (id_student, id_course)
VALUES (1, 1);
insert into course_students (id_student, id_course)
VALUES (1, 2);
insert into course_students (id_student, id_course)
VALUES (2, 2);
insert into course_students (id_student, id_course)
VALUES (2, 3);
insert into course_students (id_student, id_course)
VALUES (3, 4);
insert into course_students (id_student, id_course)
VALUES (4, 4);
insert into course_students (id_student, id_course)
VALUES (5, 4);
insert into course_students (id_student, id_course)
VALUES (6, 2);
insert into course_students (id_student, id_course)
VALUES (6, 3);
insert into course_students (id_student, id_course)
VALUES (6, 4);
insert into course_students (id_student, id_course)
VALUES (7, 3);