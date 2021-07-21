package models;

public class ClientInfo {
    private static String token = null;
    private static int onGoingGameId = 0;
    private static String competitorUsername =  null ;
    private static boolean turn = false;


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ClientInfo.token = token;
    }

    public static int getOnGoingGameId() {
        return onGoingGameId;
    }

    public static void setOnGoingGameId(int onGoingGameId) {
        ClientInfo.onGoingGameId = onGoingGameId;
    }

    public static String getCompetitorUsername() {
        return competitorUsername;
    }

    public static void setCompetitorUsername(String competitorUsername) {
        ClientInfo.competitorUsername = competitorUsername;
    }

    public static boolean isTurn() {
        return turn;
    }

    public static void setTurn(boolean turn) {
        ClientInfo.turn = turn;
    }

}
