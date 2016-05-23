package net.yorch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

/**
 * MSSQLSpatial<br>
 * 
 * MSSQLSpatial Class Manage SQL Server Connection<br><br>
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
public class MSSQLSpatial extends DBConnection {
	/**
	 * SQL Server Connection
	 */
	private Connection conn = null;
	
	/**
	 * Ogr Connection String
	 */
	private String connectionString = "";
	
	/**
	 * Ogr Format
	 */
	private String ogrFormat = "MSSQLSpatial";
	
	/**
	 * Create new MSSQLSpatial Connection
	 * 
	 * @param hostname String SQL Server Hostname
	 * @param username String SQL Server User
	 * @param password String SQL Server Password
	 * @param dbname   String SQL Server Database
	 * @param portNumber int  SQL Server Port
	 */
	public MSSQLSpatial(String hostname, String username, String password, String dbname, int portNumber) {
		String selectMethod = "Direct";
		String connectionUrl = "jdbc:sqlserver://" + hostname + ":" + String.valueOf(portNumber) + ";databaseName=" + dbname + ";user=" + username + ";password=" + password + ";selectMethod=" + selectMethod + ";";
		
		connectionString = "MSSQL:server=" + hostname + "," + String.valueOf(portNumber) + ";database=" + dbname + ";uid=" + username + ";pwd=" + password + ";";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			conn = DriverManager.getConnection(connectionUrl);
		} catch (Exception e) {
			conn = null;
			e.printStackTrace();
		}
	}
	
	/**
	 * Check SQL Server Database Connection
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
		String sql = "SELECT f_table_schema, f_table_name FROM geometry_columns";
		
		JSONObject json = new JSONObject();
	
		if (conn != null) {
			Statement pstmt;
			ResultSet result;
			
			try {
				pstmt = conn.createStatement();
				
				result = pstmt.executeQuery(sql);
				int i = 1;
				
				while (result.next()) {
					json.put(String.valueOf(i), result.getString("f_table_schema") + "." + result.getString("f_table_name"));
					i++;
				}
			} catch (SQLException e) {
				json = new JSONObject();
			}
		}
		
		return json.toString();
	}
}
