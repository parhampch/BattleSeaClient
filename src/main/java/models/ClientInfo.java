package models;

public class ClientInfo {
    private static String token = null;
    private static String onGoingGameId = null;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ClientInfo.token = token;
    }

    public static String getOnGoingGameId() {
        return onGoingGameId;
    }

    public static void setOnGoingGameId(String onGoingGameId) {
        ClientInfo.onGoingGameId = onGoingGameId;
    }
}
