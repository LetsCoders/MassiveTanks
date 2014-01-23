'use strict';

/**
 * Tank object. Extends Phaser.Sprite
 * 
 * @param {string} tankColor
 * @param {int} id
 * @param {string} name
 * @param {int} hp
 * @param {number} x
 * @param {number} y
 * @param {string} facingDir
 * @param {string} movementDir
 * @class
 */
GameSpace.Tank = function(tankColor, id, name, hp, x, y, facingDir, movementDir) {
  Phaser.Sprite.call(this, GameSpace.game, x, y, 'tanks');

  this.tankId = id;
  this.tankHp = hp;
  this.tankName = name;
  this.tankColor = tankColor;
  this.tankExplosionSound = GameSpace.game.add.audio('explosion', 1, true);

  this.body.setSize(25, 25);
  this.anchor.setTo(0.5, 0.5);
  this.tSetFacingDirection(facingDir);
  this.tSetMovementDirection(movementDir);

  // Check whether this tank is current's players tank or an opposing force
  if (this.tankColor === GameSpace.config.otherTankColor) {
    this.animations.add('move', GameSpace.game.math.numberArray(8, 14));
  } else {
    this.animations.add('move', GameSpace.game.math.numberArray(0, 6));
  }
  
  this.update = null;
};

GameSpace.Tank.prototype = (function() {
  return _.extend(Object.create(Phaser.Sprite.prototype), {
    /**
     * Updates tank position data and movement/facing direction. Used by server to adjust tank's 
     * position and directions
     * 
     * @param {number} x
     * @param {number} y
     * @param {string} facingDir
     * @param {string} movementDir
     * @returns {void}
     */
    tUpdateTankPosition: function(x, y, facingDir, movementDir) {
      this.x = x;
      this.y = y;

      this.tSetFacingDirection(facingDir);
      this.tSetMovementDirection(movementDir);
    },
    /**
     * Handles tank movement
     * 
     * @param {number} velocity
     * @returns {void}
     */
    tMove: function(velocity) {
      if (velocity) {
        this.tSetVelocity(velocity.x, velocity.y);
      }

      if (this.tankMovementDir !== 'NONE') {
        this.tSetFacingDirection(this.tankMovementDir);
        this.tStartAnimation();

        switch (this.tankMovementDir) {
          case 'NORTH':
            this.tSetVelocity(0, -GameSpace.config.tankSpeed);
            break;
          case 'SOUTH':
            this.tSetVelocity(0, GameSpace.config.tankSpeed);
            break;
          case 'WEST':
            this.tSetVelocity(-GameSpace.config.tankSpeed, 0);
            break;
          case 'EAST':
            this.tSetVelocity(GameSpace.config.tankSpeed, 0);
            break;
        }
      } else {
        this.tSetVelocity(0, 0);
        this.tStopAnimation();
      }
    },
    /**
     * Sets tank HP. If HP = 0 - tank is destroyed
     * 
     * @param {int} hp
     * @returns {void}
     */
    tSetHp: function(hp) {
      this.tankHp = hp;
      if (hp === 0) {
        this.tDestroy();
      }
    },
    /**
     * Applies velocity to the tank in currently set [movementDirection]
     * 
     * @param {number} xVel
     * @param {number} yVel
     * @returns {void}
     */
    tSetVelocity: function(xVel, yVel) {
      this.body.velocity.x = xVel;
      this.body.velocity.y = yVel;
    },
    /**
     * Turns tank in certain direction
     * 
     * @param {string} facingDir
     * @returns {void}
     */
    tSetFacingDirection: function(facingDir) {
      this.facingDirection = facingDir;

      switch (facingDir) {
        case 'NORTH':
          this.angle = 180;
          break;
        case 'SOUTH':
          this.angle = 0;
          break;
        case 'WEST':
          this.angle = 90;
          break;
        case 'EAST':
          this.angle = -90;
          break;
      }
    },
    tSetMovementDirection: function(movementDir) {
      this.tankMovementDir = (movementDir) ? movementDir : 'NONE';
    },
    /**
     * Stops current tank animation (actually pauses it, but..)
     * 
     * @returns {void}
     */
    tStopAnimation: function() {
      if (this.tAnimation) {
        this.tAnimation.paused = true;
      }
    },
    /**
     * Starts tank movement animation
     * 
     * @returns {void}
     */
    tStartAnimation: function() {
      if (this.tAnimation) {
        this.tAnimation.paused = false;
      } else {
        this.tAnimation = this.animations.play('move', 15, true);
      }
    },
    /**
     * Handles tank destruction. Also removes it from GameSpace.container
     * 
     * @param {boolean} noAnimation
     * @returns {void}
     */
    tDestroy: function(noAnimation) {
      var explosionSprite;

      if (!noAnimation) {
        explosionSprite = GameSpace.createExplosion(this);
        // Add explosion animation and play it once, then kill the explosion sprite
        explosionSprite.animations.play('explosion', 15, false, true);
      }
      this.tankExplosionSound.play('', 0, 1, false);
      GameSpace.container.removeTank(this);
    }
  });
})();