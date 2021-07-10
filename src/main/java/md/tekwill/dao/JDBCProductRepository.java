/*package md.tekwill.dao;

import md.tekwill.entity.product.Drink;
import md.tekwill.entity.product.Food;
import md.tekwill.entity.product.FoodCategory;
import md.tekwill.entity.product.Product;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.ds.common.BaseDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCProductRepository implements ProductRepository {

    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/product-manager";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "doctorhouse";

    private final BaseDataSource dataSource;

    public JDBCProductRepository() {
        this.dataSource = new PGSimpleDataSource();
        this.dataSource.setUrl(CONNECTION_URL);
        this.dataSource.setUser(USERNAME);
        this.dataSource.setPassword(PASSWORD);
    }

    @Override
    public void save(Product product) {
        String insertSQL = "INSERT INTO product(name, price, best_before, category, volume) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDate(3, Date.valueOf(product.getBestBefore()));

            if (product instanceof Food) {
                Food food = (Food) product;
                preparedStatement.setString(4, food.getCategory().name());
                preparedStatement.setNull(5, 0);
            }

            if (product instanceof Drink) {
                Drink drink = (Drink) product;
                preparedStatement.setString(4, null);
                preparedStatement.setDouble(5, drink.getVolume());
            }
            int row = preparedStatement.executeUpdate();
            System.out.println("Inserted " + row + " row(s)!");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                System.out.println("Saved product with " + generatedKeys.getInt(1) + " id ");
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String selectSQL = " SELECT id, name, price, best_before, category, volume FROM product";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                LocalDate bestBefore = resultSet.getDate("best_before").toLocalDate();
                String category = resultSet.getString("category");
                double volume = resultSet.getDouble("volume");
                if (category != null) {
                    Food food= new Food(name, price,bestBefore,FoodCategory.valueOf(category));
                    products.add(food);
                    food.setId(id);

                }
                if (volume != 0.0) {
                    Drink drink=new Drink(name, price, bestBefore, volume);
                    products.add(drink);
                    drink.setId(id);

                }

            }

            }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(int idToFind) {
        Product product = null;
        String selectSQL = "SELECT id, name, price, best_before, category, volume FROM product where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, idToFind);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                LocalDate bestBefore = resultSet.getDate("best_before").toLocalDate();
                String category = resultSet.getString("category");
                double volume = resultSet.getDouble("volume");
                if (category != null) {
                    product = new Food(name, price, bestBefore, FoodCategory.valueOf(category));

                }
                if (volume != 0.0) {
                    product = new Drink(name, price, bestBefore, volume);

                }
                if (product != null) {
                    product.setId(id);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return product;
    }

    @Override
    public Product findByName(String nameToFind) {
        Product product = null;
        String selectSQL = "SELECT id, name, price, best_before, category, volume FROM product where name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, nameToFind);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                LocalDate bestBefore = resultSet.getDate("best_before").toLocalDate();
                String category = resultSet.getString("category");
                double volume = resultSet.getDouble("volume");
                if (category != null) {
                    product = new Food(name, price, bestBefore, FoodCategory.valueOf(category));

                }
                if (volume != 0.0) {
                    product = new Drink(name, price, bestBefore, volume);

                }
                if (product != null) {
                    product.setId(id);
                }

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(int id, double volume) {
        String updateSQL = "UPDATE product SET volume = ? WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setDouble(1, volume);
            preparedStatement.setInt(2, id);

            int nrOfAffectedRows = preparedStatement.executeUpdate();
            if (nrOfAffectedRows > 0) {
                System.out.println("Successfully updated " + nrOfAffectedRows + " row(s)!");

            } else {
                System.out.println("No rows affected!");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    @Override
    public void update(int id, FoodCategory category) {
        String updateSQL = "UPDATE product SET category = ? WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, String.valueOf(category));
            preparedStatement.setInt(2, id);

            int nrOfAffectedRows = preparedStatement.executeUpdate();
            if (nrOfAffectedRows > 0) {
                System.out.println("Successfully updated " + nrOfAffectedRows + " row(s)!");

            } else {
                System.out.println("No rows affected!");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String deleteSQL = "DELETE FROM product WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            int nrOfAffectedRows = preparedStatement.executeUpdate();
            if (nrOfAffectedRows > 0) {
                System.out.println("Successfully deleted " + nrOfAffectedRows + " row(s)!");

            } else {
                System.out.println("No rows deleted!");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
*/