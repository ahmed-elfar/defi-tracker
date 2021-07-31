package com.xyvo.defi;

import com.xyvo.defi.standalone.StandaloneInit;
import com.xyvo.defi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DefiTrackerPortfolioApplication implements CommandLineRunner {

	private final Environment env;

	@Autowired
	public DefiTrackerPortfolioApplication(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication.run(DefiTrackerPortfolioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (String profileName : env.getActiveProfiles()) {
			Utils.CONSOLE_LOG.warn("Currently active profile - {}", profileName);
		}

		StandaloneInit.create(env);
	}

}
