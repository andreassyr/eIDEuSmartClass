package gr.aegean.eIdEuSmartClass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WebSecurityConfig.class, MailConfig.class })
public class EIdEuSmartClassApplication {

	public static void main(String[] args) {
		SpringApplication.run(EIdEuSmartClassApplication.class, args);
	}
}
