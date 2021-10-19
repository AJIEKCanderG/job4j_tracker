package ru.job4j.tracker;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.SqlTracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.
                getClassLoader().getResourceAsStream("test.properties")) {
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

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenDeleteInDB() {
        var tracker = new SqlTracker(connection);
        Item item = new Item("bug");
        List<Item> expected = new ArrayList<>();
        expected.add(item);
        tracker.add(item);
        assertThat(tracker.findAll(), is(expected));
        assertTrue(tracker.delete(item.getId()));
        assertThat(tracker.findAll(), is(List.of()));
    }

    @Test
    public void whenAddItemThenReplaceAndFindByGeneratedIdThenMustBeNull() {
        var tracker = new SqlTracker(connection);
        var item = new Item("item");
        tracker.add(item);
        item.setName("newItem");
        tracker.replace(item.getId(), item);
        assertEquals("newItem", tracker.findById(item.getId()).getName());
    }

    @Test
    public void whenSaveItemThenDeleteAndFindByGeneratedIdThenMustBeNull() {
        var tracker = new SqlTracker(connection);
        var item = new Item("item");
        tracker.add(item);
        assertTrue(tracker.delete(item.getId()));
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenAddItemAndFindAllGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        tracker.add(new Item("item1"));
        tracker.add(new Item("item2"));
        for (var i = 0; i < tracker.findAll().size(); i++) {
            assertEquals(String.format("item%d", (i + 1)), tracker.findAll().get(i).getName());
        }
    }

    @Test
    public void whenAddItemsAndThenFindItByName() {
        var tracker = new SqlTracker(connection);
        tracker.add(new Item(0, "item1"));
        tracker.add(new Item(0, "item2"));
        tracker.add(new Item(0, "item1"));
        var rsl = tracker.findByName("item1");
        assertEquals(2, rsl.size());
        for (var item : rsl) {
            assertEquals("item1", item.getName());
        }
    }

}