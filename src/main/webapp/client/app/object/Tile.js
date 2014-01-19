'use strict';

/**
 * Tile object
 * 
 * @param {int} id
 * @param {int} hp
 * @param {number} x
 * @param {number} y
 * @param {string} tileType
 * @class
 */
GameSpace.Tile = function(id, hp, x, y, tileType) {
  Phaser.Sprite.call(this, GameSpace.game, x, y, 'tiles');
  this.tileId = id;
  this.body.setSize(32, 32);
  this.anchor.setTo(0.5, 0.5);
  this.tankExplosionSound = GameSpace.game.add.audio('explosion', 1, true);

  this.tSetTileType(tileType);
  this.tSetHp(hp);
};

GameSpace.Tile.prototype = (function() {
  return _.extend(Object.create(Phaser.Sprite.prototype), {
    /**
     * Sets tile sprite and extra parameters based on passed tile type
     * 
     * @param {string} tileType
     * @returns {void}
     */
    tSetTileType: function(tileType) {
      this.tileType = tileType;
      switch (tileType) {
        case GameSpace.tileType.collidable.brick:
          this.loadTexture('tiles', 9);
          this.body.immovable = true;
          break;
        case GameSpace.tileType.collidable.solid:
          this.loadTexture('tiles', 7);
          this.body.immovable = true;
          break;
        case GameSpace.tileType.collidable.water:
          this.loadTexture('tiles', 8);
          this.body.immovable = true;
          break;
        case GameSpace.tileType.top.grass:
          this.body = false;
          this.loadTexture('tiles', 5);
          break;
        case GameSpace.tileType.other.sand:
          this.body = false;
          this.loadTexture('tiles', 6);
          break;
      }
    },
    /**
     * Sets tile hp. If hp = 0 - tile is destroyed. Brick tiles contain extra animation
     * 
     * @param {int} hp
     * @returns {void}
     */
    tSetHp: function(hp) {
      if (hp === 0) {
        this.tDestroy();
      } else {
        this.hp = hp;
        if (this.tileType === 'BRICK') {
          switch (this.hp) {
            case 4:
              this.loadTexture('tiles', 9);
              break;
            case 3:
              this.loadTexture('tiles', 10);
              break;
            case 2:
              this.loadTexture('tiles', 11);
              break;
            case 1:
              this.loadTexture('tiles', 12);
              break;
          }
        }
      }
    },
    /**
     * Handles sprite destruction. Removes it from the state and GameSpace.container
     * 
     * @returns {void}
     */
    tDestroy: function() {
      var explosionSprite = GameSpace.createExplosion(this);

      this.tankExplosionSound.play('', 0, 1, false);
      explosionSprite.animations.play('explosion', 15, false, true);

      GameSpace.container.removeTile(this);
    },
    update: null,
    render: null,
    postUpdate: null
  });
})();