package models;

import java.util.Objects;

public class ItemCarro {
    private int cantidad;
    private Producto producto;

    // Constructor de la clase
    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCarro itemCarro = (ItemCarro) o;
        // Solo comparamos por ID del producto, no por cantidad
        return Objects.equals(producto.getIdProducto(), itemCarro.producto.getIdProducto());
    }

    @Override
    public int hashCode() {
        // Solo usar el ID del producto
        return Objects.hash(producto.getIdProducto());
    }

    public double getSubTotal() {
        return cantidad * producto.getPrecio();
    }

    // Metodo para calcular el IVA del 15%
    public double getIva() {
        return getSubTotal() * 0.15;
    }

    // Metodo para obtener el total con IVA
    public double getTotalConIva() {
        return getSubTotal() + getIva();
    }
}
