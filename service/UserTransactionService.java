package service;

import java.util.UUID;

import data.TransactionsRepository;
import models.Clan;
import models.TransactionStatus;
import models.TransactionType;

public class UserTransactionService {
    
    private final ClanService clanService;
    private final TransactionsRepository transactionsRepository;

    public UserTransactionService(ClanService clanService, TransactionsRepository transactionsRepository) {
        this.clanService = clanService;
        this.transactionsRepository = transactionsRepository;
    }

    public void addGoldToClan(UUID userId, Clan clan, int goldAmmount) {
        clanService.addGoldToClan(clan, goldAmmount);
        transactionsRepository.saveTransaction(userId, clan.getId(), TransactionType.DEPOSIT, goldAmmount, TransactionStatus.SUCCESS);
    }
    
    /**
     * Adds gold and saves transaction
     * Sets up transaction status 'Failed' if no success subtracting clan's gold
     * 
     * @param userId
     * @param clan
     * @param goldAmmount
     */
    public void takeGoldFromClan(UUID userId, Clan clan, int goldAmmount) {
        boolean success = true;
        try {
            clanService.takeGoldFromClan(clan, goldAmmount);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            success = false;
        }
        finally {
            transactionsRepository.saveTransaction(
                userId, clan.getId(), TransactionType.WITHDRAWAL, goldAmmount, success == true ? TransactionStatus.SUCCESS : TransactionStatus.FAILED);
        }
    }
}
