package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {

        super(gp);
        this.definitionId = 6;
        this.definitionId = 5;
        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        deskripsi = "[" + name + "]\n Key to Open the Door";
    }
}
