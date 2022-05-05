/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.game;

import cards.Card;
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
import tree.Tree;

/**
 *
 * @author baquiax
 */
@WebServlet(name = "add", urlPatterns = {"/add"})
public class add extends HttpServlet {

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

        Tree tree = (Tree) request.getSession().getAttribute("tree");

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

        JsonObject convertedObject = new Gson().fromJson(body, JsonObject.class);
        String datos[] = body.split("\n");
        if (datos.length == 3) {
            String cardS = convertedObject.get("insert").getAsString();
            String value = "";
            String type = "";

            if (cardS.length() == 2) {
                value = String.valueOf(cardS.charAt(0));
                type = String.valueOf(cardS.charAt(1));
            } else if (cardS.length() == 3) {
                value = String.valueOf(cardS.charAt(0)) + String.valueOf(cardS.charAt(1));
                type = String.valueOf(cardS.charAt(2));
            } else {
                response.sendError(400);
            }
            if (!("".equals(value) && "".equals(type))) {
                Card card = new Card(value, type);
                if (null == tree.search(card)) {
                    tree.insert(new Card(value, type));
                    response.setStatus(200);
                } else {
                    response.sendError(406);
                }
            } else {
                response.sendError(400);
            }

        } else {
            response.sendError(400);
        }

    }

}
