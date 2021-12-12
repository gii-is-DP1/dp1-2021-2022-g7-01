package samuraisword.game;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.cardhand.CardHand;
import samuraisword.character.Character;
import samuraisword.comment.Comment;
import samuraisword.player.Player;
import samuraisword.player.Rol;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.card.RedCard;

@Service
public class GameService {
	
private GameRepository gameRepository;	
	
@Autowired
public GameService(GameRepository gameRepository) {
	this.gameRepository = gameRepository;
}

public Optional<Game> findById(int idGame) {
	return gameRepository.findById(idGame);
}
	
@Transactional
public void saveGame(Game game) throws DataAccessException {
	//creating game
	gameRepository.save(game);	
}	
	
public void deleteGame(int idGame) {
	gameRepository.deleteById(idGame);
}
	

public CardHand createDeck(CardService cardService) {
	//Crear y guardar deck
			CardHand gameDeck = new CardHand();//funcion que crea el deck predeterminado y ordenado aleatoriamente		
			gameDeck.setCardList(new ArrayList<>());
			for(Card card : cardService.findAll()) {
				for(int i = 0; i < card.getCardsPerDeck(); i++) {
					String name = card.getName();
					switch(cardService.findColor(name).get()) {
					case("Red"):
						Integer rango= Integer.valueOf(cardService.findRange(name).get());
						Integer damage= Integer.valueOf(cardService.findRange(name).get());
						RedCard redCard = RedCard.of(name, card.getImage(), rango, damage);
						gameDeck.getCardList().add(redCard);
					case("Yellow"):
						Card yellowCard = Card.of(name, card.getImage());
						gameDeck.getCardList().add(yellowCard);
					case("Blue"):
						Card blueCard = Card.of(card.getName(), card.getImage());
						gameDeck.getCardList().add(blueCard);
					}	
				}
			}
			Collections.shuffle(gameDeck.getCardList());
			
			return gameDeck;
}

public List<Player> asignCharacterAndHearts(List<Player> players, List<Character> characters) {
	
	for(Player p : players) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, characters.size());
		p.setCharacter(characters.get(randomNum));
		
		characters.remove(randomNum);
		
		p.setMaxHearts(p.getCharacter().getLife());
		p.setCurrentHearts(p.getCharacter().getLife());
	}
	return players;
}

public List<Player> asignRolAndHonor(List<Player> listPlayers) {
	
	Collections.shuffle(listPlayers);
	
	switch(listPlayers.size()) {
	case 4:
		listPlayers.get(0).setRol(Rol.SHOGUN);
		listPlayers.get(1).setRol(Rol.SAMURAI);
		listPlayers.get(2).setRol(Rol.NINJA);
		listPlayers.get(3).setRol(Rol.NINJA);
		//setHonor
		for(Player p : listPlayers) {
			if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
			else p.setHonor(3);
		}
	case 5:
		listPlayers.get(0).setRol(Rol.SHOGUN);
		listPlayers.get(1).setRol(Rol.SAMURAI);
		listPlayers.get(2).setRol(Rol.NINJA);
		listPlayers.get(3).setRol(Rol.NINJA);
		listPlayers.get(4).setRol(Rol.RONIN);
		
		for(Player p : listPlayers) {
			if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
			else p.setHonor(3);
		}
	case 6:
		listPlayers.get(0).setRol(Rol.SHOGUN);
		listPlayers.get(1).setRol(Rol.SAMURAI);
		listPlayers.get(2).setRol(Rol.NINJA);
		listPlayers.get(3).setRol(Rol.NINJA);
		listPlayers.get(4).setRol(Rol.NINJA);
		listPlayers.get(5).setRol(Rol.RONIN);
		
		for(Player p : listPlayers) {
			if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
			else p.setHonor(4);
		}
	case 7:
		listPlayers.get(0).setRol(Rol.SHOGUN);
		listPlayers.get(1).setRol(Rol.SAMURAI);
		listPlayers.get(2).setRol(Rol.NINJA);
		listPlayers.get(3).setRol(Rol.NINJA);
		listPlayers.get(4).setRol(Rol.NINJA);
		listPlayers.get(5).setRol(Rol.RONIN);
		listPlayers.get(6).setRol(Rol.SAMURAI);
		
		for(Player p : listPlayers) {
			if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
			else p.setHonor(4);
		}
	default:
		listPlayers.get(0).setRol(Rol.SHOGUN);
		listPlayers.get(0).setHonor(5);
	}
	
	return listPlayers;
}

public List<Player> asignOrder(List<Player> listPlayers) {
	// De el ultimo tratamiento a listPlayers (asignRolAndHonor) el 1o SHOGUN será siempre el indice 0.
	// queremos hacer un ultimo shuffle a listPlayers para que no sea siempre el mismo orden de roles
	//definidos en asignRolandHonor, pero queremos que SHOGUN siga ocupando el indice 0 ya que es importante 
	//para el reparto de cartas ya que en funcion de los jugadores que hayan entre un jugador y el SHOGUN se le
	//reparten mas o menos cartas.
	
	Player shogun = listPlayers.get(0);
	listPlayers.remove(0);
	Collections.shuffle(listPlayers);
	listPlayers.add(0, shogun);
	
	return listPlayers;
}

public List<Player> asignCards(List<Player> listPlayers, List<Card> list) {
	
	return null;
	
}
	
	

}
