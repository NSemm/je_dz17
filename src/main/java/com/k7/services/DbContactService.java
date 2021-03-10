package com.k7.services;

import com.k7.contacts.Contact;
import com.k7.contacts.ContactType;
import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class DbContactService implements ContactService {
    private final HikariConfig config;
    private final DataSource dataSource;
    private Integer userId;

    @Override
    public void add(Contact c) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contact (contact_name, contact_type, contact_value, user_id)" +
                            "SELECT ?,t.id,?,u.id" +
                            "INNER JOIN users u ON u.id = ?" +
                            "INNER JOIN contact_type t ON t.type = ?"
            );
            statement.setString(1, c.getName());
            statement.setString(2, c.getValue());
            statement.setString(3, userId.toString());
            statement.setString(4, c.getType().toString());
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public List<Contact> findByName(String name) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.contact_name, t.type, c.contact_value " +
                            "FROM contacts c " +
                            "INNER JOIN users u ON u.id = c.id " +
                            "INNER JOIN contact_type t ON t.id = c.contact_type" +
                            "WHERE c.contact_name like ?"
            );
            ResultSet res = statement.executeQuery();
            statement.setString(1, name);
            List<Contact> contacts = new ArrayList<>();
            while (res.next()) {
                Contact contact = new Contact();
                contact.setName(res.getString("c.contact_name"));
                contact.setType(ContactType.valueOf(res.getString("t.contact_type")));
                contact.setValue(res.getString("c.contact_value"));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> findByValue(String value) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.contact_name, t.type, c.contact_value " +
                            "FROM contacts c " +
                            "INNER JOIN users u ON u.id = c.id " +
                            "INNER JOIN contact_type t ON t.id = c.contact_type" +
                            "WHERE c.contact_value like ?"
            );
            ResultSet res = statement.executeQuery();
            statement.setString(1, value);
            List<Contact> contacts = new ArrayList<>();
            while (res.next()) {
                Contact contact = new Contact();
                contact.setName(res.getString("c.contact_name"));
                contact.setType(ContactType.valueOf(res.getString("t.contact_type")));
                contact.setValue(res.getString("c.contact_value"));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Contact> getAll() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT c.contact_name, t.type, c.contact_value " +
                            "FROM contacts c " +
                            "INNER JOIN users u ON u.id = c.id " +
                            "INNER JOIN contact_type t ON t.id = c.contact_type"
            );
            List<Contact> contacts = new ArrayList<>();
            while (res.next()) {
                Contact contact = new Contact();
                contact.setName(res.getString("c.contact_name"));
                contact.setType(ContactType.valueOf(res.getString("t.contact_type")));
                contact.setValue(res.getString("c.contact_value"));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

}
