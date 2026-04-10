import java.util.*;

public class VaultApp {
    static User currentUser;
    static List<Task> storage = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter name:");
        String userName = scanner.nextLine();
        System.out.println("Choose Role (1. Manager | 2. Worker):");
        User currentUser = scanner.nextLine().equals("1") ? new Manager(userName) : new Worker(userName);

        currentUser.showPermissions();

        storage = FileHandler.loadTasks();

        System.out.println("Welcome to Project Vault. Enter your Name:");
        String name = scanner.nextLine();
        System.out.println("Select Role: 1. Manager | 2. Worker");
        String role = scanner.nextLine().equals("1") ? "MANAGER" : "WORKER";

        currentUser = new User(name, role);
        
        System.out.println("Logged in as " + currentUser.name + " (" + currentUser.role + ")");
        while (true) {
            System.out.println("\n--- PROJECT VAULT MVP ---");
            System.out.println("1. Send Message (Chat) | 2. View Vault (Official) | 3. Finalize Task | 4. Exit");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Message: ");
                String msg = scanner.nextLine();

                System.out.print("Industry (Construction/Healthcare/General): ");
                String ind = scanner.nextLine();

                Task newTask = new Task(msg, ind); // Create task with industry
                storage.add(newTask);

                newTask.printIndustrySpecs(); // Immediately shows the "Rule" being applied
            } else if (choice.equals("2")) {
                System.out.println("\n--- THE VAULT (SOURCE OF TRUTH) ---");
                storage.stream().filter(t -> t.isFinalized)
                        .forEach(t -> System.out.println("[" + t.version + "] " + t.content));
            } else if (choice.equals("3")) {
                finalizeTask();
            } else if (choice.equals("4"))
                break;
        }
    }

    static void finalizeTask() {
        // SECURITY CHECK
        if (!currentUser.role.equalsIgnoreCase("MANAGER")) {
            System.out.println("ERROR: Only Managers can pull items to the Vault!");
            return; // Stops the method here
        }

        // If they ARE a manager, show the list to finalize
        for (int i = 0; i < storage.size(); i++) {
            if (!storage.get(i).isFinalized) {
                System.out.println(i + ": [" + storage.get(i).industry + "] " + storage.get(i).content);
            }
        }

        System.out.print("Enter ID to pull to Vault: ");
        int id = Integer.parseInt(scanner.nextLine());

        Task t = storage.get(id);
        t.isFinalized = true;
        t.version = "v" + (new Random().nextInt(1000));
        System.out.println("SUCCESS: " + t.content + " is now official Source of Truth.");
    }
}
