/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.game;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
@WebServlet(name = "viewImageTree", urlPatterns = {"/viewImageTree"})
public class viewImageTree extends HttpServlet {

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
        Tree tree = (Tree) request.getSession().getAttribute("tree");
        if (tree != null) {
            try {
                InputStream dot = new BufferedInputStream(new FileInputStream(getFile(tree)));
                MutableGraph g = new Parser().read(dot);
                File imagen = Graphviz.fromGraph(g).width(700).render(Format.PNG).
                        toFile(new File("imagen.png"));
                try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(imagen))) {
                    response.setContentType("image/png");
                    //response.setHeader("Content-disposition", "attachment; filename=imagen.png");
                    response.setHeader("Content-disposition", "inline; filename=imagen.png");
                    int data = fileStream.read();
                    while (data > -1) {
                        response.getOutputStream().write(data);
                        data = fileStream.read();
                    }
                }
                response.setStatus(200);
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(400);
            }
            
        } else {
            response.sendError(400);
        }
    }
    
    public File getFile(Tree tree) {
        File archivo = new File("grafico.dot");
        PrintWriter printWriter = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            printWriter = new PrintWriter(archivo);
            stringBuilder.append(tree.graphviz());
            printWriter.write(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
        return archivo;
    }
}
