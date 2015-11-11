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
var viewCuestionario = function () {    
}

viewCuestionario.prototype = new viewModule();
viewCuestionario.prototype.getViewTemplate_func = function (strClass, jsonDataViewModule) {
   /*Ejemplo
    * 
    * usuariotabla.prototype.getViewTemplate_func = function (strClass, jsonDataViewModule) {

    


    var nuevo = "<table class=\"table table table-bordered table-condensed\">";

    
    for (var i = 0; i <= jsonDataViewModule.meta.message.length; i++) {

        var short = jsonDataViewModule.meta.message[i];
        var beaner = jsonDataViewModule.bean.message;
        for (var metadatos in short) {
            for (var beandatos in beaner) {
                if (short[metadatos] === beandatos) {
                    if (short[metadatos] === 'obj_tipousuario' || short[metadatos] === 'obj_estado') {
                        var ultimorecorrido = beaner[beandatos].bean;
                        var imprimir="";
                        for (var ajena in ultimorecorrido) {
                            
                               imprimir=ultimorecorrido[ajena]+ " "+imprimir;
                               imprimir += " ";
                        }
                        nuevo += '<tr><td><strong>' + short[metadatos] + '</strong></td><td>' + imprimir+ '</td></tr>';
                    } else {
                        nuevo += '<tr><td><strong>' + short[metadatos] + '</strong></td><td>' + beaner[beandatos] + '</td></tr>';
                    }
                }
            }
        }
    }
    nuevo += '</table>';
    return nuevo;
}*/
}

