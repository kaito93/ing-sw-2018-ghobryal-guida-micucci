package it.polimi.se2018.model;

/**
 * Color Enumeration
 * @author Anton Ghobryal
 */

public enum Color {
    BLUE, GREEN, RED, PURPLE, YELLOW, NULL;

    /**
     * a string transformation in an object
     * @param color a string
     * @return an object of type Color that's the same as the passed string
     */

    public static Color parseInput(String color){
        return Enum.valueOf(Color.class, color.toUpperCase());
    }

    /**
     * controls the equivalence of two color objects
     * @param color the considered color
     * @return a boolean that indicates whether the equivalence is true or false
     */

    public boolean equalsColor(Color color){
        try {
            return this.toString().equals(color.toString());
        }catch (NullPointerException e){
            return color.toString().equals(this.toString());
        }
    }

    /**
     * a color object transformation into a string
     * @return an equivalent string to the passed color object
     */

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}


