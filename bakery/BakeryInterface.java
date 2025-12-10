import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BakeryInterface extends JFrame {
    private int displayWidth = 1000;
    private int displayHeight = 900;

    ArrayList<String> items = new ArrayList<>(Arrays.asList(
            "Bread", "Cake", "Donut", "Cookie", "Finn", "Illya"
    ));
    double[] prices = new double[] {10,12,12,13,67,13};

    private ArrayList<String> currentOrder = new ArrayList<>();

    private ArrayList<ArrayList<String>> pendingOrders = new ArrayList<>();

    private JButton createOrderButton;
    private JButton clearOrderButton;

    private JLabel priceLabel;

    public BakeryInterface() {
        setTitle("Order App");
        setSize(displayWidth, displayHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initializeItemButtons();
        initializeCreateOrderButton();
        initializeClearButton();
        updatePriceLabel();
        initializeSeparators();

        setVisible(true);
    }

    private void initializeItemButtons() {
        int initialHorizontalPosition = 50;
        int initialVerticalPosition = 150;
        int verticalOffset = 80;
        int horizontalOffset = 200;
        int itemsPerColumn = 3;
        int width = 200;
        int height = 80;

        for (String item : items) {
            JButton button = new JButton(item);
            button.setBounds(initialHorizontalPosition+(horizontalOffset*((int)items.indexOf(item)/itemsPerColumn)), initialVerticalPosition+(verticalOffset*(items.indexOf(item)%itemsPerColumn)), width, height);
            add(button);
            button.addActionListener(e -> {
                currentOrder.add(item);
                System.out.println("Added to order: " + item);
                updatePriceLabel();
            });
        }
    }

    private void initializeClearButton() {
        clearOrderButton = new JButton("Clear Order");
        clearOrderButton.setBounds(250, 50, 200, 80);
        add(clearOrderButton);

        clearOrderButton.addActionListener(e -> {
            currentOrder.clear();
            updatePriceLabel();
        });
    }

    private double calculateCurrentOrderPrice() {
        double currentOrderPrice = 0;
        for (String item : currentOrder) {
            currentOrderPrice += prices[items.indexOf(item)];
        }
        return currentOrderPrice;
    }

    private void initializeCreateOrderButton() {
        createOrderButton = new JButton("Submit Order");
        createOrderButton.setBounds(50, 50, 200, 80);
        add(createOrderButton);

        createOrderButton.addActionListener(e -> {
            System.out.println(calculateCurrentOrderPrice());
            pendingOrders.add(currentOrder);
            currentOrder.clear();
            updatePriceLabel();
        });
    }

    private void updatePriceLabel() {
        if (priceLabel == null) {
            priceLabel = new JLabel();
            priceLabel.setBounds(200, displayHeight - 100, 200, 30);
            add(priceLabel);
        }
        priceLabel.setText("Current Price: $" + calculateCurrentOrderPrice());
        repaint();
    }

    private void initializeSeparators() {
        int borderThickness = 3;
        int margin = 50;
        JSeparator horizontalSeparator1 = new JSeparator(SwingConstants.HORIZONTAL);
        horizontalSeparator1.setBounds(margin, 450, (displayWidth-(2*margin))/2, 5);
        horizontalSeparator1.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        add(horizontalSeparator1);
        JSeparator horizontalSeparator2 = new JSeparator(SwingConstants.HORIZONTAL);
        horizontalSeparator2.setBounds(margin, 750, (displayWidth-(2*margin))/2, 5);
        horizontalSeparator2.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        add(horizontalSeparator2);
        JSeparator verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
        verticalSeparator.setBounds(displayWidth/2, margin, 5, displayHeight-(2*margin));
        verticalSeparator.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        add(verticalSeparator);
    }

    private void updatePendingOrders() {
        for (ArrayList order : pendingOrders) {
            System.out.println(order.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BakeryInterface::new);
    }
}
