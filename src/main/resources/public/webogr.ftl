<!DOCTYPE html>
<html lang="en">
	<head>
		<title>WebOgr Application</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="WebOgr Application">
		<meta name="author" content="Jorge Alberto Ponce Turrubiates">
		
    	<link rel="shortcut icon" type="image/x-icon" href="./img/favicon.ico">

		<style type="text/css">
			.body {
			 	min-height: 2000px;
			}

			.navbar-static-top {
			  	margin-bottom: 19px;
			}

			.navbar {
				min-height:110px;
				height:60px;
			}

			.bsnavbar {
			  	margin-bottom: 19px;
				min-height:110px;
			}
			
			.example {
				float: left;
				margin: 15px;
			}
			
			.file_explorer {
				width: 450px;
				height: 350px;
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
				overflow: scroll;
				padding: 5px;
			}

			.modal-static { 
		        position: fixed;
		        top: 50% !important; 
		        left: 50% !important; 
		        margin-top: -100px;  
		        margin-left: -100px; 
		        overflow: visible !important;
		    }

		    .modal-static,
		    .modal-static .modal-dialog,
		    .modal-static .modal-content {
		        width: 200px; 
		        height: 200px; 
		    }

		    .modal-static .modal-dialog,
		    .modal-static .modal-content {
		        padding: 0 !important; 
		        margin: 0 !important;
		    }

		    .modal-static .modal-content .icon {
		    }
		</style>
		
		<script src="./js/jquery-1.9.1.js" type="text/javascript"></script>		
    	
		<script src="./jQueryFileTree-master/jqueryFileTree.js" type="text/javascript"></script>
		<link href="./jQueryFileTree-master/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		<link rel="stylesheet" href="./metro-bootstrap-master/dist/css/metro-bootstrap.min.css" />
		<script src="../bootstrap-3.2.0-dist/js/bootstrap.min.js"></script>
    	<script src="./js/bootbox.js"></script>
    	<script src="./js/WebOgr.js"></script>

    	<link rel="stylesheet" href="./dropzone/dropzone.min.css" />
    	<script type="text/javascript" src="./dropzone/dropzone.min.js"></script>

		<script type="text/javascript">	

			// Init JQuery
			$(document).ready( function() {		
				// Select File		
				$('#explorer').fileTree({ root: './', script: '/getfiles', folderEvent: 'click', expandSpeed: 750, collapseSpeed: 750, multiFolder: false }, function(file) { 

					// Gets File Name
					WebOgr.selFile = file;
					file = file.substring(2);
										
					var files = file.split("/");
					var arrLen = files.length - 1;
					file = files[arrLen];

					WebOgr.setFileType(file);

					$('#txtFile').val(file);
				});
				
				// Select Directory
				$('#explorer').on('filetreeexpand', 
			    		function (e, data){
							$('#txtPath').val(data.rel);
							$('#txtFile').val("");

							WebOgr.fileType = 0;
							WebOgr.selFile = '';

							WebOgr.setDirectory();

							WebOgr.enableButtons();
			    });

                // DropZone Configuration
                Dropzone.autoDiscover = false;

                Dropzone.options.dropzonefile = {
			        uploadMultiple : false,
			        maxFiles : 1,
			        acceptedFiles: ".zip",
			        error: function(file, response) {
			            this.removeAllFiles();

			            bootbox.alert(response);
			          },
			        init: function() {
			          this.on("success", function(file, response) { 
			            this.removeAllFiles();

			            if (response.length == 0)
			            	console.log("Error on upload file");
			            else{
			            	bootbox.confirm("The ZIP File " + response +  " upload successfully, do you want reload page?", 
			            		function(result){
			            			if (result)
			            				location.reload();
			            		});
			            }
			          });
			        }
			    };

			    new Dropzone("#dropzonefile" , Dropzone.options.dropzonefile );

			    // Base Directory
			    WebOgr.baseDir = '${baseDir}';

			    // Listen Events
			    WebOgr.listen();

			    // Fill Combo
			    WebOgr.fillTables();
			});
		</script>

	</head>
	
	<body>
		<div class="navbar navbar-default navbar-static-top bsnavbar">
	      <div class="container">
	      	<div class="navbar-header">
	          <h2>WebOgr Tool</h2>
	    	</div>

	    	<ul class="nav navbar-nav navbar-right">
              <li class="active" id="btn_credits"><a href="#">Credits<span class="sr-only">(current)</span></a></li>
              <li><a href="/exit">Exit<span class="sr-only">(current)</span></a></li>
            </ul>
	      </div>
	    </div>
		
		<div class="container">
			<span>
				<div class="example">
					<label for="txtPath">Selected Path:</label>
					<input id="txtPath" type="text" class="form-control" placeholder="Path" name="txtPath" value = "${baseDir}" required disabled>
					
					<input id="txtFile" type="text" class="form-control" placeholder="Shapefile Name" name="txtFile" required>
									
					<div id="explorer" class="file_explorer"></div>
					<br>
					<div>
						<center>
							<button id="btn_zip" class="btn btn-lg btn-info" btn-action="1" disabled>Zip</button>
							<button id="btn_import" class="btn btn-lg btn-success" disabled>Import</button>
							<button id="btn_export" class="btn btn-lg btn-warning">Export</button>
							<button id="btn_mkdir" class="btn btn-lg btn-default">New Folder</button>
						</center>
					</div>
				</div>
				<!--  Upload Zip -->
				<div class="example">
					<label for="dropzonefile">Upload ZIP Shapefile:</label>

					<center>
						<form action="/upload" class="dropzone" id="dropzonefile">
				          <div class="dz-message">
				            <img src="./img/upload.png" class="img-responsive" alt="Dropzone">
				          </div>
				        </form>
				        <br>
				        <button id="btn_download" class="btn btn-lg btn-default">Download</button>
					</center>
				</div>
			</span>    
		</div>
		
		<!-- Static Modal Zip -->
		<div class="modal fade" id="modal_zip" role="dialog" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		        	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">Zip Directory</h4>
				     </div>

				    <div class="modal-body">
				    	<label for="txtZipFile">Zip File Name:</label>
		        		<input id="txtZipFile" type="text" class="form-control" placeholder="Zip File Name" name="txtZipFile" required>
						<br>
						<button id="btn_zipfile" class="btn btn-lg btn-primary btn-block">Zip Directory</button>
				    </div>
		        </div>
		    </div>
		</div>

		<!-- Static Modal Unzip -->
		<div class="modal fade" id="modal_unzip" role="dialog" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		        	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">UnZip File</h4>
				     </div>

				    <div class="modal-body">
				    	<label for="txtDirectory">Directory Name:</label>
		        		<input id="txtDirectory" type="text" class="form-control" placeholder="Directory Name" name="txtDirectory" required>
						<br>
						<button id="btn_unzip" class="btn btn-lg btn-primary btn-block">UnZip File</button>
				    </div>
		        </div>
		    </div>
		</div>

		<!-- Static Modal Import -->
		<div class="modal fade" id="modal_import" role="dialog" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		        	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">Import Shapefile</h4>
				     </div>

				    <div class="modal-body">
				    	<label for="txtTable">Table Name:</label>
		        		<input id="txtTable" type="text" class="form-control" placeholder="Table Name" name="txtTable" required>
						<br>
		        		<label for="cmbProj">Projection:</label>
						<select id="cmbProj" class="form-control">
							<option value="EPSG:4326" selected>EPSG:4326</option>
							<option value="EPSG:32614">EPSG:32614</option>
						</select>

		        		<br>
						<button id="btn_impshape" class="btn btn-lg btn-primary btn-block">Import Shapefile</button>
				    </div>
		        </div>
		    </div>
		</div>

		<!-- Static Modal Esport -->
		<div class="modal fade" id="modal_export" role="dialog" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		        	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">Export Table</h4>
				     </div>

				    <div class="modal-body">
				    	<label for="cmbTables">GeoReferential Tables:</label>
						<select id="cmbTables" class="form-control">
						</select>
						<br>
						<label for="txtShape">Shapefile Name:</label>
		        		<input id="txtShape" type="text" class="form-control" placeholder="Shapefile Name" name="txtShape" required>
		        		<br>
		        		<label for="cmbProjEx">Projection:</label>
						<select id="cmbProjEx" class="form-control">
							<option value="EPSG:4326" selected>EPSG:4326</option>
							<option value="EPSG:32614">EPSG:32614</option>
						</select>

		        		<br>
						<button id="btn_expshape" class="btn btn-lg btn-primary btn-block">Import Shapefile</button>
				    </div>
		        </div>
		    </div>
		</div>

		<!-- Static Modal Credits -->
        <div class="modal fade" id="window-credits" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Credits</h4>
                     </div>

                    <div class="modal-body">
                        <center>
                            <p><h3>Jorge Alberto Ponce Turrubiates</h3></p>
                            <p><h5><a href="mailto:the.yorch@gmail.com">the.yorch@gmail.com</a></h5></p>
                            <p><h5><a href="http://the-yorch.blogspot.mx/">Blog</a></h5></p>
                            <p><h5><a href="https://bitbucket.org/yorch81">BitBucket</a></h5></p>
                            <p><h5><a href="https://github.com/yorch81">GitHub</a></h5></p>
                            <p></p>
                        </center>
                    </div>
                </div>
            </div>
        </div>

		<!-- Static Modal Processing -->
		<div class="modal modal-static fade" id="modal_process" role="dialog" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		           <div class="modal-body">
		                <div class="text-center">
		                    <img src="./img/processing.gif" class="icon" />
		                    <h5 id="label-process">Processing... 
		                    </h5>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
	</body>
</html>