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
import net.daw.bean.specific.implementation.OpcionBean;
import net.daw.bean.specific.implementation.PreguntaBean;
import net.daw.dao.generic.implementation.TableDaoGenImpl;

/**
 *
 * @author juliomiguel
 */
public class OpcionDao extends TableDaoGenImpl<OpcionBean> {
    
     public OpcionDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);
    }
    
     
    @Override
    public OpcionBean get(OpcionBean oOpcionBean, Integer expand) throws Exception {
        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
        if (oOpcionBean.getId() > 0) {

            if (oMysql.existsOne(strSqlSelectDataOrigin, oOpcionBean.getId())) {
                String strIdDoc = oMysql.getOne(strSqlSelectDataOrigin, "id_pregunta", oOpcionBean.getId());
                oOpcionBean.setId_pregunta(Integer.parseInt(strIdDoc));
                oOpcionBean.setDescripcion(oMysql.getOne(strSqlSelectDataOrigin, "descripcion", oOpcionBean.getId()));
                
                
                PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
                    //un pojo de documento con elid de documento
                    PreguntaBean oPreguntaBean = new PreguntaBean();
                    oPreguntaBean.setId(oOpcionBean.getId_pregunta());
                    oPreguntaBean = oPreguntaDao.get(oPreguntaBean, 1);

                    //rellenar el pojo de documento con el dao
                    //meter el pojo relleno a la pregunta
                    GroupBeanImpl oGroupBeanImpl = new GroupBeanImpl();
                    oGroupBeanImpl.setBean(oPreguntaBean);
                    oGroupBeanImpl.setMeta(oPreguntaDao.getmetainformation());
                    oOpcionBean.setObj_pregunta(oGroupBeanImpl);
            }
        }
        try {

            return oOpcionBean;
        } catch (Exception e) {
            throw new Exception(this.getClass().getName() + ":get ERROR: " + e.getMessage());
        }
    }
    
     @Override
    public ArrayList<OpcionBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {

        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);

        ArrayList<OpcionBean> alOpcionBean = new ArrayList<>();
        strSqlSelectDataOrigin += SqlBuilder.buildSqlWhere(alFilter);
        strSqlSelectDataOrigin += SqlBuilder.buildSqlOrder(hmOrder);
        try {
            ResultSet result = oMysql.getAllSql(strSqlSelectDataOrigin);
            if (result != null) {
                while (result.next()) {
                    OpcionBean oOpcionBean = new OpcionBean();
                    oOpcionBean.setId(result.getInt("id"));
                    oOpcionBean.setId_pregunta(result.getInt("id_pregunta"));
                    //crear un dao de documento
                    PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
                    //un pojo de documento con elid de documento
                    PreguntaBean oPreguntaBean = new PreguntaBean();
                    oPreguntaBean.setId(result.getInt("id_pregunta"));
                    oPreguntaBean = oPreguntaDao.get(oPreguntaBean, 1);

                    //rellenar el pojo de documento con el dao
                    //meter el pojo relleno a la pregunta
                    GroupBeanImpl oGroupBeanImpl = new GroupBeanImpl();
                    oGroupBeanImpl.setBean(oPreguntaBean);
                    oGroupBeanImpl.setMeta(oPreguntaDao.getmetainformation());
                    oOpcionBean.setObj_pregunta(oGroupBeanImpl);

                    //sacar del dao de documento los metadatos de documento
                    //meterlos en el pojo de la pregunta tambien
                    oOpcionBean.setDescripcion(result.getString("descripcion"));
                    alOpcionBean.add(oOpcionBean);
                }
            }

        } catch (Exception ex) {
            throw new Exception(this.getClass().getName() + ":get ERROR: " + ex.getMessage());
        }

        return alOpcionBean;
    }
}
