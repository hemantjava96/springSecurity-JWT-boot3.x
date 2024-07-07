package com.hk.sec.prep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hk.sec.prep.entity.Role;
import com.hk.sec.prep.entity.User;
import com.hk.sec.prep.repository.UserRepo;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User admUser = userRepo.findByRole(Role.ADMIN);	
		
		if(null == admUser) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(passwordEncoder.encode("admin@123"));
			userRepo.save(user);
		}
	}

}
