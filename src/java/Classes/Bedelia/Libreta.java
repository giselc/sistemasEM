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
public class Libreta {
    private int id;
    private Materia materia;
    private Grupo grupo;
    private Profesor profesor;
    private String salon;
    private HashMap<Integer,LibretaIndividual> libretasIndividuales;

    public Libreta(int id,Materia materia, Grupo grupo, Profesor profesor, String salon, HashMap<Integer, LibretaIndividual> libretasIndividuales) {
        this.id=id;
        this.materia = materia;
        this.grupo = grupo;
        this.profesor = profesor;
        this.salon = salon;
        this.libretasIndividuales = libretasIndividuales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public HashMap<Integer, LibretaIndividual> getLibretasIndividuales() {
        return libretasIndividuales;
    }

    public void setLibretasIndividuales(HashMap<Integer, LibretaIndividual> libretasIndividuales) {
        this.libretasIndividuales = libretasIndividuales;
    }
}
