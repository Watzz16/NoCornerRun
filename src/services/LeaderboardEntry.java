package services;

public class LeaderboardEntry {

    private String playername;
    private int highscore;

    public LeaderboardEntry(String playername, int highscore) {
        this.playername = playername;
        this.highscore = highscore;
    }

    public String getPlayername() {
        return playername;
    }

    public int getHighscore() {
        return highscore;
    }

    @Override
    public String toString() {
        return "LeaderboardEntry{" +
                "playername='" + playername + '\'' +
                ", highscore=" + highscore +
                '}';
    }
}
