package models;

public class ClientToken {
    private static String token = null;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ClientToken.token = token;
    }
}
