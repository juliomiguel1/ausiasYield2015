/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 * AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
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
 * 
 */
package net.daw.dao.specific.implementation;

import java.sql.Connection;
import net.daw.bean.specific.implementation.RespuestaBean;
import net.daw.dao.generic.implementation.TableDaoGenImpl;

/**
 *
 * @author juliomiguel
 */
public class RespuestaDao extends TableDaoGenImpl<RespuestaBean> {
    
     public RespuestaDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);
    }
    
    @Override
    public RespuestaBean get(RespuestaBean oRespuestaBean, Integer expand) throws Exception {
        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (oRespuestaBean.getId() > 0) {

            if (oMysql.existsOne(strSqlSelectDataOrigin, oRespuestaBean.getId())) {
                String strIdDoc = oMysql.getOne(strSqlSelectDataOrigin, "id_opcion", oRespuestaBean.getId());
                oRespuestaBean.setId_opcion(Integer.parseInt(strIdDoc));
                
                    OpcionDao oOpcionDao = new OpcionDao(oConnection);
                    
                    OpcionBean oOpcionBean = new OpcionBean();
                    oOpcionBean.setId(oRespuestaBean.getId_opcion());
                    oOpcionBean = oOpcionDao.get(oOpcionBean, 1);
                    
                    GroupBeanImpl oGroupBeanImpl = new GroupBeanImpl();
                    oGroupBeanImpl.setBean(oOpcionBean);
                    oGroupBeanImpl.setMeta(oOpcionDao.getmetainformation());
                    oRespuestaBean.setObj_opcion(oGroupBeanImpl);
                  
                //Usuario
                    
                    String strIdUsu = oMysql.getOne(strSqlSelectDataOrigin, "id_usuario", oRespuestaBean.getId());
                    oRespuestaBean.setId_usuario(Integer.parseInt(strIdUsu));
                
                    UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                    
                    UsuarioBean oUsuarioBean = new UsuarioBean();
                    oUsuarioBean.setId(oRespuestaBean.getId());
                    oUsuarioBean = oUsuarioDao.get(oUsuarioBean, 1);
                    
                    GroupBeanImpl oGroupBeanImplUsu = new GroupBeanImpl();
                    oGroupBeanImplUsu.setBean(oUsuarioBean);
                    oGroupBeanImplUsu.setMeta(oUsuarioDao.getmetainformation());
                    oRespuestaBean.setObj_usuario(oGroupBeanImplUsu);
                  
                //hora
                    String strHora = oMysql.getOne(strSqlSelectDataOrigin, "fechaHoraAlta", oRespuestaBean.getId());
                    Date date = formatter.parse(strHora);
                    oRespuestaBean.setFechaHoraAlta(date);
            }
        }
        try {

            return oRespuestaBean;
        } catch (Exception e) {
            throw new Exception(this.getClass().getName() + ":get ERROR: " + e.getMessage());
        }
    }
    
    @Override
    public ArrayList<RespuestaBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {

        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);

        ArrayList<RespuestaBean> alRespuestaBean = new ArrayList<>();
        strSqlSelectDataOrigin += SqlBuilder.buildSqlWhere(alFilter);
        strSqlSelectDataOrigin += SqlBuilder.buildSqlOrder(hmOrder);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            ResultSet result = oMysql.getAllSql(strSqlSelectDataOrigin);
            if (result != null) {
                while (result.next()) {
                    RespuestaBean oRespuestaBean = new RespuestaBean();
                    oRespuestaBean.setId(result.getInt("id"));
                    oRespuestaBean.setId_opcion(result.getInt("id_opcion"));
                    oRespuestaBean.setId_usuario(result.getInt("id_usuario"));
                    
                    OpcionDao oOpcionDao = new OpcionDao(oConnection);
                    
                    OpcionBean oOpcionBean = new OpcionBean();
                    oOpcionBean.setId(oRespuestaBean.getId_opcion());
                    oOpcionBean = oOpcionDao.get(oOpcionBean, 1);
                    
                    GroupBeanImpl oGroupBeanImpl = new GroupBeanImpl();
                    oGroupBeanImpl.setBean(oOpcionBean);
                    oGroupBeanImpl.setMeta(oOpcionDao.getmetainformation());
                    oRespuestaBean.setObj_opcion(oGroupBeanImpl);
                    
                    //Usuario
                
                    UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                    
                    UsuarioBean oUsuarioBean = new UsuarioBean();
                    oUsuarioBean.setId(oRespuestaBean.getId());
                    oUsuarioBean = oUsuarioDao.get(oUsuarioBean, 1);
                    
                    GroupBeanImpl oGroupBeanImplUsu = new GroupBeanImpl();
                    oGroupBeanImplUsu.setBean(oUsuarioBean);
                    oGroupBeanImplUsu.setMeta(oUsuarioDao.getmetainformation());
                    oRespuestaBean.setObj_usuario(oGroupBeanImplUsu);
                    
                    //hora
                    
                    String strHora = oMysql.getOne(strSqlSelectDataOrigin, "fechaHoraAlta", oRespuestaBean.getId());
                    if(strHora!=null){
                    Date date = formatter.parse(strHora);
                    oRespuestaBean.setFechaHoraAlta(date);
                    }else if(strHora==null){
                    Date date = new Date();
                    oRespuestaBean.setFechaHoraAlta(date);
                    }
                    
                    
                    alRespuestaBean.add(oRespuestaBean);
                }
            }

        } catch (Exception ex) {
            throw new Exception(this.getClass().getName() + ":get ERROR: " + ex.getMessage());
        }

        return alRespuestaBean;
    }
}
