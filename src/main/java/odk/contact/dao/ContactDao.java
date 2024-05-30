package odk.contact.dao;

import odk.contact.model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/contactdb";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_CONTACTS_SQL = "INSERT INTO contact (prenom, nom, telephone, competence) VALUES (?, ?, ?, ?)";
    private static final String SELECT_CONTACT_BY_ID = "SELECT id, prenom, nom, telephone, competence FROM contact WHERE id = ?";
    private static final String SELECT_ALL_CONTACTS = "SELECT * FROM contact";
    private static final String DELETE_CONTACT_SQL = "DELETE FROM contact WHERE id = ?";
    private static final String UPDATE_CONTACT_SQL = "UPDATE contact SET prenom = ?, nom = ?, telephone = ?, competence = ? WHERE id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertContact(Contact contact) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACTS_SQL)) {
            preparedStatement.setString(1, contact.getPrenom());
            preparedStatement.setString(2, contact.getNom());
            preparedStatement.setString(3, contact.getTelephone());
            preparedStatement.setString(4, contact.getCompetence());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Contact selectContact(int id) {
        Contact contact = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTACT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String telephone = rs.getString("telephone");
                String competence = rs.getString("competence");
                contact = new Contact(id, prenom, nom, telephone, competence);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return contact;
    }

    public List<Contact> selectAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String telephone = rs.getString("telephone");
                String competence = rs.getString("competence");
                contacts.add(new Contact(id, prenom, nom, telephone, competence));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return contacts;
    }

    public boolean deleteContact(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateContact(Contact contact) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT_SQL)) {
            statement.setString(1, contact.getPrenom());
            statement.setString(2, contact.getNom());
            statement.setString(3, contact.getTelephone());
            statement.setString(4, contact.getCompetence());
            statement.setInt(5, contact.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}

