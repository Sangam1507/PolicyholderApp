import java.util.*;
import java.text.DecimalFormat;

// PolicyHolder Class
class PolicyHolder {
    private String policyId;
    private String name;
    private Double investmentAmount;
    private Integer yearsInForce;
    
    // Constructor
    public PolicyHolder(String policyId, String name, Double investmentAmount, Integer yearsInForce) {
        this.policyId = policyId;
        this.name = name;
        this.investmentAmount = investmentAmount;
        this.yearsInForce = yearsInForce;
    }
    
    // Getters and Setters
    public String getPolicyId() {
        return policyId;
    }
    
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getInvestmentAmount() {
        return investmentAmount;
    }
    
    public void setInvestmentAmount(Double investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
    
    public Integer getYearsInForce() {
        return yearsInForce;
    }
    
    public void setYearsInForce(Integer yearsInForce) {
        this.yearsInForce = yearsInForce;
    }
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "Policy ID: " + policyId + 
               ", Name: " + name + 
               ", Investment: ₹" + df.format(investmentAmount) + 
               ", Years: " + yearsInForce;
    }
}

// Main Application Class
public class PolicyManagementConsole {
    private static List<PolicyHolder> policyHolders = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Policy Holder Management Console ===");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addPolicyHolder();
                    break;
                case 2:
                    viewAllPolicies();
                    break;
                case 3:
                    calculateMaturityForAll();
                    break;
                case 4:
                    findHighValuePolicies();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using Policy Management Console!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add Policy Holder");
        System.out.println("2. View All Policies");
        System.out.println("3. Calculate Maturity Values");
        System.out.println("4. Find High Value Policies");
        System.out.println("5. Exit");
        System.out.println("================");
    }
    
    private static void addPolicyHolder() {
        System.out.println("\n--- Add New Policy Holder ---");
        
        System.out.print("Enter Policy ID: ");
        String policyId = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        double investmentAmount = getDoubleInput("Enter Investment Amount: ");
        int yearsInForce = getIntInput("Enter Years in Force: ");
        
        PolicyHolder policyHolder = new PolicyHolder(policyId, name, investmentAmount, yearsInForce);
        policyHolders.add(policyHolder);
        
        System.out.println("Policy holder added successfully!");
    }
    
    private static void viewAllPolicies() {
        System.out.println("\n--- All Policy Holders ---");
        if (policyHolders.isEmpty()) {
            System.out.println("No policy holders found.");
            return;
        }
        
        for (int i = 0; i < policyHolders.size(); i++) {
            System.out.println((i + 1) + ". " + policyHolders.get(i));
        }
    }
    
    private static void calculateMaturityForAll() {
        System.out.println("\n--- Maturity Values Calculation ---");
        if (policyHolders.isEmpty()) {
            System.out.println("No policy holders found.");
            return;
        }
        
        for (PolicyHolder holder : policyHolders) {
            double maturityValue = calculateMaturityValue(
                holder.getInvestmentAmount(), 
                holder.getYearsInForce()
            );
            DecimalFormat df = new DecimalFormat("#,##0.00");
            System.out.println(holder.getName() + 
                " - Investment: ₹" + df.format(holder.getInvestmentAmount()) + 
                " | Maturity Value: ₹" + df.format(maturityValue));
        }
    }
    
    // Static method to calculate maturity value
    public static double calculateMaturityValue(double investmentAmount, int yearsInForce) {
        double annualReturnRate = 0.08; // 8% fixed annual return
        double futureValue = investmentAmount * Math.pow(1 + annualReturnRate, yearsInForce);
        return Math.round(futureValue * 100.0) / 100.0; // Round to 2 decimal places
    }
    
    // Static method to find high value policies
    public static void findHighValuePolicies(List<PolicyHolder> listPolicyHolders) {
        System.out.println("\n--- High Value Policies (Investment > ₹1,00,000) ---");
        boolean found = false;
        
        for (PolicyHolder holder : listPolicyHolders) {
            if (holder.getInvestmentAmount() > 100000) {
                System.out.println(holder);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No high value policies found.");
        }
    }
    
    // Helper method for high value policies (calls the static method)
    private static void findHighValuePolicies() {
        findHighValuePolicies(policyHolders);
    }
    
    // Utility methods for input validation
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}