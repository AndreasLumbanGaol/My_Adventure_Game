package entity;

import main.GamePanel;

public class Projectile extends Entity {

	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
		
	}
	public void set(int worldX, int worldY, String direction, boolean hidup, Entity user) {
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.hidup = hidup;
		this.user = user;
		this.darah = this.maxDarah;
	}
	
	public void update() {
		
		if(user == gp.player) { //Jika Player menembak projectile maka..
			int monsterIndex = gp.checker.checkEntity(this, gp.monster); //Cek Collision Monster
			if(monsterIndex != 999) {  // Jika Projectile terkena monster maka..
				gp.player.damageMonster(monsterIndex,attack); //Damage Monster
				hidup = false;
			}
		}
		if(user != gp.player) {
			boolean kontakPlayer = gp.checker.checkPlayer(this);
			if(gp.player.invincible == false && kontakPlayer == true) {
				damagePlayer(attack);
				hidup = false;
			}
		}
		switch(direction) {
		case "up":
			worldY -= speed;
			break;
		case "down":
			worldY += speed;
			break;
		case "left":
			worldX -= speed;
			break;
		case "right":
			worldX += speed;
			break;
		}
		darah--;
		if(darah <= 0) {
			hidup = false; // Ammo Habis
		}
		
		spriteCounter++;
		if(spriteCounter > 12) { //Gambar bergantian tiap 12 Frame
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	public boolean haveAmmo(Entity user) {
		
		boolean haveAmmo = false;
		return haveAmmo;
	}
	public void pengurangAmmo(Entity user) {
	}
	
}
