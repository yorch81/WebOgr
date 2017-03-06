package net.yorch;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * OgrApp<br>
 * 
 * Application Class<br><br>
 * 
 * Copyright 2017 Jorge Alberto Ponce Turrubiates
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
 * @version    1.0.0, 2017-03-02
 * @author     <a href="mailto:the.yorch@gmail.com">Jorge Alberto Ponce Turrubiates</a>
 */
public class OgrApp {

	/**
	 * Main Method
	 * 
	 * @param args String[] Arguments
	 */
	public static void main(String[] args) {
		/*
		//OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "IICSRVPRUEBAS", "sa", "", "SGC_CARTO", 1433);
		//OgrConnection sql = new OgrConnection(OgrConnection.PostGis, "localhost", "postgres", "", "postgis_23_sample", 5432);
		OgrConnection sql = new OgrConnection(OgrConnection.MySQL, "localhost", "root", "", "GEO", 3306);
		
		if (sql.checkConnection()) {
			WOgr ogr =  new WOgr();
						
			//System.out.println(sql.getOgrTables());
			
			//ogr.importToDb(sql, "pred_tula", "C:/CODE/shapes/2D/PREDIOS_TULA.shp", "EPSG:32614", "EPSG:32614");
			ogr.exportFromDb(sql, "gz_sector", "C:/shapes/gz_sector.shp", "EPSG:32614", "EPSG:32614");
		}
		else
			System.out.println("Not Connected");
		*/
		
		System.out.println( "Web OGR Tool !!!" );
		
		Properties config = new Properties();
		
		try {
			config.load(new FileInputStream("webogr.properties"));
			
			int dbType = Integer.parseInt(config.getProperty("dbtype"));
			String hostname = config.getProperty("hostname");
			String user = config.getProperty("user");
			String password = config.getProperty("password");
			String dbname = config.getProperty("dbname");
			int dbport = Integer.parseInt(config.getProperty("dbport"));
			int port = Integer.parseInt(config.getProperty("port"));
			String basedir = config.getProperty("basedir");
			String appUser = config.getProperty("appuser");
			String appPassword = config.getProperty("apppassword");
				
			if (! WebApp.dirExists(basedir)){
				System.out.println("Directory Base not Exists");
			}
			else{
				OgrConnection db = new OgrConnection(dbType, hostname,user, password, dbname, dbport);
				
				if (db.checkConnection()){
					// Initialize Application
					new WebApp(port, appUser, appPassword, basedir, db);
				}
				else
					System.out.println("Could not connect to Database");
			}
		} catch (IOException e) {
			System.out.println("Configuration File does not Exists, please configure");
			
			Interactive interactive = new Interactive();
			
			interactive.addQuestion("dbtype","Type Database (1 SQL Server 2 MySQL 3 PostGis):");
			interactive.addQuestion("hostname","Type Database Server:");
			interactive.addQuestion("user","Type Database User:");
			interactive.addQuestion("password","Type User password:");
			interactive.addQuestion("dbname","Type Database Name:");
			interactive.addQuestion("dbport","Type Database Port:");
			interactive.addQuestion("port","Type Application Web Port:");
			interactive.addQuestion("basedir","Type Base Directory:");
			interactive.addQuestion("appuser","Type Application User:");
			interactive.addQuestion("apppassword","Type Application Password:");
			
			interactive.interactive();
			interactive.save("webogr.properties");
			
			System.out.printf("File generated, please restart Application");
		}
	}
}
