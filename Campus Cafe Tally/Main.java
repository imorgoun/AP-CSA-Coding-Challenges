import java.util.Scanner;

public class Main {
    static double COFFEE_PRICE = 2.10;
    static double TEA_PRICE = 1.60;
    static double PASTRY_COST = 2.75;

    static double VAT = 1.21;
    static double SERVICE_FEE = 1.30;

    public static void main(String[] args) {
        int coffeeCount;
        int teaCount;
        int pastryCount;

        String couponCode;

        System.out.println("---------------");
        System.out.println("Menu");
        System.out.println("Coffee ($2.10)");
        System.out.println("Tea ($1.60)");
        System.out.println("Pastry ($2.75)");
        System.out.println("---------------");

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter coffee count: ");
        coffeeCount = scan.nextInt();

        System.out.print("Enter tea count: ");
        teaCount = scan.nextInt();

        System.out.print("Enter pastry count: ");
        pastryCount = scan.nextInt();

        scan.nextLine();

        System.out.print("Enter coupon code: ");
        couponCode = scan.nextLine();

        scan.close();

        System.out.println("---------------");

        double subTotal = (coffeeCount * COFFEE_PRICE) + (teaCount * TEA_PRICE) + (pastryCount * PASTRY_COST);
        String total = String.format("$%.2f", couponCode.equals("STUDENT10") ? (subTotal * (SERVICE_FEE + VAT - 0.1)) : (subTotal * (SERVICE_FEE + VAT)));

        System.out.println("Your total is : " + total);
    }
}