/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import cards.Card;

/**
 *
 * @author baquiax
 */
public class Node {

    private Card card;
    private int height;
    private Node left;
    private Node right;

    public Node(Card card) {
        this.card = card;
        this.height = 0;
        this.left = null;
        this.right = null;
    }

    

    /**
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the left
     */
    public Node getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public Node getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(Node right) {
        this.right = right;
    }

    /**
     *
     * @return the Graphviz Code
     */
    public String getGraphvizCode() {
        return "digraph grafica{\n"
                + "rankdir=TB;\n"
                + "node [shape = record, style=filled, fillcolor=seashell2];\n"
                + getInternalCode()
                + "}";
    }

    /**
     * It generates the internal code of graphviz, this method has the
     * peculiarity of being recursive, this is because going through a tree
     * recursively is quite simple and reduces the code considerably.
     *
     * @return
     */
    public String getInternalCode() {
        String etiqueta;
        if (left == null && right == null) {
            etiqueta = "nodo" + card.getValueTree() + " [ label =\"" + card.getValueCard() + "♥" + "\"];\n";
        } else {
            etiqueta = "nodo" + card.getValueTree() + " [ label =\"<C0>|" + card.getValueCard() + "♥" + "|<C1>\"];\n";
        }
        if (left != null) {
            etiqueta = etiqueta + left.getInternalCode()
                    + "nodo" + card.getValueTree() + ":C0->nodo" + left.getCard().getValueTree() + "\n";
        }
        if (right != null) {
            etiqueta = etiqueta + right.getInternalCode()
                    + "nodo" + card.getValueTree() + ":C1->nodo" + right.getCard().getValueTree() + "\n";
        }
        return etiqueta;
    }
}
