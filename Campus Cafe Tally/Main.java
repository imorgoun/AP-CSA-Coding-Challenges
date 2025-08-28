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

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter coffee count for coffee: ");
        coffeeCount = scan.nextInt();

        System.out.print("Enter coffee count for coffee: ");
        teaCount = scan.nextInt();

        System.out.print("Enter coffee count for coffee: ");
        pastryCount = scan.nextInt();

        scan.close();

        double subTotal = (coffeeCount * COFFEE_PRICE) + (teaCount * TEA_PRICE) + (pastryCount * PASTRY_COST);
        String total = String.format("$%.2f", subTotal * (SERVICE_FEE + VAT));

        System.out.print(total);
    }
}