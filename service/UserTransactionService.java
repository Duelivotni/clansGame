package service;

import java.util.UUID;

import data.TransactionsRepository;
import models.TransactionType;

public class UserTransactionService {
    
    private final ClanService clanService;
    private final TransactionsRepository transactionsRepository;

    public UserTransactionService(ClanService clanService, TransactionsRepository transactionsRepository) {
        this.clanService = clanService;
        this.transactionsRepository = transactionsRepository;
    }

    public void addGoldToClan(UUID userId, UUID clanId, int goldAmmount) {
        clanService.addGoldToClan(clanId, goldAmmount);
        transactionsRepository.saveTransaction(userId, clanId, TransactionType.DEPOSIT, goldAmmount);
    }
    
    public void takeGoldFromClan(UUID userId, UUID clanId, int goldAmmount) {
        clanService.takeGoldFromClan(clanId, goldAmmount);
        transactionsRepository.saveTransaction(userId, clanId, TransactionType.WITHDRAWAL, goldAmmount);
    }
}
