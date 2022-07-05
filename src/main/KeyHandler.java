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
        if(gamePanel.gameState == GameState.MAINMENU) keyHandleMenuState(code);
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
            gamePanel.levelManager.setLevel(1);
            gamePanel.score = 0.0; //reset score
            gamePanel.playMusic();
        }
    }

    private void keyHandleMenuState(int code) {
        if(code == KeyEvent.VK_UP) {
            if(gamePanel.ui.commandNum > 0) gamePanel.ui.commandNum--;
        } else if(code == KeyEvent.VK_DOWN) {
            if(gamePanel.ui.commandNum < gamePanel.ui.maxCommands) gamePanel.ui.commandNum++;
        }

        if(code == KeyEvent.VK_ENTER) {
            switch(gamePanel.ui.commandNum) {
                case 0 -> {
                    //username input
                }
                case 1 -> {
                    //password input
                }
                case 2 -> {
                    //LOGIN
                }
                case 3 -> {
                    //PLAY
                    gamePanel.gameState = GameState.RUNNING;
                    gamePanel.playMusic();
                }
                case 4 -> {
                    //QUIT
                    System.exit(0);
                }
            }
        }
    }

}
