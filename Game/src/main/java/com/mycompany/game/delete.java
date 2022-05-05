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
import node.Node;
import tree.Tree;

/**
 *
 * @author baquiax
 */
@WebServlet(name = "delete", urlPatterns = {"/delete"})
public class delete extends HttpServlet {

    /**
     * Handles the HTTP <code>DELETE</code> method. Elimina las cartas dadas
     * siempre que cumplan una suma de 13
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
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
        if (datos.length == 4) {
            String cardS1 = convertedObject.get("delete_1").getAsString();
            String cardS2 = convertedObject.get("delete_2").getAsString();

            Card card1 = createCard(cardS1);
            Card card2 = createCard(cardS2);

            if (tree.search(card1) != null && tree.search(card2) != null) {
                if (validarEliminar(tree, card1) && validarEliminar(tree, card2)) {
                    if (card1.getValueCard() + card2.getValueCard() == 13) {
                        tree.delete(card1);
                        tree.delete(card2);
                    } else {
                        response.sendError(406);
                    }
                } else {
                    response.sendError(409);
                }
            } else {
                response.sendError(404);
            }
        } else if (datos.length == 3) {
            String cardS = convertedObject.get("delete_1").getAsString();
            Card card1 = createCard(cardS);

            if (tree.search(card1) != null) {
                if (validarEliminar(tree, card1)) {
                    if (card1.getValueCard() == 13) {
                        tree.delete(card1);
                    } else {
                        response.sendError(406);
                    }
                } else {
                    response.sendError(409);
                }
            } else {
                response.sendError(404);
            }
        } else {
            response.sendError(400);
        }
    }

    public Card createCard(String cardS) {
        if (cardS.length() == 2) {
            return new Card(String.valueOf(cardS.charAt(0)),
                    String.valueOf(cardS.charAt(1)));
        } else if (cardS.length() == 3) {
            return new Card(String.valueOf(cardS.charAt(0))
                    + String.valueOf(cardS.charAt(1)), String.valueOf(cardS.charAt(2)));
        } else {
            return null;
        }
    }

    public boolean validarEliminar(Tree tree, Card card) {
        boolean valid = false;
        Node node = tree.search(card);
        if (node != null && node.getLeft() == null && node.getRight() == null) {
            valid = true;
        }
        return valid;
    }

}
