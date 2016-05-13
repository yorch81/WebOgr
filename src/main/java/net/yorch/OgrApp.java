package net.yorch;

public class OgrApp {

	public static void main(String[] args) {
		System.out.println( "Web OGR Tool !!!" );
        
        String command = "ogr2ogr -f \"ESRI Shapefile\" \"C:/Code/Construcciones.shp\" \"MSSQL:server=148.237.75.92,1433;database=SGC_PRINCIPAL;uid=sa;pwd=passwd;\" -sql \"SELECT * FROM [dbo].[Construcciones]\" -overwrite -a_srs EPSG:32614";
        
        String[] aCommand; 
		
		if (System.getProperty("os.name").contains("Windows"))
			aCommand = new String[]{"cmd.exe","/c",command};
		else
			aCommand = new String[]{"/bin/bash","-c",command};
		
		int status = 1;
		
		try {
			Process ogr = Runtime.getRuntime().exec(aCommand);
			status = ogr.waitFor();
			
			System.out.println("Status:" + String.valueOf(status));
			
			System.out.println("Return Value:" + String.valueOf(ogr.exitValue()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
