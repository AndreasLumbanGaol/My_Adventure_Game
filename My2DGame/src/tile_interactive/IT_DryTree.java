package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile{

	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interaktif/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		darah = 4;
	}
	public boolean correctWeapon(Entity entity) {
		boolean correctWeapon = false;
		if(entity.currentWeapon.tipe == tipe_axe) {
			correctWeapon = true; // Jika Kapak maka bisa Hancur
		}
		return correctWeapon;
	}
	
	public InteractiveTile getDestroyed() {
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}

}
