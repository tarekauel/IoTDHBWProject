"use strict";

var socket = $.atmosphere;
var subSocket;
var transport = 'websocket';

// We are now ready to cut the request
var request = { url: document.location.protocol + "//" + document.location.host + '/average-runtime',
    contentType: "application/json",
    logLevel: 'debug',
    transport: transport,
    trackMessageLength: true,
    enableProtocol: true,
    fallbackTransport: 'long-polling'};


request.onOpen = function (response) {
    console.log("open");
};

request.onMessage = function (response) {
    console.log(response);
    tick(JSON.parse(response.responseBody).difference);
};

request.onClose = function (response) {
    console.log("close");
};

request.onError = function (response) {
    console.log("error");
};

subSocket = socket.subscribe(request);
