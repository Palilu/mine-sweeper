function postGame() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
             var responseObj = JSON.parse(this.response);
             window.location.replace("/games/" + responseObj.id);
         }
    };
    xhttp.open("POST", "/api/v1/games/", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    var rows = document.getElementById("rows").value;
    var columns = document.getElementById("columns").value;
    var mines = document.getElementById("mines").value;
    xhttp.send(JSON.stringify({rows:rows, columns:columns, mines:mines}));
}

function makeMove(gameId, row, column) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
             var responseObj = JSON.parse(this.response);
             window.location.replace("/games/" + responseObj.gameId);
         }
    };
    xhttp.open("POST", "/api/v1/games/" + gameId + "/moves", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    var type;
    var radios = document.getElementsByName('move-type');
    for (var i = 0, length = radios.length; i < length; i++) {
      if (radios[i].checked) {
        type = radios[i].value;
        break;
      }
    }
    xhttp.send(JSON.stringify({type:type, row:row, column:column}));
}
