package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
		//OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "Preproduccion", "sa", "", "SGC_PRINCIPAL", 1433);
		//OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "localhost", "sa", "", "SGC_PRINCIPAL", 1433);
		OgrConnection sql = new OgrConnection(OgrConnection.PostGis, "localhost", "postgres", "", "postgis_23_sample", 5432);
		//OgrConnection sql = new OgrConnection(OgrConnection.MySQL, "localhost", "root", "", "GEO", 3306);
		
		if (sql.checkConnection()) {
			WOgr ogr =  new WOgr();
						
			System.out.println(sql.getOgrTables());
			
			//ogr.importToDb(sql, "sectores_vic3", "D:/Shapes/SECTORES_VIC/Sectores.shp", "EPSG:32614", "EPSG:32614");
			ogr.exportFromDb(sql, "sectores_vic2", "C:/Code/sec_vic2.shp", "EPSG:32614", "EPSG:32614");
		}
		else
			System.out.println("Not Connected");
	}
}
