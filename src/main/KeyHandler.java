package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean UpKeyPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) {
            //jump event
            System.out.println("KeyUp pressed!");
            UpKeyPressed = true; //set space pressed to true while space bar is pressed
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) {
            //jump event
            System.out.println("KeyUp released!");
            UpKeyPressed = false; //once space bar is released, set spacePressed to false
        }
    }
}
