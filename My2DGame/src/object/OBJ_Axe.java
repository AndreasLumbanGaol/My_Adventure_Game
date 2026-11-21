package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
	
	public OBJ_Axe(GamePanel gp) {
		
		super(gp);
                this.definitionId = 2;
		tipe = tipe_axe;
		name = "Iron Axe";
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		nilaiAttack = 2;
		areaMenyerang.width = 30;
		areaMenyerang.height = 30;
		deskripsi = "[" + name + "]\n A bit rusty but still \ncan cut some tree";
	}
}
