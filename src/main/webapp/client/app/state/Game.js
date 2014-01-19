'use strict';

/**
 * One and only game state - here the magic happens!
 * 
 * @returns {void}
 */
GameSpace.GameState = function() {

};

GameSpace.GameState.prototype = (function() {
  return {
    create: function() {
      this.gCollisionInst = new GameSpace.CollisionController();
      this.pPlayerInst = new GameSpace.PlayerController();
      this.game.world.setBounds(16, 16, GameSpace.config.mapX, GameSpace.config.mapY);
      
      this.game.add.tileSprite(16, 16, GameSpace.config.mapX, GameSpace.config.mapY, 'background');
      
      GameSpace.container.init();
      GameSpace.serverInst = (new GameSpace.ServerController()).init();
    },
    update: function() {
      // Great place for some kind of a loader
      if (GameSpace.container.mapSpawned) {
        // Check input
        this.pPlayerInst.checkPlayerInput();
        // Collision checks
        this.gCollisionInst.handleTanksCollision();
      }
    }
  };
})();