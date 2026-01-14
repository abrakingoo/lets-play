package abu.lets_play.Model.dto;

import jakarta.validation.constraints.NotBlank;

public class UserSignUp {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String id;

    public UserSignUp(){}

    public UserSignUp(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {return name; }
    public String getEmail() {return email; }
    public String getPassword() {return password; }
    public void setId(String id) {this.id = id;}
    public String getId() {return id;}

    public  void setName(String name) {this.name = name;}
}
