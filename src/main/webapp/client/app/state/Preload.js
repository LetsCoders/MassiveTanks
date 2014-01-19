'use strict';

/**
 * Preloading state
 * 
 * @class
 */
GameSpace.PreloadState = function() {

};

GameSpace.PreloadState.prototype = (function() {
  return {
    preload: function() {
      this.load.spritesheet('tiles', 'client/assets/sprites/tiles.png', 32, 32);
      this.load.spritesheet('tanks', 'client/assets/sprites/tanks.png', 32, 32);
      this.load.atlasJSONHash('bullets', 'client/assets/sprites/bullets.png', 'client/assets/sprites/bullets.json');
      this.load.image('background', 'client/assets/sprites/background.png');

      this.load.audio('explosion', ['client/assets/sounds/explosion.wav']);
      this.load.audio('hit', ['client/assets/sounds/hit.wav']);
      this.load.audio('shoot', ['client/assets/sounds/shoot.wav']);
    },
    create: function() {      
      
    },
    update: function() {
      this.game.state.start('Game');
    }
  };
})();