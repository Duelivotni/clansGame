package service;

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

    public Clan getClanById(UUID clanId) {
        return clanRepository.findById(clanId);    
    }

    public Clan getClanByName(String clanName) {
        return clanRepository.findByName(clanName);
    }

    public void addGoldToClan(UUID clanId, int goldAmmount) {
        clanRepository.addGoldById(clanId, goldAmmount);
    }

    public void takeGoldFromClan(UUID clanId, int goldAmmount) {
        clanRepository.reduceGoldById(clanId, goldAmmount);
    }

    public void healUp(UUID clanId) {
        clanRepository.reduceGoldById(clanId, 50);
        clanRepository.addHealthPointsById(clanId);
    }
}
