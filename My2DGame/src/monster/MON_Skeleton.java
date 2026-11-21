package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_ArrowAmmo;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Magic;

public class MON_Skeleton extends Entity {

	GamePanel gp;
	

	public MON_Skeleton(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		tipe = tipe_monster;
		name = "Skeleton";
		speed = 1;
		maxDarah = 4;
		darah = maxDarah;
		attack = 2;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Magic(gp);
		
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		
		up1 = setup("/monster/Skeleton_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/Skeleton_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/Skeleton_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/Skeleton_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/Skeleton_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/Skeleton_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/Skeleton_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/Skeleton_right_2", gp.tileSize, gp.tileSize);
		
	}
	public void setAction() {
		
		actionLock ++;
		
		if(actionLock == 120) { //Frame Bergantian Setiap 120 Frame atau 2 Detik
			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLock = 0;
			
		}
		
		int i = new Random().nextInt(100)+1;
		if(i > 99 && projectile.hidup == false && shotAvailable == 30) {
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			shotAvailable = 0;
		}
		
	}
	public void damageReaction() {
		
		actionLock = 0;
		

	    int knockbackDistance = gp.tileSize / 4;
	    int tempX = worldX;
	    int tempY = worldY;

	    switch (gp.player.direction) {
	        case "up":
	            worldY -= knockbackDistance;
	            break;
	        case "down":
	            worldY += knockbackDistance;
	            break;
	        case "left":
	            worldX -= knockbackDistance;
	            break;
	        case "right":
	            worldX += knockbackDistance;
	            break;
	    }

	    // Cek collision
	    collisionOn = false;
	    gp.checker.checkTile(this);
	    gp.checker.checkObject(this, false);
	    if (collisionOn) {
	        // Kalau nabrak objek/tile, batalkan knockback
	        worldX = tempX;
	        worldY = tempY;
	    }
	}
	public void checkDrop() {
	    gp.jumlahSkeletonMati++;

	    if (gp.jumlahSkeletonMati == 3) {
	        // Skeleton ke-3 selalu drop kunci
	        dropItem(new OBJ_Key(gp));
	        gp.ui.addMessage("Kunci dijatuhkan!");
	    } else {
	        // Skeleton lainnya drop random item
	        int i = new Random().nextInt(100) + 1;

	        if (i < 40) {
	            dropItem(new OBJ_Coin(gp));
	        } else if (i < 70) {
	            dropItem(new OBJ_ArrowAmmo(gp));
	        } else {
	            dropItem(new OBJ_Heart(gp));
	        }
	    }
	}
}
