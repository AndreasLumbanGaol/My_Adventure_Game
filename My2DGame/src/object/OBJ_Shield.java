package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		this.definitionId = 3;
		tipe = tipe_shield;
		name = "Shield";
		down1 = setup("/objects/shield", gp.tileSize, gp.tileSize);
		nilaiDefense = 1;
		deskripsi = "[" + name + "]\n A Strong Shield";
	}
	
}
