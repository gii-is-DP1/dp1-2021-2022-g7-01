package samuraisword.character;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.game.Game;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;
    private CardService cardService;

    @Autowired
    public CharacterService(CharacterRepository characterRepository,
            CardService cardService) {
        this.characterRepository = characterRepository;
        this.cardService = cardService;
    }

    @Transactional
    public Collection<Character> findAll() {
        return characterRepository.findAll();
    }

    @Transactional
    public Character findByName(String name) {
        return characterRepository.findByName(name);
    }

    public Boolean execute(Player player) {
    	
        Boolean res = false;
        if (player.getCharacter().getName().equals("Benkei")) {
            Integer now = player.getDistanceBonus();
            statUp(player, "distanceBonus", 1);
            res = player.getDistanceBonus().equals(now + 1);
        }
        if (player.getCharacter().getName().equals("Chiyome")) {
        	
            Integer now = player.getAntidistanceBonus();
            statUp(player, "antidistanceBonus", 1);
            res = player.getAntidistanceBonus().equals(now + 1);
        }
        if (player.getCharacter().getName().equals("Ginchiyo")) {
        	
            Integer now = player.getDrawCardBonus();
            statUp(player, "drawCard", 1);
            res = player.getDrawCardBonus().equals(now + 1);

        }
        
        if (player.getCharacter().getName().equals("Hideyoshi")) {
            Integer now = player.getHonor();
            player.setHonor(now+1);
            res = player.getHonor().equals(now + 1);
            
        }
        
        if (player.getCharacter().getName().equals("Goemon")) {
            Integer weaponsNow = player.getWeaponBonus();
                statUp(player, "weaponBonus", 1);
                res = player.getWeaponBonus().equals(weaponsNow + 1);
        }
        
        if (player.getCharacter().getName().equals("Musashi")) {
            Integer damageNow = player.getDamageBonus();
            statUp(player, "damageBonus", 1);
            res = player.getDamageBonus().equals(damageNow + 1);
        }
        
        if (player.getCharacter().getName().equals("Tomoe")) {
        	
            Integer now = player.getAntiDamageBonus();
            statUp(player, "antiDamageBonus", 1);
            res = player.getAntiDamageBonus().equals(now + 1);
        }
        return res;
    }

    public void statUp(Player player, String stat, Integer bonus) {    
        if(stat.contains("distanceBonus")) player.setDistanceBonus(player.getDistanceBonus()+bonus);
        if(stat.contains("antidistanceBonus")) player.setAntidistanceBonus((player.getAntidistanceBonus()+bonus));
        if(stat.contains("drawCard")) player.setDrawCardBonus(player.getDrawCardBonus()+ bonus);;
        if(stat.contains("weaponBonus")) player.setWeaponBonus(player.getWeaponBonus()+bonus);
        if(stat.contains("antiDamageBonus")) player.setAntiDamageBonus(player.getAntiDamageBonus()+bonus);
        if(stat.contains("damageBonus")) player.setDamageBonus(player.getDamageBonus()+bonus);
    }
    public void statDown(Player player, String stat, Integer bonus) {
            if(stat.equals("distanceBonus")) player.setDistanceBonus(player.getDistanceBonus()-bonus);
            if(stat.equals("weaponBonus")) player.setWeaponBonus(player.getWeaponBonus()-bonus);
            if(stat.equals("damageBonus")) player.setDamageBonus(player.getDamageBonus()-bonus);
    }
}