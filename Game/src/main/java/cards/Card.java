/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

/**
 *
 * @author baquiax
 */
public class Card {

    public static final int CLUB = 0;
    public static final int DIAMOND = 20;
    public static final int HEART = 40;
    public static final int SPADE = 60;

    public static final String CLUB_S = "\u2663";
    public static final String DIAMOND_S = "\u2666";
    public static final String HEART_S = "\u2665";
    public static final String SPADE_S = "\u2660";

    private String value;
    private String type;

    /**
     * Card constructor
     *
     * @param value
     * @param type
     */
    public Card(String value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the value
     */
    public int getValueCard() {
        if ("A".equals(value)) {
            return 1;
        } else if ("J".equals(value)) {
            return 11;
        } else if ("Q".equals(value)) {
            return 12;
        } else if ("K".equals(value)) {
            return 13;
        } else {
            System.out.println(value);
            return Integer.valueOf(value);
        }
    }

    /**
     * @return the value tree
     */
    public int getValueTree() {
        int valueTree = 0;
        if ("♣".equals(type)) {
            valueTree = getValueCard() + CLUB;
        } else if ("♦".equals(type)) {
            valueTree = getValueCard() + DIAMOND;
        } else if ("♥".equals(type)) {
            valueTree = getValueCard() + HEART;
        } else if ("♠".equals(type)) {
            valueTree = getValueCard() + SPADE;
        }
        return valueTree;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Value card number and type
     *
     * @return
     */
    public String getTypeCardUTF8() {
        if ("♣".equals(type)) {
            return value + CLUB_S;
        } else if ("♦".equals(type)) {
            return value + DIAMOND_S;
        } else if ("♥".equals(type)) {
            return value + HEART_S;
        } else if ("♠".equals(type)) {
            return value + SPADE_S;
        }
        return value + type;
    }

    @Override
    public String toString() {
        return "Card " + "value: " + value + ", Value Tree: " + getValueTree()
                + ", type:" + type;
    }

}
