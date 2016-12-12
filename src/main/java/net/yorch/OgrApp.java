package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
		//OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "Preproduccion", "sa", "", "SGC_PRINCIPAL", 1433);
		OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "localhost", "sa", "", "SGC_PRINCIPAL", 1433);
		//OgrConnection sql = new OgrConnection(OgrConnection.PostGis, "localhost", "postgres", "", "postgis_23_sample", 5432);
		//OgrConnection sql = new OgrConnection(OgrConnection.MySQL, "localhost", "root", "", "GEO", 3306);
		
		if (sql.checkConnection()) {
			WOgr ogr =  new WOgr();
						
			System.out.println(sql.getOgrTables());
			
			//ogr.importToDb(sql, "predios_vic", "D:/Shapes/SHP_Guemez_2D/Predios_2d.shp", "EPSG:32614", "EPSG:32614");
			//ogr.importToDb(sql, "const_guemez", "D:/Shapes/SHP_Guemez_2D/Construcciones_2d.shp", "EPSG:32614", "EPSG:32614");
			//ogr.importToDb(sql, "mzns_guemez", "D:/Shapes/SHP_Guemez_2D/Manzanas_2d.shp", "EPSG:32614", "EPSG:32614");
			ogr.exportFromDb(sql, "predios_vic", "D:/Shapes/predios_all.shp", "EPSG:32614", "EPSG:32614");
		}
		else
			System.out.println("Not Connected");
	}
}
