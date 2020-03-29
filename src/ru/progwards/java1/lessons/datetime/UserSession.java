package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private String userName;
    private LocalDateTime lastAccess;

    public UserSession(String userName) {
        this.userName = userName;
        sessionHandle = new Random().nextInt();
        lastAccess = LocalDateTime.now();
    }

    public int getSessionHandle (){
        return sessionHandle;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    void updateLastAccess() {
        lastAccess = LocalDateTime.now();
    }
}
