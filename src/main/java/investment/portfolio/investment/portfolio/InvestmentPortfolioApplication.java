package investment.portfolio.investment.portfolio;

import investment.portfolio.investment.portfolio.auth.AuthenticationService;
import investment.portfolio.investment.portfolio.dto.RegisterRequest;
import investment.portfolio.investment.portfolio.model.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class InvestmentPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentPortfolioApplication.class, args);
	}
}

