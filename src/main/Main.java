package main;

import javax.swing.*;

public class Main {

    public static void main(String [] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "No Corner Run");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame gameWindow = new JFrame(); //create a new window (class is called JFrame, gameWindow is our object)
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //what happens when we press the close button of the window?
        gameWindow.setResizable(false); //can we resize the window? False
        gameWindow.setTitle("No Corner Run"); //title of window

        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null); //set location of panel
        gameWindow.setVisible(true); //make window visible

        gamePanel.startGameThread();
    }

}
