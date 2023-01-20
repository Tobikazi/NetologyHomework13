import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
public class Basket {
    private final Product[] products;
    private double totalValue = 0;
    public Basket(Product[] products) {
        this.products = products.clone();
    }
    public void addToCart(int productNum, int amount) {
        products[productNum].changeItemInBasket(amount);
        totalValue += products[productNum].getPrice() * amount;
    }
    public void printProductsList() {
        System.out.print("Название Цена за шт. В корзине Стоимость\n" + "");
        double currentValue;
        totalValue = 0;
        for (int i = 0; i < products.length; i++) {
            currentValue = products[i].getInBasket() * products[i].getPrice();
            totalValue += currentValue;
            System.out.printf("%2d. %13s %12.2f %10d %17.2f\n", i + 1,
                    products[i].getName(), products[i].getPrice(),
                    products[i].getInBasket(), currentValue);
        }
        System.out.printf("Товаров в корзине на %10.2f\n\n", totalValue);
        System.out.print("Добавьте товар в корзину (номер и количество).");
        System.out.println("Для завершения работы введите end.");
    }
    public void printCart() {
        System.out.print("Ваша корзина:\n");
        for (Product item : products) {
            if (item.getInBasket() > 0) {
                System.out.printf("%13s %3d шт. %6.2f руб/шт. %8.2f в сумме\n", item.getName(), item.getInBasket(), item.getPrice(), item.getInBasket() * item.getPrice());
            }
        }
        System.out.printf("ИТОГО Товаров в корзине на %10.2f\n\n", totalValue);
    }
    public void saveTxt(File textFile) throws FileNotFoundException {
        var pw = new PrintWriter(textFile);
        Stream.of(products).forEach(p -> pw.printf("%s@%.4f@%d\n", p.getName(), p.getPrice(), p.getInBasket()));
        pw.close();
    }
    static Basket loadFromTxtFile(File textFile) throws FileNotFoundException, ParseException {
        Scanner sc = new Scanner(textFile);
        List<Product> products = new ArrayList<>();
        String name;
        double price;
        int inBasket;
        NumberFormat nf = NumberFormat.getInstance();
        while (sc.hasNext()) {
            String[] d = sc.nextLine().split(" ");
            name = d[0];
            price = nf.parse(d[1]).doubleValue();
            inBasket = Integer.parseInt(d[2]);
            products.add(new Product(name, price, inBasket));
        }
        return new Basket(products.toArray(Product[]::new));
    }
}