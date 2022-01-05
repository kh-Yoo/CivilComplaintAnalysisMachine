package file;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import file.storage.StorageService;

@SpringBootApplication
public class FileSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//서버 시작시 전체 업로드 경로의 파일 제거
			storageService.deleteAll();
			//파일 업로드 없을 경우 폴더 생성
			storageService.init();
		};
	}
}
