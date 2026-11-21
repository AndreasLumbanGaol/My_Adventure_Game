package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Arrow extends Projectile{

	GamePanel gp;
	
	public OBJ_Arrow(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Arrow";
		speed = 10;
		maxDarah = 80; //Jarak Arrow
		darah = maxDarah;
		attack = 2;
		useCost = 1;
		hidup = false;
		getImage();
		
	}
	
	public void getImage() {
		
		up1 = setup("/projectile/arrow_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/arrow_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/arrow_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/arrow_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/arrow_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/arrow_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/arrow_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/arrow_right_2", gp.tileSize, gp.tileSize);
	}
	
	public boolean haveAmmo(Entity user) {
		
		boolean haveAmmo = false;
		if(user.arrow >= useCost) {
			haveAmmo = true;
		}
		return haveAmmo;
	}
	public void pengurangAmmo(Entity user) {
		user.arrow -= useCost;
	}
}
