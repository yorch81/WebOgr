package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
		OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "IICSRVPRUEBAS", "sa", "u4tpa55w0rd", "SGC_CARTO", 1433);
		//OgrConnection sql = new OgrConnection(OgrConnection.MSSQLSpatial, "192.168.9.76", "sa", "ser3.PrmT", "SGC_PRINCIPAL", 1433);
		//OgrConnection sql = new OgrConnection(OgrConnection.PostGis, "localhost", "postgres", "", "postgis_23_sample", 5432);
		//OgrConnection sql = new OgrConnection(OgrConnection.MySQL, "localhost", "root", "r00tmysql", "GEO", 3306);
		
		if (sql.checkConnection()) {
			WOgr ogr =  new WOgr();
						
			//System.out.println(sql.getOgrTables());
			
			//ogr.importToDb(sql, "predios_vic", "D:/Shapes/SHP_Guemez_2D/Predios_2d.shp", "EPSG:32614", "EPSG:32614");
			//ogr.importToDb(sql, "gz_sector", "C:/CODE/shapes/Manzanas_2d.shp", "EPSG:32614", "EPSG:32614");
			//ogr.importToDb(sql, "pred_tula", "C:/CODE/shapes/2D/PREDIOS_TULA.shp", "EPSG:32614", "EPSG:32614");
			ogr.exportFromDb(sql, "gz_sector", "C:/shapes/gz_sector.shp", "EPSG:32614", "EPSG:32614");
		}
		else
			System.out.println("Not Connected");
	}
}
