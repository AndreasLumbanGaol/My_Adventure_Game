package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Magic extends Projectile {
	GamePanel gp;
	
	public OBJ_Magic(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Magic";
		speed = 8;
		maxDarah = 40; //Jarak Magio
		darah = maxDarah;
		attack = 2;
		useCost = 1;
		hidup = false;
		getImage();
		
	}
	
	public void getImage() {
		
		up1 = setup("/projectile/magic_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/magic_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/magic_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/magic_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/magic_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/magic_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/magic_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/magic_2", gp.tileSize, gp.tileSize);
	}
	public boolean haveAmmo(Entity user) {
		
		boolean haveAmmo = false;
		if(user.magic >= useCost) {
			haveAmmo = true;
		}
		return haveAmmo;
	}
	public void pengurangAmmo(Entity user) {
		user.magic -= useCost;
	}
}
