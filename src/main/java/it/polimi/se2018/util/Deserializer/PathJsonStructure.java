package it.polimi.se2018.util.Deserializer;

/**
 * class that is used to make a data structure where save the strings about
 * the path of all the files
 */
public class PathJsonStructure {

    String type;
    String path;

    /**
     * method that set the string of the type of json file
     * @param type a string that refers to the type of json files
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * method that set the string refers to the path of file
     * @param path a string refered to the path of the file json
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * getter method for the type of the file json
     * @return a string with the type of json file
     */
    public String getType() {
        return type;
    }

    /**
     * getter method for the path of the file json
     * @return a string with the path of json file
     */
    public String getPath() {
        return path;
    }
}
