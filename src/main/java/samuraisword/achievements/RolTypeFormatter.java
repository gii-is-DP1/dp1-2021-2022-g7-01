package samuraisword.achievements;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class RolTypeFormatter implements Formatter<RolType> {

	private final AchievementService peService;

	@Autowired
	public RolTypeFormatter(AchievementService petService) {
		this.peService = petService;
	}

	@Override
	public String print(RolType petType, Locale locale) {
		return petType.getName();
	}

	@Override
	public RolType parse(String text, Locale locale) throws ParseException {
		Collection<RolType> findPetTypes = this.peService.findRolTypes();
		for (RolType type : findPetTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}


