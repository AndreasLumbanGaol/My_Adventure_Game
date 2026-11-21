package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ArrowAmmo extends Entity {

	GamePanel gp;
	
	public OBJ_ArrowAmmo(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		tipe = tipe_pickup;
		name = "Arrow Ammo";
		value = 2;
		down1 = setup("/objects/arrow_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/arrow_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/arrow_blank", gp.tileSize, gp.tileSize);
	}
	
	public void use (Entity entity) {
		
		gp.ui.addMessage("Arrow +" + value);
		entity.arrow += value;
	}
}
