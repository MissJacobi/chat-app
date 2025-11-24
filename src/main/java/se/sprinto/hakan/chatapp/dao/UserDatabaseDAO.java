package se.sprinto.hakan.chatapp.dao;

import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.util.DatabaseUtil;
import java.sql.*;

public class UserDatabaseDAO implements UserDAO {
    @Override
    public User login(String username, String password) {
        String sql = """
            SELECT id,username, password 
            FROM user
            WHERE username = ? and password = ?
            """;

        try(Connection conn = DatabaseUtil.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                int userID = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String pass = resultSet.getString("password");
                return new User(userID,name,pass);
            }
        } catch (SQLException e){
        e.printStackTrace();
    }
        return null;
    }

    @Override
    public User register(User user) {
                String sql = """
                 INSERT INTO user (username,password)
                 VALUES (?,?)
            """;

        try(Connection conn = DatabaseUtil.
             getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

            try(ResultSet resultSet = ps.getGeneratedKeys()){
                if (resultSet.next()){
                    int id = resultSet.getInt(1);
                    user.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}