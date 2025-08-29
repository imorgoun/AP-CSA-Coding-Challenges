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

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter coffee count: ");
        coffeeCount = scan.nextInt();

        System.out.print("Enter coffee count: ");
        teaCount = scan.nextInt();

        System.out.print("Enter coffee count: ");
        pastryCount = scan.nextInt();

        scan.nextLine();

        System.out.print("Enter coupon code: ");
        couponCode = scan.nextLine();

        scan.close();

        double subTotal = (coffeeCount * COFFEE_PRICE) + (teaCount * TEA_PRICE) + (pastryCount * PASTRY_COST);
        String total = String.format("$%.2f", couponCode.equals("STUDENT10") ? (subTotal * (SERVICE_FEE + VAT - 0.1)) : (subTotal * (SERVICE_FEE + VAT)));

        System.out.print(total);
    }
}