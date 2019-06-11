/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

/**
 *
 * @author Gisel
 */
public class Falta {
    private int id;
    private String fecha;
    private int canthoras;
    private String observaciones;
    private int codigoMotivo; //F1,F2,F3
    private int estado; //1 enviado a bedelia, 0 leido por bedelia

    public Falta(int id, String fecha, int canthoras, int codigoMotivo,String observaciones, int estado) {
        this.id = id;
        this.fecha = fecha;
        this.canthoras = canthoras;
        this.codigoMotivo = codigoMotivo;
        this.estado = estado;
        this.observaciones=observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCanthoras() {
        return canthoras;
    }

    public void setCanthoras(int canthoras) {
        this.canthoras = canthoras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoMotivo() {
        return codigoMotivo;
    }

    public void setCodigoMotivo(int codigoMotivo) {
        this.codigoMotivo = codigoMotivo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
}
