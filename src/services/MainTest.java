package services;

import java.io.IOException;
import java.util.Arrays;

public class MainTest {

    public static void main(String[] args) {
        RequestService rs = new RequestService();
        try {
            rs.register("Luca", "abcde");
            rs.login("Luca", "abcde");
            System.out.println(rs.getPlayer().toString());

            System.out.println(Arrays.toString(rs.getLeaderboard()));
            rs.updatePlayerStats(110, 20);

            System.out.println(rs.getPlayer().toString());
            System.out.println(Arrays.toString(rs.getLeaderboard()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
