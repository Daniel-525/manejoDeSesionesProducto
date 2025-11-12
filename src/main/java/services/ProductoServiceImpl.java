package services;


import models.Producto;

import java.util.Arrays;
import java.util.List;

public class ProductoServiceImpl implements services.ProductoService {
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Teclado", "Periférico", 25),
                new Producto(2L, "Mouse", "Periférico", 15),
                new Producto(3L, "Monitor", "Pantalla", 120)
        );
    }
}
