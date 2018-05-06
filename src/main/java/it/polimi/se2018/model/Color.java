package it.polimi.se2018.model;

public enum Color {
    BLUE, GREEN, RED, PURPLE, YELLOW;

    public static Color parseInput(String color){
        return Enum.valueOf(Color.class, color.toUpperCase());
    }

    public boolean equals(Color color){
        return this.toString().equals(color.toString());
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}


