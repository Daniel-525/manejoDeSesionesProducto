package services;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginServiceSessionImpl implements services.LoginService {

    private static final String USER = "admin";
    private static final String PASS = "12345";

    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return Optional.empty();
        String username = (String) session.getAttribute("username");
        return username != null ? Optional.of(username) : Optional.empty();
    }

    @Override
    public boolean autenticar(String username, String password) {
        return USER.equals(username) && PASS.equals(password);
    }
}
