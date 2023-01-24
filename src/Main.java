import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    private static final Product[] products = {
            new Product("Хлеб", 60.0),
            new Product("Молоко", 80.0),
            new Product("Яблоки (1 кг.)", 140.0),
    };

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        String s;
        Basket shoppingCart = new Basket(products);
        int selectedItem;
        int itemCount;
        File basketFile = new File("basket.txt");
        if (basketFile.exists()) {
            System.out.println("Перезаписать список?");
            if (sc.nextLine().equals("")) {
                shoppingCart = Basket.loadFromTxtFile(basketFile);
            }
        }
        while (true) {
            shoppingCart.printProductsList();
            s = sc.nextLine();
            String[] inputValues = s.split(" ");
            if (inputValues.length == 2) {
                try {
                    selectedItem = Integer.parseInt(inputValues[0]);
                    itemCount = Integer.parseInt(inputValues[1]);
                    if (selectedItem <= 0 || selectedItem > products.length) {
                        System.out.print("\nНеправильный номер продукта\n");
                        continue;
                    }
                    if (itemCount <= 0) {
                        continue;
                    }
                    shoppingCart.addToCart(selectedItem - 1, itemCount);
                    shoppingCart.saveTxt(basketFile);
                } catch (NumberFormatException nfe) {
                    System.out.println("\nИспользовать нужно целые числа");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (s.equals("end")) {
                break;
            }
            System.out.println("\n");
        }
        sc.close();
        shoppingCart.printCart();
    }
}
