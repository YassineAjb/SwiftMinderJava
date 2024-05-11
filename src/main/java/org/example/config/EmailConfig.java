package org.example.config;

public class EmailConfig {
    private static final String USERNAME = "yassine.ajbouni@esprit.tn";
    private static final String PASSWORD = "zdmg xwfb eriz esdo";
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getHost() {
        return HOST;
    }

    public int getPort() {
        return PORT;
    }
}
