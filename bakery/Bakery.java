import java.util.*;
import java.util.stream.IntStream;

public class Bakery {
    public static List<List<String>> orders = new ArrayList<>();
    public static List<List<String>> stock = new ArrayList<>();
    public static List<List<String>> payments = new ArrayList<>();

    public static void order(String type,String customerName,String item,int amount) {
        int index = IntStream.range(0, stock.size()).filter(i -> stock.get(i).get(0).equals(item)).findFirst().orElse(-1);
        if(type.equals("new") && index != -1 && Integer.parseInt(stock.get(index).get(1)) >= amount) {
            orders.add(new ArrayList<>(Arrays.asList("pending",customerName,item,Integer.toString(amount))));
            stock.get(index).set(1,Integer.toString(Integer.parseInt(stock.get(index).get(1))-amount));
            System.out.println("Order has been placed.");
        }
        else if(type.equals("close") && orders.indexOf(new ArrayList<>(Arrays.asList("pending",customerName,item,Integer.toString(amount)))) != -1){
            orders.get(orders.indexOf(new ArrayList<>(Arrays.asList("pending",customerName,item,Integer.toString(amount))))).set(0,"closed");
            System.out.println("Order has been closed.");
        }
        else if(index == -1 || Integer.parseInt(stock.get(index).get(1)) <= amount) {
            System.out.println("Item stock is too low or item doesn't exist.");
        } else {
            System.out.println("That order doesn't exist. Dumbass.");
        }
    }
    public static void stock(String item,int amount) {
        int index = IntStream.range(0, stock.size()).filter(i -> stock.get(i).get(0).equals(item)).findFirst().orElse(-1);
        if (index != -1) {
            stock.get(index).set(1,Integer.toString(amount));
            System.out.println("Amount updated.");
        }
        else if (amount != -1){
            stock.add(new ArrayList<>(Arrays.asList(item,Integer.toString(amount))));
            System.out.println("Item added.");
        } else {
            stock.get(index).remove(index);
            System.out.println("Item removed from stock.");
        }
    }
    public static void close() {}
    public static void newCommand() {
        Scanner input = new Scanner(System.in); String command = input.nextLine()+" ";
        switch(command.split(" ")[0].trim()){
            case"order": order(command.split(" ")[1].trim(),
                    command.split(" ")[2].trim(),
                    command.split(" ")[3].trim(),
                    Integer.parseInt(command.split(" ")[4].trim()));break;
            case"stock": stock(command.split(" ")[1].trim(),
                    Integer.parseInt(command.split(" ")[2].trim()));break;
            case"close": close();break;
        }
    }

    public static void main(String[] args) {
        while(true){
            newCommand();
        }
    }
}
