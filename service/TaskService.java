package service;

import java.util.UUID;

public class TaskService {
    
    private final ClanService clanService;

    public TaskService(ClanService clanService) {
        this.clanService = clanService;
    }
    
    public void stealGold(UUID thiefClanId, UUID victimClanId, int goldAmmount) {
        clanService.takeGoldFromClan(victimClanId, goldAmmount);
        clanService.addGoldToClan(thiefClanId, goldAmmount);
    }

    public void fightClan(UUID challengerId, UUID defenderId) {
    
    }

    public void raidForCreeps(UUID clanId) {

    }
}
