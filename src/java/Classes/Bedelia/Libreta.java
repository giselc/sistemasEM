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
    private Materia materia;
    private Grupo grupo;
    private Profesor profesor;
    private int salon;
    private HashMap<Integer,LibretaIndividual> libretasIndividuales;

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

    public int getSalon() {
        return salon;
    }

    public void setSalon(int salon) {
        this.salon = salon;
    }

    public HashMap<Integer, LibretaIndividual> getLibretasIndividuales() {
        return libretasIndividuales;
    }

    public void setLibretasIndividuales(HashMap<Integer, LibretaIndividual> libretasIndividuales) {
        this.libretasIndividuales = libretasIndividuales;
    }
    
}
