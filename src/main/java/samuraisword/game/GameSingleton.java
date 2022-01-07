package samuraisword.game;

import java.util.HashMap;
import java.util.Map;

public class GameSingleton {
	private static GameSingleton instance;
	
	private Map<Integer, Game> mapGames;
	
	private GameSingleton() {
		this.mapGames = new HashMap<>();
	}
	
	public static GameSingleton getInstance() {
		if(instance == null) {
			instance = new GameSingleton();
		}
		return instance;
	}
	
	public Map<Integer, Game> getMapGames() {
		return mapGames;
	}
}
