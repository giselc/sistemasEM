/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import Classes.Cadete;
import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class LibretaIndividual {
    private int idLibreta;
    private Cadete alumno;
    private double promedioAnual;
    private double notaFinal; //calculado en base al promedioAnual y nota de Examen
    HashMap<Integer, Falta> faltas;
    HashMap<Integer, Nota> notas;
    HashMap<Integer,Promedio> promedios;
    HashMap<Integer,Sancion> sanciones;

    public LibretaIndividual(int idLibreta, Cadete alumno, HashMap<Integer, Falta> faltas, HashMap<Integer, Nota> notas, HashMap<Integer, Promedio> promedios, HashMap<Integer, Sancion> sanciones, double promedioAnual, double notaFinal) {
        this.idLibreta = idLibreta;
        this.alumno = alumno;
        this.faltas = faltas;
        this.notas = notas;
        this.promedios = promedios;
        this.sanciones = sanciones;
        this.promedioAnual = promedioAnual;
        this.notaFinal = notaFinal;
    }

    public int getIdLibreta() {
        return idLibreta;
    }

    public void setIdLibreta(int idLibreta) {
        this.idLibreta = idLibreta;
    }

    public double getPromedioAnual() {
        return promedioAnual;
    }

    public void setPromedioAnual(double promedioAnual) {
        this.promedioAnual = promedioAnual;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }
    
    public int getLibreta() {
        return idLibreta;
    }

    public void setLibreta(int idLibreta) {
        this.idLibreta = idLibreta;
    }

    public Cadete getAlumno() {
        return alumno;
    }

    public void setAlumno(Cadete alumno) {
        this.alumno = alumno;
    }

    public HashMap<Integer, Falta> getFaltas() {
        return faltas;
    }

    public void setFaltas(HashMap<Integer, Falta> faltas) {
        this.faltas = faltas;
    }

    public HashMap<Integer, Nota> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<Integer, Nota> notas) {
        this.notas = notas;
    }

    public HashMap<Integer, Promedio> getPromedios() {
        return promedios;
    }

    public void setPromedios(HashMap<Integer, Promedio> promedios) {
        this.promedios = promedios;
    }

    public HashMap<Integer, Sancion> getSanciones() {
        return sanciones;
    }

    public void setSanciones(HashMap<Integer, Sancion> sanciones) {
        this.sanciones = sanciones;
    }
    
    
    
}
