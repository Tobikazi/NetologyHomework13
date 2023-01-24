import java.io.*;
public class Basket implements Serializable {
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
    public void saveTxt(File file) throws IOException {
        var fileOutputStream = new FileOutputStream(file);
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }
    public static Basket loadFromTxtFile(File file) throws IOException, ClassNotFoundException {
        var fileInputStream = new FileInputStream(file);
        var objectInputStreams = new ObjectInputStream(fileInputStream);
        return (Basket) objectInputStreams.readObject();
    }
