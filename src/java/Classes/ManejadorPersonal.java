/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonal {
    
    private HashMap<Integer,HashMap<Integer,Personal>> personal;//hashmap idTipoPersoanal -- 0-Cadetes,1-PSubalterno,2-Oficiales,3-Profesores
    
    private ManejadorPersonal() {
        ManejadorPersonalBD mp = new ManejadorPersonalBD();
        personal= mp.obtenerPersonalEM();
    }
    
    public static ManejadorPersonal getInstance() {
        return ManejadorPersonalHolder.INSTANCE;
    }
    
    private static class ManejadorPersonalHolder {

        private static final ManejadorPersonal INSTANCE = new ManejadorPersonal();
    }
    
    public HashMap<Integer,Personal> obtenerCadetes(){
        return personal.get(1);
    }
    public HashMap<Integer,Personal> obtenerPersonalSubalterno(){
        return personal.get(2);
    }
    public HashMap<Integer,Personal> obtenerOficiales(){
        return personal.get(3);
    }
    public HashMap<Integer,Personal> obtenerProfesores(){
        return personal.get(4);
    }
    
}
