/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import Classes.Cadete;

/**
 *
 * @author Gisel
 */
public class Notificacion {
    private final int id;
    private final Libreta libreta;
    private final Cadete cadete;
    private final Falta falta;
    private final Sancion sancion;
    private int estado; //1 nuevo - 2 leido
    private String fecha;

    public Notificacion(int id, Libreta libreta, Cadete cadete, Falta falta, Sancion sancion, int estado, String fecha) {
        this.id = id;
        this.libreta = libreta;
        this.cadete = cadete;
        this.falta = falta;
        this.sancion = sancion;
        this.estado=estado;
        this.fecha=fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public Libreta getLibreta() {
        return libreta;
    }

    public Falta getFalta() {
        return falta;
    }

    public Sancion getSancion() {
        return sancion;
    }

    public int getEstado() {
        return estado;
    }

    public Cadete getCadete() {
        return cadete;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
