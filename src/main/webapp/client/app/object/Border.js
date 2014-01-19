'use strict';

/**
 * Border object. Used to add invisible borders around the map
 * 
 * @param {number} x
 * @param {number} y
 * @param {string} borderSide
 * @class
 */
GameSpace.Border = function(x, y, borderSide) {
  Phaser.Sprite.call(this, GameSpace.game, x, y, 'tiles');
  this.borderSide = borderSide;

  this.anchor.setTo(0.5, 0.5);
  this.body.immovable = true;
  if (borderSide === 'top' || borderSide === 'down') {
    this.body.setSize(GameSpace.config.mapX, 32);
  } else {
    this.body.setSize(32, GameSpace.config.mapY);
  }
};

GameSpace.Border.prototype = _.extend(Object.create(Phaser.Sprite.prototype), {
  update: null,
  render: null,
  postUpdate: null
});