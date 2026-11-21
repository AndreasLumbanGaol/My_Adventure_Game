package entity;

import java.util.Random;

import main.GamePanel;
import object.OBJ_Axe;

public class NPC_Jake extends Entity {

    String[] initialDialogs = new String[20];
    String[] postKeyDialogs = new String[20];

    private boolean hasSwitchedToPostKeyDialog = false;
    private boolean hasGivenAxe = false;

    public NPC_Jake(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getNPCImage();
        setJakeDialogs();
    }

    public void getNPCImage() {
        up1 = setup("/NPC/jake_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/jake_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/jake_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/jake_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/jake_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/jake_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/jake_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/jake_right_2", gp.tileSize, gp.tileSize);
    }

    public void setJakeDialogs() {
        // Dialog awal (sebelum mendapatkan kunci)
        initialDialogs[0] = "Finn! Aku butuh bantuanmu!";
        initialDialogs[1] = "Ada monster di pantai yang menjaga\nkunci ruangan misterius.";
        initialDialogs[2] = "Di dalam rumah itu ada portal,\ndan BMO mungkin ada\ndi pulau seberang!";
        initialDialogs[3] = "Kalahkan monster itu\ndan ambil kuncinya!";

        // Dialog setelah mendapatkan kunci
        postKeyDialogs[0] = "Wah, kamu berhasil, Finn!";
        postKeyDialogs[1] = "Aku tahu kamu bisa melakukannya!";
        postKeyDialogs[2] = "Sekarang kamu bisa membuka\nruangan misterius itu.";
        postKeyDialogs[3] = "Ambil ini, mungkin berguna\nuntuk petualanganmu selanjutnya!"; // Dialog baru untuk pemberian item
        postKeyDialogs[4] = "Semoga beruntung menemukan BMO!";
    }

    @Override
    public void setAction() {

        actionLock++;

        if (actionLock == 120) { // Frame Bergantian Setiap 120 Frame atau 2 Detik
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLock = 0;
        }
    }

    @Override
    public void speak() {

        if (gp.player.inventory.stream().anyMatch(item -> item.name.equals("Key")) && !hasSwitchedToPostKeyDialog) {

            System.arraycopy(postKeyDialogs, 0, Dialog, 0, postKeyDialogs.length);
            dialogIndex = 0; 
            hasSwitchedToPostKeyDialog = true; 
        } else if (!hasSwitchedToPostKeyDialog) {

            System.arraycopy(initialDialogs, 0, Dialog, 0, initialDialogs.length);
        }

        // Jika Jake sudah beralih ke dialog post-key
        // DAN pemain belum mendapatkan axe dari Jake
        if (hasSwitchedToPostKeyDialog && !hasGivenAxe && Dialog[dialogIndex] != null && Dialog[dialogIndex].equals(postKeyDialogs[3])) {
            // Periksa apakah inventory pemain tidak penuh
            if (gp.player.inventory.size() < gp.player.inventoryMax) {
                gp.player.inventory.add(new OBJ_Axe(gp)); // Berikan kapak
                gp.ui.addMessage("Kamu mendapatkan " + new OBJ_Axe(gp).name + " dari Jake!");
                hasGivenAxe = true; // Set flag agar kapak tidak diberikan lagi
            } else {
                gp.ui.addMessage("Inventori penuh! Jake tidak bisa memberimu kapak.");
            }
        }

        super.speak();
    }
}