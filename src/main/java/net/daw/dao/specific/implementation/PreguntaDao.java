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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.group.GroupBeanImpl;
import net.daw.bean.specific.implementation.DocumentoBean;
import net.daw.bean.specific.implementation.PreguntaBean;
import net.daw.bean.specific.implementation.UsuarioBean;
import net.daw.dao.generic.implementation.TableDaoGenImpl;
import net.daw.data.specific.implementation.MysqlDataSpImpl;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.SqlBuilder;

/**
 *
 * @author juliomiguel
 */
public class PreguntaDao extends TableDaoGenImpl<PreguntaBean> {
    
     public PreguntaDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);
    }
     
   @Override
    public ArrayList<PreguntaBean> getAll(ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {

        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);

        ArrayList<PreguntaBean> alPreguntaBean = new ArrayList<>();
        strSqlSelectDataOrigin += SqlBuilder.buildSqlWhere(alFilter);
        strSqlSelectDataOrigin += SqlBuilder.buildSqlOrder(hmOrder);
        try {
            ResultSet result = oMysql.getAllSql(strSqlSelectDataOrigin);
            if (result != null) {
                while (result.next()) {
                    PreguntaBean oPreguntaBean = new PreguntaBean();
                    oPreguntaBean.setId(result.getInt("id"));
                    oPreguntaBean.setId_documento(result.getInt("id_documento"));
                    
                    
                    //crear un dao de documento
                    DocumentoDao oDocumentoDao = new DocumentoDao(oConnection);
                    //un pojo de documento con elid de documento
                    DocumentoBean oDocumentoBean = new DocumentoBean();
                    oDocumentoBean.setId(result.getInt("id_documento"));
                   
                    oDocumentoBean=oDocumentoDao.get(oDocumentoBean, 2);
                    //rellenar el pojo de documento con el dao
                    //meter el pojo relleno a la pregunta
                    GroupBeanImpl oGroupBeanImpl = new GroupBeanImpl();
                    oGroupBeanImpl.setBean(oDocumentoBean);
                    oGroupBeanImpl.setMeta(oDocumentoDao.getmetainformation());
                    oPreguntaBean.setObj_documento(oGroupBeanImpl);
                    
                    //sacar del dao de documento los metadatos de documento
                    //meterlos en el pojo de la pregunta tambien
                    
                    
                    
                    oPreguntaBean.setDescripcion(result.getString("descripcion"));
                    alPreguntaBean.add(oPreguntaBean);
                }
            }
            

       } catch (Exception ex) {
            throw new Exception(this.getClass().getName() + ":get ERROR: " + ex.getMessage());
        }

        return alPreguntaBean;
    }
    
    
}
