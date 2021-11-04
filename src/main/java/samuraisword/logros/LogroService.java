package samuraisword.logros;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.samples.petclinic.pet.PetType;

@Service
public class LogroService {
	
	private LogroRepository logroRepository;

	@Autowired
	public LogroService(LogroRepository logroRepository) {
		this.logroRepository = logroRepository;
	}
	
	
	
	public Collection<Logro> findAll() {
		return logroRepository.findAll();
	}
	
	@Transactional
	public void saveLogros(Logro logro) {
		logroRepository.save(logro);
	}
	
	public Optional<Logro> findById(int idLogro) {
		return logroRepository.findById(idLogro);
	}
	
	public void deleteLogro(int idLogro) {
		logroRepository.deleteById(idLogro);
	}
}