package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion extends Entity {
	
	GamePanel gp;
	
	public OBJ_Potion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.definitionId = 6;
		tipe = tipe_consumable;
		name = "Red Potion";
		value = 5;
		down1 = setup("/objects/red_potion", gp.tileSize, gp.tileSize);
		deskripsi = "[" + name + "]\n Heals your life by " + value + ".";
	}
	public void use(Entity entity) {
		
		gp.gameStatus = gp.dialogStatus;
		gp.ui.dialog = "You drink the " + name + "!\n"
				+ "Your life has been recovered by " + value + ".";
		entity.darah += value;
	}
}
