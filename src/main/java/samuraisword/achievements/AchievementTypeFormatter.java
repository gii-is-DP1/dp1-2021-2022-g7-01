package samuraisword.achievements;

import java.text.ParseException;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class AchievementTypeFormatter implements Formatter<AchievementType> {

	private final AchievementService peService;

	@Autowired
	public AchievementTypeFormatter(AchievementService petService) {
		this.peService = petService;
	}
	
	@Override
	public String print(AchievementType petType, Locale locale) {
		return petType.getName();
	}

	@Override
	public AchievementType parse(String text, Locale locale) throws ParseException {
		Collection<AchievementType> findPetTypes = this.peService.findAchievementTypes();
		for (AchievementType type : findPetTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}

