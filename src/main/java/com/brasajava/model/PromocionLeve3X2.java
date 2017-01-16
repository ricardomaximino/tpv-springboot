/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brasajava.model;

import java.math.BigDecimal;
import javax.persistence.Entity;

/**
 *
 * @author Ricardo
 */
@Entity
public class PromocionLeve3X2 extends Promocion{

    @Override
    public BigDecimal aplica(Producto producto, int cantidad) {
       return null;
    }
    
}
