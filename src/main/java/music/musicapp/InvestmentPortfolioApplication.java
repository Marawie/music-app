package music.musicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("music.musicapp")
public class InvestmentPortfolioApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvestmentPortfolioApplication.class, args);

	}
}
