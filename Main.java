class Product {
    String name;
    double price;

    // Constructor
    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Method overloading
    double calculateBill(int quantity) {
        return price * quantity;
    }

    double calculateBill(int quantity, double discount) {
        double total = price * quantity;
        return total - (total * discount / 100);
    }

    // Method to be overridden
    void display() {
        System.out.println("Product: " + name);
        System.out.println("Price: Rs." + price);
    }
}

class SmartProduct extends Product {

    // Constructor
    SmartProduct(String name, double price) {
        super(name, price);
    }

    // Method overriding
    @Override
    void display() {
        System.out.println("Smart Vending Machine");
        System.out.println("Product: " + name);
        System.out.println("Price: Rs." + price);
        System.out.println("Available for purchase!");
    }
}

public class Main {
    public static void main(String[] args) {

        SmartProduct p = new SmartProduct("Chocolate", 50);

        p.display();

        System.out.println("Bill without discount: Rs."
                + p.calculateBill(3));

        System.out.println("Bill with 10% discount: Rs."
                + p.calculateBill(3, 10));
    }
}