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
public class Grupo {
    private CursoBedelia cusoBedelia;
    private int anio;
    private String nombre;
    private HashMap<Integer,Cadete> alumnos; 
    
    public Grupo(CursoBedelia cusoBedelia, int anio, String nombre,HashMap<Integer,Cadete> alumnos) {
        this.cusoBedelia = cusoBedelia;
        this.anio = anio;
        this.nombre = nombre;
        this.alumnos = alumnos;
    }

    public CursoBedelia getCusoBedelia() {
        return cusoBedelia;
    }

    public int getAnio() {
        return anio;
    }

    public String getNombre() {
        return nombre;
    }

    public HashMap<Integer, Cadete> getAlumnos() {
        return alumnos;
    }
}
