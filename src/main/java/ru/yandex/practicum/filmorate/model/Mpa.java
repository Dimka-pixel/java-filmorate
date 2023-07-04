package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Mpa {
    G(1, "нет возрастных ограничений"),
    PG(2, "детям рекомендуется смотреть фильм с родителями"),
    PG13(3, "до 13 лет просмотр не желателен"),
    R(4, "до 17 лет просматривать фильм можно только в присутствии взрослого"),
    NC17(5, "до 18 лет просмотр запрещён");


    private final int id;
    private final String name;

    Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        if (name().equals("PG13")) {
            return "PG-13";
        } else if (name().equals("NC17")) {
            return "NC-17";
        } else {
            return name();
        }
    }

    @Override
    public String toString() {
        return this.name();
    }
}