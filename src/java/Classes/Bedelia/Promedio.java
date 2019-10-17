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
    private double nota;
    static private int mes;

    public Promedio(double nota, int mes) {
        this.nota = nota;
        this.mes = mes;
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
    
    
}
