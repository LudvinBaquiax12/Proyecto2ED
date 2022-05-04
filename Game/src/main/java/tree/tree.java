/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import cards.Card;
import node.Node;

/**
 *
 * @author baquiax
 */
public class tree {

    private Node root;

    /**
     * Tree constructor
     *
     */
    public tree() {
        this.root = null;
    }

    /**
     * Insert a new node in the tree
     *
     * @param card
     */
    public void insert(Card card) {
        root = insertAVL(root, card);
    }

    /**
     * Inserting a node in a balanced way
     *
     * @param node
     * @param card
     * @return
     */
    public Node insertAVL(Node node, Card card) {
        if (node == null) {
            return new Node(card);
        }
        if (card.getValueTree() < node.getCard().getValueTree()) {
            node.setLeft(insertAVL(node.getLeft(), card));
        } else if (card.getValueTree() > node.getCard().getValueTree()) {
            node.setRight(insertAVL(node.getRight(), card));
        } else {
            return node;
        }

        node.setHeight(1 + max(getHeight(node.getLeft()), getHeight(node.getRight())));

        int balanceFactor = getBalanceFactor(node);
        // Caso Rotacion Simple a la derecha
        if (balanceFactor > 1 && card.getValueTree() < node.getLeft().getCard().getValueTree()) {
            return rightRotate(node);
        }
        // Caso Rotacion Simple a la izquierda
        if (balanceFactor < -1 && card.getValueTree() > node.getRight().getCard().getValueTree()) {
            return leftRotate(node);
        }
        // Caso Rotacion Doble Izquierda Derecha
        if (balanceFactor > 1 && card.getValueTree() > node.getLeft().getCard().getValueTree()) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }
        // Caso Rotacion Doble Derecha Izquierda
        if (balanceFactor < -1 && card.getValueTree() < node.getRight().getCard().getValueTree()) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }
        return node;
    }

    public void delete(Card card) {
        root = deleteAVL(root, card);
    }

    private Node deleteAVL(Node node, Card card) {
        if (node == null) {
            return node;
        }
        if (card.getValueTree() < node.getCard().getValueTree()) {
            node.setLeft(deleteAVL(node.getLeft(), card));
        } else if (card.getValueTree() > node.getCard().getValueTree()) {
            node.setRight(deleteAVL(node.getRight(), card));
        } else {
            //El nodo es igual a la clave, se elimina
            //Nodo con un unico hijo o es hoja
            if ((node.getLeft() == null) || (node.getRight() == null)) {
                Node aux = null;
                if (aux == node.getLeft()) {
                    aux = node.getRight();
                } else {
                    aux = node.getLeft();
                }
                // Caso que no tiene hijos
                if (aux == null) {
                    node = null;//Se elimina dejandolo en null
                } else {
                    //Caso con un hijo
                    node = aux;//Elimina el valor actual reemplazandolo por su hijo
                }
            } else {
                //Nodo con dos hijos, se busca el predecesor
                Node aux = getNodeWithMaxValue(node.getLeft());
                //Se copia el dato del predecesor
                node.setCard(aux.getCard());
                //Se elimina el predecesor
                node.setLeft(deleteAVL(node.getLeft(), aux.getCard()));
            }
        }
        //Si solo tiene un nodo
        if (node == null) {
            return node;
        }
        //Actualiza altura
        node.setHeight(max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
        //Calcula factor de equilibrio (FE)
        int fe = getBalanceFactor(node);
        // Se realizan las rotaciones pertinentes dado el FE
        // Caso Rotacion Simple Derecha
        if (fe > 1 && getBalanceFactor(node.getLeft()) >= 0) {
            return rightRotate(node);
        }
        // Caso Rotacion Simple Izquierda
        if (fe < -1 && getBalanceFactor(node.getRight()) <= 0) {
            return leftRotate(node);
        }
        // Caso Rotacion Doble Izquierda-Derecha
        if (fe > 1 && getBalanceFactor(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }
        // Caso Rotacion Doble Derecha-Izquierda
        if (fe < -1 && getBalanceFactor(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }
        return node;
    }

    /**
     *
     * @param node
     * @return
     */
    private Node rightRotate(Node node) {
        Node newRoot = node.getLeft();
        Node aux = newRoot.getRight();

        // Se realiza la rotacion
        newRoot.setRight(node);
        node.setLeft(aux);

        // Actualiza alturas
        node.setHeight(max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
        newRoot.setHeight(max(getHeight(newRoot.getLeft()), getHeight(newRoot.getRight())) + 1);

        return newRoot;
    }

    // Rotar hacia la izquierda
    private Node leftRotate(Node node) {
        Node newRoot = node.getRight();
        Node aux = newRoot.getLeft();

        // Se realiza la rotacion
        newRoot.setLeft(node);
        node.setRight(aux);

        // Actualiza alturas
        node.setHeight(max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
        newRoot.setHeight(max(getHeight(newRoot.getLeft()), getHeight(newRoot.getRight())) + 1);

        return newRoot;
    }

    /**
     * Busca un nodo en el arbol recibiendo como parametro el dato a buscar del
     * nodo
     *
     * @param card
     * @return
     */
    public Node search(Card card) {
        Node aux = root;
        while (aux.getCard().getValueTree() != card.getValueTree()) {
            if (card.getValueTree() < aux.getCard().getValueTree()) {
                aux = aux.getLeft();
            } else {
                aux = aux.getRight();
            }
            if (aux == null) {
                return null;
            }
        }
        return aux;
    }

    /**
     * Busca un nodo en el arbol recibiendo como parametro el dato a buscar del
     * nodo
     *
     * @param card
     * @param raiz
     * @return
     */
    public Node recursiveSerch(Card card, Node raiz) {
        if (raiz == null) {
            return null;
        } else if (raiz.getCard().getValueTree() == card.getValueTree()) {
            return raiz;
        } else if (raiz.getCard().getValueTree() < card.getValueTree()) {
            return recursiveSerch(card, raiz.getRight());
        } else {
            return recursiveSerch(card, raiz.getLeft());
        }
    }

    public String inOrden() {
        return getTextJson(inOrden(root));
    }

    /**
     * Recorre el Arbol de manera InOden
     *
     * @param root
     * @return
     */
    private String inOrden(Node root) {
        if (root != null) {
            return inOrden(root.getLeft())
                    + String.valueOf(root.getCard().valueCard()) + "\n"
                    + inOrden(root.getRight());
        } else {
            return "";
        }
    }

    public String preOrden() {
        return getTextJson(preOrden(root));
    }

    /**
     * Recorre el arbol en forma PreOrden
     *
     * @param root
     * @return
     */
    private String preOrden(Node root) {
        if (root != null) {
            return String.valueOf(root.getCard().valueCard()) + "\n"
                    + preOrden(root.getLeft())
                    + preOrden(root.getRight());
        } else {
            return "";
        }
    }

    public String postOrden() {
        return getTextJson(postOrden(root));
    }

    /**
     * Recorre el arbol de manera PostOrden
     *
     * @param root
     * @return
     */
    private String postOrden(Node root) {
        if (root != null) {
            return postOrden(root.getLeft())
                    + postOrden(root.getRight())
                    + String.valueOf(root.getCard().valueCard()) + "\n";
        } else {
            return "";
        }
    }

    public String getTextJson(String data) {
        String[] aux = data.split("\n");
        for (int i = 0; i < aux.length; i++) {
            aux[i] = "\"" + i + "\":" + "\"" + aux[i] + "\"";
        }
        String values = "{\n";
        for (int i = 0; i < aux.length; i++) {
            if (i == aux.length - 1) {
                values = values + "\t" + aux[i] + "\n}";
            } else {
                values = values + "\t" + aux[i] + ",\n";
            }
        }
        return values;
    }

    public String graphviz() {
        return root.getGraphvizCode();
    }

    /**
     * Ver los nodos de un nivel deperminado
     *
     * @param nivel
     * @return
     */
    public String seeLevel(int nivel) {
        return getTextJson(seeLevel(root, nivel));
    }

    /**
     * Ver el nivel de manera recursiva
     *
     * @param nodo
     * @param nivel
     * @return
     */
    private String seeLevel(Node nodo, int nivel) {
        if (nodo != null) {
            if (nivel == 1) {
                return String.valueOf(nodo.getCard().valueCard()) + "\n";
            }
            return seeLevel(nodo.getLeft(), nivel - 1)
                    + seeLevel(nodo.getRight(), nivel - 1);
        }
        return "";
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     *
     * @param node
     * @return the Height of a node
     */
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    /**
     * Returns the maximum value between two values
     *
     * @param a
     * @param b
     * @return
     */
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    /**
     *
     * @param node
     * @return the balance factor of a node
     */
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    /**
     * Retorna el nodo maximo que tiene un nodo
     *
     * @param node
     * @return the maximum value of a node
     */
    private Node getNodeWithMaxValue(Node node) {
        Node current = node;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }
}
