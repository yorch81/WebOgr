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
 * Selected File
 * 
 * @type {String}
 */
WebOgr.selFile = '';

/**
 * Listen Button Eventes
 */
WebOgr.listen = function () {
  $("#btn_zip").click(function(){
    if ($("#btn_zip").prop('btn-action') == '1'){
      $('#modal_zip').modal('toggle');
    }
    else{
      $('#modal_unzip').modal('toggle');
    }
  });

  $("#btn_zipfile").click(function(){
    WebOgr.zipDirectory();
  });

  $("#btn_unzip").click(function(){
    WebOgr.unzipFile();
  });

  $("#btn_import").click(function(){
    bootbox.alert("Import");
  });
    
  $("#btn_export").click(function(){
    bootbox.alert("Export");
  });

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

WebOgr.zipDirectory = function () {
  var fileZip = $('#txtZipFile').val();

  if (! fileZip.endsWith(".zip")){
    fileZip = fileZip + '.zip';
  }

  $.post('/zip', {dir: $('#txtPath').val(), zipname: fileZip},
          function(response, status) {
            if (status == "success"){
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


WebOgr.unzipFile = function () {
  $.post('/unzip', {dir: $('#txtDirectory').val(), zip: WebOgr.selFile},
          function(response, status) {
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