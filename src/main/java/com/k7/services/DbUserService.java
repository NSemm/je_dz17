package com.k7.services;

import com.k7.contacts.User;
import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class DbUserService implements UserService {
    private final HikariConfig config;
    private final DataSource dataSource;
    private int userId;
    private boolean isLogin;

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void registration(User user) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (login, password, date_born) " +
                            "SELECT ?, ?, ?"
            );
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setTimestamp(3, Timestamp.valueOf(user.getDateBorn()));
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void auth(User user) {
        System.out.println("start");
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT login, password" +
                            "FROM users  " +
                            "WHERE login = ?");
            statement.setString(1, user.getLogin());
            System.out.println("ussser    :   " + user.getLogin());
            ResultSet res2 = statement.executeQuery();
            while (res2.next()) {
                if (res2.getString("password").equals(user.getPassword())) {
                    isLogin = true;
                    userId = (int) res2.getLong("Id");
                } else {
                    throw new RuntimeException("Invalid password");
                }
            }
        } catch (SQLException | RuntimeException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean isAuth() {
        return isLogin;
    }
}
