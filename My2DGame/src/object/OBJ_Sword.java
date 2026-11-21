package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

    public OBJ_Sword(GamePanel gp) {
        super(gp);

        this.definitionId = 1;
        tipe = tipe_sword;
        name = "Finn Sword";
        down1 = setup("/objects/sword", gp.tileSize, gp.tileSize);
        nilaiAttack = 1;
        deskripsi = "[" + name + "]\n A Sentient Sword \nCreated by a Paradox";
        areaMenyerang.width = 36;
        areaMenyerang.height = 36;
    }
}
