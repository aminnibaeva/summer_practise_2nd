package ru.itis;

import ru.itis.models.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonsRepository {
    List<Lesson> findAllByName(String name);

    Optional<Lesson> findById(Integer id);

    void save(Lesson lesson);

    void update(Lesson lesson);
}
