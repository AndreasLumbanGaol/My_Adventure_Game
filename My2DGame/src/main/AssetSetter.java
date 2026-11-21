package main;

import entity.NPC_BMO;
import entity.NPC_Jake;
import monster.MON_Skeleton;
import object.OBJ_ArrowAmmo;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Coin;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject () {
		
		int mapNum = 0;
		int i = 0; 

		gp.obj[mapNum][i] = new OBJ_ArrowAmmo (gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*39;
		gp.obj[mapNum][i].worldY = gp.tileSize*66;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart (gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*56;
		gp.obj[mapNum][i].worldY = gp.tileSize*74;
		i++;
	}
	public void setNPC() {
		
		int mapNum = 0;
		int i = 0;
		gp.npc[mapNum][i] = new NPC_Jake(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*53;
		gp.npc[mapNum][i].worldY = gp.tileSize*48;
		i++;
		
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_BMO(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*41;
		gp.npc[mapNum][i].worldY = gp.tileSize*39;
		i++;
	}
	
	public void setMonster() {
		
		int mapNum = 0;
		int i = 0;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*27;
		gp.monster[mapNum][i].worldY = gp.tileSize*82;
		i++;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*80;
		i++;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*36;
		gp.monster[mapNum][i].worldY = gp.tileSize*85;
		i++;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*78;
		gp.monster[mapNum][i].worldY = gp.tileSize*83;
		i++;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*71;
		gp.monster[mapNum][i].worldY = gp.tileSize*85;
		i++;
		gp.monster[mapNum][i] = new MON_Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*64;
		gp.monster[mapNum][i].worldY = gp.tileSize*85;
		i++;
		
	}
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 73, 37); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 73, 38); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 74, 38); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 74, 39); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 75, 39); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 75, 40); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 76, 40); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 77, 40); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 77, 41); i++;
	}
}