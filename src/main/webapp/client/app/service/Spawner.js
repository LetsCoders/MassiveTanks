'use strict';

/**
 * Spawner helper service
 * 
 * @type {object}
 */
GameSpace.spawner = (function() {
  return {
    /**
     * Spawns players
     * 
     * @param {array} players
     * @returns {void}
     */
    spawnPlayers: function(players) {
      var i;
      var tank;
      for (i = 0; i < players.length; i++) {
        tank = players[i].tank;
        if (tank) {
          this.spawnPlayerTank(players[i]);
        }
      }
    },
    /**
     * Spawns bullets
     * 
     * @param {object} bullets
     * @returns {void}
     */
    spawnBullets: function(bullets) {
      var bulletItem;
      var bullet;
      for (bulletItem in bullets) {
        bullet = bullets[bulletItem];
        this.spawnBullet(bullet.id, bullet.position.x, bullet.position.y, bullet.movementDirection, bullet.bulletSpeed);
      }
    },
    /**
     * Spawns map
     * 
     * @param {object} map
     * @returns {void}
     */
    spawnMap: function(map) {
      var i;
      
      for (i = 0; i < map.matrix.length; i++) {
        for (var j = 0; j < map.matrix[i].length; j++) {
          // Don't render tiles on borders
          if (i !== 0 && i !== map.width + 1 && j !== 0 && j !== map.height + 1) {
            // Check if tile is not null
            if (map.matrix[i][j]) {
              this.spawnMapTile(map.matrix[i][j]);
            }
          }
        }
      }
    },
    /**
     * Creates borders on the edge of the map
     * 
     * @returns {void}
     */
    spawnMapBorders: function() {
      GameSpace.container.borders.add(new GameSpace.Border(GameSpace.config.mapX / 2, 0, 'top'));
      GameSpace.container.borders.add(new GameSpace.Border(GameSpace.config.mapX / 2, GameSpace.config.mapY + 32, 'down'));
      GameSpace.container.borders.add(new GameSpace.Border(0, GameSpace.config.mapY / 2, 'left'));
      GameSpace.container.borders.add(new GameSpace.Border(GameSpace.config.mapX + 32, GameSpace.config.mapY / 2, 'right'));
    },
    /**
     * Spawn a tank
     * 
     * @param {object} data
     * @returns {GameSpace.Tank}
     */
    spawnPlayerTank: function(data) {
      var tank;
      var existingTank;
      var playerTank = isPlayerTank(data);

      // Prevent double spawn
      existingTank = GameSpace.container.findTankBy('tankId', data.tank.id);
      if (existingTank) {
        existingTank.tDestroy();
      }

      // Create the tank
      tank = new GameSpace.Tank(
        playerTank ? GameSpace.config.playerTankColor : GameSpace.config.otherTankColor,
        data.tank.id,
        ((data.player) ? data.player.name : data.playerName),
        data.tank.hp,
        data.tank.position.x,
        data.tank.position.y,
        data.tank.facingDirection,
        data.tank.movementDirection
      );
      GameSpace.container.addTank(tank);

      // Start initial movement
      if (data.tank.velocity.x !== 0 || data.tank.velocity.y !== 0) {
        tank.tMove(data.tank.velocity);
      } else {
        tank.tMove();
      }

      // Check if controlls should be initialized (if not already done in world state)
      if (playerTank) {
        initHud(tank);
      }

      return tank;
    },
    /**
     * Spawn bullet
     * 
     * @param {int} id
     * @param {number} x
     * @param {number} y
     * @param {string} facingDirection
     * @returns {void}  
     */
    spawnBullet: function(id, x, y, facingDirection, bulletSpeed) {
      GameSpace.container.addBullet(new GameSpace.Bullet(id, x, y, facingDirection, bulletSpeed));
    },
    /**
     * Spawn single map tile
     * 
     * @param {object} data
     * @returns {void}
     */
    spawnMapTile: function(data) {
      if (data !== null) {
        GameSpace.container.addTile(new GameSpace.Tile(data.id, data.hp, data.position.x, data.position.y, data.type));
      }
    }
  };
  
  /**
   * Check if spawned tank is current player's tank
   * 
   * @param {object} playerData
   * @return {boolean}
   */
  function isPlayerTank(playerData) {
    if (playerData.player) {
      return GameSpace.container.playerData.player.name === playerData.player.name;
    } else {
      return GameSpace.container.playerData.player.name === playerData.playerName;
    }
  }

  /**
   * Init player HUD, connecting tank with the heal controller and attach controller to this tank
   * 
   * @param {GameSpace.Tank} tank
   * @return {void}
   */
  function initHud(tank) {
    // Make camera follow player tank
    GameSpace.game.camera.x = tank.position.x;
    GameSpace.game.camera.y = tank.position.y;
    GameSpace.game.camera.follow(tank);
    // This little reference below is for the player controller
    GameSpace.container.playerTank = tank;
  }
})();