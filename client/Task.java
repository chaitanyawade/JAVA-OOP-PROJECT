import java.io.Serializable;

public class Task implements Serializable {
    public String content;
    public String version;
    public String industry;
    public boolean isFinalized;
    public String finalizedBy; // Name of the manager who approved it
    public String timestamp; // When it was moved to the Vault

    public Task(String content, String industry) {
        this.content = content;
        this.industry = industry;
        this.isFinalized = false;
        this.version = "DRAFT";
    }

    public void printIndustrySpecs() {
        if (this.industry.equalsIgnoreCase("Healthcare")) {
            System.out.println("   [SECURE]: This task follows HIPAA privacy rules.");
        } else if (this.industry.equalsIgnoreCase("Construction")) {
            System.out.println("   [SAFETY]: This task requires a blueprint check.");
        } else {
            System.out.println("   [GENERAL]: Standard workflow applied.");
        }
    }
}
