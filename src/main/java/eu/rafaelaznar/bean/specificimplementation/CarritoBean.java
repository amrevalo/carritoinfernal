/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.rafaelaznar.bean.specificimplementation;

import com.google.gson.annotations.Expose;

/**
 *
 * @author alumno
 */
public class CarritoBean {
    
    private int cantidad;
    private ProductoSpecificBeanImplementation oProducto = null;

    public CarritoBean() {
    }

    
    
    
    
    
    public int getCantidad() {
        return cantidad;
    }

    public CarritoBean(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoSpecificBeanImplementation getoProducto() {
        return oProducto;
    }

    public void setoProducto(ProductoSpecificBeanImplementation oProducto) {
        this.oProducto = oProducto;
    }

    
    
    
    
    
}
