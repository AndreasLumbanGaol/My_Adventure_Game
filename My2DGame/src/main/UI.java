package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import object.OBJ_ArrowAmmo;
import object.OBJ_Heart;
import object.OBJ_Key;
import entity.Entity;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font pixelify;
	BufferedImage heart_full, heart_half, heart_blank, arrow_full, arrow_blank;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public String dialog = "";
	public int tandaMenu = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	int counter = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		pixelify = new Font("Minecraft", Font.PLAIN, 40);
		
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity arrowAmmo = new OBJ_ArrowAmmo(gp);
		arrow_full = arrowAmmo.image;
		arrow_blank = arrowAmmo.image2;
	}
	public void addMessage(String text) {
		//Default value
		message.add(text);
		messageCounter.add(0);
		
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(pixelify);
		g2.setColor(Color.white);
		//Menu Utama
		if(gp.gameStatus == gp.menuUtama) {
			drawMenuUtama();
		}
		
		//Keadaan Play
		if(gp.gameStatus == gp.playStatus) {
			drawDarahPlayer();
			drawMessage();
		}
		//Keadaan Pause
		if(gp.gameStatus == gp.pauseStatus) {
			drawDarahPlayer();
			pauseScreen();
		}
		//Keadaan Dialog
		if(gp.gameStatus == gp.dialogStatus) {
			drawDarahPlayer();
			dialogScreen();
		}
		//Statistik Karakter
		if(gp.gameStatus == gp.characterStatus) {
			drawCharacterScreen();
			drawInventory();
		}
		//Keadaan Game Over
		if(gp.gameStatus == gp.gameOverstatus) {
			gameOverScreen();
		}
		//Keadaan Transisi
		if(gp.gameStatus == gp.transisiStatus) {
			drawTransition();
		}
	}
	public void drawDarahPlayer() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//Gambar Darah Penuh
		while(i < gp.player.maxDarah/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		//Reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//Darah Sekarang
		while(i < gp.player.darah) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.darah) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		
		//Gambar Ammo Penuh
		x = (gp.tileSize/2) - 5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.maxArrow) {
			g2.drawImage(arrow_blank, x, y, null);
			i++;
			x += 35;
			
		}
		
		//Gambar Ammo
		x = (gp.tileSize/2) - 5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.arrow) {
			g2.drawImage(arrow_full, x, y, null);
			i++;
			x += 35;
		}
	}
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				//Shadow
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				//Text
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; //messageCounter++
				messageCounter.set(i, counter); //set counter ke array
				messageY += 50; //Merubah posisi y teks selanjutnya
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
					
				}
				
			}
			
		}
		
	}
	public void drawMenuUtama() {
		//Background
		g2.setColor(new Color(135,206,250));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//Judul Game
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,85F));
		String text = "Adventure Time";
		int x = textCenterX(text);
		int y = gp.tileSize*3;
		
		//Shadow
		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		
		//Warna Teks
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//Karakter
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		//Menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));
		
		text = "NEW GAME";
		x = textCenterX(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(tandaMenu == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = textCenterX(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(tandaMenu == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "QUIT";
		x = textCenterX(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(tandaMenu == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		
	}
	public void pauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "GAME TERJEDA";
		int x = textCenterX(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void dialogScreen() {
		
		//Dialog Window
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawTextWindow(x, y, width, height);
		
		//Posisi Teks
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : dialog.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	public void drawCharacterScreen() {
		
		//Frame
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		
		drawTextWindow(frameX, frameY, frameWidth, frameHeight);
		
		//Text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(26F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		// Nama-Namanya
		g2.drawString("Level", textX, textY);
		textY += lineHeight; //Pemisah Line
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Arrow", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Agility", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 13;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		//Value
		int ujungX = (frameX + frameWidth) - 50;
		//Reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level); //player level menjadi string
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.darah + "/" + gp.player.maxDarah); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX-40, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.arrow + "/" + gp.player.maxArrow); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX-40, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.agility); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevel); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin); 
		textX = textAlignKanan(value,ujungX);
		g2.drawString(value, ujungX, textY);
		textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, ujungX - gp.tileSize, textY-28, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, ujungX - gp.tileSize, textY-28, null);
	}
	public void drawInventory() {
		//Posisi Frame
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		//Memunculkan Frame dari method yang sudah dibuat
		drawTextWindow(frameX, frameY, frameWidth, frameHeight);
		
		//Slot Inventory
		final int slotXawal = frameX + 20;
		final int slotYawal = frameY + 20;
		int slotX = slotXawal;
		int slotY = slotYawal;
		int slotSize = gp.tileSize+3;
		
		//Draw Player Item
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			//Cursor item yang di equip
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXawal;
				slotY += slotSize;
			}
		}
		
		//Cursor
		int cursorX = slotXawal + (slotSize * slotCol);
		int cursorY = slotYawal + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		//Draw Cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// Frame Deskripsi
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize*3;
		
		//Draw Text Deskripsi
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(20F));
		
		int itemIndex = getItemIndex();
		
		if(itemIndex < gp.player.inventory.size()) {
			
			drawTextWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			
			for(String line: gp.player.inventory.get(itemIndex).deskripsi.split("\n")) { //Untuk new Line
				
				g2.drawString(line, textX, textY);
				textY += 32;
			}
		}
	}
	public void gameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 95F));
		
		text = "Game Over";
		//Shadow
		g2.setColor(Color.black);
		x = textCenterX(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		//Utama
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(45f));
		text = "Retry";
		x = textCenterX(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(tandaMenu == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//Back to Title Screen
		text = "Quit";
		x = textCenterX(text);
		y += 55;
		g2.drawString(text, x, y);
		if(tandaMenu == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	
	public void drawTransition() {
		
		counter++;
		g2.setColor(new Color(255,255,255, counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(counter == 50) {
			counter = 0;
			gp.gameStatus = gp.playStatus;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}
	public int getItemIndex() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	
	public void drawTextWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210); // Nomor RGB Hitam dan Opacity
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255); // Nomor RGB Putih
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	public int textCenterX(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int textAlignKanan(String text, int ujungX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = ujungX - length;
		return x;
	}
}
