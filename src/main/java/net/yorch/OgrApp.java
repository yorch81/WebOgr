package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
		//MSSQLSpatial sql = new MSSQLSpatial("localhost", "sa", "", "SGC_PRINCIPAL", 1433);
		MySQL sql = new MySQL("localhost", "root", "", "markers", 3306);
				
		if (sql.check()) {
			WOgr ogr =  new WOgr();
			System.out.println(sql.getOgrConnection());
			System.out.println(sql.getOgrTables());
			
			ogr.exportFromDb(sql, "construcciones", "C:/Code/Construcciones.shp", "EPSG:32614", "EPSG:32614");
			ogr.importToDb(sql, "Construcciones2", "C:/Code/Construcciones.shp", "EPSG:32614", "EPSG:4326");
		}
		else
			System.out.println("Not Connected");
	}

}
