import java.sql.*;
import java.util.Scanner;
public class main{
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "LUFFYtaroo111&&&";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS favourite_manhwas (" +
            "id BIGINT PRIMARY KEY," +
            "manhwa_name VARCHAR(100) NOT NULL," +
            "genre VARCHAR(200) NOT NULL," +
            "chapters INT NOT NULL," +
            "rate INT NOT NULL" +
            ")";

    private static final String INSERT_QUERY = "INSERT INTO favourite_manhwas (id,manhwa_name, genre, chapters, rate) VALUES (?,?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM favourite_manhwas";
    private static final String UPDATE_QUERY = "UPDATE favourite_manhwas SET manhwa_name = ?, genre = ?, chapters = ?, rate = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM favourite_manhwas WHERE id = ?";
    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            createTable(connection);

            boolean exit = false;
            Scanner scanner = new Scanner(System.in);
            while (!exit) {
                System.out.println("Choose an option:");
                System.out.println("1. Create a new manhwa");
                System.out.println("2. Read manhwa data");
                System.out.println("3. Update manhwa data");
                System.out.println("4. Delete a manhwa");
                System.out.println("5. Exit");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        createManhwa(connection, scanner);
                        break;
                    case 2:
                        readManhwa(connection);
                        break;
                    case 3:
                        updateManhwa(connection, scanner);
                        break;
                    case 4:
                        deleteManhwa(connection, scanner);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }

            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(CREATE_TABLE_QUERY);
        statement.close();
    }

    private static void createManhwa(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter manhwa name: ");
        String name = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();


        System.out.print("Enter number of chapters: ");
        int chapters = scanner.nextInt();

        System.out.print("Enter rate: ");
        int rate = scanner.nextInt();


        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, genre);
        preparedStatement.setInt(4, chapters);
        preparedStatement.setInt(5, rate);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Manhwa created successfully.");
    }

    private static void readManhwa(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY);

        System.out.println("id\tmanhwa_name\tgenre\tchapters\trate");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("manhwa_name");
            String genre = resultSet.getString("genre");
            int chapters = resultSet.getInt("chapters");
            int rate = resultSet.getInt("rate");

            System.out.println(id + "\t" + name + "\t" + genre + "\t" + chapters + "\t" + rate);
        }

        resultSet.close();
        statement.close();
    }

    private static void updateManhwa(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter manhwa ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new manhwa name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter new number of chapters: ");
        int chapters = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new rate: ");
        int rate = scanner.nextInt();
        scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, genre);
        preparedStatement.setInt(3, chapters);
        preparedStatement.setInt(4, rate);
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Manhwa updated successfully.");
    }

    private static void deleteManhwa(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter manhwa ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Manhwa deleted successfully.");
    }
}
