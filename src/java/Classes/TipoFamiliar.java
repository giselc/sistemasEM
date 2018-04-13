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

//SE CREARON ESTA CLASE DEBIDO A QUE CADA TIPO ES TRAIDO DE LA BASE DE DATOS, SI LO HACIAMOS A TRAVES DE UN ENUM 
// Y LUEGO QUERIAMOS AGREGAR UN NUEVO "TIPO DE FAMILIAR" HAY QUE GENERAR UN NUEVO .JAR
public class TipoFamiliar extends Tipo{ 

    public TipoFamiliar(int id, String descripcion) {
        super(id,descripcion);
    }
}
