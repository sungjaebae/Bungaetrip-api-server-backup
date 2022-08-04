package GoGetters.GoGetter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class GoGetterApplication {
	@PostConstruct
	public void started() {
		// timezone UTC 셋팅
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml";
//			+ "classpath:application.yml,"
//			+ "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(GoGetterApplication.class)
				.properties(APPLICATION_LOCATIONS)
						.run(args);
	}

}
