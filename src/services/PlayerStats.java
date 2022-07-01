package services;

public class PlayerStats {

    private String playername;
    private int highscore;
    private int knowledge;

    public PlayerStats(String playername, int highscore, int knowledge) {
        this.playername = playername;
        this.highscore = highscore;
        this.knowledge = knowledge;
    }

    public String getPlayername() {
        return playername;
    }

    public int getHighscore() {
        return highscore;
    }

    public int getKnowledge() {
        return knowledge;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "playername='" + playername + '\'' +
                ", highscore=" + highscore +
                ", knowledge=" + knowledge +
                '}';
    }
}
