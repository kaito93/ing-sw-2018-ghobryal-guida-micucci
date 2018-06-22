package it.polimi.se2018.shared;

/**
 * Class that manage the logger in this project
 * @author Samuele Guida
 */
public class Logger {

        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_RED = "\u001B[31m";
        private static final String ANSI_GREEN = "\u001B[32m";
        private static final String ANSI_YELLOW = "\u001B[33m";
        private static final String ANSI_BLUE = "\u001B[34m";
        private static final String ANSI_PURPLE = "\u001B[35m";
        private static final String ANSI_WHITE = "\u001B[37m";

     private Logger(){
         throw new IllegalStateException("Utility class");
     }

    /**
     * method that print a text like an information
     * @param message a string to print
     */
    //info
        public static void information( String message) {
            System.out.println(ANSI_WHITE + message + ANSI_RESET);
        }

    /**
     * method that print a text like an error
     * @param message a string to print
     */
        //error
        public static void error( String message) {
            System.out.println(ANSI_RED + message + ANSI_RESET);
        }

    /**
     * method that print a red text
     * @param message a string to print
     */
        public static void red (String message){
            System.out.print(ANSI_RED + message + ANSI_RESET);
        }
    /**
     * method that print a green text
     * @param message a string to print
     */
        public static void green (String message){
            System.out.print(ANSI_GREEN + message + ANSI_RESET);
        }
    /**
     * method that print a blue text
     * @param message a string to print
     */
        public static void blue (String message){
            System.out.print(ANSI_BLUE + message + ANSI_RESET);
        }
    /**
     * method that print a purple text
     * @param message a string to print
     */
        public static void purple (String message){
            System.out.print(ANSI_PURPLE + message + ANSI_RESET);
        }
    /**
     * method that print a yellow text
     * @param message a string to print
     */
        public static void yellow (String message){
            System.out.print(ANSI_YELLOW + message + ANSI_RESET);
        }

    }
