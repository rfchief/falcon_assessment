<!DOCTYPE html>
<html>
<head>
    <title>Apache Access Log View</title>
    <script src="http://localhost:8080/resources/sockjs.min.js"></script>
    <script src="http://localhost:8080/resources/stomp.min.js"></script>
    <script type="text/javascript">
        var stompClient = null;
        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('getAccessLogDiv').style.visibility = connected ? 'visible' : 'hidden';
            document.getElementById('accessLogResponse').innerHTML = '';
        }
        function connect() {
            var socket = new SockJS('/accesslog');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/falcon/show/logs', function(accessLogResult){
                    showResult(JSON.parse(accessLogResult.body).result);
                });
            });
        }
        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }
        function sendParams() {
            var seq = document.getElementById('seq').value;
            var offset = document.getElementById('offset').value;
            var size = document.getElementById('size').value;
            stompClient.send("/falcon/socket/accesslog", {}, JSON.stringify({ 'seq': seq, 'offset': offset, 'size': size }));
        }
        function showResult(message) {
            var response = document.getElementById('accessLogResponse');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }
    </script>
</head>
<body>
<noscript><h2>Enable Java script and reload this page to run Websocket</h2></noscript>
<h1>Show Apache Access Logs</h1>
<div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button><br/><br/>
    </div>
    <div id="getAccessLogDiv">
        <label>seq:</label><input type="text" id="seq" /><br/>
        <label>offset:</label><input type="text" id="offset" /><br/>
        <label>size:</label><input type="text" id="size" /><br/><br/>
        <button id="sendParams" onclick="sendParams();">Send to Params</button>
        <p id="accessLogResponse"></p>
    </div>
</div>
</body>
</html>