'use strict';

/**
 * Create full explosion sprite. Used for bricks and tanks destruction
 * 
 * @param {sprite} sprite
 * @returns {Phaser.Sprite}
 * @type {function}
 */
GameSpace.createExplosion = function(sprite) {
  var explosionSprite = GameSpace.game.add.sprite(sprite.x, sprite.y, 'tiles');
  explosionSprite.anchor.setTo(0.5, 0.5);
  explosionSprite.animations.add('explosion', GameSpace.game.math.numberArray(0, 4));

  return explosionSprite;
};

/**
 * Creates small explosion sprite. Used for bullet on hit explosion
 * 
 * @param {sprite} sprite
 * @returns {Phaser.Sprite}
 * @type {function}
 */
GameSpace.createSmallExplosion = function(sprite) {
  var explosionSprite = GameSpace.game.add.sprite(sprite.x, sprite.y, 'tiles');
  explosionSprite.anchor.setTo(0.5, 0.5);
  explosionSprite.animations.add('smallExplosion', GameSpace.game.math.numberArray(0, 2));

  return explosionSprite;
};