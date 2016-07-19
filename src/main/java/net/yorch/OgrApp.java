package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
		OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "localhost", "sa", "password", "SGC_PRINCIPAL", 1433);
		//OgrConnection mysql = new OgrConnection(OgrConnection.MySQL, "localhost", "root", "", "markers", 3306);
		
		if (sql.checkConnection()) {
			WOgr ogr =  new WOgr();
						
			System.out.println(sql.getOgrTables());
			
			ogr.exportFromDb(sql, "dbo.construcciones", "C:/Code/shapes/construcciones.shp", "EPSG:32614", "EPSG:32614");
			//ogr.importToDb(sql, "postes_victoria", "C:/Code/shapes/Postes_CdVictoria.shp", "EPSG:4326", "EPSG:4326");
		}
		else
			System.out.println("Not Connected");
	}
}
