'use strict';

/**
 * Global application storage container
 * 
 * @type {object}
 */
GameSpace.container = (function() {
  return {
    mapSpawned: false,
    playerData: null,
    playerTank: null,
    borders: null,
    tanks: null,
    bullets: null,
    tiles: null,
    init: function() {
      // Layers set in "z-index" order
      this.tiles = {
        'collidable': GameSpace.game.add.group(),
        'other': GameSpace.game.add.group()
      };
      this.tanks = GameSpace.game.add.group();
      this.bullets = GameSpace.game.add.group();
      this.tiles['top'] = GameSpace.game.add.group();
      this.borders = GameSpace.game.add.group();

      return this;
    },
    /**
     * Returns a tank based on value o certain {key} property
     * 
     * @param {string} key
     * @param {number|string} value
     * @returns {GameSpace.Tank}
     */
    findTankBy: function(key, value) {
      var returnedChild;
      this.tanks.forEach(function(child) {
        if (child[key] === value) {
          returnedChild = child;
        }
      }, this);
      return returnedChild;
    },
    /**
     * Adds tank to the {tank} group and to the game
     * 
     * @param {GameSpace.Tank} bullet
     * @returns {void}
     */
    addTank: function(tank) {
      this.tanks.add(tank);
    },
    /**
     * Removes tank from {this.tanks} group and remove it from the game
     * 
     * @param {GameSpace.Tank} tank
     * @returns {void}
     */
    removeTank: function(tank) {
      this.tanks.remove(tank);
      tank.destroy();
    },
    /**
     * Returns a bullet based on value o certain {key} property
     * 
     * @param {string} key
     * @param {number|string} value
     * @returns {GameSpace.Bullet}
     */
    findBulletBy: function(key, value) {
      var returnedChild;
      this.bullets.forEach(function(child) {
        if (child[key] === value) {
          returnedChild = child;
        }
      }, this);
      return returnedChild;
    },
    /**
     * Adds bullet to the {this.bullets} group and to the game
     * 
     * @param {GameSpace.Bullet} bullet
     * @returns {void}
     */
    addBullet: function(bullet) {
      this.bullets.add(bullet);
    },
    /**
     * Removes bullet from {this.bullets} group and remove it from the game
     * 
     * @param {GameSpace.Tank} tank
     * @returns {void}
     */
    removeBullet: function(bullet) {
      this.bullets.remove(bullet);
      bullet.destroy();
    },
    /**
     * Returns a tile based on value o certain {key} property and {tileType}
     * 
     * @param {string} tileType
     * @param {string} key
     * @param {number|string} value
     * @returns {GameSpace.Tile}
     */
    findTileBy: function(tileType, key, value) {
      var returnedChild;
      this.tiles[tileType].forEach(function(child) {
        if (child[key] === value) {
          returnedChild = child;
        }
      }, this);
      return returnedChild;
    },
    /**
     * Adds tile to the {this.tiles} group and to the game
     * 
     * @param {GameSpace.Tile} tile
     * @returns {void}
     */
    addTile: function(tile) {
      var tileType;
      for (tileType in GameSpace.tileType) {
        if (_.contains(GameSpace.tileType[tileType], tile.tileType)) {
          this.tiles[tileType].add(tile);
          break;
        }
      }
    },
    /**
     * Removes tile from {this.tiles} group and remove it from the game
     * 
     * @param {GameSpace.Tile} tile
     * @returns {void}
     */
    removeTile: function(tile) {
      for (var tileGroup in this.tiles) {
        if (_.contains(GameSpace.tileType[tileGroup], tile.tileType)) {
          this.tiles[tileGroup].remove(tile);
          tile.destroy();
          break;
        }
      }
    }
  }
})();