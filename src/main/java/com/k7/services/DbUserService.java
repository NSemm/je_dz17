package com.k7.services;

import com.k7.contacts.User;
import com.k7.utility.UserIdStorage;
import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class DbUserService implements UserService {
    private final HikariConfig config;
    private final DataSource dataSource;
    private UserIdStorage userId;
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
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, login, password FROM users WHERE login = ?"
            );
            statement.setString(1, user.getLogin());
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                if (res.getString("password").equals(user.getPassword())) {
                    isLogin = true;
                    userId.setId((int) res.getLong("Id"));
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
