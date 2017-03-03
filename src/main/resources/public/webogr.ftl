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
				width: 300px;
				height: 300px;
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

		<script type="text/javascript">	
					
			// Init JQuery
			$(document).ready( function() {				
				$('#explorer').fileTree({ root: './', script: '/getfiles', folderEvent: 'click', expandSpeed: 750, collapseSpeed: 750, multiFolder: false }, function(file) { 

					// Gets File Name
					file = file.substring(2);
					var files = file.split("/");
					var arrLen = files.length - 1;
					file = files[arrLen];

					$('#txtFile').val(file);
				});
			
				$('#explorer').on('filetreeexpand', 
			    		function (e, data){
							$('#txtPath').val(data.rel);
							//$('#btn_backup').html("Backup");
							$('#txtFile').val("");
			    });
				
				$("#btn_backup").click(function(){
					bootbox.alert("Backup");
			    });

				$("#btn_validate").click(function(){
					bootbox.alert("Validate");
			    });
			    
			    $("#btn_restore").click(function(){
					bootbox.alert("Restore");
			    });

			    $("#btn_credits").click(function() {
                    $('#window-credits').modal('toggle');
                });
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
			<div class="example">
				<label for="txtPath">Selected Path:</label>
				<input id="txtPath" type="text" class="form-control" placeholder="Path" name="txtPath" value = "${baseDir}" required disabled>
				
				<label for="cmbDb">DataBases:</label>
				<select id="cmbDb" class="form-control">
				</select>
				
				<input id="txtFile" type="text" class="form-control" placeholder="Backup File Name" name="txtFile" required>
								
				<div id="explorer" class="file_explorer"></div>
				<br>
				<div>
					<center>
						<button id="btn_backup" class="btn btn-lg btn-info">Backup</button>
						<button id="btn_validate" class="btn btn-lg btn-success">Restore</button>
					</center>
				</div>
			</div>    
		</div>
		
		<!-- Static Modal Restore Data -->
		<div class="modal fade" id="processing-restore" role="dialog" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		        	<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">Restore DataBase</h4>
				     </div>

				    <div class="modal-body">
				    	<label for="txtDbRestore">DataBase:</label>
		        		<input id="txtDbRestore" type="text" class="form-control" placeholder="DataBase Restore" name="txtDbRestore" required>
						<br>
						<button id="btn_restore" class="btn btn-lg btn-primary btn-block">Restore</button>
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
		<div class="modal modal-static fade" id="processing-modal" role="dialog" aria-hidden="true">
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