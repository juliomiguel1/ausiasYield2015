
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
var cuestionarioView = function () {
};

cuestionarioView.prototype = new viewModule();
cuestionarioView.prototype.getViewTemplate_func = function (strClass, jsonDataViewModule) {

    var nuevo = "<div>";
    var k = 0;
    var short;
    var j = 0;
    var opciones = 0;
    var nuevaopcion = 0;
    for (var i = 0; i < jsonDataViewModule.bean.message.length; i++) {
        if (k == 0) {
            short = jsonDataViewModule.bean.message[i].titulo;
            nuevo += short;
            nuevo += "</div>"
            k++;
        }

        if (j < jsonDataViewModule.bean.message.length - 1) {
            j = j + 1;
        }

        if (jsonDataViewModule.bean.message[i].id_pregunta !== jsonDataViewModule.bean.message[j].id_pregunta) {
            nuevo += "<div>"
            short = jsonDataViewModule.bean.message[i].descripcionPregunta;
            nuevo += short;
            nuevo += "</div>"

            while (opciones >= 0) {

                nuevo += "<div>"
                short = jsonDataViewModule.bean.message[nuevaopcion].descripcionOpcion;
                nuevo += short;
                nuevo += "</div>"
                nuevaopcion++;
                opciones--;
            }
            opciones = 0;
        } else if (i === jsonDataViewModule.bean.message.length - 1) {
            nuevo += "<div>"
            short = jsonDataViewModule.bean.message[i].descripcionPregunta;
            nuevo += short;
            nuevo += "</div>"
            while (opciones >= 0) {

                nuevo += "<div>"
                short = jsonDataViewModule.bean.message[nuevaopcion].descripcionOpcion;
                nuevo += short;
                nuevo += "</div>"
                nuevaopcion++;
                opciones--;
            }

        } else {
            opciones++;
        }
    }


    return nuevo;
};