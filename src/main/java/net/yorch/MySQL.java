package net.yorch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

/**
 * MySQL<br>
 * 
 * MySQL Class Manage MySQL Connection<br><br>
 * 
 * Copyright 2016 Jorge Alberto Ponce Turrubiates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @version    1.0.0, 2016-17-05
 * @author     <a href="mailto:the.yorch@gmail.com">Jorge Alberto Ponce Turrubiates</a>
 */
public class MySQL extends DBConnection {

	/**
	 * MySQL Connection
	 */
	private Connection conn = null;
	
	/**
	 * Ogr Connection String
	 */
	private String connectionString = "";
	
	/**
	 * Ogr Format
	 */
	private String ogrFormat = "MySQL";
	
	/**
	 * Create new MySQL Connection
	 * 
	 * @param hostname String MySQL Hostname
	 * @param username String MySQL User
	 * @param password String MySQL Password
	 * @param dbname   String MySQL Database
	 * @param portNumber int  MySQL Port
	 */
	public MySQL(String hostname, String username, String password, String dbname, int portNumber) {
		String connectionUrl = "jdbc:mysql://" + hostname + "/" + dbname; 
				
		connectionString = "MYSQL:"+ dbname + ",host=" + hostname + ",user=" + username + ",password=" + password + ",port=" + String.valueOf(portNumber);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(connectionUrl, username, password);
		} catch (Exception e) {
			conn = null;
			e.printStackTrace();
		}
	}
	
	/**
	 * Check MySQL Connection
	 */
	@Override
	public boolean checkConnection() {
		return conn == null ? false : true;
	}

	/**
	 * Get Ogr Connection String
	 */
	@Override
	public String getOgrConnection() {
		return connectionString;
	}

	/**
	 * Get Ogr Format
	 */
	@Override
	public String getOgrFormat() {
		return ogrFormat;
	}

	/**
	 * Get Ogr Tables in Database
	 */
	@Override
	public String getOgrTables() {
		String sql = "SELECT f_table_name FROM geometry_columns";
		
		JSONObject json = new JSONObject();
	
		if (conn != null) {
			Statement pstmt;
			ResultSet result;
			
			try {
				pstmt = conn.createStatement();
				
				result = pstmt.executeQuery(sql);
				int i = 1;
				
				while (result.next()) {
					json.put(String.valueOf(i), result.getString("f_table_name"));
					i++;
				}
			} catch (SQLException e) {
				json = new JSONObject();
			}
		}
		
		return json.toString();
	}
}
