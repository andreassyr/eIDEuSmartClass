package gr.aegean.eIdEuSmartClass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class EIdEuSmartClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(EIdEuSmartClassApplication.class, args);
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        return javaMailSender;
    }

}
