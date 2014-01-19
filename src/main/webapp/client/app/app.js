'use strict';

var GameSpace = {};

/**
 * Launches the whole games. Initializes states
 */
(function() {
  window.onload = function() {
    GameSpace.game = new Phaser.Game(GameSpace.config.windowSizeX, GameSpace.config.windowSizeY, Phaser.AUTO, 'gameContainer');
    GameSpace.game.state.add('Boot', GameSpace.BootState);
    GameSpace.game.state.add('Preload', GameSpace.PreloadState);
    GameSpace.game.state.add('Game', GameSpace.GameState);

    GameSpace.game.state.start('Boot');
  };
}());
