package samuraisword.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	public String store(MultipartFile file, String folder, HttpSession http) {
		String fileName;
		try {
		String path = http.getServletContext().getRealPath("/").replace("webapp", "resources\\static\\resources\\images\\" + folder);
		fileName = file.getName().trim().concat(String.valueOf(new Date().getTime())).concat(".png");
		file.transferTo(new File(path + fileName));
		TimeUnit.SECONDS.sleep(3);
		} catch (Exception exception) {
			fileName = "error";
		}
		return fileName;
	}
}
