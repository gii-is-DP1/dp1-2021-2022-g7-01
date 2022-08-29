package samuraisword.achievements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.comment.CommentRepository;
import samuraisword.comment.CommentService;
import samuraisword.game.Game;
import samuraisword.game.GameRepository;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.player.Rol;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserRepository;

@Service
public class AchievementService {

	private AchievementRepository achievementRepository;
	private PersonalAchievementRepository personalAchievementRepository;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private GameRepository gameRepository;
	private PlayerService playerService;

	@Autowired
	public AchievementService(AchievementRepository achievementRepository, CommentRepository commentRepository,
			PersonalAchievementRepository personalAchievementRepository, UserRepository userRepository,
			GameRepository gameRepository, PlayerService playerService) {
		this.achievementRepository = achievementRepository;
		this.commentRepository = commentRepository;
		this.personalAchievementRepository = personalAchievementRepository;
		this.userRepository = userRepository;
		this.gameRepository = gameRepository;
		this.playerService = playerService;
	}

	@Transactional(readOnly = true)
	public Collection<AchievementType> findAchievementTypes() throws DataAccessException {
		return achievementRepository.findAchievementTypes();
	}

	@Transactional(readOnly = true)
	public Collection<RolType> findRolTypes() throws DataAccessException {
		return achievementRepository.findRolTypes();
	}

	public Collection<Achievement> findAll() {
		return achievementRepository.findAll();
	}

	public Collection<PersonalAchievement> findAllPersonalAchievements() {
		return personalAchievementRepository.findAll();
	}

	@Transactional
	public void saveAchievements(Achievement achievement) {
		achievementRepository.save(achievement);
	}

	public Optional<Achievement> findById(int idAchievement) {
		return achievementRepository.findById(idAchievement);
	}

	public void deleteAchievement(int idAchievement) {
		achievementRepository.deleteById(idAchievement);
	}

	@Transactional(readOnly = true)
	public void achivedCheck(User user) {

		Collection<Achievement> ListAchievevement = achievementRepository.findAll();
		Collection<Integer> personal = personalAchievementRepository.findAll(user);
		for (Achievement a : ListAchievevement) {
			if (!personal.contains(a.getId())) {
				String[] body = a.getBody().split(" ");
				if (body[0].equals("Write")) {
					Integer n = commentRepository.findByUser(user).size();
					if (n >= Integer.valueOf(body[1])) {
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}
				}
				if (body[0].equals("Send")) {
					Integer n = userRepository.listSendRequestAll(user.getUsername()).size();
					n += userRepository.listFriendAll2(user.getUsername()).size();
					if (n >= Integer.valueOf(body[1])) {
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}
				}
				if (body[0].equals("Be")) {
					Integer n = userRepository.listFriendAll1(user.getUsername()).size();
					n += userRepository.listFriendAll1(user.getUsername()).size();
					if (n >= Integer.valueOf(body[3])) {
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}
				}
				if (body[0].equals("Win")) {
					List<Game> listGames = (List<Game>) gameRepository.findAll();
					int n = 0;
					Rol rol =Rol.valueOf(body[5]);
					Optional<Player> p = playerService.findByUser(user);
					for (Game g : listGames) {
						if (p.isPresent() && g.getWonPlayers().contains(p.get()) && p.get().getCharacter().equals(rol)) {
							n++;

						}
					}
					if (n >= Integer.valueOf(body[1])) {
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}

				}
				if (body[0].equals("Play")) {
					List<Game> listGames = (List<Game>) gameRepository.findAll();
					int n = 0;
					Rol rol =Rol.valueOf(body[5]);
					Optional<Player> p = playerService.findByUser(user);
					for (Game g : listGames) {
						if (p.isPresent() && g.getListPlayers().contains(p.get()) && p.get().getCharacter().equals(rol)) {
							n++;

						}
					}
					if (n >= Integer.valueOf(body[1])) {
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}

				}
				if (body[0].equals("Lose")) {
					List<Game> listGames = (List<Game>) gameRepository.findAll();
					int n = 0;
					int m = 0;
					Rol rol =Rol.valueOf(body[5]);
					Optional<Player> p = playerService.findByUser(user);
					for (Game g : listGames) {
						if (p.isPresent() && g.getListPlayers().contains(p.get()) && p.get().getCharacter().equals(rol)) {
							m++;
							if (p.isPresent() && g.getWonPlayers().contains(p.get()) && p.get().getCharacter().equals(rol)) {
								n++;

							}

						}
					}
					if ((m-n) >= Integer.valueOf(body[1])) {
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}

				}
				if (body[0].equals("Create")) {
					personalAchievementRepository.achieved(a.getId(), user.getUsername());
				}
			}
		}

	}

	public Map<Achievement, Integer> findAllPersonalAchievements(User user) {
		Collection<Achievement> ListAchievevement = achievementRepository.findAll();
		Collection<Integer> personal = personalAchievementRepository.findAll(user);
		Map<Achievement, Integer> res = new HashMap<Achievement, Integer>();
		for (Achievement a : ListAchievevement) {
			if (personal.contains(a.getId())) {
				res.put(a, 1);
			} else {
				res.put(a, 0);
			}
		}
		return res;
	}
}