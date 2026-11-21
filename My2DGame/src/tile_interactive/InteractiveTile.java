package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean destructible = false;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public boolean correctWeapon(Entity entity) {
		boolean correctWeapon = false;
		return correctWeapon;
	}
	
	public InteractiveTile getDestroyed() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void update() {
		
		if(invincible == true) {
			penghitungInv++;
			if(penghitungInv > 20) {
				invincible = false;
				penghitungInv = 0;
			}
		}
	}
}
