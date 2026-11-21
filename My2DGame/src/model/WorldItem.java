package model;

public class WorldItem {

    private int id;
    private int itemId;
    private int worldX;
    private int worldY;
    private int mapId;
    private Integer isCollectedByPlayerId; 

    public WorldItem() {
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getMapId() {
        return mapId;
    }

    public Integer getIsCollectedByPlayerId() {
        return isCollectedByPlayerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public void setIsCollectedByPlayerId(Integer isCollectedByPlayerId) {
        this.isCollectedByPlayerId = isCollectedByPlayerId;
    }
}
