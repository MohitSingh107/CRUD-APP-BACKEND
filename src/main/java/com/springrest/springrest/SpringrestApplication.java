package com.springrest.springrest;

import com.springrest.springrest.dao.RoleDao;
import com.springrest.springrest.model.Roles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringrestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringrestApplication.class, args);
		
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	private RoleDao roleDao;


	@Override
	public void run(String... args) throws Exception {
		try {
			Roles role = new Roles();
			role.setId(401);
			role.setName("ROLE_ADMIN");

			Roles role1 = new Roles();
			role1.setId(402);
			role1.setName("ROLE_USERN");

			List<Roles> roles = List.of(role,role1);
			List<Roles>	result= roleDao.saveAll(roles);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
