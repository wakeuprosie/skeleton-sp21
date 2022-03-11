package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author risa
 */

import java.io.IOException;
import java.util.Arrays;

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */

    public static void main(String[] args) throws IOException {

        /** Failure: Exit if no args passed */
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }

        /** Body to handle commands */
        String firstArg = args[0];
        switch(firstArg) {
             case "init":
                validateNumArgs("init", args, 1);
                gitlet.Repository.setupPersistence();
                break;
            case "add":
                validateMinNumArgs("add", args, 1);
                for (int i = 1; i < args.length; i += 1) {
                    gitlet.Staging.add(args[i]);
                }
                break;
            case "commit":
                validateNumArgs("commit", args, 2);
                CommitMethod.commitMethod(args[1]);
                break;
        }

        /** Failure: Exit if arg doesn't match any existing command */
        System.out.println("No command with that name exists.");
        System.exit(0);

    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param cmd Name of command you are validating
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }
    }

    public static void validateMinNumArgs(String cmd, String[] args, int n) {
        if (args.length < n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }
    }

}
