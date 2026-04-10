// The Base Class
abstract class User {
    protected String name;
    protected String role;

    public User(String name, String role) { this.name = name; this.role = role; }
    public abstract void showPermissions(); // Every user must explain what they can do
}

// Subclass for Manager
class Manager extends User {
    public Manager(String name) { super(name); this.role = "MANAGER"; }
    public void showPermissions() {
        System.out.println("Role: Manager. I can Finalize tasks to the Vault.");
    }
}

// Subclass for Worker
class Worker extends User {
    public Worker(String name) { super(name); this.role = "WORKER"; }
    public void showPermissions() {
        System.out.println("Role: Worker. I can send Chat messages only.");
    }
}
