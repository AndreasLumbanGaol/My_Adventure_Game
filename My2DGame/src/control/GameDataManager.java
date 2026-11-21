package control;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import model.ItemDefinition;
import model.PlayerInventory;
import model.PlayerModel;
import object.*;

import java.util.ArrayList;
import java.util.List;
import model.WorldItem;

public class GameDataManager {

    private GamePanel gp;
    private PlayerDAO playerDAO;
    private InventoryDAO inventoryDAO;
    private ItemDefinitionDAO itemDefinitionDAO;
    private WorldItemDAO worldItemDAO;

    private PlayerModel loadedPlayerModel;

    public GameDataManager(GamePanel gp) {
        this.gp = gp;
        this.playerDAO = new PlayerDAO();
        this.inventoryDAO = new InventoryDAO();
        this.itemDefinitionDAO = new ItemDefinitionDAO();
        this.worldItemDAO = new WorldItemDAO();
    }

    public void saveGame() {
        try {
            PlayerModel playerModel = mapPlayerEntityToModel(gp.player);
            List<PlayerInventory> inventoryModels = mapInventoryEntityToModel(gp.player);

            playerDAO.savePlayer(playerModel);
            inventoryDAO.saveInventory(inventoryModels, gp.player.id);

            System.out.println("Game berhasil disimpan untuk Player ID: " + gp.player.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame(int playerId) {
        try {
            this.loadedPlayerModel = playerDAO.loadPlayer(playerId);

            if (this.loadedPlayerModel == null) {
                System.out.println("Save game dengan ID " + playerId + " tidak ditemukan.");
                gp.restart();
                return;
            }

            List<PlayerInventory> inventoryModels = inventoryDAO.loadInventory(playerId);
            mapPlayerModelToEntity(this.loadedPlayerModel, gp.player);
            mapInventoryModelToEntity(inventoryModels, gp.player);
            loadWorldObjects();
            gp.aSetter.setNPC();
            gp.aSetter.setMonster();

            System.out.println("Game berhasil dimuat untuk Player ID: " + gp.player.id);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Gagal memuat game!");
        } finally {
            this.loadedPlayerModel = null;
        }
    }

    public void loadWorldObjects() {
        gp.obj = new Entity[gp.maxMap][20];
        List<WorldItem> worldItems = worldItemDAO.loadAllWorldItemsForMap(gp.currentMap);

        int i = 0;
        for (WorldItem itemModel : worldItems) {
            //lewati jika sudah di collect
            if (itemModel.getIsCollectedByPlayerId() != null) {
                continue;
            }

            ItemDefinition itemDef = itemDefinitionDAO.findById(itemModel.getItemId());
            if (itemDef != null) {
                Entity itemEntity = createEntityFromDefinition(itemDef);

                if (itemEntity != null) {
                    itemEntity.worldX = itemModel.getWorldX();
                    itemEntity.worldY = itemModel.getWorldY();
                    itemEntity.worldItemId = itemModel.getId();

                    gp.obj[gp.currentMap][i++] = itemEntity;
                }
            }
        }
    }

    public void newGame(int playerId) {
        inventoryDAO.resetInventory(playerId);
        worldItemDAO.resetWorldItemsForPlayer(playerId);

        System.out.println("NEW GAME");
    }

    //Mengubah player unntuk ke database
    private PlayerModel mapPlayerEntityToModel(Player playerEntity) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setId(playerEntity.id);
        playerModel.setPlayerName(playerEntity.getPlayerName());
        playerModel.setLevel(playerEntity.getLevel());
        playerModel.setMaxHealth(playerEntity.getMaxHealth());
        playerModel.setCurrentHealth(playerEntity.getCurrentHealth());
        playerModel.setMaxArrow(playerEntity.getMaxArrow());
        playerModel.setCurrentArrow(playerEntity.getCurrentArrow());
        playerModel.setStrength(playerEntity.getStrength());
        playerModel.setAgility(playerEntity.getAgility());
        playerModel.setExperience(playerEntity.getExperience());
        playerModel.setNextLevelExp(playerEntity.getNextLevelExp());
        playerModel.setCoin(playerEntity.getCoin());
        playerModel.setWorldX(playerEntity.getWorldX());
        playerModel.setWorldY(playerEntity.getWorldY());
        playerModel.setCurrentMap(gp.currentMap);

        playerModel.setEquippedWeaponId(playerEntity.currentWeapon != null ? playerEntity.currentWeapon.definitionId : null);
        playerModel.setEquippedShieldId(playerEntity.currentShield != null ? playerEntity.currentShield.definitionId : null);

        return playerModel;
    }
//Mengubah item untuk kedatabse

    private List<PlayerInventory> mapInventoryEntityToModel(Player playerEntity) {
        List<PlayerInventory> inventoryModels = new ArrayList<>();
        for (Entity itemEntity : playerEntity.inventory) {
            PlayerInventory itemModel = new PlayerInventory();
            itemModel.setId(itemEntity.inventoryId);
            itemModel.setPlayerId(playerEntity.id);
            itemModel.setItemId(itemEntity.definitionId);
            itemModel.setQuantity(1);
            inventoryModels.add(itemModel);
        }
        System.out.println("----------------------------------------------");
        return inventoryModels;
    }
//Mengubah database ke player

    private void mapPlayerModelToEntity(PlayerModel playerModel, Player playerEntity) {
        playerEntity.id = playerModel.getId();
        playerEntity.name = playerModel.getPlayerName();
        playerEntity.level = playerModel.getLevel();
        playerEntity.maxDarah = playerModel.getMaxHealth();
        playerEntity.darah = playerModel.getCurrentHealth();
        playerEntity.maxArrow = playerModel.getMaxArrow();
        playerEntity.arrow = playerModel.getCurrentArrow();
        playerEntity.strength = playerModel.getStrength();
        playerEntity.agility = playerModel.getAgility();
        playerEntity.exp = playerModel.getExperience();
        playerEntity.nextLevel = playerModel.getNextLevelExp();
        playerEntity.coin = playerModel.getCoin();
        playerEntity.worldX = playerModel.getWorldX();
        playerEntity.worldY = playerModel.getWorldY();
        gp.currentMap = playerModel.getCurrentMap();
    }

//Mengubah database ke item
    private void mapInventoryModelToEntity(List<PlayerInventory> inventoryModels, Player playerEntity) {
        playerEntity.inventory.clear();
        for (PlayerInventory itemModel : inventoryModels) {
            ItemDefinition itemDef = itemDefinitionDAO.findById(itemModel.getItemId());
            if (itemDef != null) {
                Entity itemEntity = createEntityFromDefinition(itemDef);
                if (itemEntity != null) {
                    itemEntity.inventoryId = itemModel.getId();
                    playerEntity.inventory.add(itemEntity);

                    if (loadedPlayerModel.getEquippedWeaponId() != null && itemModel.getId() == loadedPlayerModel.getEquippedWeaponId()) {
                        playerEntity.currentWeapon = itemEntity;
                    }
                    if (loadedPlayerModel.getEquippedShieldId() != null && itemModel.getId() == loadedPlayerModel.getEquippedShieldId()) {
                        playerEntity.currentShield = itemEntity;
                    }
                }
            }
        }
        if (playerEntity.currentWeapon == null) {
            playerEntity.currentWeapon = new OBJ_Sword(gp);
        }
        if (playerEntity.currentShield == null) {
            playerEntity.currentShield = new OBJ_Shield(gp);
        }
        if (playerEntity.currentWeapon == null) {
            playerEntity.currentWeapon = new OBJ_Legendary(gp);
        }
    }
//Mengubah data menjadi objek di game

    private Entity createEntityFromDefinition(ItemDefinition itemDef) {
    Entity entity = null;

    // Periksa tipe item dari database
    switch (itemDef.getType()) {
        case "SWORD":
            if (itemDef.getName().equals("Legendary Sword")) {
                entity = new OBJ_Legendary(gp);
            } else {
                entity = new OBJ_Sword(gp);
            }
            break;
            
        case "AXE": // Logika untuk kapak juga diperbaiki
            entity = new OBJ_Axe(gp);
            break;

        case "SHIELD":
            // Logika ini sudah benar, bisa dibiarkan
            if (itemDef.getName().equals("Shield")) {
                entity = new OBJ_Shield(gp);
            }
            break;

        case "CONSUMABLE":
            if (itemDef.getName().equals("Key")) {
                entity = new OBJ_Key(gp);
            } else if (itemDef.getName().equals("Red Potion")) {
                entity = new OBJ_Potion(gp);
            }
            break;

        case "PICKUP_ONLY":
            switch (itemDef.getName()) {
                case "Heart":
                    entity = new OBJ_Heart(gp);
                    break;
                case "Arrow Ammo":
                    entity = new OBJ_ArrowAmmo(gp);
                    break;
                case "Gold Coin":
                    entity = new OBJ_Coin(gp);
                    break;
            }
            break;
            
        case "DOOR":
            entity = new OBJ_Door(gp);
            break;
    }

    // Bagian ini untuk menetapkan properti setelah objek dibuat
    if (entity != null) {
        entity.definitionId = itemDef.getId();
        entity.name = itemDef.getName();
        entity.nilaiAttack = itemDef.getAttackValue();
        entity.nilaiDefense = itemDef.getDefenseValue();
        entity.deskripsi = itemDef.getDescription();
    }
    
    return entity;
}
//Tandai item terambil

    public void collectWorldItem(int worldItemId, int playerId) {
        worldItemDAO.collectWorldItem(worldItemId, playerId);
    }
}
