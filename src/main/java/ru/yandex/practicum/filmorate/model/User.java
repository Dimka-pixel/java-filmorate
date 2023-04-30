package model;

import java.time.LocalDate;

@lombok.Data
@lombok.AllArgsConstructor
public class User {
    final private int ID;
    final private String email;
    private String name;
    private String login;
    private LocalDate birthday;

}
