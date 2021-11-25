package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuraisword.logros.LogroService;
import samuraisword.logros.LogroType;
import samuraisword.logros.RolType;
import samuraisword.logros.Logro;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LogroServiceTests {
	
	@Autowired
	protected LogroService logroService;
	
	@Test
	void shouldFindLogroById() {
		Optional<Logro> logro = this.logroService.findById(1);
		assertThat(logro.isPresent());
		
		logro = this.logroService.findById(2);
		assertThat(logro.isEmpty()).isTrue();
	}
	
	@Test
	void shouldHaveTitleAndBody() {
		Collection<Logro> logros = this.logroService.findAll();
		for(Logro l : logros) {
			assertThat(l.getTitle().length() > 0 && l.getBody().length() > 0)
			.isTrue();
		}			
	}
	
	@Test
	@Transactional
	void shouldInsertComment() {
		Collection<Logro> logros = this.logroService.findAll();
		int previousCount = logros.size();
		
		Logro logro = new Logro();
		
		logro.setTitle("Title");
		logro.setBody("This is the body");
		
		this.logroService.saveLogros(logro);
		logros = this.logroService.findAll();
		
		assertThat(logros.size()).isEqualTo(previousCount + 1);
	}
	
	@Test
	@Transactional
	void shouldUpdateLogro() {
		Logro logro = this.logroService.findById(1).get();
		String newTitle = "this is a new title";

		logro.setTitle(newTitle);
		this.logroService.saveLogros(logro);

		// retrieving new name from database
		logro = this.logroService.findById(1).get();
		assertThat(logro.getTitle()).isEqualTo(newTitle);
	}
	
	@Test
	@Transactional
	void shouldDeleteLogro() {
		int count = this.logroService.findAll().size();
		
		assertThat(this.logroService.findById(1).isPresent());
		this.logroService.deleteLogro(1);
		assertThat(this.logroService.findAll().size()).isEqualTo(count - 1);		
	}
	
	@Test
	void shouldHaveValidType() {
		Collection<LogroType> logroTypes = this.logroService.findLogroTypes();
		Optional<Logro> logro = this.logroService.findById(1);
		assertThat(logro.get().getType()).isIn(logroTypes);
	}
	
	@Test
	void shouldHaveValidRol() {
		Collection<RolType> rolTypes = this.logroService.findRolTypes();
		Optional<Logro> logro = this.logroService.findById(1);
		assertThat(logro.get().getTypes()).isIn(rolTypes);
	}
	
	
	
	
	
	
}
