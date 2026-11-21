package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        // Keadaan Menu
        if (gp.gameStatus == gp.menuUtama) {
            titleStatus(code);
        } //Keadaan Play
        else if (gp.gameStatus == gp.playStatus) {
            playStatus(code);
        } //Keadaan Pause
        else if (gp.gameStatus == gp.pauseStatus) {
            pauseStatus(code);
        } //Keadaan Dialog
        else if (gp.gameStatus == gp.dialogStatus) {
            dialogStatus(code);
        } //Statistik Karakter
        else if (gp.gameStatus == gp.characterStatus) {
            characterStatus(code);
        } else if (gp.gameStatus == gp.gameOverstatus) {
            gameOverStatus(code);
        }
    }

    public void titleStatus(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.tandaMenu--;
            if (gp.ui.tandaMenu < 0) {
                gp.ui.tandaMenu = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.tandaMenu++;
            if (gp.ui.tandaMenu > 2) {
                gp.ui.tandaMenu = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.tandaMenu == 0) { // Opsi "NEW GAME"
                gp.restart();
                gp.gameStatus = gp.playStatus;
            }
            if (gp.ui.tandaMenu == 1) { // Opsi "LOAD GAME"
                gp.loadGame(1);
                gp.gameStatus = gp.playStatus;
            }
            if (gp.ui.tandaMenu == 2) { // Opsi "QUIT"
                System.exit(0);
            }
        }
    }

    public void playStatus(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        //Pause n Play Key
        if (code == KeyEvent.VK_P) {
            gp.gameStatus = gp.pauseStatus;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameStatus = gp.characterStatus;
        }
        //Tombol interaksi dengan NPC
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotPressed = true;
        }
    }

    public void pauseStatus(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameStatus = gp.playStatus;
        }
        //Menambahkan tombol 'S' untuk menyimpan game saat di menu pause
        if (code == KeyEvent.VK_S) {
            gp.saveGame();
            gp.ui.addMessage("Game Saved!");
        }
    }

    public void dialogStatus(int code) {
        if (code == KeyEvent.VK_SPACE) {
            gp.gameStatus = gp.playStatus;
        }
    }

    public void characterStatus(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameStatus = gp.playStatus;
        }
        if (code == KeyEvent.VK_W) {
            //If Statement agar Cursor tidak keluar dari batas Frame
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    public void gameOverStatus(int code) {

        if (code == KeyEvent.VK_W) {
            gp.ui.tandaMenu--;
            if (gp.ui.tandaMenu < 0) {
                gp.ui.tandaMenu = 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.tandaMenu++;
            if (gp.ui.tandaMenu > 1) {
                gp.ui.tandaMenu = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.tandaMenu == 0) { // retry
                gp.restart();
                gp.gameStatus = gp.playStatus;
            } else if (gp.ui.tandaMenu == 1) { // Quit and go to main menu
                gp.gameStatus = gp.menuUtama;
                gp.restart(); // Restart akan membersihkan data game sebelumnya
            }
        }
    }

    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotPressed = false;
        }
    }

}
