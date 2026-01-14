package abu.lets_play.Model.dto;

import jakarta.validation.constraints.NotBlank;

public class UserSignIn {

    @NotBlank(message = "Email is required for user authentication")
    private String email;
    
    @NotBlank(message = "Password is required for user authentication")
    private String password;

    public UserSignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
