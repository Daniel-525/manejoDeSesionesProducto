package models;

import java.time.LocalDate;

/**
 * Clase modelo que representa un producto
 * Contiene información básica como id, nombre, tipo y precio
 * Autor: ITQ
 */

public class Producto {
    // Encapsulamos y declaramos las variables del objeto producto
    private Long idProducto;
    private String nombre;
    private Categoria categoria;
    private String tipo;
    private double precio;
    private int stock;
    private String descripcion;
    private LocalDate fechaElaboracion;
    private LocalDate fechaCaducidad;
    private int condicion;

    /*
     *
     * */
    public Producto(){}

    /*
     *
     * */
    public Producto(Long idProducto, String nombre, String tipo, double precio, int stock, String descripcion, LocalDate fechaCaducidad, int condicion, LocalDate fechaElaboracion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.fechaCaducidad = fechaCaducidad;
        this.condicion = condicion;
        this.fechaElaboracion = fechaElaboracion;
        Categoria categoria = new Categoria();
        categoria.setNombre(tipo);
    }

    /*
     *
     *  */
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(LocalDate fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }
}
