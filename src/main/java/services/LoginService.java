package services;
/*
 * Interfaz para servicios de autenticación
 * Define el contrato para obtener el nombre de usuario
 * Autor: ITQ
 */

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface LoginService {
    Optional<String> getUsername(HttpServletRequest request);
}
