package userjsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static User getUserFrom(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserid(resultSet.getString("userid"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setDepartmentId(resultSet.getInt("departmentId"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setUserType(resultSet.getString("userType"));
        user.setDepartmentName(resultSet.getString("departmentName"));
        return user;
    }

    public static List<User> findAll() throws Exception {
        String sql = "SELECT u.*, d.departmentName" +
                     " FROM user u LEFT JOIN department d ON u.departmentId = d.id";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            ArrayList<User> list = new ArrayList<User>();
            while (resultSet.next())
                list.add(getUserFrom(resultSet));
            return list;
        }
    }

    public static List<User> findAll(int currentPage, int pageSize) throws Exception {
        String sql = "SELECT u.*, d.departmentName" +
                     " FROM user u LEFT JOIN department d ON u.departmentId = d.id" +
                     " LIMIT ?, ?";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (currentPage - 1) * pageSize);
            statement.setInt(2, pageSize);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<User> list = new ArrayList<User>();
                while (resultSet.next())
                    list.add(getUserFrom(resultSet));
                return list;
            }
        }
    }

    public static int count() throws Exception {
        String sql = "SELECT COUNT(*) FROM user";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return resultSet.getInt(1);
        }
        return 0;
    }

    public static User findByUserid(String userid) throws Exception {
        String sql = "SELECT u.*, d.departmentName" +
                     " FROM user u LEFT JOIN department d ON u.departmentId = d.id" +
                     " WHERE u.userid = ?";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return getUserFrom(resultSet);
                else
                    return null;
            }
        }
    }

    public static User findById(int id) throws Exception {
        String sql = "SELECT u.*, d.departmentName " +
                     " FROM user u LEFT JOIN department d ON u.departmentId = d.id" +
                     " WHERE u.id = ?";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return getUserFrom(resultSet);
                else
                    return null;
            }
        }
    }

    public static void insert(User user) throws Exception {
        String sql = "INSERT user (userid, password, name, email, departmentId, enabled, userType)" +
                     " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserid());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getDepartmentId());
            statement.setBoolean(6, user.isEnabled());
            statement.setString(7, user.getUserType());
            statement.executeUpdate();
        }
    }


    public static void update(User user) throws Exception {
        String sql = "UPDATE user SET userid=?, name=?, email=?, departmentId=?, enabled=?, userType=? " +
                     " WHERE id = ?";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserid());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getDepartmentId());
            statement.setBoolean(5, user.isEnabled());
            statement.setString(6, user.getUserType());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        }
    }

    public static void delete(int id) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = DB.getConnection("student1");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
   }
}
