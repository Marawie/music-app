package investment.portfolio.investment.portfolio.dto;


import investment.portfolio.investment.portfolio.model.user.Role;
import investment.portfolio.investment.portfolio.model.user.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private Sex sex;
}