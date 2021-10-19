package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 18.10.21
 * @author Ajiekcander
 */

public class SqlTracker implements Store {
    private Connection connection;

    public SqlTracker(Connection connection) {
        this.connection = connection;
    }

    public void init() {
        try (InputStream in = SqlTracker.class.
                getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public Item add(Item item) {
        String sql = "Insert into items(name, created) values(?, ?)";
        try (var statement = connection.prepareStatement(sql, Statement
                .RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, getTimestamp(item.getCreated()));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean rsl = false;
        var sql = "update items set name = ?, created = ? where id = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, getTimestamp(item.getCreated()));
            statement.setInt(3, id);
            rsl = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        var rsl = false;
        var sql = "delete from items where id = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            rsl = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public List<Item> findAll() {
        List<Item> rsl = new ArrayList<>();
        var sql = "select * from items";
        try (var statement = connection.prepareStatement(sql)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rsl.add(new Item(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            getLocalDateTime(resultSet.getTimestamp("created"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> rsl = new ArrayList<>();
        var sql = "select * from items where name = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, key);
            try (var rslKey = statement.executeQuery()) {
                while (rslKey.next()) {
                    rsl.add(new Item(rslKey.getInt("id"),
                            rslKey.getString("name"),
                            getLocalDateTime(rslKey.getTimestamp("created"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Item findById(int id) {
        var sql = "select * from items where id = ?";
        Item item = null;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (var rslKey = statement.executeQuery()) {
                if (rslKey.next()) {
                    item = new Item(rslKey.getInt("id"),
                            rslKey.getString("name"),
                            getLocalDateTime(rslKey.getTimestamp("created"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    private LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    private Timestamp getTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}