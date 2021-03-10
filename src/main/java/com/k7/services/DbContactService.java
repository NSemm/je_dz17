package com.k7.services;

import com.k7.contacts.Contact;
import com.k7.contacts.ContactType;
import com.k7.utility.UserIdStorage;
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
    private UserIdStorage userId;

    @Override
    public void add(Contact c) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contacts (contact_name, contact_type, contact_value, user_id) " +
                            "SELECT ?, t.id, ?, ? " +
                            "FROM contact_type t " +
                            "WHERE t.type = ? "
            );
            statement.setString(1, c.getName());
            statement.setString(2, c.getValue());
            statement.setLong(3, userId.getId());
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
                            "INNER JOIN contact_type t ON t.id = c.contact_type " +
                            "WHERE c.user_id = ? AND c.contact_name like ? "
            );
            statement.setLong(1, userId.getId());
            statement.setString(2, name);
            ResultSet res = statement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (res.next()) {
                Contact contact = new Contact();
                contact.setName(res.getString("contact_name"));
                contact.setType(ContactType.valueOf(res.getString("type")));
                contact.setValue(res.getString("contact_value"));
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
                            "INNER JOIN contact_type t ON t.id = c.contact_type " +
                            "WHERE c.user_id = ? AND c.contact_value like ?"
            );
            statement.setLong(1, userId.getId());
            statement.setString(2, value);
            ResultSet res = statement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (res.next()) {
                Contact contact = new Contact();
                contact.setName(res.getString("contact_name"));
                contact.setType(ContactType.valueOf(res.getString("type")));
                contact.setValue(res.getString("contact_value"));
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
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.contact_name, t.type, c.contact_value " +
                            "FROM contacts c " +
                            "INNER JOIN contact_type t ON t.id = c.contact_type " +
                            "WHERE c.user_id = ? "
            );
            statement.setLong(1, userId.getId());
            ResultSet res = statement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (res.next()) {
                Contact contact = new Contact();
                contact.setName(res.getString("contact_name"));
                contact.setType(ContactType.valueOf(res.getString("type")));
                contact.setValue(res.getString("contact_value"));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

}
