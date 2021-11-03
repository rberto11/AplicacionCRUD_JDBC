package models;

import java.util.Date;

public class Pedidos {

    private int id;
    private String nombre_cliente;
    private Date fecha;
    private Boolean estado;
    private Carta  productos;

    public Pedidos() {
    }

    public Pedidos(int id, String nombre_cliente, Date fecha, Boolean estado, Carta productos) {
        this.id = id;
        this.nombre_cliente = nombre_cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Carta getProductos() {
        return productos;
    }

    public void setProductos(Carta productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "id=" + id +
                ", nombre_cliente='" + nombre_cliente + '\'' +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", productos=" + productos +
                '}';
    }
}
