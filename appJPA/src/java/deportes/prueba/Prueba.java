/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deportes.prueba;

import deportes.controlador.Categoria;
import deportes.dao.CategoriaJpaController;

/**
 *
 * @author lab01
 */
public class Prueba {
    public static void main(String[] args) {
        CategoriaJpaController cd= new CategoriaJpaController();
        Categoria c= new Categoria();
        c.setCategoria("Categoría de prueba");
        cd.create(c);
    }
    
}
