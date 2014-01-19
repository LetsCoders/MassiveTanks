'use strict';

/**
 * Server controller. Handles all server communication. Established connection when instantiated
 * 
 * @returns {function}
 * @class
 */
GameSpace.ServerController = function() {
  this.transport = null;
};

GameSpace.ServerController.prototype = (function() {
  return {
    init: function() {
      this.transport = createTransport();
      return this;
    },
    /**
     * Sends move packet to the server
     * 
     * @param {string} direction
     * @returns {void}
     */
    sendMovePacket: function(direction) {
      this.transport.push(JSON.stringify({type: 'directionChange', direction: direction}));
    },
    /**
     * Sends fire packet to the server. Server responds with spawn bullet packet
     * 
     * @returns {void}
     */
    sendFirePacket: function() {
      this.transport.push(JSON.stringify({type: 'tankFire'}));
    },
    /**
     * Sends stop movement packet to the server
     * 
     * @returns {void}
     */
    sendStopPacket: function() {
      this.transport.push(JSON.stringify({type: 'directionChange', direction: 'NONE'}));
    },
    /**
     * Sends respawn request to the server
     * 
     * @returns {void}
     */
    sendRespawnPacket: function() {
      this.transport.push(JSON.stringify({type: 'respawn'}));
    }
  };

  /**
   * Inits the websocket connection
   * 
   * @returns {void}
   */
  function createTransport() {
    return atmosphere.subscribe(getRequest());
  }

  /**
   * Init server communication request
   * 
   * @returns {object}
   */
  function getRequest() {
    var transporter = 'websocket';
    var ServerResponse = new GameSpace.ServerResponse();
    var request = {
      url: getServerUrl(),
      contentType: 'application/json',
      logLevel: 'debug',
      transport: transporter,
      trackMessageLength: true,
      reconnectInterval: 5000,
      enableXDR: true,
      enableProtocol: true,
      timeout: 100000
    };

    request.onOpen = function(response) {
      transporter = response.transport;
    };

    request.onMessage = function(response) {
      ServerResponse.process(JSON.parse(response.responseBody));
    };

    return request;
  }

  /**
   * Returns server request url for websockets
   * 
   * @returns {string}
   */
  function getServerUrl() {
    return document.location.origin + document.location.pathname + "/tanks";
  }
})();

