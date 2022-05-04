/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cards.Card;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import tree.Tree;

/**
 *
 * @author baquiax
 */
public class Game {

    public Game() {
    }

    public static Tree starGame(String data) {
        Tree tree = new Tree();
        JsonObject convertedObject = new Gson().fromJson(data, JsonObject.class);
        String datos[] = data.split("\n");
        datos = new String[datos.length - 2];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = convertedObject.get(String.valueOf(i)).getAsString();
        }
        for (int i = 0; i < datos.length; i++) {
            String value = "";
            String type = "";
            if (datos[i].length() == 2) {
                value = String.valueOf(datos[i].charAt(0));
                type = String.valueOf(datos[i].charAt(1));
            } else if (datos[i].length() == 3) {
                value = String.valueOf(datos[i].charAt(0)) + String.valueOf(datos[i].charAt(1));
                type = String.valueOf(datos[i].charAt(2));
            }
            tree.insert(new Card(value, type));
        }
        return tree;
    }

}
