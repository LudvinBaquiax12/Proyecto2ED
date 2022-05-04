/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.game;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "avltree", urlPatterns = {"/avltree"})
public class avltree extends HttpServlet {

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
        String transversal = request.getParameter("transversal");
        JsonObject json = null;
        Tree tree = (Tree) request.getSession().getAttribute("tree");
        if (tree != null) {
            if ("preOrder".equals(transversal)) {
                json = tree.preOrdenJson();
            } else if ("inOrder".equals(transversal)) {
                json = tree.inOrdenJson();
            } else if ("postOrder".equals(transversal)) {
                json = tree.postOrdenJson();
            } else {
                response.sendError(400);
            }
        } else {
            System.out.println("null tree");
        }

        if (json != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();

            out.print(json.toString());
        } else {
            response.sendError(400);
        }
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
