import java.util.UUID;

import data.ClanRepository;
import data.DataService;
import data.TransactionsRepository;
import models.Clan;
import service.ClanService;
import service.UserTransactionService;

public class Main {
    public static void main(String[] args) {
        // Setting up game environment
        DataService dataService= new DataService();
        dataService.setUpDatabase();
        ClanRepository clanRepository = new ClanRepository(dataService);
        TransactionsRepository transactionsRepository = new TransactionsRepository(dataService);
        ClanService clanService = new ClanService(clanRepository);
        UserTransactionService userTransactionService = new UserTransactionService(clanService, transactionsRepository);
        // completed setup

        //game starts
        Clan punks = clanService.createClan("punks");
        Clan raiders = clanService.createClan("raiders");
        Clan prists = clanService.createClan("prists");
        Clan yankees = clanService.createClan("yankees");
        Clan faggots = clanService.createClan("faggots");

        updateGoldConcurrently(userTransactionService, punks);
    }

    /**
     * Multiple users update clan's gold simultaneously
     * 
     * @param userTransactionService
     * @param clan
     */
    private static void updateGoldConcurrently(UserTransactionService userTransactionService, Clan clan) {
        Runnable user1Task = () -> {
            UUID userId = UUID.randomUUID();
            System.out.println("thread 1 executing..");
            userTransactionService.takeGoldFromClan(userId, clan.getId(), 100);
        };
        Runnable user2Task = () -> {
            UUID userId = UUID.randomUUID();
            System.out.println("thread 2 executing..");
            userTransactionService.addGoldToClan(userId, clan.getId(), 100);
        };
        Runnable user3Task = () -> {
            UUID userId = UUID.randomUUID();
            System.out.println("thread 3 executing..");
            userTransactionService.takeGoldFromClan(userId, clan.getId(), 100);
        };
        Runnable user4Task = () -> {
            UUID userId = UUID.randomUUID();
            System.out.println("thread 4 executing..");
            userTransactionService.addGoldToClan(userId, clan.getId(), 120);
        };

        new Thread(user1Task).start();
        new Thread(user2Task).start();
        new Thread(user3Task).start();
        new Thread(user4Task).start();
    }
}
