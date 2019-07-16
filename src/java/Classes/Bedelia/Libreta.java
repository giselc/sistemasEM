/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import Manejadores.ManejadorBedeliaBD;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

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
    private LinkedList<TemaTratado> temasTratados;

    public Libreta(int id,Materia materia, Grupo grupo, Profesor profesor, String salon, HashMap<Integer, LibretaIndividual> libretasIndividuales, LinkedList<TemaTratado> temasTratados) {
        this.id=id;
        this.materia = materia;
        this.grupo = grupo;
        this.profesor = profesor;
        this.salon = salon;
        this.libretasIndividuales = libretasIndividuales;
        this.temasTratados = temasTratados;
    }

    public LinkedList<TemaTratado> getTemasTratados() {
        return temasTratados;
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

    public int agregarTemaTratado(String fecha, String texto) {
        Manejadores.ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        int idTema= mb.agregarTemaTratado(fecha,texto,this.id);
        if(idTema>0){
            if(temasTratados.isEmpty()){
                temasTratados.add(new TemaTratado(idTema, fecha, texto));
                return idTema;
            }
            else{
                Iterator it= temasTratados.iterator();
                TemaTratado tt;
                int i=0;
                while (it.hasNext()){
                    tt=(TemaTratado)it.next();
                    if(tt.getFecha().compareTo(fecha)<0){
                        temasTratados.add(i, new TemaTratado(idTema, fecha, texto));
                        return idTema;
                    }
                    i++;
                }
                temasTratados.addLast(new TemaTratado(idTema, fecha, texto));
                return idTema;
            }
        }
        return idTema;
    }

    public boolean elimTemaTratado(Integer idTema) {
        Manejadores.ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.elimTemaTratado(idTema)){
            if(temasTratados.isEmpty()){
                return true;
            }
            else{
                Iterator it= temasTratados.iterator();
                TemaTratado tt;
                while (it.hasNext()){
                    tt=(TemaTratado)it.next();
                    if(tt.getId()==idTema){
                        it.remove();
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
