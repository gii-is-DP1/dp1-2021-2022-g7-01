package samuraisword.logros;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class LogroValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Logro pet = (Logro) obj;
		

		// type validation
		if (pet.getType() == null) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}
	}

	
		@Override
		public boolean supports(Class<?> clazz) {
			return Logro.class.isAssignableFrom(clazz);
		}

}

	

