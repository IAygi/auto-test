package ru.iaygi.extension;

import java.util.List;

public class User {

    private List<String> userList;

    public User(List<String> userList) {
        this.userList = userList;
    }

//    public List<String> userList;

    public List<String> getUser() {
        return userList;
    }
}
