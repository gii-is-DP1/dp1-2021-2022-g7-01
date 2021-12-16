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
import samuraisword.samples.petclinic.user.User;


@Service
public class AchievementService {
	
	private AchievementRepository achievementRepository;
	private PersonalAchievementRepository personalAchievementRepository;
	private CommentRepository commentRepository;

	@Autowired
	public AchievementService(AchievementRepository achievementRepository, CommentRepository commentRepository, PersonalAchievementRepository personalAchievementRepository) {
		this.achievementRepository = achievementRepository;
		this.commentRepository = commentRepository;
		this.personalAchievementRepository = personalAchievementRepository;
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
		
		Collection<Achievement>ListAchievevement = achievementRepository.findAll();
		Collection<Integer>personal = personalAchievementRepository.findAll(user);
		for(Achievement a : ListAchievevement) {
			if(!personal.contains(a.getId())) {
				String[] body = a.getBody().split(" ");
				if(body[0].equals("Escribe")) {
					Integer n = commentRepository.findByUser(user).size();
					if(n>=Integer.valueOf(body[1])) {
						
						personalAchievementRepository.achieved(a.getId(), user.getUsername());
					}
				}
			}
		}
		
	}
	
	public Map<Achievement, Integer> findAllPersonalAchievements(User user) {
		Collection<Achievement>ListAchievevement = achievementRepository.findAll();
		Collection<Integer>personal = personalAchievementRepository.findAll(user);
		Map<Achievement, Integer> res = new HashMap<Achievement, Integer>();
		for(Achievement a : ListAchievevement) {
			if(personal.contains(a.getId())) {
				res.put(a, 1);
			}
			else {
				res.put(a, 0);
			}
		}
		return res;
	}
}