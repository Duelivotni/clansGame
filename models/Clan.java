package models;

import java.util.UUID;

import properties.GameProperties;

public class Clan {

    private UUID id;     
    private String name;
    private volatile int gold;
    private volatile int healthPoints;
    private volatile int exp;
    
    public Clan(UUID id, String name, int gold, int healthPoints, int exp) {
        this.id = id;
        this.name = name;
        this.gold = gold;
        this.healthPoints = healthPoints;
        this.exp = exp;
    }

    public Clan(String name) {
        this.id = UUID.randomUUID();
        this.healthPoints = GameProperties.NEW_CLAN_HEALTH_POINTS;
        this.exp = GameProperties.NEW_CLAN_EXP;
        this.name = name;
        this.gold = GameProperties.NEW_CLAN_GOLD;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "Clan [id=" + id + ", name=" + name + ", gold=" + gold + ", healthPoints=" + healthPoints + ", exp="
                + exp + "]";
    } 
}
