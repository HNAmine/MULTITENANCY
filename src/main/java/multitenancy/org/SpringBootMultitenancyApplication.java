package multitenancy.org;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.yaml.snakeyaml.Yaml;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringBootMultitenancyApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultitenancyApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
   	 Map<String, Object> data = new HashMap<String, Object>();
	   data.put("name", "Silenthand Olleander");
	   data.put("race", "Human");
	   data.put("traits", new String[] { "ONE_HAND", "ONE_EYE" });

	   Yaml yaml = new Yaml();
	   FileWriter writer = new FileWriter("application.yml");
	   yaml.dump(data, writer);
	}
}
