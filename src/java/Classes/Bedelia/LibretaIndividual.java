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
class LibretaIndividual {
    private Libreta libreta;
    private Cadete alumno;
    HashMap<Integer, Falta> faltas;
    HashMap<Integer, Nota> notas;
    HashMap<Integer,Promedio> promedios;
    HashMap<Integer,Sanciones> sanciones;

    public LibretaIndividual(Libreta libreta, Cadete alumno, HashMap<Integer, Falta> faltas, HashMap<Integer, Nota> notas, HashMap<Integer, Promedio> promedios, HashMap<Integer, Sanciones> sanciones) {
        this.libreta = libreta;
        this.alumno = alumno;
        this.faltas = faltas;
        this.notas = notas;
        this.promedios = promedios;
        this.sanciones = sanciones;
    }
    
    public Libreta getLibreta() {
        return libreta;
    }

    public void setLibreta(Libreta libreta) {
        this.libreta = libreta;
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

    public HashMap<Integer, Sanciones> getSanciones() {
        return sanciones;
    }

    public void setSanciones(HashMap<Integer, Sanciones> sanciones) {
        this.sanciones = sanciones;
    }
    
    
    
}
