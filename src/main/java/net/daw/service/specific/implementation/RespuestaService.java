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
package net.daw.service.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.PreguntaBean;
import net.daw.bean.specific.implementation.RespuestaBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.specific.implementation.PreguntaDao;
import net.daw.dao.specific.implementation.RespuestaDao;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author juliomiguel
 */
public class RespuestaService extends TableServiceGenImpl {

    public RespuestaService(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getpages() throws Exception {
        if (this.checkpermission("getpages")) {
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            String strResult = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                RespuestaDao oRespuestaDao = new RespuestaDao(oConnection);
                int intRpp = ParameterCook.prepareRpp(oRequest);
                ArrayList<FilterBeanHelper> alFilterBeanHelper = ParameterCook.prepareFilter(oRequest);
                strResult = ((Integer) oRespuestaDao.getPages(intRpp, alFilterBeanHelper)).toString();
            } catch (Exception ex) {
                oConnection.rollback();
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return JsonMessage.getJsonMsg("200",strResult);
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }
    
    @Override
    public String set() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        RespuestaDao oRespuestaDao = new RespuestaDao(oConnection);
        RespuestaBean oRespuestaBean = new RespuestaBean();
        String json = ParameterCook.prepareJson(oRequest);
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").excludeFieldsWithoutExposeAnnotation().create();        
        oRespuestaBean = gson.fromJson(json, RespuestaBean.class);
        oRespuestaBean = oRespuestaDao.set(oRespuestaBean);
        Map<String, String> data = new HashMap<>();
        data.put("status", "200");
        data.put("message", Integer.toString(oRespuestaBean.getId()));
        String resultado = gson.toJson(data);
        return resultado;
    }
    
    @Override
    public String remove() throws Exception{
        
        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        int id = ParameterCook.prepareId(oRequest);
        RespuestaDao oRespuestaDao = new RespuestaDao(oConnection);
        
        RespuestaBean oRespuestaBean = new RespuestaBean();
        oRespuestaBean.setId(id);
        oRespuestaDao.remove(oRespuestaBean);
        
        Map<String, String> data = new HashMap<>();
        data.put("status", "200");
        data.put("message", "se ha eliminado la pregunta con id= " + ((Integer)id).toString());
        Gson gson = new Gson();
        String resultado = gson.toJson(data);
        return resultado;
    }
    
    @Override
    public String getmetainformation() throws Exception {

        Connection oConnection = new BoneConnectionPoolImpl().newConnection();
        RespuestaDao oRespuestaDao = new RespuestaDao(oConnection);
        Gson oGson = new GsonBuilder().setDateFormat("dd/MM/yyyy").excludeFieldsWithoutExposeAnnotation().create();
        return "{\"status\":200,\"message\":"+oGson.toJson(oRespuestaDao.getmetainformation())+"}";

    }
}
