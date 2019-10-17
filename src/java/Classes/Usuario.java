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
    private final int id;
    private final String nombre;
    private final String nombreMostrar;
    private final boolean admin;
    private final TipoPersonal permisosPersonal;
    private final TipoDescuento permisosDescuento; 
    private final boolean notas;
    private final boolean habilitacion;
    private final boolean profesor;
    private final int ciProfesor;
    private boolean cambiarcontra;
    
    public Usuario(int id, String nombre, String nombreMostrar, boolean admin, TipoPersonal permisosPersonal, TipoDescuento tipoDescuento, boolean notas, boolean habilitacion,boolean profesor,int ciProfesor,boolean cambiarContra) {
        this.id = id;
        this.nombre = nombre;
        this.nombreMostrar = nombreMostrar;
        this.admin = admin;
        this.permisosPersonal = permisosPersonal;
        this.permisosDescuento = tipoDescuento;
        this.notas = notas;
        this.habilitacion = habilitacion;
        this.profesor = profesor;
        this.ciProfesor= ciProfesor;
        this.cambiarcontra = cambiarContra;
    }

    public boolean isCambiarcontra() {
        return cambiarcontra;
    }

    public void setCambiarcontra(boolean cambiarcontra) {
        this.cambiarcontra = cambiarcontra;
    }

    public int getCiProfesor() {
        return ciProfesor;
    }

    public boolean isProfesor() {
        return profesor;
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
