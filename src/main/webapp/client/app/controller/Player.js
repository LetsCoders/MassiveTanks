'use strict';

/**
 * Manages player input and sends it to the server
 * 
 * @param {object} GameSpace.
 * @class
 */
GameSpace.PlayerController = function() {
  this.isMoving = false;
  this.lastFiredTimestamp = (new Date()).getTime();
  this.directions = [];
  this.lastDirection = 'NONE';

  this.keys = GameSpace.game.input.keyboard.createCursorKeys();
  this.spaceButton = GameSpace.game.input.keyboard.addKey(Phaser.Keyboard.SPACEBAR);
};

GameSpace.PlayerController.prototype = (function() {
  return {
    /**
     * Used in state update() to check for player input checks
     * 
     * @returns {void}
     */
    checkPlayerInput: function() {
      var inputCheckTimestamp = (new Date()).getTime();
      var newLastKey;
      var newLastDirection;

      if (GameSpace.container.playerTank && GameSpace.container.playerTank.tankHp > 0) {
        // Updates this.directions array
        updateDirections.call(this);

        newLastKey = this.directions[this.directions.length - 1];
        newLastDirection = keyToString.call(this, newLastKey);
        if (newLastKey && newLastDirection !== this.lastDirection) {
          this.lastDirection = newLastDirection;
          GameSpace.serverInst.sendMovePacket(this.lastDirection)
        } else if (!newLastKey && this.lastDirection !== 'NONE') {
          this.lastDirection = 'NONE';
          GameSpace.serverInst.sendStopPacket();
        }
      } else {
        if (this.spaceButton.isDown) {
          GameSpace.serverInst.sendRespawnPacket();
        }
      }

      if (this.spaceButton.isDown &&
              ((inputCheckTimestamp - this.lastFiredTimestamp) / 1000) > GameSpace.config.tankFireDelay) {
        this.lastFiredTimestamp = (new Date()).getTime();
        GameSpace.serverInst.sendFirePacket();
      }
    }
  };

  /**
   * Checks which key is down/up and adds it to {this.directions} array or removes from it
   * 
   * @returns {void}
   */
  function updateDirections() {
    var keyName;
    var key;
    var keyIndex;
    for (keyName in this.keys) {
      key = this.keys[keyName];
      keyIndex = this.directions.indexOf(key);
      if (key.isDown && keyIndex === -1) {
        this.directions.push(key);
      }

      if (key.isUp && keyIndex !== -1) {
        this.directions.splice(keyIndex, 1);
      }
    }
  }
  /**
   * Converts certain key to string
   * 
   * @param {int} key
   * @returns {string}
   */
  function keyToString(key) {
    if (key === this.keys.up) {
      return 'NORTH';
    } else if (key === this.keys.down) {
      return 'SOUTH';
    } else if (key === this.keys.left) {
      return 'WEST';
    } else if (key === this.keys.right) {
      return 'EAST';
    }

    return false;
  }
})();