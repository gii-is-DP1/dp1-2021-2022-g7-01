package samuraisword.achievements;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AchievementService {
	
	private AchievementRepository achievementRepository;

	@Autowired
	public AchievementService(AchievementRepository achievementRepository) {
		this.achievementRepository = achievementRepository;
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
}