var lat = 0;
var lng = 0;


$.ajax({
    url: getBaseURL()+"googleSetting/list",
    type: 'post',
    dataType: 'json',
    async:false,
    success:function(response) {
        lat = response.records.zoomSettingLat; 
        lng = response.records.zoomSettingLng;
        function loadScript() {
          var script = document.createElement('script');
          script.type = 'text/javascript';
          script.src = 'https://maps.googleapis.com/maps/api/js?key=' + response.records.key_value +'&libraries=places,drawing,geometry&callback=init'; 
          document.body.appendChild(script);
        }
        window.onload = loadScript;
    } 
});
