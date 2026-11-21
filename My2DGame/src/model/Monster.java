package model;

import java.sql.Timestamp;

public class Monster {

    private int id;
    private String monsterType;
    private int worldX;
    private int worldY;
    private int mapId;
    private int currentHealth;
    private boolean isAlive;
    private Timestamp lastKilledAt;

    public Monster() {
    }

    public int getId() {
        return id;
    }

    public String getMonsterType() {
        return monsterType;
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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Timestamp getLastKilledAt() {
        return lastKilledAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
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

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setLastKilledAt(Timestamp lastKilledAt) {
        this.lastKilledAt = lastKilledAt;
    }
}
