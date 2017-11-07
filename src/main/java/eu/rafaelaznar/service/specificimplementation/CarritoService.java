/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.rafaelaznar.service.specificimplementation;

import com.google.gson.Gson;
import eu.rafaelaznar.bean.ReplyBean;
import eu.rafaelaznar.bean.specificimplementation.CarritoBean;
import eu.rafaelaznar.bean.specificimplementation.LineadepedidoSpecificBeanImplementation;
import eu.rafaelaznar.bean.specificimplementation.PedidoSpecificBeanImplementation;
import eu.rafaelaznar.bean.specificimplementation.ProductoSpecificBeanImplementation;
import eu.rafaelaznar.bean.specificimplementation.UsuarioSpecificBeanImplementation;
import eu.rafaelaznar.connection.ConnectionInterface;
import eu.rafaelaznar.dao.specificimplementation.LineadepedidoSpecificDaoImplementation;
import eu.rafaelaznar.dao.specificimplementation.PedidoSpecificDaoImplementation;
import eu.rafaelaznar.dao.specificimplementation.ProductoSpecificDaoImplementation;
import eu.rafaelaznar.helper.AppConfigurationHelper;
import static eu.rafaelaznar.helper.AppConfigurationHelper.getJsonMsgDepth;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import static net.daw.helper.AppConfigurationHelper.getJsonMsgDepth;
import eu.rafaelaznar.helper.Log4jConfigurationHelper;

/**
 *
 * @author alumno
 */
public class CarritoService {

    //public static ArrayList<CarritoBean> alcarritoBean = new ArrayList<CarritoBean>();
    HttpServletRequest oRequest = null;

    public CarritoService(HttpServletRequest request) {
        oRequest = request;
    }

//    private Boolean checkPermission(String strMethodName) throws Exception {
//        PedidoBean oUsuarioBean = (PedidoBean) oRequest.getSession().getAttribute("user");
//        if (oUsuarioBean != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    public boolean findexist(int id, int cant) {
        boolean exist = false;
        String idtoremove = "";
        int elemento;
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        //String[] arrayresult;
        //ArrayList<String> arrayList = new ArrayList<String>();
        if(alcarritoBean !=null){
        for (int i = 0; i < alcarritoBean.size(); i++) {
            elemento = alcarritoBean.get(i).getoProducto().getId();
            if (id == elemento) {
                alcarritoBean.get(i).setCantidad(alcarritoBean.get(i).getCantidad() + cant);
                exist = true;
            }
        }
        }else{
            exist = false;
        }
        return exist;
    }

    public void findtoremove(int id) {
        String idtoremove = "";
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        int elemento;
        //String[] arrayresult;
        //ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i <= alcarritoBean.size(); i++) {
            elemento = alcarritoBean.get(i).getoProducto().getId();
            if (id == elemento) {
                alcarritoBean.remove(i);
            }
        }
    }

//    public void buyproduct() throws Exception {
//        Connection oConnection = null;
//        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
//        Date dfecha = new Date(454543543);
//
//        java.util.Date fechaActual = new java.util.Date();
//        oConnection = AppConfigurationHelper.getSourceConnection().newConnection();
//
//        PedidoDao oPedidoDao = new PedidoDao(oConnection);
//        PedidoBean oPedidoBean = new PedidoBean();
//        oPedidoBean.setId_usuario(1);
//        oPedidoBean.setFecha(dfecha);
//        int idpedido = oPedidoDao.set(oPedidoBean);
//
//        for (int i = 0; i <= alcarritoBean.size(); i++) {
//
//            int arestar = alcarritoBean.get(i).getCantidad();
//
//            // oProductoBean.setCantidad(oProductoBean.getCantidad()-cant);
//            ProductoDao oProductoDao = new ProductoDao(oConnection);
//            ProductoBean oProductoBean = new ProductoBean(alcarritoBean.get(i).getoProducto().getId());
//            //oProductoBean.setId(i+1);
//            oProductoBean = oProductoDao.get(oProductoBean, getJsonMsgDepth());
//            oProductoBean.setCantidad(oProductoBean.getCantidad() - arestar);
//            oProductoDao.set(oProductoBean);
//
//            LineadepedidoDao oLineadepedidoDao = new LineadepedidoDao(oConnection);
//            LineadepedidoBean oLineadepedidoBean = new LineadepedidoBean();
//            oLineadepedidoBean.setCantidad(arestar);
//            oLineadepedidoBean.setId_pedido(idpedido);
//            oLineadepedidoBean.setId_producto(oProductoBean.getId());
//            oLineadepedidoDao.set(oLineadepedidoBean);
//
//        }
//
//    }

    /*
    * http://127.0.0.1:8081/carrito-server/json?ob=carrito&op=add&id=1&cantidad=2
     */
    public ReplyBean add() throws Exception {
        int id = Integer.parseInt(oRequest.getParameter("id"));
        int cantidad = Integer.parseInt(oRequest.getParameter("cantidad"));
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        Connection oConnection = null;
//        JdbcImpl oJdbc = null;
        ReplyBean oReplyBean = null;

        try {

            String strJson = "";
            Gson oGson = new Gson();

            ProductoSpecificBeanImplementation oBean = new ProductoSpecificBeanImplementation(id);
            //ProductoDao oDao = new ProductoDao(oConnection);            
            CarritoBean oBean1 = new CarritoBean();
            if (findexist(id, cantidad) == false) {
                oBean1.setCantidad(cantidad);
                oBean1.setoProducto(oBean);
                alcarritoBean.add(oBean1);
                strJson = oGson.toJson(oBean1);
            } else {
                strJson = "\"Se ha añadido la cantidad de " + cantidad + " al producto de id " + id + "\"";
            }

            oReplyBean = new ReplyBean(200, strJson);

        } catch (Exception ex) {
            throw new Exception();
        } finally {
            if (oConnection != null) {
                oConnection.close();
            }
            if (AppConfigurationHelper.getSourceConnection() != null) {
                AppConfigurationHelper.getSourceConnection().disposeConnection();
            }

        }

        return oReplyBean;
    }

    public ReplyBean list() throws Exception {

        Connection oConnection = null;
//        JdbcImpl oJdbc = null;
        ReplyBean oReplyBean = null;
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        try {
            Gson oGson = new Gson();
            String strJson = oGson.toJson(alcarritoBean);
            oReplyBean = new ReplyBean(200, strJson);
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            if (oConnection != null) {
                oConnection.close();
            }
            if (AppConfigurationHelper.getSourceConnection() != null) {
                AppConfigurationHelper.getSourceConnection().disposeConnection();
            }

        }
        return oReplyBean;
    }

    public ReplyBean remove() throws Exception {
        int id = Integer.parseInt(oRequest.getParameter("id"));
        Connection oConnection = null;
//        JdbcImpl oJdbc = null;
        ReplyBean oReplyBean = null;
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        try {
            findtoremove(id);
            Gson oGson = new Gson();
            String strJson = oGson.toJson("Producto con id " + id + " ha sido borrado");
            oReplyBean = new ReplyBean(200, strJson);

        } catch (Exception ex) {
            throw new Exception();
        } finally {
            if (oConnection != null) {
                oConnection.close();
            }
            if (AppConfigurationHelper.getSourceConnection() != null) {
                AppConfigurationHelper.getSourceConnection().disposeConnection();
            }

        }
        return oReplyBean;
    }

    public ReplyBean empty() throws Exception {

        Connection oConnection = null;
//        JdbcImpl oJdbc = null;
        ReplyBean oReplyBean = null;
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        try {
            alcarritoBean.clear();
            Gson oGson = new Gson();
            String strJson = oGson.toJson("El carrito ha sido vaciado");
            oReplyBean = new ReplyBean(200, strJson);
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            if (oConnection != null) {
                oConnection.close();
            }
            if (AppConfigurationHelper.getSourceConnection() != null) {
                AppConfigurationHelper.getSourceConnection().disposeConnection();
            }

        }
        return oReplyBean;
    }

    public ReplyBean buy() throws Exception {

        Connection oConnection = null;
        ReplyBean oReplyBean = null;
        HttpSession oSession = oRequest.getSession();
        ArrayList<CarritoBean> alcarritoBean = (ArrayList) oRequest.getSession().getAttribute("carrito");
        Date dfecha = new Date(2017 / 11 / 01);
        ConnectionInterface oPooledConnection = null;
        //java.util.Date fechaActual = new java.util.Date();
        oConnection = AppConfigurationHelper.getSourceConnection().newConnection();
        try {
            //UsuarioDao oUsuariodao = new UsuarioDao(oConnection);
            oPooledConnection = AppConfigurationHelper.getSourceConnection();
            oConnection = oPooledConnection.newConnection();
            UsuarioSpecificBeanImplementation oUsuarioBean = (UsuarioSpecificBeanImplementation) oRequest.getSession().getAttribute("user");
            PedidoSpecificDaoImplementation oPedidoDao = new PedidoSpecificDaoImplementation(oConnection, (UsuarioSpecificBeanImplementation) oRequest.getSession().getAttribute("user"), null);
            //PedidoDao oPedidoDao = new PedidoDao(oConnection);
            PedidoSpecificBeanImplementation oPedidoBean = new PedidoSpecificBeanImplementation();
            oPedidoBean.setId_usuario(oUsuarioBean.getId());
            oPedidoBean.setFecha(dfecha);
            int idpedido = oPedidoDao.set(oPedidoBean);
            //ProductoDao oProductoDao = new ProductoDao(oConnection);
            for (int i = 0; i < alcarritoBean.size(); i++) {

                int arestar = alcarritoBean.get(i).getCantidad();

                // oProductoBean.setCantidad(oProductoBean.getCantidad()-cant);
                ProductoSpecificBeanImplementation oProductoBean = new ProductoSpecificBeanImplementation(alcarritoBean.get(i).getoProducto().getId());
                //oProductoBean.setId(i+1);
                ProductoSpecificDaoImplementation oProductoDao = new ProductoSpecificDaoImplementation(oConnection, (UsuarioSpecificBeanImplementation) oRequest.getSession().getAttribute("user"), null);

           //oProductoBean = oProductoDao.get(oProductoBean, getJsonMsgDepth());
                oProductoBean.setExistencias(oProductoBean.getExistencias() - arestar);
                oProductoDao.set(oProductoBean);

                LineadepedidoSpecificDaoImplementation oLineadepedidoDao = new LineadepedidoSpecificDaoImplementation(oConnection, (UsuarioSpecificBeanImplementation) oRequest.getSession().getAttribute("user"), null);
                //LineadepedidoDao oLineadepedidoDao = new LineadepedidoDao(oConnection);
                LineadepedidoSpecificBeanImplementation oLineadepedidoBean = new LineadepedidoSpecificBeanImplementation();
                //pasado a string el arestar
                oLineadepedidoBean.setCantidad(Integer.toString(arestar));
                oLineadepedidoBean.setId_pedido(idpedido);
                oLineadepedidoBean.setId_producto(oProductoBean.getId());
                oLineadepedidoDao.set(oLineadepedidoBean);
                
            }
            Gson oGson = new Gson();
            String strJson = oGson.toJson("Se ha comprado");
            oReplyBean = new ReplyBean(200, strJson);
            empty();
            
        } catch (Exception ex) {
            String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName();
            Log4jConfigurationHelper.errorLog(msg, ex);
            throw new Exception(msg, ex);
        } finally {
            if (oConnection != null) {
                oConnection.close();
            }
            if (AppConfigurationHelper.getSourceConnection() != null) {
                AppConfigurationHelper.getSourceConnection().disposeConnection();
            }
            
        }       
        return oReplyBean;
    }
////    //http://127.0.0.1:8081/conexion/json?ob=usuario&op=getpage&np=1&rpp=1
////    @Override
////    public ReplyBean getpage() throws Exception {
////        int np = Integer.parseInt(oRequest.getParameter("np"));
////        int rpp = Integer.parseInt(oRequest.getParameter("rpp"));
////        String strOrder = oRequest.getParameter("order");
////        String strFilter = oRequest.getParameter("filter");
////        LinkedHashMap<String, String> hmOrder = ParameterCook.getOrderParams(strOrder);
////        ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(strFilter);
////        Connection oConnection = null;
//////        JdbcImpl oJdbc = null;
////        ReplyBean oReplyBean = null;
////        ArrayList<PedidoBean> aloBean = null;
////        try {
//////            oJdbc = new JdbcImpl();
//////            oConnection = oJdbc.newConnection();
////            oConnection = AppConfigurationHelper.getSourceConnection().newConnection();
////            PedidoDao oDao = new PedidoDao(oConnection);
////            aloBean = oDao.getPage(rpp, np, hmOrder,alFilter);
////            Gson oGson = new Gson();
////            String strJson = oGson.toJson(aloBean);
////            oReplyBean = new ReplyBean(200, strJson);
////        } catch (Exception ex) {
////            throw new Exception();
////        } finally {
////            if (oConnection != null) {
////                oConnection.close();
////            }
////            if (AppConfigurationHelper.getSourceConnection() != null) {
////                AppConfigurationHelper.getSourceConnection().disposeConnection();
////            }
////        }
////        return oReplyBean;
////    }
////    // http://127.0.0.1:8081/conexion/json?ob=usuario&op=getcount
////    @Override
////    public ReplyBean getcount() throws Exception {
////        Long lResult;
////        Connection oConnection = null;
//////        JdbcImpl oJdbc = null;
////        ReplyBean oReplyBean = null;
////        String strFilter = oRequest.getParameter("filter");
////        ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(strFilter);
////        try {
//////            oJdbc = new JdbcImpl();
//////            oConnection = oJdbc.newConnection();
////            oConnection = AppConfigurationHelper.getSourceConnection().newConnection();
////            PedidoDao oDao = new PedidoDao(oConnection);
////            lResult = oDao.getCount(alFilter);
////            Gson oGson = new Gson();
////            String strJson = oGson.toJson(lResult);
////            oReplyBean = new ReplyBean(200, strJson);
////        } catch (Exception ex) {
////            throw new Exception();
////        } finally {
////            if (oConnection != null) {
////                oConnection.close();
////            }
////            if (AppConfigurationHelper.getSourceConnection() != null) {
////                AppConfigurationHelper.getSourceConnection().disposeConnection();
////            }
////        }
////        return oReplyBean;
////    }

    /*
    
     */
////    @Override
////    public ReplyBean set() throws Exception {
////        String jason = oRequest.getParameter("jason");
////        Connection oConnection = null;
//////        JdbcImpl oJdbc = null;
////        ReplyBean oReplyBean = null;
////        PedidoBean oBean = new PedidoBean();
////        Gson oGson = new Gson();
////        oBean = oGson.fromJson(jason, oBean.getClass());
////        int iResult = 0;
////        try {
//////            oJdbc = new JdbcImpl();
//////            oConnection = oJdbc.newConnection();
////            oConnection = AppConfigurationHelper.getSourceConnection().newConnection();
////            PedidoDao oDao = new PedidoDao(oConnection);
////            iResult = oDao.set(oBean);
////            String strJson = oGson.toJson(iResult);
////            oReplyBean = new ReplyBean(200, strJson);
////        } catch (Exception ex) {
////            throw new Exception();
////        } finally {
////            if (oConnection != null) {
////                oConnection.close();
////            }
////            if (AppConfigurationHelper.getSourceConnection() != null) {
////                AppConfigurationHelper.getSourceConnection().disposeConnection();
////            }
////        }
////        return oReplyBean;
////    }
////    @Override
////    public ReplyBean remove() throws Exception {
////        int id = Integer.parseInt(oRequest.getParameter("id"));
////        Boolean iResult = false;
////        Connection oConnection = null;
//////        JdbcImpl oJdbc = null;
////        ReplyBean oReplyBean = null;
////        try {
//////            oJdbc = new JdbcImpl();
//////            oConnection = oJdbc.newConnection();
////            oConnection = AppConfigurationHelper.getSourceConnection().newConnection();
////            PedidoDao oDao = new PedidoDao(oConnection);
////            iResult = oDao.remove(id);
////            Gson oGson = new Gson();
////            String strJson = oGson.toJson(iResult);
////            oReplyBean = new ReplyBean(200, strJson);
////        } catch (Exception ex) {
////            throw new Exception();
////        } finally {
////            if (oConnection != null) {
////                oConnection.close();
////            }
////            if (AppConfigurationHelper.getSourceConnection() != null) {
////                AppConfigurationHelper.getSourceConnection().disposeConnection();
////            }
////        }
////        return oReplyBean;
////    }
//    public ReplyBean getpagexusuario() throws Exception {
//        //if (this.checkPermission("getpage")) {
//            int np = Integer.parseInt(oRequest.getParameter("np"));
//            int rpp = Integer.parseInt(oRequest.getParameter("rpp"));
//            int id = Integer.parseInt(oRequest.getParameter("id"));
//            String strOrder = oRequest.getParameter("order");
//            String strFilter = oRequest.getParameter("filter");
//            LinkedHashMap<String, String> hmOrder = ParameterCook.getOrderParams(strOrder);
//            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(strFilter);
//            Connection oConnection = null;
//            ConnectionInterface oPooledConnection = null;
//            ReplyBean oReplyBean = null;
//            ArrayList<PedidoBean> aloBean = null;
//            try {
//                oPooledConnection = AppConfigurationHelper.getSourceConnection();
//                oConnection = oPooledConnection.newConnection();
//                PedidoDao oDao = new PedidoDao(oConnection);
//                aloBean = oDao.getPagexusuario(rpp, np, hmOrder, alFilter, id);
//                Gson oGson = AppConfigurationHelper.getGson();
//                String strJson = oGson.toJson(aloBean);
//                oReplyBean = new ReplyBean(200, strJson);
//            } catch (Exception ex) {
//                String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName();
//                Log4j.errorLog(msg, ex);
//                throw new Exception(msg, ex);
//            } finally {
//                if (oConnection != null) {
//                    oConnection.close();
//                }
//                if (oPooledConnection != null) {
//                    oPooledConnection.disposeConnection();
//                }
//            }
//            return oReplyBean;
//   //     } else {
//   //         return new ReplyBean(401, "Unauthorized");
//   //     }
//    }
////    /*
////    * http://127.0.0.1:8081/carrito-server/json?ob=getcountxtiposuario&op=getcount&id=1
////     */
////    public ReplyBean getcountxusuario() throws Exception {
//// //       if (this.checkPermission("getcount")) {
////            Long lResult;
////            Connection oConnection = null;
////            ConnectionInterface oPooledConnection = null;
////            ReplyBean oReplyBean = null;
////            String strFilter = oRequest.getParameter("filter");
////            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(strFilter);
////            int id = Integer.parseInt(oRequest.getParameter("id"));
////            try {
////                oPooledConnection = AppConfigurationHelper.getSourceConnection();
////                oConnection = oPooledConnection.newConnection();
////                PedidoDao oDao = new PedidoDao(oConnection);
////                lResult = oDao.getCountxusuario(alFilter, id);
////                Gson oGson = AppConfigurationHelper.getGson();
////                String strJson = oGson.toJson(lResult);
////                oReplyBean = new ReplyBean(200, strJson);
////            } catch (Exception ex) {
////                String msg = this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName();
////                Log4j.errorLog(msg, ex);
////                throw new Exception(msg, ex);
////            } finally {
////                if (oConnection != null) {
////                    oConnection.close();
////                }
////                if (oPooledConnection != null) {
////                    oPooledConnection.disposeConnection();
////                }
////            }
////            return oReplyBean;
//////        } else {
//////            return new ReplyBean(401, "Unauthorized");
////        }
}
