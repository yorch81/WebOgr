package net.yorch;

/**
 * OgrConnection<br>
 * 
 * OgrConnection ogr2ogr Database Connection<br><br>
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
 * @version    1.0.0, 2016-19-05
 * @author     <a href="mailto:the.yorch@gmail.com">Jorge Alberto Ponce Turrubiates</a>
 */
public class OgrConnection {
	/**
	 * MSSQLSpatial
	 */
	public static final int MSSQLSpatial=1;
	
	/**
	 * MySQL
	 */
    public static final int MySQL=2;
    
    /**
     * PostGis
     */
    public static final int PostGis=3;
    
    /**
     * Database Connection
     */
    DBConnection conn = null;
    
    /**
     * Create new Ogr Database Connection
     * 
     * @param type     int    Database Type
     * @param hostname String Hostname
	 * @param username String User
	 * @param password String Password
	 * @param dbname   String Database Name
	 * @param portNumber int  Database Port
     */
	public OgrConnection(int type, String hostname, String username, String password, String dbname, int portNumber) {		
		switch (type) {
			case MSSQLSpatial:
				conn = new MSSQLSpatial(hostname, username, password, dbname, portNumber);
				break;
			case MySQL:
				conn = new MySQL(hostname, username, password, dbname, portNumber);
				break;
			case PostGis:
				conn = new PostGis(hostname, username, password, dbname, portNumber);
				break;
			default:
				conn = new MSSQLSpatial(hostname, username, password, dbname, portNumber);
		}
	}
	
	/**
	 * Check Database Connection
	 * 
	 * @return boolean
	 */
	public boolean checkConnection() {
		return conn.checkConnection();
	}
	
	/**
	 * Get Ogr Connection String
	 * 
	 * @return String
	 */
	public String getOgrConnection() {
		return conn.getOgrConnection();
	}
	
	/**
	 * Get Ogr Format
	 * 
	 * @return String
	 */
	public String getOgrFormat() {
		return conn.getOgrFormat();
	}
	
	/**
	 * Get Ogr Tables in Database
	 * 
	 * @return String
	 */
	public String getOgrTables() {
		return conn.getOgrTables();
	}
}
