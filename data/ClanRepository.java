package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Clan;

public class ClanRepository {

    private final DataService dataService;

    public ClanRepository(DataService dataService) {
        this.dataService = dataService;
    }

    public Clan findById(UUID clanId) {
        PreparedStatement st = null;
        Clan clan = null;
        try {
            st = dataService.getConnection()
            .prepareStatement("select * from clan where id = ?");
            st.setObject(1, clanId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                clan = new Clan(
                    (UUID)rs.getObject("id"),
                    rs.getString("name"),
                    rs.getInt("gold"),
                    rs.getInt("health_points"),
                    rs.getInt("exp"));
            };
        } catch (Exception e) {
            System.out.println("Failed to fetch clan with id: " + clanId);
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                    dataService.closeConnection();
                } catch (SQLException se) {}
            }
        }
        return clan;
    }

    public List<Clan> findByName(String clanName) {
        PreparedStatement st = null;
        List<Clan> clans = new ArrayList<>();
        try {
            st = dataService.getConnection()
            .prepareStatement("select * from clan where name = ?");
            st.setObject(1, clanName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                clans.add(
                    new Clan(
                        (UUID)rs.getObject("id"),
                        rs.getString("name"),
                        rs.getInt("gold"),
                        rs.getInt("health_points"),
                        rs.getInt("exp"))
                );
            };
        } catch (Exception e) {
            System.out.println("Failed to fetch clans with name: " + clanName);
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                    dataService.closeConnection();
                } catch (SQLException se) {}
            }
        }
        return clans;
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

    /**
     * Adds or subtracts clan's gold
     * depending on positive/negative goldAmmount respectively
     * 
     * @param clanId
     * @param goldAmmount
     */
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

    public void deleteById(UUID clanId) {
        PreparedStatement statement = null;
        try {
            System.out.println("Removing clan with id: " + clanId);
            statement = dataService.getConnection().prepareStatement("delete from clan where id = ?");
            statement.setObject(1, clanId);
            statement.executeUpdate();
            System.out.println("Clan with id [" + clanId + "] has been removed");

        } catch (Exception e) {
            System.out.println("Failed to remove clan with id: " + clanId);
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

    public void updateExp(UUID clanId, int exp) {
        PreparedStatement statement = null;
        try {
            System.out.println("Updating Exp for clan with id: " + clanId);
            statement = dataService.getConnection().prepareStatement("update clan set exp = exp + ? where id = ?");
            statement.setInt(1, exp);
            statement.setObject(2, clanId);
            statement.executeUpdate();
            System.out.println("Updated " + exp + " Exp for clan with id: " + clanId);

        } catch (Exception e) {
            System.out.println("Failed to update Exp for clan with id: " + clanId);
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

