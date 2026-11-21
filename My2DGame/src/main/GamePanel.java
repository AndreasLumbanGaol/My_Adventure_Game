package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import control.GameDataManager;
import entity.Entity;
import entity.Player;
import object.OBJ_Door;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    public final int originalTileSize = 16; //16x16 TILE
    final int scale = 3;
    public final GameDataManager gameDataManager = new GameDataManager(this);
    public final int tileSize = originalTileSize * scale; //48x48 TILE Scale
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 Pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //WORLD SETTING
    public int maxWorldCol = 100;
    public int maxWorldRow = 100;
    public final int maxMap = 10;//Bisa membuat 10 Map
    public int currentMap = 0;//Map Sekarang 
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    //SISTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public collisionChecker checker = new collisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public int jumlahSkeletonMati = 0;

    public final GameDataManager dataManager = new GameDataManager(this);

    //Entitas dan Objek
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][15];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //Status Game
    public int gameStatus;
    public final int menuUtama = 0;
    public final int playStatus = 1;
    public final int pauseStatus = 2;
    public final int dialogStatus = 3;
    public final int characterStatus = 4;
    public final int gameOverstatus = 5;
    public final int transisiStatus = 6;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameStatus = menuUtama;
    }

    public void retry() {
        player.setDefault();
        player.restoreAtribut();
        aSetter.setNPC();
        aSetter.setMonster();
    }

public void restart() {
    gameDataManager.newGame(1);
    
    player.setDefaultValues();
    player.setItems();
     
    aSetter.setNPC();
    aSetter.setMonster();
    aSetter.setInteractiveTile();
    
    gameDataManager.loadWorldObjects(); 
}

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        //FPS
        double drawInterval = 1000000000 / FPS; //Supaya bisa menampilkan 60x dalam 1 detik
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            //CEK PERHITUNGAN FPS
            if (timer >= 1000000000) {
                System.out.println("FPS : " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void update() {

        if (gameStatus == playStatus) {
            //Player
            player.update();
            //NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].hidup == true && monster[currentMap][i].sekarat == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].hidup == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).hidup == true) {
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).hidup == false) {
                        projectileList.remove(i);
                    }
                }
            }
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
        //IF Statement agar informasi player tidak terupdate (tidak bisa gerak)
        if (gameStatus == pauseStatus) {

        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Menu utama
        if (gameStatus == menuUtama) {
            ui.draw(g2);
        } else {
            //Tile
            tileM.draw(g2);

            //Interactive tile
            for (int i = 0; i < iTile[currentMap].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //Menambahkan entitas ke list
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

            });

            //Draw entitas
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //Empty Entity List
            entityList.clear();

            //UI
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void saveGame() {
        dataManager.saveGame();
    }

    public void loadGame(int playerId) {
        dataManager.loadGame(playerId);
    }
}
