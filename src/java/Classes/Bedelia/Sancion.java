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
public class Sancion {
    private int id;
    private int tipo; //1-M,2-R;
    private int minutosTardes; //si tipo==R
    private String causa;
    private String fecha;
    private int estado; //1 enviado a bedelia, 0 leido por bedelia

    public Sancion(int id, int tipo, int minutosTardes, String causa, String fecha, int estado) {
        this.id = id;
        this.tipo = tipo;
        this.minutosTardes = minutosTardes;
        this.causa = causa;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getMinutosTardes() {
        return minutosTardes;
    }

    public void setMinutosTardes(int minutosTardes) {
        this.minutosTardes = minutosTardes;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
