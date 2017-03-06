package net.yorch;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.json.JSONObject;
import org.zeroturnaround.zip.ZipUtil;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * WebApp<br>
 * 
 * Web Application<br><br>
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
 * @version    1.0.0, 2017-02-03
 * @author     <a href="mailto:the.yorch@gmail.com">Jorge Alberto Ponce Turrubiates</a>
 */
public class WebApp {    
	/**
     * Application User
     *
     * VAR String appUser Application User
     * @access private
     */
	private String appUser = "";
	
	/**
     * User Password
     *
     * VAR String appPassword User Password
     * @access private
     */
	private String appPassword = "";
	
	/**
     * Directory Base
     *
     * VAR String basedir Directory Base
     * @access private
     */
	private String basedir = "";
		
	/**
     * Default Database Connection
     *
     * VAR OgrConnection dftDb Default Database Connection
     * @access private
     */
	private OgrConnection dftDb;
	
	/**
	 * Constructor of WebApp
	 * 
	 * @param port int Application Port
	 * @param user String Application User
	 * @param password String Application Password
	 * @param basedir String Directory Base
	 * @param db OgrConnection Database Connection
	 * @see WebApp
	 */
	public WebApp(int port, String user, String password, String basedir, OgrConnection db) {
		this.appUser = user;
		this.appPassword = password;
		this.basedir = basedir;
		this.dftDb = db;
		
		/**
	     * Port Applicacion
	     */
		Spark.port(port);
		
		/**
	     * Public Files Path
	     */	
		Spark.staticFileLocation("/public");
		
		/**
	     * Path /
	     */
		get("/", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	        	StringWriter template = null;
	        	
	        	if (existsSession(request)){
	        		request.session().attribute("appdir", getBaseDir());
	        		
	        		template = getOgrTemplate();
	        	}
	        	else{
	        		String LoginError = request.session().attribute("loginerror");
	        		template = getLoginTemplate(LoginError);
	        	}
	        		
	        	if (template == null)
	        		halt(500, "Internal Error");
					
				response.status(200);
					
				// Returns Template
				return template;
	        }
	    });
			
		/**
	     * Path /exit
	     */
		get("/exit", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	        	request.session().removeAttribute("appuser");
	        	request.session().removeAttribute("loginerror");
	        	
	        	response.redirect("/");

	        	return "exit";
	        }
	    });

		/**
	     * Path /getfiles
	     * Get Files Structure
	     */
		post("/getfiles", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	        	String dir = request.queryParams("dir");
	        	
	        	String fs = getFiles(dir);
	        	
	        	return fs;
	        }
	    });
		
		// Upload Zip file
		post("/upload", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	        	String selDir = request.session().attribute("appdir");
	        	
	        	MultipartConfigElement multipartConfigElement = new MultipartConfigElement(selDir);
	        	
	        	request.raw().setAttribute("org.eclipse.multipartConfig", multipartConfigElement);
	        	String retResponse = "";
	        	
	        	try {
					Part file = (Part) request.raw().getPart("file");
										
					String fileName = UUID.randomUUID().toString().replace("-", "") + ".zip";
					
					retResponse = fileName;
					
					file.write(fileName);
				} catch (IOException | ServletException e) {
					e.printStackTrace();
				}
	        	   
	            return retResponse;
	        }
	    });
		
		// Set Selected Directory
		post("/setdir", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {	 
	        	String dir = request.queryParams("dir");
	        	
	        	request.session().attribute("appdir", dir);
	        	
	        	response.status(200);
	    		
	    		return dir;
	        }
	    });
		
		// Unzip Zip File
		post("/unzip", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {	
	        	String retResponse = "OK";
	        	
	        	String dir = request.queryParams("dir");
	        	String zip = request.queryParams("zip");
	        	
	        	String curDir = request.session().attribute("appdir");
	        	
	        	dir = curDir + dir;
	        	
	        	if (WebApp.dirExists(dir)){
	        		retResponse = "BAD";
	        	}
	        	else {
	        		// create Dir
	        		new File(dir).mkdir();
	        		
	        		// Unzip File
	        		ZipUtil.unpack(new File(zip), new File(dir));
	        	}
	        	
	        	response.status(200);
	        	
	    		return retResponse;
	        }
	    });
		
		// Zip Folder
		post("/zip", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {	 
	        	String retResponse = "OK";
	        	
	        	String dir = request.queryParams("dir");
	        	String zip = request.queryParams("zipname");
	        	
	        	String curDir = request.session().attribute("appdir");
	        	
	        	zip = curDir + "../" + zip;
	        	
	        	if (WebApp.fileExists(zip)){
	        		retResponse = "BAD";
	        	}
	        	else {
	        		// Zip Directory
	        		ZipUtil.pack(new File(dir), new File(zip));
	        	}
	        	
	        	response.status(200);
	        	
	    		return retResponse;
	        }
	    });
		
		get("/gettables", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	        	OgrConnection conn = getDftDb();
	        	
	        	response.status(200);
	        	
	        	String jsonTab = conn.getOgrTables();
	        	
	        	return jsonTab;
	        }
	    });
		
		// Import Shapefile
		post("/import", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	        	String retResponse = "OK";
	        	
	        	String sf = request.queryParams("file");
	        	String tn = request.queryParams("table");
	        	String pj = request.queryParams("proj");
	        	
	        	WOgr ogr =  new WOgr();
	        	
	        	if (! ogr.importToDb(getDftDb(), tn, sf, pj, pj))
	        		retResponse = "BAD";
	        	
	        	ogr = null;
	        	
	        	response.status(200);
	    		
	        	return retResponse;
	        }
	    });
		
		// Export Table to Shapefile
		post("/export", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {	 
	        	String retResponse = "OK";
	        	
	        	String sf = request.queryParams("file");
	        	String tn = request.queryParams("table");
	        	String pj = request.queryParams("proj");
	        	String curDir = request.session().attribute("appdir");
	        	
	        	sf = curDir + sf;
	        	
	        	WOgr ogr =  new WOgr();
	        	
	        	if (! ogr.exportFromDb(getDftDb(), tn, sf, pj, pj))
	        		retResponse = "BAD";
	        	
	        	ogr = null;
	        	
	        	response.status(200);
	    		
	        	return retResponse;
	        }
	    });
				
		/**
	     * Login User
	     */
	    post("/webauth", new Route() {
	        @Override
	        public Object handle(Request request, Response response) {
	            String user = request.queryParams("txtUser");
	            String password = request.queryParams("txtPassword");
	           	                        	            
	        	if (login(user, password)){
	        		request.session().attribute("appuser", user);
	        		
	        		response.redirect("/");
	        		
	        		return "";
	        	}
	        	else{
	        		request.session().attribute("loginerror", "Could not login with credentials");
	        		
	        		response.status(401);	        		
	        		response.redirect("/");
	        		
	        		return "Could not login with credentials";
	        	}
	        }
	    });
		
	}
	
	/**
	 * Return Login Template
	 * 
	 * @param loginError String Error Login if Exits
	 * @return StringWriter
	 */
	private StringWriter getLoginTemplate(String loginError) {
		Map<String, Object> tempData = new HashMap<String, Object>();
		tempData.put("loginError", loginError);
		
		FMTemplate loginTemp = new FMTemplate("login.ftl", tempData);
		
		StringWriter swLogin = loginTemp.get();
		
		loginTemp = null;
		
		return swLogin;
	}
	
	/**
	 * Get Ogr Template
	 * 
	 * @return StringWriter
	 */
	private StringWriter getOgrTemplate() {
		Map<String, Object> tempData = new HashMap<String, Object>();
		
		tempData.put("baseDir", this.basedir);
		
		FMTemplate ogrTemp = new FMTemplate("webogr.ftl", tempData);
		
		StringWriter swOgr = ogrTemp.get();
		
		ogrTemp = null;
		
		return swOgr;
	}
	
	/**
	 * Checks login of user and password
	 * 
	 * @param user User Application
	 * @param password Password User
	 * @return boolean
	 */
	private boolean login(String user, String password){
		if (user.equals(this.appUser) && password.equals(this.appPassword))
			return true;
		else
			return false;
	}
	
	/**
	 * Gets Base Directory
	 * 
	 * @return String
	 */
	private String getBaseDir(){
		return this.basedir;
	}
	
	/**
	 * Get Default Database Connection
	 * 
	 * @return OgrConnection
	 */
	private OgrConnection getDftDb(){
		return this.dftDb;
	}
	
	/**
     * Checks if Session Exists
     * 
     * @return boolean
     */
	private boolean existsSession(Request request){
		boolean retValue = false;
		
		String user = request.session().attribute("appuser");
	
		if (user != null){
			retValue = true;
		}
				
		return retValue;
	}
		
	/**
	 * Check if Directory exists
	 * 
	 * @param dir String Directory
	 * @return boolean
	 */
	public static boolean dirExists(String dir){
		File dirFile = new File(dir);
	
		return dirFile.exists();
	}
	
	/**
	 * Check if File Exists
	 * 
	 * @param filePath String File Full Name
	 * @return boolean
	 */
	public static boolean fileExists(String filePath){
		File file = new File(filePath);
	
		return file.exists();
	}
	
	/**
	 * Return Files Structure
	 * 
	 * @param dir String Subdirectory
	 * @return String Files Structure
	 */
	private String getFiles(String dir){
		StringBuffer fs = new StringBuffer("");
						
		if (dir == null) {
			return "";
	    }
		
		if (dir.charAt(dir.length()-1) == '\\') {
	    	dir = dir.substring(0, dir.length()-1) + "/";
		} else if (dir.charAt(dir.length()-1) != '/') {
		    dir += "/";
		}
				
		try {
			dir = java.net.URLDecoder.decode(dir, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		if (dir.equals("./")){
			dir = this.basedir;
		}
		
	    if (new File(dir).exists()) {
			String[] files = new File(dir).list(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
					return name.charAt(0) != '.';
			    }
			});
			
			Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
			fs.append("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
			
			// All dirs
			for (String file : files) {
			    if (new File(dir, file).isDirectory()) {
			    	fs.append("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + file + "/\">"
						+ file + "</a></li>");
			    }
			}
			
			// All files
			for (String file : files) {
			    if (!new File(dir, file).isDirectory()) {
					int dotIndex = file.lastIndexOf('.');
					String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";
					fs.append("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + dir + file + "\">"
						+ file + "</a></li>");
			    	}
			}
			
			fs.append("</ul>");
	    }
	    
		return fs.toString();
	}
}


