'use strict';

/**
 * Server reponse. Actually the server controller's core
 * 
 * @returns {function}
 * @class
 */
GameSpace.ServerResponse = function() {
  
};

GameSpace.ServerResponse.prototype = (function() {
  return {
    process: process
  };

  /**
   * Handles server response processing
   * 
   * @param {object} json
   * @returns {void}
   */
  function process(json) {
    switch (json.type) {
      case 'worldState':
        GameSpace.container.playerData = json.thisPlayer;
        // Spawn map
        GameSpace.spawner.spawnMap(json.map);
        // Spawn players
        GameSpace.spawner.spawnPlayers(json.players);
        // Spawn bullets
        GameSpace.spawner.spawnBullets(json.bullets);
        // Spawn map borders
        GameSpace.spawner.spawnMapBorders();

        if (!GameSpace.container.playerData.tank) {
          GameSpace.serverInst.sendRespawnPacket();
        }
        
        GameSpace.container.mapSpawned = true;
        break;
      case 'tankRespawn':
        GameSpace.spawner.spawnPlayerTank(json);
        break;
      case 'directionChange':
        updatePlayerTank(json);
        break;
      case 'tankFire':
        GameSpace.spawner.spawnBullet(
          json.bulletId, json.position.x, json.position.y, json.movementDirection, json.bulletSpeed
        );
        break;
      case 'bulletHit':
        handleBulletHit(json.bulletId, json.hitObjectId, json.hp);
        break;
      case 'playerQuit':
        var tank = GameSpace.container.findTankBy('tankId', json.tankId);
        if (tank) {
          tank.tDestroy();
        }
        break;
      case 'mapRespawn':
        GameSpace.spawner.spawnMapTile(json.block);
        break;
    }
  }

  /**
   * Updates player tank data in the container. Used to update tank position on move start/end
   * 
   * @param {object} data
   * @returns {void}
   */
  function updatePlayerTank(data) {
    var tank = GameSpace.container.findTankBy('tankId', data.tankId);
    if (tank) {
      tank.tUpdateTankPosition(data.position.x, data.position.y, data.facingDirection, data.movementDirection);
      tank.tMove();
    }
    
  }

  /**
   * Handles bullet hit
   * 
   * @param {int} id
   * @param {int} targetId
   * @param {int} hp
   * @returns {void} 
   */
  function handleBulletHit(id, targetId, hp) {
    var bullet = GameSpace.container.findBulletBy('bulletId', id);
    var tile;
    var tank;
    
    if (bullet) {
      bullet.bDestroy();
    }

    tile = GameSpace.container.findTileBy('collidable', 'tileId', targetId);
    if (tile) {
      tile.tSetHp(hp);
    } else {
      tank = GameSpace.container.findTankBy('tankId', targetId);
      if (tank) {
        tank.tSetHp(hp);
      }
    }
  }
})();