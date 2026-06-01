package ma.ac.esi.culinaryarts.repository;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ma.ac.esi.culinaryarts.util.DBUtil;

public class UserRepository {
	
	public boolean userExists( String email, String password) throws SQLException
	{
		
		String sql="SELECT * from Users where email=? and password =?";
		
		Connection connection= DBUtil.getConnection();
		PreparedStatement statement;
		
		try {
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultset= statement.executeQuery();
			if (resultset.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

}

