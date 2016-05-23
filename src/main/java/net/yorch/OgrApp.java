package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
		OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "localhost", "sa", "", "GEO", 1433);
		//OgrConnection mysql = new OgrConnection(OgrConnection.MySQL, "localhost", "root", "", "markers", 3306);
		
		if (sql.checkConnection()) {
			WOgr ogr =  new WOgr();
						
			System.out.println(sql.getOgrTables());
			
			//ogr.exportFromDb(sql, "dbo.Predios", "C:/Code/Predios.shp", "EPSG:32614", "EPSG:32614");
			//ogr.importToDb(mysql, "Construcciones2", "C:/Code/Construcciones.shp", "EPSG:32614", "EPSG:32614");
		}
		else
			System.out.println("Not Connected");
	}
}
