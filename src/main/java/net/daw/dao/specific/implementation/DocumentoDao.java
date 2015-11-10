/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
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
 */
package net.daw.dao.specific.implementation;

import net.daw.dao.generic.implementation.TableDaoGenImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.daw.bean.group.GroupBeanImpl;
import net.daw.bean.specific.implementation.DocumentoBean;
import net.daw.bean.specific.implementation.DocumentoxPreguntasBean;
import net.daw.bean.specific.implementation.PreguntaBean;
import net.daw.data.specific.implementation.MysqlDataSpImpl;

public class DocumentoDao extends TableDaoGenImpl<DocumentoBean> {

    public DocumentoDao(Connection pooledConnection) throws Exception {
        super(pooledConnection);
    }

//    public String getDescription(int id) throws Exception {
//        DocumentoBean oDocumentoBean = new DocumentoBean();
//        oDocumentoBean.setId(id);
//        oDocumentoBean = this.get(oDocumentoBean);
//        String description;
//        if (oDocumentoBean.getTitulo().length() > 20) {
//            description = oDocumentoBean.getTitulo().substring(0, 19) + "...";
//        } else {
//            description = oDocumentoBean.getTitulo();
//        }
//        description += " (" + oDocumentoBean.getHits().toString() + " hits)";
//        return description;
//    }
    
     public ArrayList<DocumentoxPreguntasBean> getAllPreguntas(DocumentoBean oDocumentoBean) throws Exception {

        ArrayList<DocumentoxPreguntasBean> alString = new ArrayList<DocumentoxPreguntasBean>();

        MysqlDataSpImpl oMysql = new MysqlDataSpImpl(oConnection);
        if (oDocumentoBean.getId() > 0) {
            if (oMysql.existsOne(strSqlSelectDataOrigin, oDocumentoBean.getId())) {
                ResultSet result = oMysql.getAllSql(strSqlSelectDataOrigin);

                if (result != null) {
                    while (result.next()) {
                        if (result.getInt("id") == oDocumentoBean.getId()) {

                            ResultSet resultpregunta = oMysql.getAllSql("select * from pregunta");

                            while (resultpregunta.next()) {

                                if (result.getInt("id") == resultpregunta.getInt("id_documento")) {
                                    DocumentoxPreguntasBean oDocumentoxPreguntasBean = new DocumentoxPreguntasBean();
                                    oDocumentoxPreguntasBean.setId_documento(result.getInt("id"));
                                    oDocumentoxPreguntasBean.setTitulo(result.getString("titulo"));
                                    PreguntaDao oPreguntaDao = new PreguntaDao(oConnection);
                                    PreguntaBean oPreguntaBean = new PreguntaBean();
                                    oPreguntaBean.setId(resultpregunta.getInt("id"));
                                    oDocumentoxPreguntasBean.setId_pregunta(resultpregunta.getInt("id"));
                                    oDocumentoxPreguntasBean.setDescripcion(resultpregunta.getString("descripcion"));
                                    oPreguntaBean = oPreguntaDao.get(oPreguntaBean, 1);

                                    GroupBeanImpl oGroupBeanImpl = new GroupBeanImpl();
                                    oGroupBeanImpl.setBean(oPreguntaBean);
                                    oGroupBeanImpl.setMeta(oPreguntaDao.getmetainformation());
                                    oDocumentoxPreguntasBean.setObj_pregunta(oGroupBeanImpl);
                                    alString.add(oDocumentoxPreguntasBean);
                                }

                            }

                        }
                    }
                }
            }

        }
        return alString;
    }
}
