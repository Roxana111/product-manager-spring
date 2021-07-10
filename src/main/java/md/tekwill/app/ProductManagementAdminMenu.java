/*package md.tekwill.app;

import md.tekwill.entity.product.FoodCategory;
import md.tekwill.entity.product.Product;
import md.tekwill.exceptions.ProductUpdateUnknownPropertyException;
import md.tekwill.service.ProductService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class ProductManagementAdminMenu {
    private final Scanner scanner;
    private final ProductService productService;

    ProductManagementAdminMenu(Scanner scanner, ProductService productService) {
        this.scanner = scanner;
        this.productService = productService;
    }

    public void showMenu() {
        boolean exitOptionNotSelected;
        do {
            System.out.println("Available options: ");
            System.out.println("=================ADMIN OPTIONS=============");
            System.out.println("[1] View all products");
            System.out.println("[2] View all expired products");
            System.out.println("[3] Add new product ");
            System.out.println("[4] Update food product");
            System.out.println("[5] Update drink product");
            System.out.println("[6] Remove product");
            System.out.println("==========================================");
            System.out.println("[0] Exit");
            System.out.println("==========================================");
            System.out.println(">> ");
            exitOptionNotSelected = handleAdminChoice(scanner.nextInt());
        }
        while (exitOptionNotSelected);
    }

    private boolean handleAdminChoice(int adminChoice) {
        scanner.nextLine();
        switch (adminChoice) {
            case 1:
                viewAllProducts();
                return true;
            case 2:
                viewAllExpiredProducts();
                return true;
            case 3:
                addNewProduct();
                return true;
            case 4:
                updateFood();
                return true;
            case 5:
                updateDrink();
                return true;
            case 6:
                removeProduct();
                return true;
            case 0:
                System.out.println("BYE!");
                return false;
            default:
                System.out.println("Unknown option selected! ");
                return true;
        }

    }

    private void addNewProduct() throws DateTimeParseException {
        System.out.println("--- ADD NEW PRODUCT ---");
        try {
            System.out.println("Input the name of the product:  ");
            String name = scanner.nextLine();
            System.out.println("Input the price of the product: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Input the best before of the product (such as \"2021-12-03\" )");
            String bestBeforeString = scanner.nextLine();
            LocalDate bestBefore = LocalDate.parse(bestBeforeString);
            System.out.println("Is it a food or a drink? ");
            if ("food".equalsIgnoreCase(scanner.nextLine())) {
                System.out.println("What category is that? CATEGORIES: " + Arrays.toString(FoodCategory.values()) + ")");
                FoodCategory foodCategory = FoodCategory.valueOf(scanner.nextLine());
                productService.create(name, price, bestBefore, foodCategory);
                System.out.println("Food " + name + " successfully created!");
            } else {
                System.out.println("Input the volume of the drink: ");
                double volume = scanner.nextDouble();
                scanner.nextLine();
                productService.create(name, price, bestBefore, volume);
                System.out.println("Drink " + name + "successfully created! ");
            }
        } catch (RuntimeException ex) {
            scanner.nextLine();
            System.out.println("Error: " + ex.getMessage());
        }


    }

    private void updateFood() {
        try {
            System.out.println("Insert the ID of food to update: ");
            int foodId = scanner.nextInt();
            System.out.println("Choose the category to update (CATEGORIES:" + Arrays.toString(FoodCategory.values()) + ")");
            scanner.nextLine();
            FoodCategory foodCategory = FoodCategory.valueOf(scanner.nextLine());
            productService.update(foodId, foodCategory);
            System.out.println("Product with ID " + foodId + "successfully updated!");
        } catch (ProductUpdateUnknownPropertyException e) {
            System.out.println(e.getMessage());

        }
    }

    private void updateDrink() {
        try {
            System.out.println("Insert the ID of drink to update: ");
            int drinkId = scanner.nextInt();
            System.out.println("Enter the new drink volume");
            double volume = scanner.nextDouble();
            scanner.nextLine();
            productService.update(drinkId, volume);
            System.out.println("Product with ID " + drinkId + " successfully updated!");
        } catch (ProductUpdateUnknownPropertyException ex) {
            System.out.println(ex.getMessage());
        }


    }

    private void removeProduct() {
        System.out.println("Enter the id of the product you'd want to remove ");
        int id = scanner.nextInt();
        productService.delete(id);
        System.out.println("Product with ID " + id + "successfully removed!");
    }

    private void viewAllProducts() {
        System.out.println("-----ALL EXISTING PRODUCTS------");
        for (Product p : productService.getAll()) {
            System.out.println(p);
        }
    }

    private void viewAllExpiredProducts() {
        System.out.println("-----ALL EXPIRED PRODUCTS-----");
        for (Product p : productService.getAllExpired()) {
            System.out.println(p);
        }
    }


}*/
