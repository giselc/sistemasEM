/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Bedelia.Falta;
import Classes.Bedelia.Sancion;

/**
 *
 * @author Gisel
 */
public class FaltaSancion {
    private Falta falta;
    private Sancion sancion;

    public FaltaSancion(Falta f, Sancion s) {
        this.falta = f;
        this.sancion = s;
    }

    public Falta getFalta() {
        return falta;
    }

    public void setFalta(Falta falta) {
        this.falta = falta;
    }

    public Sancion getSancion() {
        return sancion;
    }

    public void setSancion(Sancion sancion) {
        this.sancion = sancion;
    }
    
}
