package hr.fer.wpu.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDataApplication.class, args);
                System.out.println("User Data Server started");
	}

}
