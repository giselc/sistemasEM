/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Gisel
 */
public class Usuario {
    private int id;
    private String nombre;
    private String nombreMostrar;
    private boolean admin;
    private boolean s1;
    private boolean descuentos;
    private TipoDescuento tipoDescuento; 
    private boolean notas;
    private boolean habilitacion;

    public Usuario(int id, String nombre, String nombreMostrar, boolean admin, boolean s1, boolean descuentos, TipoDescuento tipoDescuento, boolean notas, boolean habilitacion) {
        this.id = id;
        this.nombre = nombre;
        this.nombreMostrar = nombreMostrar;
        this.admin = admin;
        this.s1 = s1;
        this.descuentos = descuentos;
        this.tipoDescuento = tipoDescuento;
        this.notas = notas;
        this.habilitacion = habilitacion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isS1() {
        return s1;
    }

    public boolean isDescuentos() {
        return descuentos;
    }

    public TipoDescuento getTipoDescuento() {
        return tipoDescuento;
    }

    public boolean isNotas() {
        return notas;
    }

    public boolean isHabilitacion() {
        return habilitacion;
    }
    
    
    
    

    
}
