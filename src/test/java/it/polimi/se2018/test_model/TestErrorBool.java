package it.polimi.se2018.test_model;

import it.polimi.se2018.server.util.ErrorBool;
import junit.framework.TestCase;

/**
 * class ErrorBool Tester
 * @author Anton Ghobryal
 */
public class TestErrorBool extends TestCase {

    private ErrorBool errorBool;

    /**
     * Class Constructor
     * @param name test method name
     */
    public TestErrorBool(String name){
        super(name);
    }

    /**
     * setup a new Error message_socket with a boolean
     * @throws Exception setup general exception
     */
    @Override
    protected void setUp() throws Exception {
        errorBool = new ErrorBool(null, false);
        super.setUp();
    }

    /**
     * releases used data in the process
     * @throws Exception teardown general exception
     */
    @Override
    protected void tearDown() throws Exception {
        errorBool.setErrorMessage(null);
        errorBool.setErrBool(false);
        errorBool=null;
        System.gc();
        super.tearDown();
    }

    /**
     * tests get and set methods of errBool in class ErrorBool
     */
    public void testGetSetErrBool(){
        assertFalse(errorBool.getErrBool());
        errorBool.setErrBool(true);
        assertTrue(errorBool.getErrBool());
    }

    /**
     * tests get and set methods of errMessage in class ErrorBool
     */
    public void testGetSetErrMessage(){
        assertNull(errorBool.getErrorMessage());
        errorBool.setErrorMessage("Error");
        assertNotNull(errorBool.getErrorMessage());
    }
}
