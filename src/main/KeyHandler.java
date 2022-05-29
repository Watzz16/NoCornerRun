package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean spacePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_SPACE) {
            //jump event
            System.out.println("Space Bar pressed!");
            spacePressed = true; //set space pressed to true while space bar is pressed
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_SPACE) {
            //jump event
            System.out.println("Space Bar released!");
            spacePressed = false; //once space bar is released, set spacePressed to false
        }
    }
}
