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
public class Arma extends Tipo{
    private final String abreviacion;
    public Arma(int id, String descripcion, String abreviacion) {
        super(id, descripcion);
        this.abreviacion=abreviacion;
    }

    public String getAbreviacion() {
        return abreviacion;
    }
    
}
