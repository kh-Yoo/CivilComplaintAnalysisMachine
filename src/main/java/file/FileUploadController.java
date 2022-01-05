package file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import file.storage.StorageService;

// 자바에서 파이썬 호출 관련 import
import org.apache.commons.exec.CommandLine;
import java.io.*;
import java.io.Reader;


@Controller
public class FileUploadController extends Thread {
	
	@Autowired
	private StorageService storageService;

	@GetMapping("/")
	public String listUploadedFiles(Model model) {	
		return "index.html";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response)  throws IOException {
		storageService.store(file);


		//파일 첨부 안 했을 때 alert 표시
		if(file.isEmpty()){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('파일을 첨부했는지 확인해주세요!'); history.go(-1);</script>");
            out.flush();
			return "index.html";
		}
		else{
			String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3
			//엑셀 파일이 아닐 때 alert 표시
			if (!extension.equals("xlsx") && !extension.equals("xls")) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('엑셀 파일만 첨부해주세요!'); history.go(-1);</script>");
				out.flush();

				return "index.html";
			}
			else{
				// 자바에서 파이썬 실행
				System.out.println("Python Call");

				ProcessBuilder pb =
                new ProcessBuilder("python","/Users/ganghyun/Desktop/boot/python_code/python_model.py");

				pb.redirectErrorStream(true);
				Process proc = pb.start();

				Reader reder = new InputStreamReader(proc.getInputStream());
				BufferedReader bf = new BufferedReader(reder);
				String s;
				while ((s = bf.readLine()) != null) {
					System.out.println(s);
				}

				System.out.println("Python End");

				return "generic.html";
			}
		}

	}

	public static void execPython(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }
    }    

}
