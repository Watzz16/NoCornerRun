package services;

import java.io.IOException;

public class MainTest {

    public static void main(String[] args) {
        RequestService rs = new RequestService();
        try {
            rs.login("Jonn", "test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
