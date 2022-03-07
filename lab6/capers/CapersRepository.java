package capers;

import java.io.File;
import java.io.IOException;
import static capers.Utils.*;

/** A repository for Capers 
 * @author risa
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));


    /** Main metadata folder. */
    static final File CAPERS_FOLDER =  Utils.join(CWD, ".capers");
    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() throws IOException {
        // TODO
        // Initiate a capers metadata folder
        CAPERS_FOLDER.mkdir();

        // Initiate a dog metadata folder
        capers.Dog.DOG_FOLDER.mkdir();

        // Initiate a story.txt file
        File story = Utils.join(".capers","story.txt");
        story.createNewFile();

    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File story = Utils.join(CWD, ".capers", "story.txt");
        String updatedStory = Utils.readContentsAsString(story) + text + "\n";
        // Read in the story file as a string
        // Add text as a new line
        // Write it all back into the story file
        if(story.exists()) {
            Utils.writeContents(story, updatedStory);
        }
        System.out.println(updatedStory);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        //Creates a dog object
        Dog newDog = new Dog(name, breed, age);
        //Save the dog object
        newDog.saveDog();
        //Print the dog info
        System.out.println(newDog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // De-serialize the dog file
        File currentFile = Utils.join(CWD, ".capers", "dog", name);
        Dog currentDog = readObject(currentFile, Dog.class);
        currentDog.haveBirthday();
        // Re-serialize the dog after its birthday
        writeObject(currentFile, currentDog);

    }
}
