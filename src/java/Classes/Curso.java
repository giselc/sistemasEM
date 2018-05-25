/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Gisel
 */
public class Curso extends Tipo{
    private String abreviacion;
    public Curso(int id, String descripcion, String abreviacion) {
        super(id, descripcion);
        this.abreviacion=abreviacion;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }
    
}
