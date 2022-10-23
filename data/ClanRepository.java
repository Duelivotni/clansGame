package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import models.Clan;

public class ClanRepository {

    private final DataService dataService;

    public ClanRepository(DataService dataService) {
        this.dataService = dataService;
    }

    public Clan findById(UUID clanId) {
        return null;
    }

    public Clan findByName(String clanName) {
        return null;
    }
    
    public void addGoldById(UUID clanId, int goldAmmount) {
        this.updateGoldById(clanId, goldAmmount);
    }

    public void reduceGoldById(UUID clanId, int goldAmmount) {
        this.updateGoldById(clanId, goldAmmount * -1);
    }

    public void addHealthPointsById(UUID clanId) {
    }

    public void save(Clan clan) {
        PreparedStatement statement = null;
        try {
            System.out.println("Creating clan with name: " + clan.getName());
            statement = dataService.getConnection().prepareStatement("insert into clan values (?, ?, ?, ?, ?)");
            statement.setObject(1, clan.getId());
            statement.setString(2, clan.getName());
            statement.setInt(3, clan.getGold());
            statement.setInt(4, clan.getHealthPoints());
            statement.setInt(5, clan.getExp());
            statement.executeUpdate();
            System.out.println("Clan with name [" + clan.getName() + "] has been created");

        } catch (Exception e) {
            System.out.println("Failed to create clan with name " + clan.getName());
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    dataService.closeConnection();
                } catch (SQLException sq) {}
            }
        }
    }

    private void updateGoldById(UUID clanId, int goldAmmount) {
        PreparedStatement statement = null;
        try {
            System.out.println("Updating gold for clan with id: " + clanId);
            statement = dataService.getConnection().prepareStatement("update clan set gold = gold + ? where id = ?");
            statement.setInt(1, goldAmmount);
            statement.setObject(2, clanId);
            statement.executeUpdate();
            System.out.println("Updated " + goldAmmount + " gold to clan with id: " + clanId);

        } catch (Exception e) {
            System.out.println("Failed to update gold to clan with id: " + clanId);
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    dataService.closeConnection();
                } catch (SQLException sq) {}
            }
        }
    }
}   

