package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface CrudStorage < T > {
    int addObject(T t);

    void updateObject(T t);

    T getObjectById(int id);

    List < T > getAllObjects();

    void deleteObject(int id);
}
