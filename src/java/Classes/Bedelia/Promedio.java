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
public class Promedio {
    private int id;
    private int tipoPromedio; //1-mensual, 2-semestral, 3-anual
    private double nota;
    private int mes;
    private String juicio;

    public Promedio(int id, int tipoPromedio, double nota, int mes, String juicio) {
        this.id = id;
        this.tipoPromedio = tipoPromedio;
        this.nota = nota;
        this.mes = mes;
        this.juicio= juicio;
    }

    public String getJuicio() {
        return juicio;
    }

    public void setJuicio(String juicio) {
        this.juicio = juicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoPromedio() {
        return tipoPromedio;
    }

    public void setTipoPromedio(int tipoPromedio) {
        this.tipoPromedio = tipoPromedio;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
    
    
}
