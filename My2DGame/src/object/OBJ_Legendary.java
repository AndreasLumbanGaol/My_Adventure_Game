package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Legendary extends Entity {

    public OBJ_Legendary(GamePanel gp) {
        super(gp);

        this.definitionId = 11;
        tipe = tipe_sword;
        name = "Legendary Sword";
        down1 = setup("/objects/legendary_sword", gp.tileSize, gp.tileSize);
        nilaiAttack = 100;
        deskripsi = "[" + name + "]\n A Legendary Sword \nFrom BMO";
        areaMenyerang.width = 36;
        areaMenyerang.height = 36;
    }
}