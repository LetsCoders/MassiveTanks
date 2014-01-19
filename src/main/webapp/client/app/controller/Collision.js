'use strict';

/**
 * Contains methods used to manage collisions between sprites
 * 
 * @class
 */
GameSpace.CollisionController = function() {

};

GameSpace.CollisionController.prototype = (function() {  
  return {
    /**
     * Handles certain tank collision between all other sprites
     * 
     * @returns {void}
     */
    handleTanksCollision: function() {
      GameSpace.game.physics.collide(GameSpace.container.tanks, GameSpace.container.tanks); // Check this one
      GameSpace.game.physics.collide(GameSpace.container.tanks, GameSpace.container.tiles.collidable);
      GameSpace.game.physics.collide(GameSpace.container.tanks, GameSpace.container.borders);
    }
  };
})();