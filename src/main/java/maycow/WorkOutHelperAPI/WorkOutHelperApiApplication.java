package maycow.WorkOutHelperAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorkOutHelperApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOutHelperApiApplication.class, args);
	}

}
