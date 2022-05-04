/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author baquiax
 */
@WebServlet(name = "start", urlPatterns = {"/start"})
public class start extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Leer el Json en texto plano
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();

        //Convertir el String a Json y leer los datos
        JsonObject convertedObject = new Gson().fromJson(body, JsonObject.class);
        String datos[] = body.split("\n");
        datos = new String[datos.length - 2];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = convertedObject.get(String.valueOf(i)).getAsString();
        }
        for (int i = 0; i < datos.length; i++) {
            String valor = "";
            String tipo = "";
            if (datos[i].length() == 2) {
                valor = String.valueOf(datos[i].charAt(0));
                tipo = String.valueOf(datos[i].charAt(1));
            } else if (datos[i].length() == 3) {
                valor = String.valueOf(datos[i].charAt(0)) + String.valueOf(datos[i].charAt(1));
                tipo = String.valueOf(datos[i].charAt(2));
            }
            System.out.println("Valor: " + valor + " ,Tipo: " + tipo);
        }
        //response.sendError(400,"hola");
        //response.setStatus(200);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}