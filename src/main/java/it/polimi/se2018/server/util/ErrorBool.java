package it.polimi.se2018.server.util;

/**
 * @author Anton Ghobryal
 */
public class ErrorBool {
    private String errorMessage;
    private Boolean errBool;

    /**
     * Class Constructor
     * @param errorMessage an error message_socket of where and why there is an error in a certain tool card strategy
     * @param errBool a boolean, if there is an error or not
     */
    public ErrorBool(String errorMessage, Boolean errBool){
        setErrorMessage(errorMessage);
        setErrBool(errBool);
    }

    /**
     * gets the boolean of the error
     * @return the error boolean
     */
    public Boolean getErrBool() {
        return errBool;
    }

    /**
     * gets the error message_socket
     * @return the error message_socket if there is any else it's null
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * sets the error boolean, true if there is an error message_socket else false
     * @param errBool the forced boolean into the structure
     */
    public void setErrBool(Boolean errBool) {
        this.errBool = errBool;
    }

    /**
     * sets the error message_socket if there is any else null
     * @param errorMessage a string of the error message_socket
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
