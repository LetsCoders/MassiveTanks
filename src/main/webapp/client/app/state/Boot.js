'use strict';

/**
 * Basic game settings - background and stuff
 * 
 * @class
 */
GameSpace.BootState = function() {

};

GameSpace.BootState.prototype = (function() {
  return {
    preload: function() {
      // Preload stuff etc
      this.game.stage.backgroundColor = '#000000';
    },
    create: function() {
      this.game.stage.disableVisibilityChange = true;
      this.game.state.start('Preload');
    }
  };
})();