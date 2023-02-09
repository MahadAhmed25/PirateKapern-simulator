package pk;

/*
 * Class to determine if tracing is activated or not
 * if "trace" is inputted into the 4th command line argument
 * toLog will be set to true and all "LOGGER.trace" elements
 * will be printed
 */

public class ManageLog {
    public static boolean toLog = false;

    public static void setManageLog(String traceInput){
        if(traceInput.equalsIgnoreCase("trace")) toLog = true;
        else toLog = false;
    }
}
