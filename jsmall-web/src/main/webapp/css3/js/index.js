// Easy way to wait for all videos to load before start playing

var promises = [];
function makePromise(i, video) {
  promises[i] = new $.Deferred();
  // This event tells us video can be played all the way through, without stopping or buffering
  video.oncanplaythrough = function() {
    // Resolve the promise
    promises[i].resolve();
  }
}
// Pause all videos and create the promise array
$('video').each(function(index){
  this.pause();
  makePromise(index, this);
})

// Pause all videos and create the promise array
$('button').on("click",(function(){
    var userName = $('.name').val();  
    var passWord = $('.word').val();  
    var http = 'http://localhost:8080/jsmall-web/';
    alert(http + "longin/login");
    $.ajax({
        type: "POST",
        url: http + "longin/login",
        data: { user: userName, pass:passWord},
        dataType: "text",
        timeout: 15000,
        success: function (data) {
            if (data.code == 200 && data.message_code == 9998 && window.sessionStorage) {
                var token = data.result.user_logon_token;  
                window.sessionStorage.setItem("token",token);
                window.sessionStorage.setItem("http",http);
                window.location.href = "index.html";
                window.location.href ="view/list.html?token="+token;
            }
        }
    });
    
}));

// Wait for all promises to resolve then start playing
$.when.apply(null, promises).done(function () {
  $('video').each(function(){
    this.play();
  });
});