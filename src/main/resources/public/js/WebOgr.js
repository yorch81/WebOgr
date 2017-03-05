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
 * Listen Button Eventes
 */
WebOgr.listen = function () {
  $("#btn_zip").click(function(){
    if ($("#btn_zip").prop('btn-action') == '1')
      bootbox.alert("Zip");
    else
      bootbox.alert("UnZip");
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