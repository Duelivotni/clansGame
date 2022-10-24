package service;

import java.util.UUID;

import models.Clan;
import properties.GameProperties;

public class TaskService {
    
    private final ClanService clanService;

    public TaskService(ClanService clanService) {
        this.clanService = clanService;
    }
    
    /** 
     * @param thiefClan steals gold
     * @param victimClan loses gold
     * @param goldAmmount gold to steal
     * 
     * catches exception if goldAmmount is bigger than victimClan's gold
     */
    public void stealGold(Clan thiefClan, Clan victimClan, int goldAmmount) {
        try {
            clanService.takeGoldFromClan(victimClan, goldAmmount);
            clanService.addGoldToClan(thiefClan, goldAmmount);
            System.out.println(
                String.format("Clan with id %s has stolen %s gold from clan with id %s", thiefClan.getId(), goldAmmount, victimClan.getId())
            );
        } catch (Exception e) {
            System.out.println(thiefClan.getName() + " failed to steal gold from " + victimClan.getName() +
            "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Winner is defined by max(HP + Exp)
     * 
     * Winner clan gains Exp points and gold
     * Loser is removed from the game
     * In case of draw both clans gain Exp
     * 
     * @param challenger
     * @param defender
     * 
     * @return winner or null if none
     */
    public Clan fightClan(Clan challenger, Clan defender) {
        System.out.println("Clan fight has started between: " + challenger.getName() + " and " + defender.getName());
        if (challenger.getHealthPoints() + challenger.getExp() > defender.getHealthPoints() + defender.getExp()) {
            processClansFightResult(challenger, defender);
            return challenger;
        } else if (challenger.getHealthPoints() + challenger.getExp() < defender.getHealthPoints() + defender.getExp()) {
            processClansFightResult(defender, challenger);
            return defender;
        } else {
            clanService.levelUp(challenger, GameProperties.CLAN_FIGHT_DRAW_EXP);
            clanService.levelUp(defender, GameProperties.CLAN_FIGHT_DRAW_EXP);
            System.out.println(
                String.format("Clan fight between %s and %s ends up by draw. Both clans gain Exp", challenger.getName(), defender.getName())
            );
        }
        return null;
    }

    public void raidForCreeps(UUID clanId) {

    }

    private void processClansFightResult(Clan winner, Clan loser) {
        System.out.println("Clan " + winner.getName() + " with id: " + winner.getId() + "  wins the fight." +
        " Clan " + loser.getName() + " is beeing removed");
        clanService.removeClanById(loser.getId());
        loser = null;
        clanService.addGoldToClan(winner, GameProperties.CLAN_DEFEAT_REWARD);
        clanService.levelUp(winner, GameProperties.CLAN_DEFEAT_EXP);
        System.out.println(
            String.format("Clan %s with id: %s gains %s gold and %s exp", 
            winner.getName(), winner.getId(), GameProperties.CLAN_DEFEAT_REWARD, GameProperties.CLAN_DEFEAT_EXP)
        );
    }
}
