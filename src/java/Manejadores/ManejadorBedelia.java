/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Bedelia.CursoBedelia;
import Classes.Bedelia.Libreta;
import Classes.Bedelia.Materia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Gisel
 */
public class ManejadorBedelia {
    private HashMap<Integer, CursoBedelia> cursos;
    private HashMap<Integer, Materia> materias;
    private HashMap<Integer, Libreta> libretas;
    
    private ManejadorBedelia() {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        materias = mb.obtenerMaterias();
        cursos = mb.obtenerCursos(materias);
        libretas = mb.obtenerLibretas(materias,cursos);
    }
    
    public static ManejadorBedelia getInstance() {
        return ManejadorBedeliaHolder.INSTANCE;
    }
    
    private static class ManejadorBedeliaHolder {

        private static final ManejadorBedelia INSTANCE = new ManejadorBedelia();
    }

    public HashMap<Integer, CursoBedelia> getCursos() {
        return cursos;
    }
     public CursoBedelia getCurso(int id) {
        return cursos.get(id);
    }
    public HashMap<Integer, Materia> getMaterias() {
        return materias;
    }
    
    public boolean agregarCurso(CursoBedelia cb){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.agregarCurso(cb)){
            cursos.put(cb.getId(), cb);
            return true;
        };
        return false;
    }
    private int sePuedeModificarEliminarCurso(int id){
        CursoBedelia c = cursos.get(id);
        if(c.getMaterias().size()>0){
            return 1; //no se puede eliminar un curso que tenga materias asociadas
        }
        if(!this.asociacionCursoLibreta(id)){
            return 2; //no se puede eliminar un curso que tenga asociadas libretas
        }
        return 0; //se puede eliminar
    }
    public int modificarCurso(CursoBedelia cb){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        int i = sePuedeModificarEliminarCurso(cb.getId());
        if(i!=0){
            return i; //no se puede modificar un curso que tenga asociadas materias o libretas 
        }
        if(mb.modificarCurso(cb)){
            CursoBedelia c = cursos.get(cb.getId());
            c.setAnioCurricular(cb.getAnioCurricular());
            c.setCodigo(cb.getCodigo());
            c.setJefatura(cb.isJefatura());
            c.setNombre(cb.getNombre());
            return 0;
        };
        return -1;//error BD
    }
    public int eliminarCurso(int id){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        CursoBedelia c = cursos.get(id);
        int i = sePuedeModificarEliminarCurso(id);
        if(i!=0){
            return i; //no se puede eliminar un curso que tenga asociadas materias o libretas 
        }
        if(mb.eliminarCurso(id)){
            cursos.remove(id);
            return 0;
        }
        return -1; //error BD
    }
    
    //retorna true si hay alguna libreta asociada al curso idCurso
    public boolean asociacionCursoLibreta(int idCurso){
        Libreta l;
        Iterator it =libretas.values().iterator();
        while(it.hasNext()){
            l=(Libreta)it.next();
            if(l.getGrupo().getCusoBedelia().getId()==idCurso){
                return true;
            }
        }
        return false;
    }

}
