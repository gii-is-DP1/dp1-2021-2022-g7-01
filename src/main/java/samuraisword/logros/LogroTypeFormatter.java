package samuraisword.logros;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LogroTypeFormatter implements Formatter<LogroType> {

	private final LogroService peService;

	@Autowired
	public LogroTypeFormatter(LogroService petService) {
		this.peService = petService;
	}

	@Override
	public String print(LogroType petType, Locale locale) {
		return petType.getName();
	}

	@Override
	public LogroType parse(String text, Locale locale) throws ParseException {
		Collection<LogroType> findPetTypes = this.peService.findLogroTypes();
		for (LogroType type : findPetTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}

