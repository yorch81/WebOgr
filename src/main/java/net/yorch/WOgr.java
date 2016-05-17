package net.yorch;

/**
 * WOgr<br>
 * 
 * WOgr ogr2ogr Wrapper<br><br>
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
public class WOgr {
	
	/**
	 * Create new Ogr Wrapper
	 */
	public WOgr() {
		
	}
	
	/**
	 * Import a ShapeFile into Database
	 * 
	 * @param connection DBConnection Database Connection
	 * @param tableName  String Table to Import ShapeFile
	 * @param shapeFile  String Path of Shapefile
	 * @param asrs	     String Default SRS
	 * @param ssrs	 	 String Override SRS
	 * @return boolean
	 */
	public boolean importToDb(DBConnection connection, String tableName, String shapeFile, String asrs, String ssrs) {
		StringBuilder sbCommand = new StringBuilder("");
		
		sbCommand.append("ogr2ogr -f \"");
		sbCommand.append(connection.getOgrFormat());
		sbCommand.append("\" ");
		
		sbCommand.append("\"");
		sbCommand.append(connection.getOgrConnection());
		sbCommand.append("\" ");
		
		sbCommand.append("-nln \"");
		sbCommand.append(tableName);
		sbCommand.append("\" ");
				
		sbCommand.append("\"");
		sbCommand.append(shapeFile);
		sbCommand.append("\" ");
		
		sbCommand.append("-overwrite ");
		
		if (! asrs.isEmpty()) {
			sbCommand.append("-a_srs ");
			sbCommand.append(asrs);
			sbCommand.append(" ");
		}
		
		if (! ssrs.isEmpty()) {
			sbCommand.append("-s_srs ");
			sbCommand.append(ssrs);
			sbCommand.append(" ");
		}
		
		// MySQL
		if (connection.getOgrFormat().equals("MySQL"))
			sbCommand.append("-lco ENGINE=MyISAM");
			
		return executeOgr(sbCommand.toString());
	}
	
	/**
	 * Export a ShapeFile from a Database Table
	 * 
	 * @param connection DBConnection Database Connection
	 * @param tableName  String Table to Import ShapeFile
	 * @param shapeFile  String Path of Shapefile
	 * @param asrs	     String Default SRS
	 * @param ssrs	 	 String Override SRS
	 * @return boolean
	 */
	public boolean exportFromDb(DBConnection connection, String tableName, String shapeFile, String asrs, String ssrs) {
		StringBuilder sbCommand = new StringBuilder("");
		String sqlTable = "SELECT * FROM " + tableName;
		
		sbCommand.append("ogr2ogr -f \"ESRI Shapefile\" ");
		
		sbCommand.append("\"");
		sbCommand.append(shapeFile);
		sbCommand.append("\" ");
		
		sbCommand.append("\"");
		sbCommand.append(connection.getOgrConnection());
		sbCommand.append("\" ");
		
		sbCommand.append("-sql \"");
		sbCommand.append(sqlTable);
		sbCommand.append("\" ");
		
		sbCommand.append("-overwrite ");
		
		if (! asrs.isEmpty()) {
			sbCommand.append("-a_srs ");
			sbCommand.append(asrs);
			sbCommand.append(" ");
		}
		
		if (! ssrs.isEmpty()) {
			sbCommand.append("-s_srs ");
			sbCommand.append(ssrs);
			sbCommand.append(" ");
		}
		
		return executeOgr(sbCommand.toString());
	}
	
	/**
	 * Execute ogr2ogr command
	 * 
	 * @param command String ogr2ogr command
	 * @return boolean
	 */
	private boolean executeOgr(String command) {
        String[] aCommand; 
		
		if (System.getProperty("os.name").contains("Windows"))
			aCommand = new String[]{"cmd.exe","/c",command};
		else
			aCommand = new String[]{"/bin/bash","-c",command};
		
		int status = 1;
				
		try {
			Process ogr = Runtime.getRuntime().exec(aCommand);
			status = ogr.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return status == 0 ? true : false;
	}
}
