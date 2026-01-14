package abu.lets_play.Security;

import org.springframework.stereotype.Component;

@Component
public class InputSanitizer {
    
    public String sanitize(String input) {
        if (input == null) return null;
        return input.replaceAll("[{}$]", "");
    }
}
