package net.yorch;

/**
 * DBConnection<br>
 * 
 * Abstract Class to Manage Database Connection<br><br>
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
public abstract class DBConnection {
	
	/**
	 * Check connection to Database
	 * 
	 * @return boolean
	 */
	public abstract boolean checkConnection();
	
	/**
	 * Get Ogr String Database Connection
	 * 
	 * @return String
	 */
	public abstract String getOgrConnection();
	
	/**
	 * Get Ogr Format
	 * 
	 * @return String
	 */
	public abstract String getOgrFormat();
	
	/**
	 * Get Ogr Tables in Database as JSON String
	 * 
	 * @return String
	 */
	public abstract String getOgrTables();
}
