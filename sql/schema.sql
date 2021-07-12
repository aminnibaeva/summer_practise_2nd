create table teacher
(
    id              serial primary key,
    first_name      varchar(30) not null,
    last_name     varchar(30) not null,
    work_experience int
);

create table course
(
    id         serial primary key,
    name       varchar(30) not null,
    start_date varchar(30),
    end_date   varchar(30),
    teacher_id int,
    foreign key (teacher_id) references teacher (id)
);


create table student
(
    id           serial primary key,
    first_name   varchar(30),
    last_name  varchar(30),
    group_number int
);

create table lesson
(
    id        serial primary key,
    name      varchar(30) not null,
    date      varchar(30),
    course_id int,
    foreign key (course_id) references course (id)
);

create table course_students
(
    id_student int,
    id_course  int,
    foreign key (id_course) references course (id),
    foreign key (id_student) references student (id)
);

create table course_teachers
(
    id_teacher int,
    id_course  int,
    foreign key (id_course) references course (id),
    foreign key (id_teacher) references teacher (id)
);
