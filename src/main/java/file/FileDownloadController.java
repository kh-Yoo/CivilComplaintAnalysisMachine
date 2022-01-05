package file;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDownloadController{
	
    @GetMapping("/excel/download")
	public void download(HttpServletResponse response) throws IOException {

		String path = "/Users/ganghyun/Desktop/boot/downfile/download_file.xlsx";
		byte[] fileByte = FileUtils.readFileToByteArray(new File(path));
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode("download_file.xlsx", "UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	  }
}
