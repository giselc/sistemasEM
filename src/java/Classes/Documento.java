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
    private Personal p;
    private TipoDocumento tipo;
    private String nombre;
    
    
    public Documento(int id, Personal p, TipoDocumento tipo,String nombre) {
        this.id = id;
        this.tipo = tipo;
        this.p = p;
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    
    public Personal getP() {
        return p;
    }

    public int getId() {
        return id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    
    
    
}
