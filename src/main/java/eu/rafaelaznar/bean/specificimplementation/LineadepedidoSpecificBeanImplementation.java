/*
 * Copyright (c) 2017 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * generic-carrito-server: Helps you to develop easily AJAX web applications 
 *               by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/generic-carrito-server
 * 
 * generic-carrito-server is distributed under the MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.rafaelaznar.bean.specificimplementation;

import com.google.gson.annotations.Expose;
import eu.rafaelaznar.bean.genericimplementation.TableGenericBeanImplementation;
import eu.rafaelaznar.dao.specificimplementation.TipousuarioSpecificDaoImplementation;
import eu.rafaelaznar.helper.EncodingUtilHelper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import eu.rafaelaznar.bean.publicinterface.GenericBeanInterface;
import eu.rafaelaznar.dao.specificimplementation.PedidoSpecificDaoImplementation;
import eu.rafaelaznar.dao.specificimplementation.ProductoSpecificDaoImplementation;
import java.sql.Date;

public class LineadepedidoSpecificBeanImplementation extends TableGenericBeanImplementation {

    @Expose
    private int cantidad;
    
    @Expose
    private Boolean tiene_iva;
          
    @Expose(serialize = false)
    private Integer id_pedido = 0;
    @Expose(deserialize = false)
    private PedidoSpecificBeanImplementation obj_pedido = null;
    
    @Expose(serialize = false)
    private Integer id_producto = 0;
    @Expose(deserialize = false)
    private ProductoSpecificBeanImplementation obj_producto = null;
    
    

    public LineadepedidoSpecificBeanImplementation() {

    }

    public LineadepedidoSpecificBeanImplementation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public PedidoSpecificBeanImplementation getObj_pedido() {
        return obj_pedido;
    }

    public void setObj_pedido(PedidoSpecificBeanImplementation obj_pedido) {
        this.obj_pedido = obj_pedido;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public ProductoSpecificBeanImplementation getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoSpecificBeanImplementation obj_producto) {
        this.obj_producto = obj_producto;
    }

    public Boolean getTiene_iva() {
        return tiene_iva;
    }

    public void setTiene_iva(Boolean tiene_iva) {
        this.tiene_iva = tiene_iva;
    }

    
    

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "cantidad,";        
        strColumns += "id_pedido,";
        strColumns += "tiene_iva,";
        strColumns += "id_producto";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += cantidad + ",";
        strColumns += id_pedido + ",";
        strColumns += tiene_iva + ",";
        strColumns += id_producto;
        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "cantidad=" + cantidad + ",";
        strPairs += "id_pedido=" + id_pedido + ",";
        strPairs += "tiene_iva=" + tiene_iva + ",";
        strPairs += "id_producto=" + id_producto;
        return strPairs;
    }

    @Override
    public GenericBeanInterface fill(ResultSet oResultSet, Connection oConnection, UsuarioSpecificBeanImplementation oPuserBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));        
        this.setId_pedido(oResultSet.getInt("id_pedido"));
        this.setTiene_iva(oResultSet.getBoolean("tiene_iva"));
        this.setId_producto(oResultSet.getInt("id_producto"));
        if (expand > 0) {
            PedidoSpecificBeanImplementation oPedidoBean = new PedidoSpecificBeanImplementation();
            PedidoSpecificDaoImplementation oPedidoDao = new PedidoSpecificDaoImplementation(oConnection, oPuserBean_security, null);
            oPedidoBean = (PedidoSpecificBeanImplementation) oPedidoDao.get(oResultSet.getInt("id_pedido"), expand - 1);
            this.setObj_pedido(oPedidoBean);
        } else {
            this.setId_pedido(oResultSet.getInt("id_pedido"));
        }

        if (expand > 0) {
            ProductoSpecificBeanImplementation oProductoBean = new ProductoSpecificBeanImplementation();
            ProductoSpecificDaoImplementation oProductoDao = new ProductoSpecificDaoImplementation(oConnection, oPuserBean_security, null);
            oProductoBean = (ProductoSpecificBeanImplementation) oProductoDao.get(oResultSet.getInt("id_producto"), expand - 1);
            this.setObj_producto(oProductoBean);
        } else {
            this.setId_pedido(oResultSet.getInt("id_producto"));
        }
        
        
        return this;
    }



}
