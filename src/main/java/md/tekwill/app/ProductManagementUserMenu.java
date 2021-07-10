package md.tekwill.app;

import md.tekwill.ShoppingCart;
import md.tekwill.entity.product.Product;
import md.tekwill.service.ProductService;

import java.util.Scanner;

public class ProductManagementUserMenu {
    private final ProductService productService;
    private final ShoppingCart cart;
    private final Scanner scanner;

    public ProductManagementUserMenu(ProductService productService, ShoppingCart cart, Scanner scanner) {
        this.productService = productService;
        this.cart = cart;
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean exitOptionNotSelected;
        do {
            System.out.println("Available options: ");
            System.out.println("=================USER OPTIONS=============");
            System.out.println("[1] View all products");
            System.out.println("[2] View shopping cart");
            System.out.println("[3] Add product to shopping cart");
            System.out.println("[4] Print bill");
            System.out.println("==========================================");
            System.out.println("[0] Exit");
            System.out.println("==========================================");
            System.out.println(">> ");
            exitOptionNotSelected = handleUserChoice(scanner.nextInt());
        }
        while (exitOptionNotSelected);

    }

    private boolean handleUserChoice(int userChoice) {
        scanner.nextLine();
        switch (userChoice) {
            case 1:
                viewAllNonExpiredProducts();
                return true;
            case 2:
                viewShoppingCart();
                return true;
            case 3:
                addProductToShoppingCart();
                return true;
            case 4:
                printBill();
                return true;
            case 0:
                System.out.println("BYE!");
                return false;
            default:
                System.out.println("Unknown option selected! ");
                return true;
        }

    }

    private void printBill() {
        if (cart.getProductList().isEmpty()) {
            System.out.println("No products yet! ");
        } else {
            System.out.println("You want to purchase the following products: ");
            viewShoppingCart();
            System.out.println("Full price: " + cart.getPriceWithoutDiscount());
            System.out.println("DISCOUNT: " + cart.getSavedMoney());
            System.out.println("Total to pay: " + cart.getPrice());
        }


    }

    private void viewAllNonExpiredProducts() {
        for (Product p : productService.getAllNonExpired()) {
            System.out.println(p);
        }
    }

    private void viewShoppingCart() {
        System.out.println("--- SHOPPING CART CONTENT ---");
        if (cart.getProductList().isEmpty()) {
            System.out.println("Empty!");
        } else {
            System.out.println(cart.getProductList());
        }


    }


    private void addProductToShoppingCart() {
        try {
            System.out.println("Input the id of the item to add to cart: ");
            int optionID = scanner.nextInt();

            cart.addProduct(productService.getById(optionID));
            System.out.println("Product with id " + optionID + " successfully added!");
        } catch (RuntimeException ex) {
            scanner.nextLine();
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
