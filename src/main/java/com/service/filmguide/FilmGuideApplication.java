package com.service.filmguide;

import java.util.HashSet;

import com.service.filmguide.model.Role;
import com.service.filmguide.model.User;
import com.service.filmguide.controller.user.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.service.filmguide.controller.authentication.security.JwtTokenAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Configuration
@EnableAsync

public class FilmGuideApplication {

	@Autowired
	private IUserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public FilterRegistrationBean<JwtTokenAuthenticationFilter> registration(JwtTokenAuthenticationFilter filter) {
    	FilterRegistrationBean<JwtTokenAuthenticationFilter> registration = new FilterRegistrationBean<JwtTokenAuthenticationFilter>(filter);
    	registration.setEnabled(false);
    	return registration;
	}



	@Bean
	CommandLineRunner commandLineRunner() {
		return new CommandLineRunner(){
			@Override
			public void run(String... args) throws Exception {

				if(userService.findByUsername("furkankayar").isPresent()){
					return;
				}

				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = bCryptPasswordEncoder.encode("756ee75b");
				HashSet<Role> roles = new HashSet<Role>();
				roles.add(Role.builder().role("admin").build());
				roles.add(Role.builder().role("user").build());

				userService.save(
					User.builder()
						.username("furkankayar")
						.firstName("Furkan")
						.lastName("Kayar")
						.email("furkankayar27@gmail.com")
						.password(hashedPassword)
						.roles(roles)
						.build()
				);
			}
		};

	}

	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;
	}

	public static void main(String[] args) {
		SpringApplication.run(FilmGuideApplication.class, args);
		
	}

}
