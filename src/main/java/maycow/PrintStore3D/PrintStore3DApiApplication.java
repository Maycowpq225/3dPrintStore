package maycow.printstore3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrintStore3DApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrintStore3DApiApplication.class, args);
	}

}
