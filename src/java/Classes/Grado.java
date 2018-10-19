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
public class Grado extends Tipo{
    private String abreviacion;
    private TipoPersonal idTipoPersonal;
    public Grado(int id, String descripcion, String abreviacion, TipoPersonal idTipoPersonal) {
        super(id,descripcion);
        this.abreviacion = abreviacion;
        this.idTipoPersonal = idTipoPersonal;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public TipoPersonal getIdTipoPersonal() {
        return idTipoPersonal;
    }
    
}
