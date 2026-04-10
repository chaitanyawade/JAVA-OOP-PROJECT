import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "vault_data.txt";

    // Saves all tasks to a simple text file
    public static void saveTasks(List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                writer.println(t.content + "|" + t.isFinalized + "|" + t.version);
            }
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // Loads them back when you start the app
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split("\\|");
                Task t = new Task(data[0]);
                t.isFinalized = Boolean.parseBoolean(data[1]);
                t.version = data[2];
                tasks.add(t);
            }
        } catch (FileNotFoundException e) { /* New file will be created */ }
        return tasks;
    }
}
