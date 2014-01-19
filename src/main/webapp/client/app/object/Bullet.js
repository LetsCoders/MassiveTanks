'use strict';

/**
 * Bullet object
 * 
 * @param {hp} id
 * @param {number} x
 * @param {number} y
 * @param {string} facingDir
 * @param {number} bulletSpeed
 * @class
 */
GameSpace.Bullet = function(id, x, y, facingDir, bulletSpeed) {
  Phaser.Sprite.call(this, GameSpace.game, x, y, 'bullets');

  this.anchor.setTo(0.5, 0.5);
  this.bulletId = id;
  this.bulletHitSound = GameSpace.game.add.audio('hit', 1, true);
  this.bulletSpeed = bulletSpeed;

  this.body.customSeparateX = true;
  this.body.customSeparateY = true;

  this.bSetFacingDirection(facingDir);
  this.bStartMovement();
};

GameSpace.Bullet.prototype = (function() {
  return _.extend(Object.create(Phaser.Sprite.prototype), {
    /**
     * Sets velocity and adjusts bullet body based on facing direction
     * 
     * @returns {void}
     */
    bStartMovement: function() {
      switch (this.facingDir) {
        case 'NORTH':
          this.bSetVelocity(0, -this.bulletSpeed);
          this.body.setSize(8, 5);
          break;
        case 'SOUTH':
          this.bSetVelocity(0, this.bulletSpeed);
          this.body.setSize(8, 5);
          break;
        case 'WEST':
          this.bSetVelocity(-this.bulletSpeed, 0);
          this.body.setSize(5, 8);
          break;
        case 'EAST':
          this.bSetVelocity(this.bulletSpeed, 0);
          this.body.setSize(5, 8);
          break;
      }
    },
    /**
     * Set bullet velocity
     * 
     * @param {number} xVel
     * @param {number} yVel
     * @returns {void}
     */
    bSetVelocity: function(xVel, yVel) {
      this.body.velocity.x = xVel;
      this.body.velocity.y = yVel;
    },
    /**
     * Sets bullet facing direction
     * 
     * @param {strings} facingDir
     * @returns {void}
     */
    bSetFacingDirection: function(facingDir) {
      this.facingDir = facingDir;
      switch (facingDir) {
        case 'NORTH':
          this.frameName = 'b_top.png';
          break;
        case 'SOUTH':
          this.frameName = 'b_bot.png';
          break;
        case 'WEST':
          this.frameName = 'b_left.png';
          break;
        case 'EAST':
          this.frameName = 'b_right.png';
          break;
      }
    },
    /**
     * Handles bullet destruction. Removes it from GameSpace.container
     * 
     * @returns {void}
     */
    bDestroy: function() {
      var explosionSprite = GameSpace.createSmallExplosion(this);

      this.bulletHitSound.play('', 0, 1, false);
      explosionSprite.animations.play('smallExplosion', 12, false, true);

      GameSpace.container.removeBullet(this);
    },
    update: null,
    render: null
  });
})();