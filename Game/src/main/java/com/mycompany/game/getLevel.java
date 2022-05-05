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
@WebServlet(name = "getLevel", urlPatterns = {"/get-level"})
public class getLevel extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Tree tree = (Tree) request.getSession().getAttribute("tree");
        String level = request.getParameter("level");
        if (tree != null) {
            try {
                JsonObject json = tree.seeLevelJson(Integer.valueOf(level));
                if (json != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    PrintWriter out = response.getWriter();

                    out.print(json.toString());
                    response.setStatus(200);
                } else {
                    response.sendError(400);
                }
            } catch (NumberFormatException e) {
                response.sendError(400);
            }
        } else {
            response.sendError(400);
        }
    }
}
