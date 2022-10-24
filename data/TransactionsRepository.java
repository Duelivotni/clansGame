package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import models.TransactionStatus;
import models.TransactionType;

public class TransactionsRepository {

    private final DataService dataService;

    public TransactionsRepository(DataService dataService) {
        this.dataService = dataService;
    }   

    public void saveTransaction(UUID userId, UUID clanId, TransactionType type, int goldAmmount, TransactionStatus status) {
        PreparedStatement statement = null;
        try {
            System.out.println(
                String.format("Saving transaction for user id: %s, clan id: %s, transaction type: %s, gold ammount: %s", userId, clanId, type, goldAmmount));
            statement = dataService.getConnection().prepareStatement(
                "insert into user_transaction values(default, ?, ?, ?, ?, ?, default)"
            );
            statement.setObject(1, userId);
            statement.setObject(2, clanId);
            statement.setInt(3, goldAmmount);
            statement.setString(4, type.toString());
            statement.setString(5, status.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to save transaction for user id: " + userId + "/n" + e.getMessage());
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
