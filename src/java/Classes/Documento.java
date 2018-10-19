/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Blob;
import java.sql.Date;

/**
 *
 * @author Gisel
 */

public class Documento {
    private int id;
    private TipoDocumento tipo;
    private String nombre;
    
    
    public Documento(int id, TipoDocumento tipo,String nombre) {
        this.id = id;
        this.tipo = tipo;
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }


    public int getId() {
        return id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    
    
    
}
