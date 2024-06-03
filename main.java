        import java.sql.*;
        import java.util.*;
        import java.util.Date;

        // Abstract class for database operations
        abstract class DatabaseOperation {
            protected Connection connection;
            protected Statement statement;
            protected ResultSet resultSet;
            protected PreparedStatement preparedStatement;

            public DatabaseOperation(String url, String username, String password) throws SQLException {
                // Connects to the database
                connection = DriverManager.getConnection(url, username, password);

                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
        }

        interface Viewable {
            void viewDatabase() throws SQLException;

        }

        // Class for FYPL database operations
        class FYPLDatabaseOperation extends DatabaseOperation implements Viewable {
            public FYPLDatabaseOperation(String url, String username, String password) throws SQLException {
                // Constructor for fypldatabase, takes url of database, username , and password, all of which is required for MySQL.
                super(url, username, password);
            }
           @Override
            public void viewDatabase() throws SQLException {
                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Selects & retrieves all the data from fypldatabase.
                resultSet = statement.executeQuery("SELECT * FROM final_project_schema.fypldatabase;");

                // No first row or data means there are no students.
                if (!resultSet.first()) {
                    System.out.println("There are no students on the database");
                }
                else {
                    // If there are students, it will loop through each row and display each column.
                    do {
                        System.out.println("---------");
                        System.out.println("NIM: " + resultSet.getString("NIM"));
                        System.out.println("Name: " + resultSet.getString("Name"));
                        System.out.println("Email: " + resultSet.getString("Email"));
                        System.out.println("Contact Number: " + resultSet.getString("Contact Number"));
                        System.out.println("Major: " + resultSet.getString("Major"));
                        System.out.println("Position: " + resultSet.getString("Position"));
                        System.out.println("Division: " + resultSet.getString("Division"));
                    }
                    // Pushes the selected data to the next row to continue the loop.
                    while (resultSet.next());
                }
            }
            public void insertRow() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Selects & retrieves all the data from the fypldatabase.
                resultSet = statement.executeQuery("SELECT * FROM final_project_schema.fypldatabase;");

                try {
                    // Asks user for NIM, must be integers, put in the form of Long since NIMs are large numbers.
                    System.out.print("NIM: ");
                    while (!scanner.hasNextLong()) {
                        System.out.println("Error: NIM must be an integer.");
                        System.out.print("NIM: ");
                        scanner.next(); // Discard the invalid input
                    }
                    Long nim = scanner.nextLong();

                    // Asks for a name string
                    System.out.print("Name: ");
                    String name = scanner.next();

                    // Asks for an email string, must contain '@' and '.'.
                    System.out.print("Email: ");
                    String email = scanner.next();
                    while (!email.contains("@") || !email.contains(".")) {
                        System.out.println("Error: Email must contain '@' & '.' symbol.");
                        System.out.print("Email: ");
                        email = scanner.next();
                    }

                    // Asks for integers, in the form of Long since contact numbers are large numbers.
                    System.out.print("Contact Number: ");
                    while (!scanner.hasNextLong()) {
                        System.out.println("Error: Contact Number must be an integer.");
                        System.out.print("Contact Number: ");
                        scanner.next(); // Discard the invalid input
                    }
                    Long contactNumber = scanner.nextLong();

                    // Asks user to pick from 1-8 for major.
                    System.out.println("Major: ");
                    System.out.println("1. Computer Science");
                    System.out.println("2. Business Information Systems");
                    System.out.println("3. Fashion Design");
                    System.out.println("4. Graphic Design and New Media");
                    System.out.println("5. Communication");
                    System.out.println("6. International Business");
                    System.out.println("7. Business Management");
                    System.out.println("8. Finance");
                    String major;
                    while (true) {
                        System.out.print("Choose major (1-8): ");
                        int majorChoice = scanner.nextInt();
                        switch (majorChoice) {
                            case 1:
                                major = "Computer Science";
                                break;
                            case 2:
                                major = "Business Information Systems";
                                break;
                            case 3:
                                major = "Fashion Design";
                                break;
                            case 4:
                                major = "Graphic Design and New Media";
                                break;
                            case 5:
                                major = "Communication";
                                break;
                            case 6:
                                major = "International Business";
                                break;
                            case 7:
                                major = "Business Management";
                                break;
                            case 8:
                                major = "Finance";
                                break;
                            default:
                                System.out.println("Error: Invalid major choice. Please choose a number between 1 and 8.");
                                continue;
                        }
                        break;
                    }

                    String position;
                    // Asks user to pick from 1-4.
                    System.out.println("Position:");
                    System.out.println("1. First Year Program Leader");
                    System.out.println("2. Freshmen Leader");
                    System.out.println("3. Freshmen Partner");
                    System.out.println("4. Dual Role");

                    while (true) {
                        System.out.print("Choose position (1-4): ");
                        int positionChoice = scanner.nextInt();
                        switch (positionChoice) {
                            case 1:
                                position = "First Year Program Leader";
                                break;
                            case 2:
                                position = "Freshmen Leader";
                                break;
                            case 3:
                                position = "Freshmen Partner";
                                break;
                            case 4:
                                position = "Dual Role";
                                break;
                            default:
                                System.out.println("Error: Invalid position choice. Please choose a number between 1 and 4.");
                                continue;
                        }
                        break;
                    }

                    // Asks user to pick from 1-2.
                    System.out.println("Division: ");
                    System.out.println("1. Event Division");
                    System.out.println("2. Mobile Division");
                    String division;
                    while (true) {
                        System.out.print("Choose position (1-2): ");
                        Integer divisionChoice = scanner.nextInt();
                        switch (divisionChoice) {
                            case 1:
                                division = "Event Division";
                                break;
                            case 2:
                                division = "Mobile Division";
                                break;
                            default:
                                System.out.println("Error: Invalid position choice. Please choose a number between 1 and 2.");
                                continue;
                        }
                        break;
                    }

                    try {
                        // Check if a row with the same NIM value already exists
                        String sql = "SELECT COUNT(*) FROM final_project_schema.fypldatabase WHERE NIM = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, nim);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        int count = resultSet.getInt(1);

                        // If the count is greater than zero (meaning there is already the same NIM in the database) it will send out a duplicate error.
                        if (count > 0) {
                            System.out.println("Error: Duplicate entry for NIM. Please enter a unique NIM.");
                            return;
                        }

                        // No duplicates mean that it gets inputted into fypldatabase.
                        sql = "INSERT INTO final_project_schema.fypldatabase (NIM, Name, Email, `Contact Number`, Major, Position, Division) VALUES (?, ?, ?, ?, ?, ?, ?);";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, nim);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, email);
                        preparedStatement.setLong(4, contactNumber);
                        preparedStatement.setString(5, major);
                        preparedStatement.setString(6, position);
                        preparedStatement.setString(7, division);

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted.");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                        throw e;
                    }
                } catch (InputMismatchException e) {
                    // Handle non-integer input
                    System.out.println("Error: Incorrect input.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
            public void removeRow() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                    statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Selects & retrieves all the data from fypl database.
                resultSet = statement.executeQuery("SELECT * FROM final_project_schema.fypldatabase;");

                try {
                    // Checks if there is even any data in the database.
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("There are no students on the database");
                        return;
                    }
                    // If there is, it will ask the user to input the NIM of the user.
                    // Program will also display each NIM and the name of each user.
                    System.out.println("Select a student to remove:");
                    int i = 1;
                    do {
                        resultSet.next();
                        if (resultSet.isAfterLast()) {
                            break;
                        }
                        Long nim = resultSet.getLong("NIM");
                        String name = resultSet.getString("Name");
                        System.out.println(i + ". " + nim + " - " + name);
                        i++;
                    } while (true);
                    System.out.print("NIM: ");
                    Long nim = scanner.nextLong();

                    // Delete rows from punishmentsdatabase first
                    String sql2 = "DELETE FROM final_project_schema.punishmentsdatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql2);
                    preparedStatement.setLong(1, nim);
                    int rowsAffected2 = preparedStatement.executeUpdate();

                    // Delete row from fypldatabase
                    String sql1 = "DELETE FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql1);
                    preparedStatement.setLong(1, nim);
                    int rowsAffected1 = preparedStatement.executeUpdate();

                    // If no rows were affected, that meant there is no matching NIMs.
                    if (rowsAffected1 == 0) {
                        System.out.println("Error: No matching NIMs.");
                    } else {
                        System.out.println(rowsAffected1 + " row(s) removed from fypldatabase.");
                        System.out.println(rowsAffected2 + " row(s) removed from punishmentsdatabase.");
                    }
                } catch (InputMismatchException e) {
                    // Handle non-integer input
                    System.out.println("Error: Incorrect input.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
            public void updateRow() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                Long nim;
                String name;
                String email;
                Long contactNumber;
                String major;
                String position;
                String division;
                int majorChoice;
                int positionChoice;
                int divisionChoice;

                try {
                    // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                    // database, read_only to only allow for editing of the database.
                    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    // Retrieve and select all the data that is inside fypldatabase.
                    resultSet = statement.executeQuery("SELECT * FROM final_project_schema.fypldatabase;");

                    // If there is no data, this option will be output by the program.
                    if (!resultSet.first()) {
                        System.out.println("There are no students on the database");
                        return;
                    }

                    // If there is, it will ask the user to input the NIM of the user.
                    // Program will also display each NIM and the name of each user.
                    System.out.println("Select a student to update:");
                    int i = 1;
                    do {
                        if (resultSet.isAfterLast()) {
                            break;
                        }
                        nim = resultSet.getLong("NIM");
                        name = resultSet.getString("Name");
                        System.out.println(i + ". " + nim + " - " + name);
                        i++;
                        resultSet.next();
                    } while (true);

                    // Asks user for user's NIM.
                    System.out.print("NIM: ");
                    nim = scanner.nextLong();

                    // Check if a row with the same NIM value already exists
                    String sql = "SELECT COUNT(*) FROM final_project_schema.fypldatabase WHERE NIM = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, nim);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    int count = resultSet.getInt(1);

                    // If the count is greater than zero, a row with the same NIM value already exists
                    if (count == 0) {
                        System.out.println("Error: No NIM matches your input.");
                        return;
                    }

                    // Asks user for data for each column, essentially re-inputting it with the correct or updated information.
                    System.out.print("Name: ");
                    name = scanner.next();
                    System.out.print("Email: ");
                    email = scanner.next();
                    while (!email.contains("@") || !email.contains(".")) {
                        System.out.println("Error: Email must contain '@' & '.' symbol.");
                        System.out.print("Email: ");
                        email = scanner.next();
                    }
                    System.out.print("Contact Number: ");
                    while (!scanner.hasNextLong()) {
                        System.out.println("Error: Contact Number must be an integer.");
                        System.out.print("Contact Number: ");
                        scanner.next(); // Discard the invalid input
                    }
                    contactNumber = scanner.nextLong();
                    System.out.println("Major: ");
                    System.out.println("1. Computer Science");
                    System.out.println("2. Business Information Systems");
                    System.out.println("3. Fashion Design");
                    System.out.println("4. Graphic Design and New Media");
                    System.out.println("5. Communication");
                    System.out.println("6. International Business");
                    System.out.println("7. Business Management");
                    System.out.println("8. Finance");
                    while (true) {
                        System.out.println("Choose major (1-8): ");
                        majorChoice = scanner.nextInt();
                        switch (majorChoice) {
                            case 1:
                                major = "Computer Science";
                                break;
                            case 2:
                                major = "Business Information Systems";
                                break;
                            case 3:
                                major = "Fashion Design";
                                break;
                            case 4:
                                major = "Graphic Design and New Media";
                                break;
                            case 5:
                                major = "Communication";
                                break;
                            case 6:
                                major = "International Business";
                                break;
                            case 7:
                                major = "Business Management";
                                break;
                            case 8:
                                major = "Finance";
                                break;
                            default:
                                System.out.println("Error: Invalid major choice. Please choose a number between 1 and 8.");
                                continue;
                        }
                        break;
                    }
                    System.out.println("Position:");
                    System.out.println("1. First Year Program Leader");
                    System.out.println("2. Freshmen Leader");
                    System.out.println("3. Freshmen Partner");
                    System.out.println("4. Dual Role");
                    while (true) {
                        System.out.print("Choose position (1-4): ");
                        positionChoice = scanner.nextInt();
                        switch (positionChoice) {
                            case 1:
                                position = "First Year Program Leader";
                                break;
                            case 2:
                                position = "Freshmen Leader";
                                break;
                            case 3:
                                position = "Freshmen Partner";
                                break;
                            case 4:
                                position = "Dual Role";
                                break;
                            default:
                                System.out.println("Error: Invalid position choice. Please choose a number between 1 and 4.");
                                continue;
                        }
                        break;
                    }
                    System.out.println("Division: ");
                    System.out.println("1. Event Division");
                    System.out.println("2. Mobile Division");
                    while (true) {
                        System.out.print("Choose position (1-2): ");
                        divisionChoice = scanner.nextInt();
                        switch (divisionChoice) {
                            case 1:
                                division = "Event Division";
                                break;
                            case 2:
                                division = "Mobile Division";
                                break;
                            default:
                                System.out.println("Error: Invalid position choice. Please choose a number between 1 and 2.");
                                continue;
                        }
                        break;
                    }

                    // Create a prepared statement for the update query
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE final_project_schema.fypldatabase SET Name = ?, Email = ?, `Contact Number` = ?, Major = ?, Position = ?, Division = ? WHERE NIM = ?;");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setLong(3, contactNumber);
                    preparedStatement.setString(4, major);
                    preparedStatement.setString(5, position);
                    preparedStatement.setString(6, division);
                    preparedStatement.setLong(7, nim);

                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) updated.");
                } catch (InputMismatchException e){
                    System.out.println("Error: Incorrect Output");
                }
            }
        }

        // Class for Punishments database operations
        class PunishmentsDatabaseOperation extends DatabaseOperation {
            public PunishmentsDatabaseOperation(String url, String username, String password) throws SQLException {
                // Constructor for punishmentdatabase, takes url of database, username , and password, all of which is required for MySQL.
                super(url, username, password);
            }

            public void viewPunishment() throws SQLException {
                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Retrieves data from punishmentdatabase and the fypldatabase, only selected the needed columns. Also links the "NIM" of each database together.
                resultSet = statement.executeQuery("SELECT p.PunishmentID, p.NIM, p.Date, p.Reason, p.Punishment, f.Name " +
                        "FROM final_project_schema.punishmentsdatabase p " +
                        "JOIN final_project_schema.fypldatabase f ON p.NIM = f.NIM;");

                // No data within database outputs this option.
                if (!resultSet.first()) {
                    System.out.println("There are no punishments on the database");
                } else {
                    do {
                        // Outputs each column of each punishment, loops through every.
                        System.out.println("---------");
                        System.out.println("PunishmentID: " + resultSet.getString("PunishmentID"));
                        System.out.println("NIM: " + resultSet.getLong("NIM"));
                        System.out.println("Name: " + resultSet.getString("Name"));
                        System.out.println("Date: " + resultSet.getString("Date"));
                        System.out.println("Reason: " + resultSet.getString("Reason"));
                        System.out.println("Punishment: " + resultSet.getString("Punishment"));
                    } while (resultSet.next());
                }
            }

            public void issuePunishment() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                try {
                    // Retrieve all NIM and Name values from the database
                    String sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase;";
                    // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                    // database, read_only to only allow for editing of the database.
                    preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = preparedStatement.executeQuery();


                    // Checks if fypldatabase is empty, if empty then there is no students to issue punishments to.
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("There are no students on the database");
                        return;
                    }
                    // Display the list of NIMs and Names to the user
                    System.out.println("Select a NIM to issue punishment:");
                    int i = 1;
                    do {
                        resultSet.next();
                        if (resultSet.isAfterLast()) {
                            break;
                        }
                        Long nim = resultSet.getLong("NIM");
                        String name = resultSet.getString("Name");
                        System.out.println(i + ". " + nim + " - " + name);
                        i++;
                    } while (true);

                    // Reset the result set to the beginning
                    resultSet.beforeFirst();

                    // Get the user's selection
                    System.out.print("Enter the NIM to issue punishment: ");
                    Long nim = scanner.nextLong();


                    // Check if the input NIM matches any NIM in the fypldatabase
                    boolean foundNim = false;
                    do {
                        resultSet.next();
                        if (resultSet.isAfterLast()) {
                            break;
                        }
                        Long dbNim = resultSet.getLong("NIM");
                        if (dbNim.equals(nim)) {
                            foundNim = true;
                            break;
                        }
                    } while (true);
                    if (!foundNim) {
                        System.out.println("Error: no matching NIM.");
                        return;
                    }

                    // Prompt the user to enter the punishment
                    System.out.print("Enter the punishment: ");
                    String punishment = scanner.next();

                    // Prompt the user to enter the reason
                    System.out.print("Enter the reason for the punishment: ");
                    String reason = scanner.next();

                    // Get the current date of the machine.
                    Date date = new Date();

                    // Insert the punishment into the punishmentsdatabase
                    sql = "INSERT INTO final_project_schema.punishmentsdatabase (NIM, Punishment, Reason, Date) VALUES (?, ?, ?, ?);";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, nim);
                    preparedStatement.setString(2, punishment);
                    preparedStatement.setString(3, reason);
                    preparedStatement.setDate(4, new java.sql.Date(date.getTime()));

                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted.");
                } catch (InputMismatchException e) {
                    // Handle non-integer input
                    System.out.println("Error: Incorrect input.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            public void removePunishment() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Retrieves data from punishmentdatabase and the fypldatabase, only selected the needed columns. Also links the "NIM" of each database together.
                String sql = "SELECT p.PunishmentID, f.NIM, f.Name FROM final_project_schema.punishmentsdatabase p " +
                        "JOIN final_project_schema.fypldatabase f ON p.NIM = f.NIM;";
                resultSet = statement.executeQuery(sql);

                try {

                    // Check if there are even punishments on the database.
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("There are no punishments in the database");
                        return;
                    }
                    // Display the list of NIMs and Names to the user
                    System.out.println("Select a PunishmentID to remove punishment:");
                    int i = 1;
                    do {
                        resultSet.next();
                        if (resultSet.isAfterLast()) {
                            break;
                        }
                        Long nim = resultSet.getLong("NIM");
                        int id = resultSet.getInt("PunishmentID");
                        String name = resultSet.getString("Name");
                        System.out.println(i + ". " + id + " - " + nim + " - " + name);
                        i++;
                    } while (true);

                    // Asks user for PunishmentID
                    System.out.print("PunishmentID: ");
                    // Check if the input is a valid integer
                    if (!scanner.hasNextInt()) {
                        System.out.println("Error: Incorrect input.");
                        // Consume the invalid input
                        scanner.next();
                        return;
                    }
                    int punishmentID = scanner.nextInt();

                    // Uses the inputted punishmentID to find a match in the database and remove.
                    sql = "DELETE FROM final_project_schema.punishmentsdatabase WHERE PunishmentID = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, punishmentID);
                    int rowsAffected = preparedStatement.executeUpdate();

                    // If no rows were removed, that means there were no matches.
                    if (rowsAffected == 0) {
                        System.out.println("Error: No matching PunishmentIDs.");
                    } else {
                        System.out.println(rowsAffected + " row(s) removed.");
                    }
                } catch (InputMismatchException e) {
                    // Handle non-integer input
                    System.out.println("Error: Incorrect input.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
        }

        class EventDatabaseOperation extends DatabaseOperation implements Viewable {
            public EventDatabaseOperation(String url, String username, String password) throws SQLException {
                // Constructor for eventdatabaseoperation, takes url of database, username , and password, all of which is required for MySQL.
                super(url, username, password);
            }
           @Override
            public void viewDatabase() throws SQLException{
                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Selects & retrieves all the data from eventdatabase.
                resultSet = statement.executeQuery("SELECT * FROM final_project_schema.eventdatabase;");

                // No data on the database will output this.
                if (!resultSet.first()) {
                    System.out.println("There are no events on the database");
                }
                else {
                    // Outputs all the columns for the user to see, will loop through each event.
                    do {
                        System.out.println("---------");
                        System.out.println("Name: " + resultSet.getString("EventName"));
                        System.out.println("Type: " + resultSet.getString("EventType"));
                        System.out.println("Description: " + resultSet.getString("EventDescription"));
                        System.out.println("MC: " + resultSet.getString("MC"));
                        System.out.println("Timekeeper: " + resultSet.getString("Timekeeper"));
                        System.out.println("Slides: " + resultSet.getString("Slides"));
                        System.out.println("Mobile1: " + resultSet.getString("Mobile1"));
                        System.out.println("Mobile2: " + resultSet.getString("Mobile2"));
                        System.out.println("Total seats: " + resultSet.getString("Seats"));
                        System.out.println("Date: " + resultSet.getString("Date"));
                    } while (resultSet.next());
                }
            }
            public void addevent() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                try {

                    // Retrieve all NIM and Name values from the database
                    String sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase;";
                    // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                    // database, read_only to only allow for editing of the database.
                    preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = preparedStatement.executeQuery();


                    // Checks if fypldatabase is empty, if empty then there is no students to issue punishments to.
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("There are no students on the database");
                        return;
                    }

                    // Asks for EventName.
                    System.out.print("Event Name: ");
                    String eventName = scanner.next();

                    // Asks for Event Type, only allows user to pick from 1-4.
                    System.out.println("Event Type:");
                    System.out.println("1. Seminar");
                    System.out.println("2. Workshop");
                    System.out.println("3. Lecture");
                    System.out.println("4. Guest Seminar");
                    String eventType;
                    while (true) {
                        System.out.print("Choose position (1-4): ");
                        int eventTypeChoice = scanner.nextInt();
                        switch (eventTypeChoice) {
                            case 1:
                                eventType = "Seminar";
                                break;
                            case 2:
                                eventType = "Workshop";
                                break;
                            case 3:
                                eventType = "Lecture";
                                break;
                            case 4:
                                eventType = "Guest Seminar";
                                break;
                            default:
                                System.out.println("Error: Invalid position choice. Please choose a number between 1 and 4.");
                                continue;
                        }
                        break;
                    }

                    // Asks for Event Description.
                    System.out.print("Event Description: ");
                    String eventDescription = scanner.next();

                    // Retrieve the list of all students from the fypldatabase
                    sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase;";
                    resultSet = statement.executeQuery(sql);

                    // Display the entire list of NIMs and Names.
                    System.out.println("Select a NIM to give the MC Role:");
                    int i = 1;
                    while (resultSet.next()) {
                        Long nim = resultSet.getLong("NIM");
                        String name = resultSet.getString("Name");
                        System.out.println(i + ". " + nim + " - " + name);
                        i++;
                    }
                    // Reset the result set to the beginning
                    resultSet.beforeFirst();

                    // Get the user's NIM selection for the MC role.
                    Long mcNim;
                    do {
                        System.out.print("Enter the NIM to give the MC role: ");
                        mcNim = scanner.nextLong();

                        // Retrieve the inputted NIM from the user to find match in fypldatabase, no match causes error and loops.
                        sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, mcNim);
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            System.out.println("Error: No matching NIM.");
                        } else {
                            break;
                        }
                    } while (true);

                    // Retrieve the inputted NIM from the user to find match in fypldatabase, store as mcNim and mcName.
                    sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, mcNim);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    String mcName = resultSet.getString("Name");

                    // Use the selected fypldatabse student as the MC
                    String mc = mcNim + "-" + mcName;
                    System.out.println("You chose: " + mc);

                    // Get the user's NIM selection for the Timekeeper role.
                    Long timekeeperNim;
                    do {
                        System.out.print("Enter the NIM to give the Timekeeper role: ");
                        timekeeperNim = scanner.nextLong();

                        // Retrieve the inputted NIM from the user to find match in fypldatabase, no match causes error and loops.
                        sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, timekeeperNim);
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            System.out.println("Error: No matching NIM.");
                        } else {
                            break;
                        }
                    } while (true);

                    // Retrieve the inputted NIM from the user to find match in fypldatabase, store as timekeeperNim and timekeeperName.
                    sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, timekeeperNim);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    String timekeeperName = resultSet.getString("Name");

                    // Use the selected fypldatabse student as the Timekeeper
                    String timekeeper = timekeeperNim + "-" + timekeeperName;
                    System.out.println("You chose: " + timekeeper);

                    // Get the user's NIM selection for the Slides role.
                    Long slidesNim;
                    do {
                        System.out.print("Enter the NIM to give the Slides role: ");
                        slidesNim = scanner.nextLong();

                        // Retrieve the inputted NIM from the user to find match in fypldatabase, no match causes error and loops.
                        sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, slidesNim);
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            System.out.println("Error: No matching NIM.");
                        } else {
                            break;
                        }
                    } while (true);

                    // Retrieve the inputted NIM from the user to find match in fypldatabase, store as slidesNim and slidesName.
                    sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, slidesNim);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    String slidesName = resultSet.getString("Name");

                    // Use the selected fypldatabse student as the Slides
                    String slides = slidesNim + "-" + slidesName;
                    System.out.println("You chose: " + slides);

                    // Get the user's NIM selection for the Mobile1 role.
                    Long mobile1Nim;
                    do {
                        System.out.print("Enter the NIM to give the first Mobile role: ");
                        mobile1Nim = scanner.nextLong();

                        // Retrieve the inputted NIM from the user to find match in fypldatabase, no match causes error and loops.
                        sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, mobile1Nim);
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            System.out.println("Error: No matching NIM.");
                        } else {
                            break;
                        }
                    } while (true);

                    // Retrieve the inputted NIM from the user to find match in fypldatabase, store as mobile1Nim and mobile1Name.
                    sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, mobile1Nim);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    String mobile1Name = resultSet.getString("Name");

                    // Use the selected fypldatabase student as the first Mobile role.
                    String mobile1 = mobile1Nim + "-" + mobile1Name;
                    System.out.println("You chose: " + mobile1);

                    // Get the user's NIM selection for the Mobile2 role.
                    Long mobile2Nim;
                    do {
                        System.out.print("Enter the NIM to give the second Mobile role: ");
                        mobile2Nim = scanner.nextLong();

                        // Retrieve the inputted NIM from the user to find match in fypldatabase, no match causes error and loops.
                        sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setLong(1, mobile2Nim);
                        resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            System.out.println("Error: No matching NIM.");
                        } else {
                            break;
                        }
                    } while (true);

                    // Retrieve the inputted NIM from the user to find match in fypldatabase, store as timekeeperNim and timekeeperName.
                    sql = "SELECT NIM, Name FROM final_project_schema.fypldatabase WHERE NIM = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, mobile2Nim);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    String mobile2Name = resultSet.getString("Name");

                    // Use the selected fypldatabase student as the second Mobile
                    String mobile2 = mobile2Nim + "-" + mobile2Name;
                    System.out.println("You chose: " + mobile2);

                    // Asks user to input total amount of seats for event.
                    System.out.print("Total seats for the event: ");
                    while (!scanner.hasNextLong()) {
                        System.out.println("Error: Seats must be an integer.");
                        System.out.print("Seats: ");
                        // Discard the invalid input
                        scanner.next();
                    }
                    Long seats = scanner.nextLong();

                    // Asks for the date the event is taking place, in a DD-MM-YYYY format.
                    String date;
                    while (true) {
                        System.out.print("Enter a date (DD-MM-YYYY): ");
                        date = scanner.next();

                        if (date.matches("^[0-9]{2}-[0-9]{2}-[0-9]{4}$")) {
                            break;
                        }
                        else {
                            System.out.println("Invalid input. Please enter a date in the format DD-MM-YYYY, where each input is a number.");
                        }
                        System.out.println("You entered: " + date);
                    }

                    // Insert the new event along with the inputted data into eventdatabase.
                    sql = "INSERT INTO final_project_schema.eventdatabase (EventName, EventType, EventDescription, MC, Timekeeper, Slides, Mobile1, Mobile2, Seats, Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, eventName);
                    preparedStatement.setString(2, eventType);
                    preparedStatement.setString(3, eventDescription);
                    preparedStatement.setString(4, mc);
                    preparedStatement.setString(5, timekeeper);
                    preparedStatement.setString(6, slides);
                    preparedStatement.setString(7, mobile1);
                    preparedStatement.setString(8, mobile2);
                    preparedStatement.setLong(9, seats);
                    preparedStatement.setString(10, date);
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted.");
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    throw e;
                }
            }
            public void removeEvent() throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                // Create object used for MySQL queries, insensitive so it can be moved forwards and backwards along the
                // database, read_only to only allow for editing of the database.
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Selects & retrieves all the data on the eventdatabase.
                resultSet = statement.executeQuery("SELECT * FROM final_project_schema.eventdatabase;");

                // Try loop, any incorrect input from user causes a restart.
                try {
                    // If there is no data on database, it selects this output.
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("There are no events in the database");
                        return;
                    }

                    // Display the list of IDs and event names to the user.
                    System.out.println("Select a EventID to remove event:");
                    int i = 1;
                    do {
                        resultSet.next();
                        if (resultSet.isAfterLast()) {
                            break;
                        }
                        int id = resultSet.getInt("EventID");
                        String name = resultSet.getString("EventName");
                        String type = resultSet.getString("EventType");
                        System.out.println(i + ". " + id + " - " + name + " - " + type);
                        i++;
                    } while (true);

                    // Asks user for EventID
                    System.out.print("EventID: ");
                    // Check if the input is a valid integer
                    if (!scanner.hasNextInt()) {
                        System.out.println("Error: Incorrect input.");
                        // Consume the invalid input
                        scanner.next();
                        return;
                    }
                    int EventID = scanner.nextInt();

                    // The inputted EventID is then used to delete a matching EventID row on the database.
                    String sql = "DELETE FROM final_project_schema.eventdatabase WHERE EventID = ?;";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, EventID);
                    int rowsAffected = preparedStatement.executeUpdate();

                    // If no rows were affected, that means there were no matches.
                    if (rowsAffected == 0) {
                        System.out.println("Error: No matching EventIDs.");
                    } else {
                        System.out.println(rowsAffected + " row(s) removed.");
                    }
                } catch (InputMismatchException e) {
                    // Handle any extra errors, needed for a Try-Catch.
                    System.out.println("Error: Incorrect input.");
                    // Consume the invalid input
                    scanner.nextLine();
                }
            }
        }

        public class Main {
            public static void main(String[] args) throws SQLException {
                // Creates scanner object to be used as an input for users.
                Scanner scanner = new Scanner(System.in);
                // Set the delimiter to a newline character
                scanner.useDelimiter("\\r?\\n");

                FYPLDatabaseOperation fyplDatabaseOperation = null;
                PunishmentsDatabaseOperation punishmentsDatabaseOperation = null;
                EventDatabaseOperation eventDatabaseOperation = null;
                try {
                    // Constructors for all 3 databases used here.
                    fyplDatabaseOperation = new FYPLDatabaseOperation("jdbc:mysql://127.0.0.1:3306/final_project_schema", "root", "0n34lW4s33m");
                    punishmentsDatabaseOperation = new PunishmentsDatabaseOperation("jdbc:mysql://127.0.0.1:3306/final_project_schema", "root", "0n34lW4s33m");
                    eventDatabaseOperation = new EventDatabaseOperation("jdbc:mysql://127.0.0.1:3306/final_project_schema", "root", "0n34lW4s33m");
                } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
                }

                while (true) {
                    System.out.println("---------");
                    System.out.println("Menu:");
                    System.out.println("1. View FYPL database");
                    System.out.println("2. Add FYPL student");
                    System.out.println("3. Remove FYPL student");
                    System.out.println("4. Update FYPL student");
                    System.out.println("5. View Punishments database");
                    System.out.println("6. Issue Punishment");
                    System.out.println("7. Remove Punishment");
                    System.out.println("8. View Event database");
                    System.out.println("9. Add Events");
                    System.out.println("10. Remove Events");
                    System.out.println("11. Exit");
                    System.out.println("---------");
                    System.out.print("Choose option: ");
                    try {
                        int option = scanner.nextInt();

                        switch (option) {
                            case 1:
                                fyplDatabaseOperation.viewDatabase();
                                break;
                            case 2:
                                fyplDatabaseOperation.insertRow();
                                break;

                            case 3:
                                fyplDatabaseOperation.removeRow();
                                break;
                            case 4:
                                fyplDatabaseOperation.updateRow();
                                break;
                            case 5:
                                punishmentsDatabaseOperation.viewPunishment();
                                break;
                            case 6:
                                punishmentsDatabaseOperation.issuePunishment();
                                break;
                            case 7:
                                punishmentsDatabaseOperation.removePunishment();
                                break;
                            case 8:
                                eventDatabaseOperation.viewDatabase();
                                break;
                            case 9:
                                eventDatabaseOperation.addevent();
                                break;
                            case 10:
                                eventDatabaseOperation.removeEvent();
                                break;
                            case 11:
                                System.out.println("Exiting...");
                                System.exit(0);
                                break;

                            default:
                                System.out.println("Invalid option.");
                                break;
                        }
                    } catch (InputMismatchException e) {
                        // Handle non-integer input
                        System.out.println("Error: Incorrect input.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                }
            }
        }
