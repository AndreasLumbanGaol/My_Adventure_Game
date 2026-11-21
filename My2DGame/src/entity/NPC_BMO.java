package entity;

import main.GamePanel;
import object.OBJ_Legendary; // Pastikan ini diimpor

public class NPC_BMO extends Entity {

    String[] initialDialogs = new String[20];
    String[] postKeyDialogs = new String[20];

    // Flag untuk memastikan dialog setelah kunci hanya diatur sekali
    private boolean hasSwitchedToPostKeyDialog = false;

    public NPC_BMO(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getNPCImage();
        setDialogs(); 
    }

    public void getNPCImage() {
        up1 = setup("/NPC/BMO1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/BMO2", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/BMO1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/BMO2", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/BMO1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/BMO2", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/BMO1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/BMO2", gp.tileSize, gp.tileSize);
    }

    // Metode baru untuk mengatur kedua set dialog
    public void setDialogs() {
        // Dialog awal (sebelum kunci diberikan)
        initialDialogs[0] = "Finn! Terima kasih\nsudah menemukanku!";
        initialDialogs[1] = "Kamu juga sudah mengalahkan\nmonster-monster di pantai,\nitu luar biasa.";
        initialDialogs[2] = "Aku benar-benar lega sekarang.";
        initialDialogs[3] = "Ambil ini sebagai hadiah,\nbisa kamu gunakan untuk \nmembunuh musuh yang kuat!";

        // Dialog setelah kunci diberikan
        postKeyDialogs[0] = "Senang melihatmu lagi, Finn!";
        postKeyDialogs[1] = "Apakah kamu sudah menggunakan\npedangnya?";
        postKeyDialogs[2] = "Aku harap itu berguna untukmu!";
        postKeyDialogs[3] = "Petualangan kita masih panjang!";
    }

    @Override
    public void speak() {
        if (!hasSwitchedToPostKeyDialog && dialogIndex == initialDialogs.length - 1) {
            // Berikan kunci hanya jika pemain belum memilikinya
            if (!gp.player.inventory.stream().anyMatch(item -> item.name.equals("Legendary Sword"))) {
                gp.player.inventory.add(new OBJ_Legendary(gp));
                gp.ui.addMessage("Kamu mendapatkan sebuah pedang misterius!");
            }
        }

        if (gp.player.inventory.stream().anyMatch(item -> item.name.equals("Legendary Sword")) && !hasSwitchedToPostKeyDialog) {
            System.arraycopy(postKeyDialogs, 0, Dialog, 0, postKeyDialogs.length);
            dialogIndex = 0; 
            hasSwitchedToPostKeyDialog = true; 
        } else if (!hasSwitchedToPostKeyDialog) {
            System.arraycopy(initialDialogs, 0, Dialog, 0, initialDialogs.length);
        }


        super.speak();
    }
}