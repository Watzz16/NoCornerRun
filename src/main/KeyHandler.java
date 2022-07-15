package main;


import entity.PlayerSkin;
import services.PlayerStats;
import services.RequestService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Optional;

public class KeyHandler implements KeyListener {

    public boolean upKeyPressed = false;
    public boolean canPressUpKey = true;

    public boolean downKeyPressed = false;
    public boolean canPressDownKey = true;

    public boolean rightKeyPressed = false;
    public boolean canPressRight = true;

    private GamePanel gamePanel;
    private RequestService requestService;

    public KeyHandler(GamePanel gamePanel, RequestService requestService) {
        this.gamePanel = gamePanel;
        this.requestService = requestService;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch(gamePanel.gameState) {
            case RUNNING -> keyHandleRunningState(code);
            case GAMEOVER -> keyHandleGameOverState(code);
            case MAINMENU -> keyHandleMenuState(code);
            case LOGINREGISTER -> {
                keyHandleLoginRegisterState(code);
                if(gamePanel.ui.commandNum == 0 || gamePanel.ui.commandNum == 1) handleMenuKeyTyping(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(gamePanel.gameState != GameState.RUNNING) return;

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

    private void retryClick() {
        if(requestService.isLoggedIn()) {
            System.out.println("FETCHING PLAYER STATS");
            try {
                PlayerStats player = requestService.getPlayer();
                gamePanel.currentlyCollectedGems = player.getKnowledge();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gamePanel.gameState = GameState.RUNNING;
        resetGameComponents();
        gamePanel.playMusic();
    }

    private void resetGameComponents() {
        gamePanel.player.resetPlayer();
        gamePanel.enemyManager.resetEnemies();
        gamePanel.itemManager.resetItems();
        gamePanel.levelManager.setLevel(1);
        gamePanel.score = 0.0; //reset score
    }

    private void keyHandleMenuState(int code) {
        handleMenuSelector(code);
        handSkinSelector(code);

        if(code == KeyEvent.VK_ENTER) {
            switch(gamePanel.ui.commandNum) {
                case 0 -> {
                    //PLAY
                    gamePanel.player.setSkin(gamePanel.ui.selectedSkin);
                    gamePanel.gameState = GameState.RUNNING;
                    gamePanel.playMusic();
                }
                case 1 -> {
                    //QUIT
                    System.exit(0);
                }
            }
        }
    }

    private void keyHandleLoginRegisterState(int code) {
        handleMenuSelector(code);

        if(code == KeyEvent.VK_ENTER) {
            switch(gamePanel.ui.commandNum) {
                case 2 -> {
                    //LOGIN
                    handleLoginPress();
                }
                case 3 -> {
                    //REGISTER
                    handleRegisterPress();
                }
            }
        }
    }

    private void keyHandleGameOverState(int code) {
        handleMenuSelector(code);

        if(code == KeyEvent.VK_ENTER) {
            switch(gamePanel.ui.commandNum) {
                case 0 -> {
                    //RETRY
                    retryClick();
                }
                case 1 -> {
                    //BACK TO MENU
                    gamePanel.ui.minCommandNum = 0;
                    gamePanel.ui.maxCommandNum = 1;
                    gamePanel.ui.commandNum = 0;
                    resetGameComponents();
                    gamePanel.gameState = GameState.MAINMENU;
                }
                case 2 -> {
                    //QUIT
                    System.exit(0);
                }
            }
        }
    }

    private void handleMenuSelector(int code) {
        if(code == KeyEvent.VK_UP) {
            if(gamePanel.ui.commandNum > gamePanel.ui.minCommandNum) gamePanel.ui.commandNum--;
        } else if(code == KeyEvent.VK_DOWN) {
            if(gamePanel.ui.commandNum < gamePanel.ui.maxCommandNum) gamePanel.ui.commandNum++;
        }
    }

    private void handSkinSelector(int code) {
        if(code == KeyEvent.VK_LEFT) {
            if(gamePanel.ui.skinSelectorIndex > 0) {
                gamePanel.ui.skinSelectorIndex--;
            } else {
                gamePanel.ui.skinSelectorIndex = PlayerSkin.getSkinWheel().length-1;
            }
        } else if(code == KeyEvent.VK_RIGHT) {
            if(gamePanel.ui.skinSelectorIndex < PlayerSkin.getSkinWheel().length-1) {
                gamePanel.ui.skinSelectorIndex++;
            } else {
                gamePanel.ui.skinSelectorIndex = 0;
            }
        } else {
            return;
        }

        //update selected skin in UI here, so we dont need to set this every few milliseconds
        gamePanel.ui.selectedSkin = PlayerSkin.getSkinWheel()[gamePanel.ui.skinSelectorIndex];
    }

    private void handleMenuKeyTyping(KeyEvent e) {
        if(!Character.isLetterOrDigit(e.getKeyCode()) && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) return;

        if(gamePanel.ui.commandNum == 0) {
            if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                gamePanel.ui.menuUsername = removeLastCharOptional(gamePanel.ui.menuUsername);
            } else {
                gamePanel.ui.menuUsername += e.getKeyChar();
            }
        } else if(gamePanel.ui.commandNum == 1) {
            if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                gamePanel.ui.menuPassword = removeLastCharOptional(gamePanel.ui.menuPassword);
            } else {
                gamePanel.ui.menuPassword += e.getKeyChar();
            }
        }
    }

    private void handleLoginPress() {
        if(gamePanel.ui.menuUsername.length() == 0 || gamePanel.ui.menuPassword.length() == 0) return;

        System.out.println("LOGIN WITH USERNAME: " + gamePanel.ui.menuUsername + " and password: " + gamePanel.ui.menuPassword);

        try {
            requestService.login(gamePanel.ui.menuUsername, gamePanel.ui.menuPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(requestService.isLoggedIn()) {
            try {
                PlayerStats player = requestService.getPlayer();
                gamePanel.currentlyCollectedGems = player.getKnowledge();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gamePanel.ui.minCommandNum = 0;
            gamePanel.ui.maxCommandNum = 1;
            gamePanel.ui.commandNum = 0;
            gamePanel.gameState = GameState.MAINMENU;
        }
    }

    private void handleRegisterPress() {
        if(gamePanel.ui.menuUsername.length() == 0 || gamePanel.ui.menuPassword.length() == 0) return;

        System.out.println("REGISTER WITH USERNAME: " + gamePanel.ui.menuUsername + " and password: " + gamePanel.ui.menuPassword);

        try {
            requestService.register(gamePanel.ui.menuUsername, gamePanel.ui.menuPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(requestService.isLoggedIn()) {
            try {
                PlayerStats player = requestService.getPlayer();
                gamePanel.currentlyCollectedGems = player.getKnowledge();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gamePanel.gameState = GameState.MAINMENU;
        }
    }

    private static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

}
