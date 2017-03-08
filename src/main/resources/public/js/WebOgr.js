/**
 * Web ogr2ogr Application
 */
function WebOgr(){  
}

/**
 * File Type -1 Invalid 0 Directory 1 Shapefile 2 Zip
 * 
 * @type {int}
 */
WebOgr.fileType = 0;

/**
 * Base Directory
 * 
 * @type {String}
 */
WebOgr.baseDir = '';

/**
 * Selected File
 * 
 * @type {String}
 */
WebOgr.selFile = '';

/**
 * Listen Button Eventes
 */
WebOgr.listen = function () {
  // Load Zip Unzip
  $("#btn_zip").click(function(){
    if ($("#btn_zip").prop('btn-action') == '1'){
      $('#txtZipFile').val(''); 
      $('#modal_zip').modal('toggle');
    }
    else{
      $('#txtDirectory').val(''); 
      $('#modal_unzip').modal('toggle');
    }
  });

  // Zip File
  $("#btn_zipfile").click(function(){
    if ($('#txtZipFile').val() == '')
      bootbox.alert("Must type Zip File")
    else
      WebOgr.zipDirectory();
  });

  // Unzip File
  $("#btn_unzip").click(function(){
    if ($('#txtDirectory').val() == '')
      bootbox.alert("Must type Directory")
    else
      WebOgr.unzipFile();
  });

  // Load Import Shapefile
  $("#btn_import").click(function(){
    $('#txtTable').val('');
    $('#modal_import').modal('toggle');
  });
  
  // Import Shapefile
  $("#btn_impshape").click(function(){
    if ($('#txtTable').val() == '')
      bootbox.alert("Must type Table Name")
    else
      WebOgr.importShape();
  });

  // Load Export Table
  $("#btn_export").click(function(){
    $('#txtShape').val('');
    $('#modal_export').modal('toggle');
  });

  // Export Table
  $("#btn_expshape").click(function(){
    if ($('#txtShape').val() == '')
      bootbox.alert("Must type Shapefile Name")
    else
      WebOgr.exportShape();
  });

  // Load Make Folder
  $("#btn_mkdir").click(function(){
    bootbox.prompt("Type Folder Name:", 
      function(result){ 
        if (result != null){
          if (result == ''){
            bootbox.alert("Must type Folder Name");
          }
          else{
            WebOgr.mkDir(result);
          }
        }
      });
  });

  // Download File
  $("#btn_download").click(function() {
    if (WebOgr.fileType != 0){
      var urlDownload = '/' + WebOgr.selFile.replace(WebOgr.baseDir,'');
    
      window.open(
        urlDownload,
        '_blank'
      );
    }
    else{
      bootbox.alert("Must select a File");
    }
  });

  // Load Credits
  $("#btn_credits").click(function() {
    $('#window-credits').modal('toggle');
  });
}

/**
 * Sets Current Directory
 */
WebOgr.setDirectory = function () {
  $.post('/setdir', {dir: $('#txtPath').val()},
          function(response,status) {
                    //console.log(response);
                  }).error(
                      function(){
                          console.log('Application not responding');
                      }
                  );
}

/**
 * Zip Directory
 */
WebOgr.zipDirectory = function () {
  var fileZip = $('#txtZipFile').val();

  if (! fileZip.endsWith(".zip")){
    fileZip = fileZip + '.zip';
    $('#txtZipFile').val(fileZip);
  }

  $('#modal_process').modal('toggle');

  $.post('/zip', {dir: $('#txtPath').val(), zipname: fileZip},
          function(response, status) {
            if (status == "success"){
              $('#modal_process').modal('hide');
              if (response == "BAD"){
                bootbox.alert("File already exists");
              }
              else{
                bootbox.confirm("Zip File successfully, do you want reload page?", 
                      function(result){
                        if (result)
                          location.reload();
                        else
                          $('#modal_unzip').modal('hide');
                      });
              }  
            }
                  }).error(
                      function(){
                          console.log('Application not responding');
                      }
                  );
}

/**
 * Unzip  File
 */
WebOgr.unzipFile = function () {
  $('#modal_process').modal('toggle');

  $.post('/unzip', {dir: $('#txtDirectory').val(), zip: WebOgr.selFile},
          function(response, status) {
            $('#modal_process').modal('hide');

            if (status == "success"){
              if (response == "BAD"){
                bootbox.alert("Directory already exists");
              }
              else{
                bootbox.confirm("Unzip File successfully, do you want reload page?", 
                      function(result){
                        if (result)
                          location.reload();
                        else
                          $('#modal_unzip').modal('hide');
                      });
              }  
            }
                  }).error(
                      function(){
                          console.log('Application not responding');
                      }
                  );
}

/**
 * Fill Combo Tables
 */
WebOgr.fillTables = function () {
  $.get('/gettables', function(response, status){
                  if (status == "success"){
                    var jTables = JSON.parse(response);
                    var selHtml = '';

                    for(var i in jTables) {
                      selHtml = selHtml + '<option value="' + jTables[i] + '">' + jTables[i] + '</option>';
                    }

                    $('#cmbTables').html(selHtml);
                  }
                  else
                    console.log("Error in Status");
                }).error(
                        function(){
                            console.log('Application not responding');
                        });
}

/**
 * Import Shapefile
 */
WebOgr.importShape = function () {
  $('#modal_import').modal('hide');
  $('#modal_process').modal('toggle');

  $.post('/import', {file: WebOgr.selFile, 
                    table: $('#txtTable').val(),
                    proj : $('#cmbProj').val()},
          function(response, status) {
            $('#modal_process').modal('hide');

            if (status == "success"){
              if (response == "BAD"){
                bootbox.alert("Error on import Shapefile");
              }
              else{
                bootbox.alert("Import Shapefile successfully");

                WebOgr.fillTables();
              }  
            }
                  }).error(
                      function(){
                          console.log('Application not responding');
                      }
                  );
}

/**
 * Export Shapefile
 */
WebOgr.exportShape = function () {
  var fileShp= $('#txtShape').val();

  if (! fileShp.endsWith(".shp")){
    fileShp = fileShp + '.shp';
    $('#txtShape').val(fileShp);
  }

  $('#modal_export').modal('hide');
  $('#modal_process').modal('toggle');

  $.post('/export', {file: $('#txtShape').val(),
                  table: $('#cmbTables').val(),
                  proj : $('#cmbProjEx').val()},
          function(response, status) {
            $('#modal_process').modal('hide');

            if (status == "success"){
              if (response == "BAD"){
                bootbox.alert("Error on Export Table to Shapefile");
              }
              else{
                bootbox.confirm("Export Table to Shapefile successfully, do you want reload page?", 
                      function(result){
                        if (result)
                          location.reload();
                        else
                          $('#modal_export').modal('hide');
                      });
              }  
            }
                  }).error(
                      function(){
                          console.log('Application not responding');
                      }
                  );
}

/**
 * Make New Folder
 * 
 * @param  {string} folder Folder Name
 */
WebOgr.mkDir = function (folder) {
  $.post('/mkdir', {dir: folder},
          function(response, status) {
            if (status == "success"){
              if (response == "BAD"){
                bootbox.alert("Directory already exists");
              }
              else{
                bootbox.confirm("Directory created successfully, do you want reload page?", 
                      function(result){
                        if (result)
                          location.reload();
                      });
              }  
            }
                  }).error(
                      function(){
                          console.log('Application not responding');
                      }
                  );
}

/**
 * Set File Type
 * 
 * @param {string} file File Full Name
 */
WebOgr.setFileType = function (file) {
  var fileSt = file.split(".");
  var fileExt = fileSt[1];

  if (fileExt == 'shp' | fileExt == 'SHP') {
    WebOgr.fileType = 1;
  } else if (fileExt == 'zip' | fileExt == 'ZIP') {
      WebOgr.fileType = 2;
  } else {
      WebOgr.fileType = -1;
  }

  WebOgr.enableButtons();
}

/**
 * Enable Buttons
 */
WebOgr.enableButtons = function() {
  if (WebOgr.fileType == 0) {
    $("#btn_zip").prop('disabled', false);
    $("#btn_zip").prop('btn-action', 1);
    $("#btn_zip").html('Zip');

    $("#btn_import").prop('disabled', true);
  } else if (WebOgr.fileType == 1) {
    $("#btn_zip").prop('disabled', true);
    $("#btn_import").prop('disabled', false);
  } else if (WebOgr.fileType == 2) {
    $("#btn_zip").prop('disabled', false);
    $("#btn_zip").prop('btn-action', 2);
    $("#btn_zip").html('UnZip');

    $("#btn_import").prop('disabled', true);
  } else {
    $("#btn_zip").prop('disabled', true);
    $("#btn_import").prop('disabled', true);
  }
}