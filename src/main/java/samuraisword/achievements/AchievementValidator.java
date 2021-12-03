package samuraisword.achievements;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class AchievementValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Achievement pet = (Achievement) obj;
		

		// type validation
		if (pet.getType() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}
	}

	
		@Override
		public boolean supports(Class<?> clazz) {
			return Achievement.class.isAssignableFrom(clazz);
		}

}

	

