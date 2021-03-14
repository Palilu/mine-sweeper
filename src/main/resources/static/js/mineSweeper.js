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
    xhttp.send(JSON.stringify({rows:10, columns:10, mines:10}));
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
    xhttp.send(JSON.stringify({type:'CHECK_MINE', row:row, column:column}));
}



