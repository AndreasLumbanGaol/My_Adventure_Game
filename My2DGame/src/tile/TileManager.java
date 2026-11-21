package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	//Tile Data
	InputStream is = getClass().getResourceAsStream("/maps/tilesdata.txt");
	BufferedReader br = new BufferedReader(new InputStreamReader(is));

	
	public TileManager (GamePanel gp) {
		
		this.gp = gp;
		
		//Tile Data
		InputStream is = getClass().getResourceAsStream("/maps/tilesdata.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		tile = new Tile[70];
		//untuk menentukan tile yg ingin dibuat
		mapTileNum = new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		
		loadMap("/maps/worldV4.txt", 0);
		loadMap("/maps/worldV5.txt", 1);
	}

	
	public void getTileImage() {
		//PlaceHolder
		setup(0, "grass00", false);
		setup(1, "grass00", false);
		setup(2, "grass01", false);
		setup(3, "water00", true);
		setup(4, "water01", true);
		setup(5, "water02", true);
		setup(6, "water03", true);
		setup(7, "water04", true);
		setup(8, "water05", true);
		setup(9, "water06", true);
		setup(10, "water07", true);
		setup(11, "grass00", false);
		
		setup(12, "water08", true);
		setup(13, "water09", true);
		setup(14, "water10", true);
		setup(15, "water11", true);
		setup(16, "water12", true);
		setup(17, "water13", true);
		
		setup(18, "road00", false);
		setup(19, "road01", false);
		setup(20, "road02", false);
		setup(21, "road03", false);
		setup(22, "grass00", false);
		
		setup(23, "road04", false);
		setup(24, "road05", false);
		setup(25, "road06", false);
		setup(26, "road07", false);
		setup(27, "road08", false);
		setup(28, "road09", false);
		setup(29, "road10", false);
		setup(30, "road11", false);
		setup(31, "road12", false);
		setup(32, "floor01", false);
		setup(33, "grass00", false);
		setup(34, "wall", true);
		setup(35, "tree", true);
		setup(36, "wall 2", true);
		setup(37, "door open", false);
		
		setup(38, "sand1", true);
		setup(39, "sand2", true);
		setup(40, "sand3", true);
		setup(41, "sand4", true);
		setup(42, "sand5", true);
		setup(43, "sand6", true);
		
		setup(44, "grass00", false);
		
		setup(45, "sand7", true);
		setup(46, "sand8", true);
		setup(47, "sand9", true);
		setup(48, "sand10", true);
		setup(49, "sand11", true);
		setup(50, "sand12", true);
		setup(51, "chest", true);
		setup(53, "floor01", false);
		setup(53, "sand", false);
		setup(54, "table01", true);
		setup(55, "grass00", false);
		setup(56, "water", true);
		setup(57, "tree02", true);
		setup(58, "anvil", true);
		setup(59, "bed", true);
		
		setup(60, "plant00", true);
		setup(61, "plant01", false);
		setup(62, "plant02", false);
		
		setup(63, "tree03", true);
		
		setup(64, "portal", false);
		setup(65, "grass00", false);
		setup(66, "grass00",false);
		setup(67, "grass00",false);

}
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath, int map) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader (new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col] [row] = num;
					col++;
					
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
					
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			//Kamera
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//Efisiensi Render 
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenX) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
