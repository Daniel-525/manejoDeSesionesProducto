package services;

import models.Producto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductosServicesImpl implements ProductoServices {

    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(6L, "Impresora Láser", "Tecnología", 220.00, 60,
                        "Impresora láser multifuncional con WiFi", LocalDate.of(2029, 7, 15), 1,
                        LocalDate.of(2025, 2, 10)),

                new Producto(7L, "Escritorio Premium", "Oficina", 350.00, 20,
                        "Escritorio de madera maciza con acabado premium", LocalDate.of(2032, 3, 1), 1,
                        LocalDate.of(2025, 4, 5)),

                new Producto(8L, "Audífonos Bluetooth", "Electrónica", 75.99, 150,
                        "Audífonos inalámbricos con cancelación de ruido", LocalDate.of(2028, 11, 25), 1,
                        LocalDate.of(2025, 1, 30)),

                new Producto(9L, "Cámara Web Full HD", "Electrónica", 89.50, 80,
                        "Cámara web 1080p ideal para videollamadas", LocalDate.of(2029, 5, 12), 1,
                        LocalDate.of(2025, 3, 18)),

                new Producto(10L, "Router WiFi 6", "Tecnología", 145.00, 45,
                        "Router de alta velocidad compatible con WiFi 6", LocalDate.of(2029, 9, 3), 1,
                        LocalDate.of(2025, 2, 22))

        );
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream()
                .filter(p -> id != null && p.getIdProducto().equals(id))
                .findAny();
    }
}
