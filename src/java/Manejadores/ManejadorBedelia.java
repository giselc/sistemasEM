/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Bedelia.CursoBedelia;
import Classes.Bedelia.Grupo;
import Classes.Bedelia.Libreta;
import Classes.Bedelia.Materia;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Gisel
 */
public class ManejadorBedelia {
    private HashMap<Integer, CursoBedelia> cursos;
    private HashMap<Integer, Materia> materias;
    private HashMap<Integer, HashMap<Integer,Libreta>> libretas; //HashMap<ciProfesor,Hashmap<idLibreta,Libreta>>
    
    private ManejadorBedelia() {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        materias = mb.obtenerMaterias();
        cursos = mb.obtenerCursos(materias);
        libretas = mb.obtenerLibretas(materias,cursos);
    }
    
    public static ManejadorBedelia getInstance() {
        return ManejadorBedeliaHolder.INSTANCE;
    }

    public boolean asociarMateriasCurso(String[] idMaterias, String idCurso) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.asociarMateriasCurso(idMaterias,idCurso)){
            HashMap mateCurso= cursos.get(Integer.valueOf(idCurso)).getMaterias();
            for(String m: idMaterias){
               mateCurso.put(Integer.valueOf(m), materias.get(Integer.valueOf(m)));
            }
            return true;
        };
        return false;
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
    public boolean agregarMateria(Materia m,int idCurso){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.agregarMateria(m)){
            materias.put(m.getId(), m);
            if(idCurso!=0){
                asociarMateriaCurso(m.getId(), idCurso);
            }
            return true;
        };
        return false;
    }
    public boolean asociarMateriaCurso(int idMateria, int idCurso){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.asociarMateriaCurso(idMateria,idCurso)){
            cursos.get(idCurso).getMaterias().put(idMateria, materias.get(idMateria));
            return true;
        };
        return false;
    }
    public boolean desasociarMateriaCurso(int idMateria,int idCurso){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(cursoMateriaConLibretasAsociadas(idCurso, idMateria)){
            return false; //no se puede desasociar una materia de un curso que tenga libretas asociadas.
        }
        if(mb.desasociarMateriaCurso(idMateria,idCurso)){
            cursos.get(idCurso).getMaterias().remove(idMateria);
            return true;
        };
        return false;
    }
    public boolean modificarMateria(Materia m) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.modificarMateria(m)){
            Materia mat = materias.get(m.getId());
            mat.setCoeficiente(m.getCoeficiente());
            mat.setNombre(m.getNombre());
            mat.setSecundaria(m.isSecundaria());
            mat.setSemestre(m.getSemestre());
            mat.setSemestral(m.isSemestral());
            return true;
        }
        return false;//error BD
    }
    public boolean eliminarMateria(int idMateria){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(materiaConLibretasAsociadas(idMateria)){
            return false; //no se puede eliminar una materia que tenga libretas asociadas.
        }
        if(eliminarMateriaDeCursos(idMateria) && mb.eliminarMateria(idMateria)){
            materias.remove(idMateria);
            return true;
        };
        return false;
    }
    public boolean eliminarMateriaDeCursos(int idMateria){
        Iterator itCursos = cursos.values().iterator();
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(!mb.eliminarMateriaDeCursos(idMateria)){
            return false;
        }
        while(itCursos.hasNext()){
            ((CursoBedelia)itCursos.next()).getMaterias().remove(idMateria);
        }
        return true;
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
        if(this.cursoConLibretasAsociadas(id)){
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
            c.setJefatura(cb.isJefatura());
            c.setNombre(cb.getNombre());
            c.setActivo(cb.isActivo());
            return 0;
        }
        return -1;//error BD
    }
    public int eliminarCurso(int id){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        CursoBedelia c = cursos.get(id);
        int i = sePuedeModificarEliminarCurso(id);
        if(i!=0){
            return i; //no se puede eliminar un curso que tenga asociadas materias o libretas 
        }
        if(mb.eliminarCurso(c)){
            cursos.remove(id);
            return 0;
        }
        return -1; //error BD
    }
    public boolean materiaConLibretasAsociadas(int idMateria){
        Libreta l;
        HashMap<Integer,Libreta> lp;
        Iterator it =libretas.values().iterator();
        Iterator it1;
        while(it.hasNext()){
            lp=(HashMap<Integer,Libreta>)it.next();
            it1=lp.values().iterator();
            while(it1.hasNext()){
                l=(Libreta)it1.next();
                if(l.getMateria().getId()==idMateria){
                    return true;
                }
            }
        }
        return false;
    }
    
    //retorna true si hay alguna libreta de la materia idMateria asociada al curso idCurso
    public boolean cursoMateriaConLibretasAsociadas(int idCurso, int idMateria){
        Libreta l;
        HashMap<Integer,Libreta> lp;
        Iterator it =libretas.values().iterator();
        Iterator it1;
        while(it.hasNext()){
            lp=(HashMap<Integer,Libreta>)it.next();
            it1=lp.values().iterator();
            while(it1.hasNext()){
                l=(Libreta)it1.next();
                if(l.getGrupo().getCusoBedelia().getId()==idCurso && l.getMateria().getId()==idMateria){
                    return true;
                }
            }
        }
        return false;
    }
    //retorna true si hay alguna libreta asociada al curso idCurso
    public boolean cursoConLibretasAsociadas(int idCurso){
        Libreta l;
        HashMap<Integer,Libreta> lp;
        Iterator it =libretas.values().iterator();
        Iterator it1;
        while(it.hasNext()){
            lp=(HashMap<Integer,Libreta>)it.next();
            it1=lp.values().iterator();
            while(it1.hasNext()){
                l=(Libreta)it1.next();
                if(l.getGrupo().getCusoBedelia().getId()==idCurso){
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<Integer, HashMap<Integer,Libreta>> getLibretas() {
        return libretas;
    }
    
    public HashMap<Integer, CursoBedelia> getCursosMateria(int idMateria){
        HashMap<Integer, CursoBedelia> hcb= new HashMap<>();
        for(CursoBedelia cb: cursos.values()){
            if(cb.getMaterias().containsKey(idMateria)){
                hcb.put(cb.getId(), cb);
            }
        }
        return hcb;
    }
    
    public boolean agregarGrupoCurso(int idCurso, int anio, String nombre){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.agregarGrupoCurso(idCurso,anio,nombre)){
            CursoBedelia c = cursos.get(idCurso);
            Grupo g = new Grupo(c, anio, nombre, new HashMap<>());
            c.agregarGrupoEnOrden(g);
            return true;
        }
        return false;
    }
    
}
