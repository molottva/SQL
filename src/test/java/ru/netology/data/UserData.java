package ru.netology.data;

import lombok.Data;

@Data
public class UserData {
    private final String id;
    private final String password;

    public UserData() {
        this.id = "22548480-f1e9-48cb-8f4e-27013299071a";
        this.password = "qwerty123";
    }
}
