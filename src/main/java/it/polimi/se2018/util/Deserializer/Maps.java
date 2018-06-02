package it.polimi.se2018.util.Deserializer;

public class Maps {

    private int numberCell;
    private String type;
    private String color;
    private int value;

    public Maps(){
        numberCell = 0;
        value = 0;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNumberCell(int numberCell) {
        this.numberCell = numberCell;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
