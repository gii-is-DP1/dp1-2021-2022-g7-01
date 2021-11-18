package samuraisword.logros;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LogroService {
	
	private LogroRepository logroRepository;

	@Autowired
	public LogroService(LogroRepository logroRepository) {
		this.logroRepository = logroRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<LogroType> findLogroTypes() throws DataAccessException {
		return logroRepository.findLogroTypes();
	}
	
	
	@Transactional(readOnly = true)
	public Collection<RolType> findRolTypes() throws DataAccessException {
		return logroRepository.findRolTypes();
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