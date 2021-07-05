package models;

public class ClientToken {
    private static String token;

    public String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ClientToken.token = token;
    }
}
