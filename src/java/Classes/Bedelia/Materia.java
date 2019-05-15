/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class Materia {
    private int id;
    private String nombre;
    private final String codigo;
    private boolean semestral;
    private int semestre;
    private boolean secundaria;
    private double coeficiente;
    private boolean activo;

    public Materia(int id, String nombre, String codigo, boolean semestral, int semestre, boolean secundaria, double coeficiente, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.semestral = semestral;
        this.semestre = semestre;
        this.secundaria = secundaria;
        this.coeficiente = coeficiente;
        this.activo=activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isSemestral() {
        return semestral;
    }

    public int getSemestre() {
        return semestre;
    }

    public boolean isSecundaria() {
        return secundaria;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSemestral(boolean semestral) {
        this.semestral = semestral;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setSecundaria(boolean secundaria) {
        this.secundaria = secundaria;
    }

    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }
    
    
    
}
