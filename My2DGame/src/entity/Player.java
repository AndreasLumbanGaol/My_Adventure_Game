package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Arrow;
import object.OBJ_Key;
import object.OBJ_Potion;
import object.OBJ_Shield;
import object.OBJ_Sword;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    //public int hasKey = 0;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventoryMax = 20;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        //SOLID AREA
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        //areaMenyerang.width = 36;
        //areaMenyerang.height = 36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttack();
        setItems();
    }

    public String getPlayerName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getMaxHealth() {
        return this.maxDarah;
    }

    public int getCurrentHealth() {
        return this.darah;
    }

    public int getMaxArrow() {
        return this.maxArrow;
    }

    public int getCurrentArrow() {
        return this.arrow;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getExperience() {
        return this.exp;
    }

    public int getNextLevelExp() {
        return this.nextLevel;
    }

    public int getCoin() {
        return this.coin;
    }

    public int getWorldX() {
        return this.worldX;
    }

    public int getWorldY() {
        return this.worldY;
    }

    public void setDefaultValues() {

        // ID dan Nama Player
        id = 1; 
        name = "Finn";

        // Posisi player
        worldX = gp.tileSize * 36;
        worldY = gp.tileSize * 51;

        speed = 4;
        direction = "down";

        // Status Player
        level = 1;
        maxDarah = 6; // Setengah Darah terhitung 1
        darah = maxDarah;
        maxArrow = 4;
        arrow = maxArrow;
        magic = 10;
        strength = 1; // Semakin tinggi strength, semakin besar damage diberikan
        agility = 1; // semakin tinggi agility, semakin kecil damage yg diterima
        exp = 0;
        nextLevel = 4;
        coin = 0;
        currentWeapon = new OBJ_Sword(gp);
        currentShield = new OBJ_Shield(gp);
        projectile = new OBJ_Arrow(gp);
        attack = getAttack(); // total damage yang diberikan dari strength dan weapon
        defense = getDefense(); // total defense yang diberikan dari agility dan shield

    }

    public void setDefault() {
        worldX = gp.tileSize * 36;
        worldY = gp.tileSize * 51;
        direction = "down";
    }

    public void restoreAtribut() {

        darah = maxDarah;
        arrow = maxArrow;
        invincible = false;
    }

    public void setItems() {

        inventory.clear(); // untuk restart game
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Potion(gp));
    }

    public int getAttack() {
        areaMenyerang = currentWeapon.areaMenyerang;
        return attack = strength * currentWeapon.nilaiAttack;
    }

    public int getDefense() {
        return defense = agility * currentShield.nilaiDefense;
    }

    public void getPlayerImage() {

        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttack() {

        if (currentWeapon.tipe == tipe_sword) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.tipe == tipe_axe) {
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void update() {

        if (menyerang == true) {
            menyerang();
        } //Cek Bergerak
        else if ((keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed) && !attackCanceled) {

            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            //Check tile Collision
            collisionOn = false;
            gp.checker.checkTile(this);

            //Check Objek Collision
            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check NPC Collision
            int npcIndex = gp.checker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //Check Event 
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

            //Check Monster Collision
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            kontakMonster(monsterIndex);

            //Check Interactive Tile Collision
            int iTileIndex = gp.checker.checkEntity(this, gp.iTile);

            //Cek IF Collision False, Player bisa bergerak
            if (collisionOn == false && keyH.enterPressed == false) {

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

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) { //Gambar bergantian tiap 14 Frame
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } //Jika diam dia akan di pose berdiri
        else {
            spriteNum = 1;
        }

        if (gp.keyH.shotPressed == true && projectile.hidup == false
                && shotAvailable == 30 && projectile.haveAmmo(this) == true) {
            //tidak bisa mengeluarkan projectile jika ada projectile yang masih berjalan

            //Default koordinat arah dan player
            projectile.set(worldX, worldY, direction, true, this);

            //Pengurangan Ammo
            projectile.pengurangAmmo(this);

            //Tambah ke list
            gp.projectileList.add(projectile);

            shotAvailable = 0;

        }

        //Agar Player mendapatkan delay saat akan di damage
        if (invincible == true) {
            penghitungInv++;
            if (penghitungInv > 60) {
                invincible = false;
                penghitungInv = 0;
            }
        }
        if (shotAvailable < 30) { //Delay menembak 30 Frame
            shotAvailable++;
        }
        if (darah > maxDarah) {
            darah = maxDarah;
        }
        if (arrow > maxArrow) {
            arrow = maxArrow;
        }
        if (darah <= 0) {
            gp.gameStatus = gp.gameOverstatus;//Jika Darah Karakter 0 GameOver
            gp.ui.tandaMenu = -1;
        }
        attackCanceled = false;

    }

    public void menyerang() {

        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= areaMenyerang.height;
                    break;
                case "down":
                    worldY += areaMenyerang.height;
                    break;
                case "left":
                    worldX -= areaMenyerang.width;
                    break;
                case "right":
                    worldX += areaMenyerang.width;
                    break;
            }
            //Area Menyerang mejadi solid Area
            solidArea.width = areaMenyerang.width;
            solidArea.height = areaMenyerang.height;

            //Cek Collision dari monster dengan koordinat x,y dari world dan solid area terupdate
            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.checker.checkEntity(this, gp.iTile);
            damageInteractive(iTileIndex);

            //Setelah cek collision, reset ke data awal
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            menyerang = false;
        }
    }

    public void pickUpObject(int i) {
        //If Statement untuk cek Object adalah yg ada di index Array
        if (i != 999) {
            String objectName = gp.obj[gp.currentMap][i].name;

            //Pickup Item Saja 
            if (gp.obj[gp.currentMap][i].tipe == tipe_pickup) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } // Jika objek adalah Door → buka jika punya key 
            else if (objectName.equals("Door")) {
                int keyIndex = -1;
                for (int j = 0; j < inventory.size(); j++) {
                    if (inventory.get(j).name.equals("Key")) { // Pastikan ini untuk Key yang benar jika ada banyak jenis kunci 
                        keyIndex = j;
                        break;
                    }
                }

                if (keyIndex != -1) {
                    // Punya kunci, buka pintu
                    inventory.remove(keyIndex);  // Hapus kunci
                    gp.obj[gp.currentMap][i] = null; // Hapus pintu dari map
                    gp.ui.addMessage("Kamu membuka pintu!");
                } else {
                    gp.ui.addMessage("Pintu terkunci. Kamu butuh kunci!");
                }
            } // Tambahkan ini untuk interaksi dengan Chest
            else if (gp.obj[gp.currentMap][i].tipe == tipe_chest) {
                // Memanggil metode use() dari OBJ_Chest
                // Logika kunci, pemberian item, dan perubahan gambar peti ada di OBJ_Chest.use()
                gp.obj[gp.currentMap][i].use(this);
            } // Inventoy Item 
            else {
                String text;

                if (inventory.size() != inventoryMax) {
                    inventory.add(gp.obj[gp.currentMap][i]);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "You cannot carry any more!";
                }

                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                gp.gameStatus = gp.dialogStatus;
                gp.player.attackCanceled = true; // Ini penting agar player tidak menyerang saat berdialog
                gp.npc[gp.currentMap][i].speak();
            } else {
                menyerang = true;
            }
        }
    }

    public void kontakMonster(int i) {

        if (i != 999) {

            if (invincible == false && gp.monster[gp.currentMap][i].sekarat == false) {

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                darah -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack) {

        if (i != 999) {
            //Jika tidak kebal = Bisa didamage
            if (gp.monster[gp.currentMap][i].invincible == false) {

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[gp.currentMap][i].darah -= damage;
                gp.ui.addMessage(damage + " Damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].darah <= 0) {
                    gp.monster[gp.currentMap][i].sekarat = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    //Cek LevelUp
                    cekLevelUp();
                }
            }
        }
    }

    public void damageInteractive(int i) {

        if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true
                && gp.iTile[gp.currentMap][i].correctWeapon(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {

            gp.iTile[gp.currentMap][i].darah--;
            gp.iTile[gp.currentMap][i].invincible = true;

            if (gp.iTile[gp.currentMap][i].darah == 0) { //Jika darah pohon habis maka hancur
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyed();
            }
        }
    }

    public void cekLevelUp() {

        //Penambahan Status
        if (exp >= nextLevel) {

            level++;
            nextLevel = nextLevel * 2;
            maxDarah += 2;
            strength++;
            agility++;
            //Rekalkulasi attack dan defense method
            attack = getAttack();
            defense = getDefense();

            gp.gameStatus = gp.dialogStatus;
            gp.ui.dialog = "You are level " + level + " now!\n"
                    + "You feel stronger!";
        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndex();

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.tipe == tipe_sword || selectedItem.tipe == tipe_axe) {

                currentWeapon = selectedItem;
                attack = getAttack();
                //Update senjata yg digunakan
                getPlayerAttack();

            }
            if (selectedItem.tipe == tipe_shield) {

                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.tipe == tipe_consumable) {

                selectedItem.use(this);
                inventory.remove(itemIndex); // Jika item sudah digunakan akan hilang
            }
        }
    }

    public void draw(Graphics2D g2) {

        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (menyerang == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }

                if (menyerang == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (menyerang == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }

                if (menyerang == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (menyerang == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }

                if (menyerang == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (menyerang == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }

                if (menyerang == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        //Visual Efek Terkena Damage
        if (invincible == true && !menyerang) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset Efek Menjadi Semula
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
