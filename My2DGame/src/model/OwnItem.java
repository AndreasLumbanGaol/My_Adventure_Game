package model;

public class OwnItem {
    private int id;
    private int playerId;
    private int itemId;

    public OwnItem(int id, int playerId, int itemId) {
        this.id = id;
        this.playerId = playerId;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
