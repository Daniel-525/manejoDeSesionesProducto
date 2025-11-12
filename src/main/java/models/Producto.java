package models;

public class Producto {
    private Long id;
    private String nombre;
    private String tipo;
    private Integer precio;

    public Producto(Long id, String nombre, String tipo, Integer precio) {
        this.id=id; this.nombre=nombre; this.tipo=tipo; this.precio=precio;
    }
    public Long getId(){return id;}
    public String getNombre(){return nombre;}
    public String getTipo(){return tipo;}
    public Integer getPrecio(){return precio;}
}
