package models;

import java.util.UUID;

public class Clan {

    private UUID id;     
    private String name;
    private int gold;
    private int healthPoints;
    private int exp;
    
    public Clan(UUID id, String name, int gold, int healthPoints, int exp) {
        this.id = id;
        this.name = name;
        this.gold = gold;
        this.healthPoints = healthPoints;
        this.exp = exp;
    }

    public Clan(String name) {
        this.id = UUID.randomUUID();
        this.healthPoints = 100;
        this.exp = 0;
        this.name = name;
        this.gold = 100;
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
}