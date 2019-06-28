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
    private final RecordFalta falta;
    private final RecordSancion sancion;
    private int estado; //1 nuevo - 2 leido
    private String fecha;
    private boolean eliminado;

    public Notificacion(int id, Libreta libreta, Cadete cadete, RecordFalta falta, RecordSancion sancion, int estado, String fecha,boolean eliminado) {
        this.id = id;
        this.libreta = libreta;
        this.cadete = cadete;
        this.falta = falta;
        this.sancion = sancion;
        this.estado=estado;
        this.fecha=fecha;
        this.eliminado=eliminado;
    }

    public boolean isEliminado() {
        return eliminado;
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

    public RecordFalta getFalta() {
        return falta;
    }

    public RecordSancion getSancion() {
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
