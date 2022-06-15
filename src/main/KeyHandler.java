package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    public boolean upKeyPressed = false;
    public boolean canPressUpKey = true;

    public boolean downKeyPressed = false;
    public boolean canPressDownKey = true;

    public boolean rightKeyPressed = false;
    public boolean canPressRight = true;

    private GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(gamePanel.gameState == GameState.RUNNING) keyHandleRunningState(code);
        if(gamePanel.gameState == GameState.GAMEOVER) keyHandleGameOverState(code);
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
        } else if(code == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false;
            canPressRight = true;
        }
    }

    private void keyHandleRunningState(int code) {
        if(code == KeyEvent.VK_UP && canPressUpKey) {
            upKeyPressed = true;
            canPressUpKey = false;
        } else if(code == KeyEvent.VK_DOWN && canPressDownKey) {
            downKeyPressed = true;
            canPressDownKey = false;
        } else if(code == KeyEvent.VK_RIGHT && canPressRight) {
            rightKeyPressed = true;
            canPressRight = false;
        }
    }

    private void keyHandleGameOverState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gamePanel.gameState = GameState.RUNNING;
            gamePanel.player.resetPlayer();
            gamePanel.enemyManager.resetEnemies();
            gamePanel.itemManager.resetItems();
            gamePanel.score = 0.0; //reset score
            gamePanel.playMusic();
        }
    }
}
