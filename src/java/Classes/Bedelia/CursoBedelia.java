/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Gisel
 */
public class CursoBedelia {

    private int id;
    private String Codigo;
    private String Nombre;
    private int anioCurricular;
    private boolean Jefatura; //1-JE, 2-JCC
    private HashMap<Integer,Materia> materias; 
    private LinkedList<Grupo> grupos; 
    private boolean activo;

    public CursoBedelia(int id, String Codigo, String Nombre, int anioCurricular, boolean Jefatura, boolean activo) {
        this.id = id;
        this.Codigo = Codigo;
        this.Nombre = Nombre;
        this.anioCurricular = anioCurricular;
        this.Jefatura = Jefatura;
        this.activo= activo;
    }

    

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getAnioCurricular() {
        return anioCurricular;
    }

    public boolean isJefatura() {
        return Jefatura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setAnioCurricular(int anioCurricular) {
        this.anioCurricular = anioCurricular;
    }

    public void setJefatura(boolean Jefatura) {
        this.Jefatura = Jefatura;
    }

    public HashMap<Integer, Materia> getMaterias() {
        return materias;
    }

    public LinkedList<Grupo> getGrupos() {
        return grupos;
    }

    public void setMaterias(HashMap<Integer, Materia> materias) {
        this.materias = materias;
    }

    public void setGrupos(LinkedList<Grupo> grupos) {
        this.grupos = grupos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
}
