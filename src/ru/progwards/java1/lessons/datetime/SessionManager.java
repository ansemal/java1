package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.util.*;

public class SessionManager {
    static Map <Integer, Map<String, UserSession>> sessions = new HashMap<>();
    private int sessionValid;

    public SessionManager(int sessionValid) {
        this.sessionValid = sessionValid;
    }

    public void add(UserSession userSession) {
        Map<String, UserSession> sessionsPart = new HashMap<>();
        sessionsPart.put(userSession.getUserName(),userSession);
        sessions.put(userSession.getSessionHandle(), sessionsPart);
    }

    public UserSession get (int sessionHandle) {
        if (!sessions.containsKey(sessionHandle))
            return null;
        else {
            for (var entry : sessions.get(sessionHandle).entrySet()) {
                int validYes = LocalDateTime.now().compareTo(entry.getValue().getLastAccess().plusSeconds(sessionValid));
                if (validYes < 0) {
                    entry.getValue().updateLastAccess();
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public UserSession find (String userName) {
        for (var entry : sessions.entrySet()) {
            if (entry.getValue().containsKey(userName)) {
                int validYes = LocalDateTime.now().compareTo(entry.getValue().get(userName).getLastAccess().plusSeconds(sessionValid));
                if (validYes < 0) {
                    entry.getValue().get(userName).updateLastAccess();
                    return entry.getValue().get(userName);
                }
            }
        }
        return null;
    }

    public void delete(int sessionHandle) {
        sessions.remove(sessionHandle);
    }

    public void deleteExpired() {
        Map<Integer, Map<String, UserSession>> sessionsTemp = new HashMap<>(sessions);
        for (var entry : sessionsTemp.entrySet()) {
            Collection <UserSession> userSessions = entry.getValue().values();
            for (UserSession proverka : userSessions) {
                int validYes = LocalDateTime.now().compareTo(proverka.getLastAccess().plusSeconds(sessionValid));
                if (validYes > 0)
                    sessions.remove(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SessionManager test = new SessionManager(5);
        System.out.println(test.find("Alex") == null);
        UserSession alex = new UserSession("Alex");
        test.add(alex);
        test.get(alex.getSessionHandle());
        test.get(alex.getSessionHandle());
        test.get(alex.getSessionHandle());
        Thread.sleep(7000);
        UserSession serg = new UserSession("Serg");
        test.add(serg);
        Thread.sleep(2500);
        UserSession petr = new UserSession("Petr");
        test.add(petr);
        Thread.sleep(2500);
        test.deleteExpired();
        test.delete(petr.getSessionHandle());
    }
}
