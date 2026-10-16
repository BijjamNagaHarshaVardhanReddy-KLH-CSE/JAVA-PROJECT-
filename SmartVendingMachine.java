
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

// MODULE 4: Interface
interface Payable {
    boolean pay(double amount);
}

// MODULE 4: Abstract class and encapsulation
abstract class Product {
    private final int id;
    private final String name;
    private final double price;
    private int stock;
    private final String category;

    public Product(int id, String name, double price,
                   int stock, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format(
            "%-4d %-20s Rs.%-8.2f Stock: %-3d [%s]",
            id, name, price, stock, getType()
        );
    }
}

// MODULE 4: Inheritance and method overriding
class SmartProduct extends Product {
    public SmartProduct(int id, String name, double price,
                        int stock, String category) {
        super(id, name, price, stock, category);
    }

    @Override
    public String getType() {
        return getCategory();
    }
}

// MODULE 4: Interface implementation
class DigitalPayment implements Payable {
    @Override
    public boolean pay(double amount) {
        return Double.isFinite(amount) && amount >= 0;
    }
}

public class SmartVendingMachine {

    // MODULE 1: Constants and static members
    private static final String MACHINE_NAME =
        "NEOTECH AI VENDING SYSTEM";
    private static final String FILE_NAME = "transactions.txt";
    private static final Scanner sc = new Scanner(System.in);

    // MODULE 3: One-dimensional array
    private static final Product[] products = {
        new SmartProduct(101, "Lays Chips", 20, 10, "SNACK"),
        new SmartProduct(102, "Oreo Biscuits", 30, 8, "SNACK"),
        new SmartProduct(103, "Kurkure", 20, 12, "SNACK"),
        new SmartProduct(104, "KitKat", 40, 5, "SNACK"),
        new SmartProduct(105, "Coca-Cola", 40, 7, "DRINK"),
        new SmartProduct(106, "Sprite", 40, 10, "DRINK"),
        new SmartProduct(107, "Red Bull", 125, 5, "DRINK"),
        new SmartProduct(108, "Dairy Milk", 50, 8, "CHOCOLATE"),
        new SmartProduct(109, "Hide & Seek", 35, 6, "SNACK"),
        new SmartProduct(110, "Bingo Mad Angles", 20, 9, "SNACK")
    };

    // MODULE 3: Two-dimensional array
    private static final int[][] stockMatrix = {
        {10, 8},
        {12, 5},
        {7, 10},
        {5, 8},
        {6, 9}
    };

    // MODULE 6: Collections framework
    private static final List<String> cart = new ArrayList<>();
    private static final Map<String, Integer> sales = new HashMap<>();
    private static final Set<String> categories = new TreeSet<>();

    private static double totalRevenue = 0.0;
    private static int transactionCount = 0;

    public static void main(String[] args) {
        initializeMachine();

        boolean running = true;

        System.out.println("\nBooting futuristic systems...");
        System.out.println("AI Core: ONLINE");
        System.out.println("Inventory: READY");
        System.out.println("Digital Payment: READY");
        System.out.println("System Status: ACTIVE");

        // MODULE 2: while loop
        while (running) {
            showMenu();
            int choice = readInt("Select an option: ");

            // MODULE 2: switch statement
            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    purchaseProduct();
                    break;
                case 3:
                    recommendProducts();
                    break;
                case 4:
                    showAnalytics();
                    break;
                case 5:
                    showStockMatrix();
                    break;
                case 6:
                    viewTransactions();
                    break;
                case 0:
                    running = false;
                    System.out.println(
                        "\nNEOTECH shutting down. Thank you!"
                    );
                    break;
                default:
                    System.out.println(
                        "Invalid option. Please try again."
                    );
            }
        }

        sc.close();
    }

    // MODULE 3: Initialization method and enhanced for loop
    private static void initializeMachine() {
        for (Product p : products) {
            categories.add(p.getCategory());
            sales.put(p.getName(), 0);
        }
    }

    private static void showMenu() {
        System.out.println("\n==========================================");
        System.out.println("       " + MACHINE_NAME);
        System.out.println("==========================================");
        System.out.println("1. View Products");
        System.out.println("2. Purchase Product");
        System.out.println("3. Smart Recommendations");
        System.out.println("4. Sales Analytics");
        System.out.println("5. Inventory Matrix");
        System.out.println("6. Transaction History");
        System.out.println("0. Shutdown System");
        System.out.println("==========================================");
    }

    // MODULE 3: Array traversal
    private static void displayProducts() {
        System.out.println("\n--- AVAILABLE PRODUCTS ---");

        for (Product p : products) {
            System.out.println(p);
        }
    }

    // MODULE 2: Conditions and MODULE 3: Methods
    private static void purchaseProduct() {
        displayProducts();

        int id = readInt("\nEnter product ID: ");

        Product selected = findProductRecursive(id, 0);

        if (selected == null) {
            System.out.println("ERROR: Product not found.");
            return;
        }

        if (selected.getStock() <= 0) {
            System.out.println("OUT OF STOCK!");
            return;
        }

        System.out.println("Selected product: " + selected.getName());

        String confirm = readText(
            "Confirm purchase? (YES/NO): "
        );

        // MODULE 5: String comparison
        if (!confirm.equalsIgnoreCase("YES")) {
            System.out.println("Purchase cancelled.");
            return;
        }

        int quantity = readInt("Enter quantity: ");

        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        if (quantity > selected.getStock()) {
            System.out.println("Insufficient stock available.");
            return;
        }

        double subtotal = selected.getPrice() * quantity;

        // MODULE 1: Ternary operator
        double discount = subtotal >= 100
            ? subtotal * 0.05 : 0.0;

        double finalAmount = subtotal - discount;

        System.out.printf("Subtotal: Rs.%.2f%n", subtotal);
        System.out.printf("Discount: Rs.%.2f%n", discount);
        System.out.printf("Final amount: Rs.%.2f%n", finalAmount);

        double payment = readDouble("Enter payment amount: ");

        Payable gateway = new DigitalPayment();

        if (!gateway.pay(payment)) {
            System.out.println("Invalid payment.");
            return;
        }

        if (payment < finalAmount) {
            System.out.println(
                "Insufficient payment. Transaction cancelled."
            );
            return;
        }

        // Update inventory only after successful payment
        selected.setStock(selected.getStock() - quantity);

        totalRevenue += finalAmount;
        transactionCount++;

        sales.put(
            selected.getName(),
            sales.get(selected.getName()) + quantity
        );

        // MODULE 6: List collection
        cart.add(selected.getName() + " x " + quantity);

        double change = payment - finalAmount;

        System.out.println("\n==========================================");
        System.out.println("           DIGITAL RECEIPT");
        System.out.println("==========================================");
        System.out.println("Machine  : " + MACHINE_NAME);
        System.out.println("Product  : " + selected.getName());
        System.out.println("Quantity : " + quantity);
        System.out.printf("Subtotal : Rs.%.2f%n", subtotal);
        System.out.printf("Discount : Rs.%.2f%n", discount);
        System.out.printf("Total    : Rs.%.2f%n", finalAmount);
        System.out.printf("Paid     : Rs.%.2f%n", payment);
        System.out.printf("Change   : Rs.%.2f%n", change);
        System.out.println("Status   : PAYMENT SUCCESSFUL");
        System.out.println("==========================================");

        saveTransaction(selected, quantity, finalAmount, change);
    }

    // MODULE 3: Recursion with base and recursive cases
    private static Product findProductRecursive(int id, int index) {
        if (index >= products.length) {
            return null;
        }

        if (products[index].getId() == id) {
            return products[index];
        }

        return findProductRecursive(id, index + 1);
    }

    // MODULE 6: Streams, filter, sorted and collect
    private static void recommendProducts() {
        System.out.println("\n--- SMART RECOMMENDATION ENGINE ---");
        System.out.println("Available categories: " + categories);

        String category = readText(
            "Choose category (SNACK, DRINK, CHOCOLATE): "
        ).toUpperCase(Locale.ROOT);

        List<Product> recommendations = Arrays.stream(products)
            .filter(p -> p.getCategory().equals(category))
            .filter(p -> p.getStock() > 0)
            .sorted(Comparator.comparingDouble(Product::getPrice))
            .collect(Collectors.toList());

        if (recommendations.isEmpty()) {
            System.out.println(
                "No products available in this category."
            );
        } else {
            System.out.println("Recommended products:");

            recommendations.forEach(System.out::println);
        }
    }

    // MODULE 6: Stream processing and grouping
    private static void showAnalytics() {
        System.out.println("\n--- SALES ANALYTICS DASHBOARD ---");

        System.out.printf(
            "Total Revenue: Rs.%.2f%n", totalRevenue
        );

        System.out.println(
            "Completed Transactions: " + transactionCount
        );

        System.out.println("\nProducts by Category:");

        Map<String, Long> report = Arrays.stream(products)
            .collect(Collectors.groupingBy(
                Product::getCategory,
                TreeMap::new,
                Collectors.counting()
            ));

        report.forEach((category, count) ->
            System.out.println(category + ": " + count + " products")
        );

        System.out.println("\nUnits Sold:");

        sales.forEach((name, quantity) ->
            System.out.println(name + " -> " + quantity)
        );

        // MODULE 5: Sorting using Arrays.sort and Comparator
        Product[] sortedProducts = products.clone();

        Arrays.sort(
            sortedProducts,
            Comparator.comparingDouble(Product::getPrice)
        );

        System.out.println("\nProducts Sorted by Price:");

        for (Product p : sortedProducts) {
            System.out.printf(
                "%-20s Rs.%.2f%n", p.getName(), p.getPrice()
            );
        }

        // MODULE 1: Bitwise operator
        int loyaltyPoints = transactionCount << 1;

        System.out.println(
            "\nDigital Loyalty Points: " + loyaltyPoints
        );
    }

    // MODULE 3: Two-dimensional array traversal and summation
    private static void showStockMatrix() {
        System.out.println("\n--- INVENTORY MATRIX ---");

        int total = 0;

        for (int i = 0; i < stockMatrix.length; i++) {
            int rowTotal = 0;

            for (int j = 0; j < stockMatrix[i].length; j++) {
                System.out.print(stockMatrix[i][j] + "\t");
                rowTotal += stockMatrix[i][j];
            }

            System.out.println("| Row total: " + rowTotal);
            total += rowTotal;
        }

        System.out.println("Matrix total: " + total);
        System.out.println(
            "Note: This is a sample matrix, not live stock."
        );
    }

    // MODULE 5: Append transactions to a text file
    private static void saveTransaction(
        Product p, int quantity, double amount, double change
    ) {
        String time = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        StringBuilder record = new StringBuilder();

        record.append(time)
              .append(" | Product: ").append(p.getName())
              .append(" | Quantity: ").append(quantity)
              .append(" | Amount: ")
              .append(String.format(Locale.ROOT, "%.2f", amount))
              .append(" | Change: ")
              .append(String.format(Locale.ROOT, "%.2f", change))
              .append(System.lineSeparator());

        try {
            Files.write(
                Paths.get(FILE_NAME),
                record.toString().getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );

            System.out.println(
                "Transaction saved to " + FILE_NAME
            );
        } catch (IOException e) {
            System.out.println(
                "Could not save transaction: " + e.getMessage()
            );
        }
    }

    // MODULE 5: Read transaction history using try-with-resources
    private static void viewTransactions() {
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) {
            System.out.println("No saved transactions yet.");
            return;
        }

        System.out.println("\n--- TRANSACTION HISTORY ---");

        try (java.io.BufferedReader reader =
                 Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println(
                "Could not read transactions: " + e.getMessage()
            );
        }
    }

    // MODULE 1 and MODULE 5: Validated integer input
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid input. Enter a whole number."
                );
            }
        }
    }

    // MODULE 1: Validated numeric input
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                double value = Double.parseDouble(
                    sc.nextLine().trim()
                );

                if (Double.isFinite(value) && value >= 0) {
                    return value;
                }

                System.out.println(
                    "Enter a valid non-negative amount."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid amount. Enter a number."
                );
            }
        }
    }

    // MODULE 5: String trimming
    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
