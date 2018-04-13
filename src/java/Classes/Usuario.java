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
    private TipoPersonal permisosPersonal;
    private TipoDescuento permisosDescuento; 
    private boolean notas;
    private boolean habilitacion;

    public Usuario(int id, String nombre, String nombreMostrar, boolean admin, TipoPersonal permisosPersonal, TipoDescuento tipoDescuento, boolean notas, boolean habilitacion) {
        this.id = id;
        this.nombre = nombre;
        this.nombreMostrar = nombreMostrar;
        this.admin = admin;
        this.permisosPersonal = permisosPersonal;
        this.permisosDescuento = tipoDescuento;
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



    public TipoPersonal getPermisosPersonal() {
        return permisosPersonal;
    }

    public TipoDescuento getPermisosDescuento() {
        return permisosDescuento;
    }


    public boolean isNotas() {
        return notas;
    }

    public boolean isHabilitacion() {
        return habilitacion;
    }
    
    
    
    

    
}
