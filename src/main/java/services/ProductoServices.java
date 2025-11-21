package services;
/**
 * Interfaz para servicios de productos
 * Define el contrato para operaciones relacionadas con productos
 * Autor: ITQ
 */

import models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoServices {
    List<Producto> listar();
    Optional<Producto> porId(Long id);
}
