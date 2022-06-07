package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upKeyPressed = false;
    public boolean canPressUpKey = true;

    public boolean downKeyPressed = false;
    public boolean canPressDownKey = true;

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP && canPressUpKey) {
            upKeyPressed = true;
            canPressUpKey = false;
        } else if(code == KeyEvent.VK_DOWN && canPressDownKey) {
            downKeyPressed = true;
            canPressDownKey = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP) {
            canPressUpKey = true;
            upKeyPressed = false;
        } else if(code == KeyEvent.VK_DOWN) {
            downKeyPressed = false;
            canPressDownKey = true;
        }
    }
}
