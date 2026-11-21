package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Legendary;

public class Entity {

    public GamePanel gp;
    public int worldItemId = 0;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    //Collision Setting
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle areaMenyerang = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public String direction = "down";
    String Dialog[] = new String[20];

    //Kondisi
    public int worldX, worldY;
    int dialogIndex = 0;
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean menyerang = false;
    public boolean hidup = true;
    public boolean sekarat = false;

    //Penghitung
    public int spriteCounter = 0;
    public int standCounter = 0;
    public int actionLock = 0;
    public int penghitungInv = 0;
    public int shotAvailable = 0;
    int dyingCounter = 0;

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int definitionId;

    public int inventoryId;

    //Atribut Karakter
    public String name;
    public int speed;
    public int maxDarah;
    public int darah;
    public int maxArrow;
    public int arrow;
    public int magic;
    public int level;
    public int strength;
    public int agility;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevel;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //Item Attribute
    public int value;
    public int nilaiAttack;
    public int nilaiDefense;
    public String deskripsi = "";
    public int useCost;

    //Tipe
    public int tipe; // 0 = player, 1 = npc, 2 = monster
    public final int tipe_player = 0;
    public final int tipe_npc = 1;
    public final int tipe_monster = 2;
    public final int tipe_sword = 3;
    public final int tipe_axe = 4;
    public final int tipe_shield = 5;
    public final int tipe_consumable = 6;
    public final int tipe_pickup = 7;
     public final int tipe_chest = 8;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public GamePanel getGamePanel() {
        return this.gp;
    }

    public void setAction() {

    }

    public void damageReaction() {

    }

    public void speak() {

        if (Dialog[dialogIndex] == null) {
            // Ini adalah blok yang dieksekusi saat dialog berakhir.
            dialogIndex = 0; // Reset dialog untuk interaksi berikutnya
            gp.gameStatus = gp.playStatus; // Kembali ke status bermain
            gp.player.attackCanceled = false; // Izinkan pemain menyerang lagi

            // --- LOGIKA PEMBERIAN KUNCI UNTUK BMO ---
            // Periksa apakah entitas yang sedang berbicara adalah instance dari NPC_BMO
            if (this instanceof NPC_BMO) {
                // Berikan kunci hanya jika pemain belum memilikinya
                if (!gp.player.inventory.stream().anyMatch(item -> item.name.equals("Legendary Sword"))) {
                    gp.player.inventory.add(new OBJ_Legendary(gp));
                    gp.ui.addMessage("Kamu mendapatkan sebuah pedang misterius!");
                }
            }
            // --- AKHIR LOGIKA PEMBERIAN KUNCI ---

        } else {
            // Ini adalah blok yang dieksekusi untuk setiap baris dialog aktif.
            gp.ui.dialog = Dialog[dialogIndex]; // Tampilkan baris dialog saat ini
            dialogIndex++; // Maju ke baris dialog berikutnya
        }

        //Mengubah arah NPC saat berbicara ke Player
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void use(Entity entity) {

    }

    public void checkDrop() {

    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // Mayat Monster worldX dan Y
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        //Membuat class menjadi entitas
        gp.checker.checkTile(this);
        gp.checker.checkObject(this, false);
        gp.checker.checkEntity(this, gp.npc);
        gp.checker.checkEntity(this, gp.monster);
        boolean kontakPlayer = gp.checker.checkPlayer(this);

        if (this.tipe == tipe_monster && kontakPlayer == true) {
            damagePlayer(attack);
        }

        //Cek IF Collision False, Player bisa bergerak
        if (collisionOn == false) {

            switch (direction) {
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
        }

        spriteCounter++;
        if (spriteCounter > 14) { //Gambar bergantian tiap 14 Frame
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            penghitungInv++;
            if (penghitungInv > 40) {
                invincible = false;
                penghitungInv = 0;
            }
        }
        if (shotAvailable < 30) { //Delay menembak 30 Frame
            shotAvailable++;
        }

    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            //Boleh memberikan Damage
            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.darah -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        //IF Statement Jika Entitas ada di Frame 
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenX) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            if (tipe == 2) {

                double oneScale = (double) gp.tileSize / maxDarah; //Untuk membagi darah dari max darah
                double hpBar = oneScale * darah;

                g2.setColor(new Color(35, 35, 35));//Stroke
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                g2.setColor(new Color(255, 0, 30)); //Warna Merah
                g2.fillRect(screenX, screenY - 15, (int) hpBar, 10);
            }

            if (invincible == true && !menyerang) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if (sekarat == true) {
                animasiSekarat(g2);
            }

            g2.drawImage(image, screenX, screenY, null);

            blinkingAlpha(g2, 1F);
        }

    }

    public void animasiSekarat(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            blinkingAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            blinkingAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            blinkingAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            blinkingAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            blinkingAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            blinkingAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            blinkingAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            blinkingAlpha(g2, 1f);
        }

        if (dyingCounter > i * 8) {
            hidup = false;
        }

    }

    public void blinkingAlpha(Graphics2D g2, float alpha) {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}