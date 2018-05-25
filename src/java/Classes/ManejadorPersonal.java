/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonal {
    
    private HashMap<Integer,ArrayList<Personal>> personal;//hashmap idTipoPersoanal -- 0-Cadetes,1-PSubalterno,2-Oficiales,3-Profesores
    
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
    
    public ArrayList<Personal> obtenerCadetes(){
        return personal.get(1);
    }
    public ArrayList<Personal> obtenerPersonalSubalterno(){
        return personal.get(2);
    }
    public ArrayList<Personal> obtenerOficiales(){
        return personal.get(3);
    }
    public ArrayList<Personal> obtenerProfesores(){
        return personal.get(4);
    }
    public Personal getPersonal(int ci, int tipoPersonal){
        boolean encontre=false;
        int i=0;
        int size= personal.get(tipoPersonal).size();
        while(!encontre && i<size){
            if(personal.get(tipoPersonal).get(i).getCi()==ci){
                return personal.get(tipoPersonal).get(i);
            }
            else{
                i++;
            }
        }
        return null;
    }
}
