package academy.devdojo.springbootessentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class SpringbootEssentialsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEssentialsApplication.class, args);
	}

}
