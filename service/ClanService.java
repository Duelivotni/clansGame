package service;

import java.util.List;
import java.util.UUID;

import data.ClanRepository;
import models.Clan;

public class ClanService {
    
    private final ClanRepository clanRepository;

    public ClanService(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    public Clan createClan(String clanName) {    
        Clan clan = new Clan(clanName);
        clanRepository.save(clan);
        return clan;
    }

    public void removeClanById(UUID clanId) {
        clanRepository.deleteById(clanId);
    }

    public Clan getClanById(UUID clanId) {
        return clanRepository.findById(clanId);    
    }

    public List<Clan> getClansByName(String clanName) {
        return clanRepository.findByName(clanName);
    }

    public synchronized void addGoldToClan(Clan clan, int goldAmmount) {
        clan.setGold(clan.getGold() + goldAmmount);
        clanRepository.addGoldById(clan.getId(), goldAmmount);
    }

    public synchronized void takeGoldFromClan(Clan clan, int goldAmmount) {
        if (goldAmmount > clan.getGold()) {
            throw new RuntimeException(
                "Failed to take gold from clan with id: " + clan.getId() +
                ": gold ammount is bigger than clan's gold available\n");
        }
        clan.setGold(clan.getGold() - goldAmmount);
        clanRepository.reduceGoldById(clan.getId(), goldAmmount);
    }

    public synchronized void healUp(UUID clanId) {
        clanRepository.reduceGoldById(clanId, 50);
        clanRepository.addHealthPointsById(clanId);
    }

    public synchronized void levelUp(Clan clan, int exp) {
        clan.setExp(clan.getExp() + exp);
        clanRepository.updateExp(clan.getId(), exp);
    }
}
