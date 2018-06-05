package it.polimi.se2018.util.Deserializer;

public class Maps {

    private int numbercell;
    private String type;
    private String color;
    private int value;

    public Maps(){
        numbercell = 0;
        value = 0;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNumberCell(int numberCell) {
        this.numbercell = numberCell;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public int getNumberCell() {
        return numbercell;
    }
}
