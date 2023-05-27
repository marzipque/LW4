package app.models;

import app.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker {
    private static Connection _connection;
    public Connection getConnection() {
        return _connection;
    }
    // Добавление пользователя в таблицу базы данных (CREATE)
    public Integer add(User user) {
        Integer addCount = 0;

        try {
            String query = "INSERT INTO users (name, lastName, age, email, phone) values (?,?,?,?,?)";
            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone());
            addCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return addCount;
    }
    // Получение всех пользователей из таблицы базы данных (READ)
    public ArrayList<User> load() {
        var users = new ArrayList<User>();

        try {
            String query = "SELECT * FROM users";
            PreparedStatement statement = _connection.prepareStatement(query);
            ResultSet items = statement.executeQuery();

            while (items.next()) {
                users.add(new User(
                        items.getInt(1),
                        items.getString(2),
                        items.getString(3),
                        items.getInt(4),
                        items.getString(5),
                        items.getString(6)));
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return users;
    }

    // Редактирование выбранного пользователя (UPDATE)
    public Integer update(User user) {
        Integer updatedCount = 0;

        try {
            String query = "UPDATE users SET name=?, lastName=?, age=?, email=?, phone=? WHERE id=?";

            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone());
            statement.setInt(6, user.getId());

            updatedCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return updatedCount;
    }

    // Удаление выбранного пользователя (DELETE)
    public Integer delete(User user) {
        Integer deletedCount = 0;

        try {
            String query = "DELETE FROM users WHERE id= ? ";

            PreparedStatement statement = _connection.prepareStatement(query);
            statement.setInt(1, user.getId());

            deletedCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return deletedCount;
    }

    public DBWorker(Connection connection) {
        _connection = connection;
    }
}