package music.musicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("music.musicapp")
@EnableScheduling
public class InvestmentPortfolioApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvestmentPortfolioApplication.class, args);
	}
}