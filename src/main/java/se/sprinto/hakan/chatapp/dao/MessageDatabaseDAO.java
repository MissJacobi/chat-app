package se.sprinto.hakan.chatapp.dao;

import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseDAO implements MessageDAO{
    @Override
    public void saveMessage(Message message) {
        String sql = """
                Insert INTO message(message_text,created_at, user_id)
                VALUES (?,?,?)
                """;
        try(Connection conn = DatabaseUtil.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setString(1, message.getText());
        ps.setTimestamp(2, Timestamp.valueOf(message.getTimestamp()));
        ps.setInt(3,message.getUserId());

       ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        String sql = """ 
                SELECT message_text,created_at, user_id 
                FROM message 
                WHERE user_id = ?
                """;
        List<Message> messages = new ArrayList<>();
        try(Connection conn = DatabaseUtil.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,userId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                int userID = resultSet.getInt("user_id");
                String text = resultSet.getString("message_text");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                Message message = new Message(userID, text, createdAt.toLocalDateTime());
                messages.add(message);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }
}
