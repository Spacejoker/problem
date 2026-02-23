const mapLookup = {};

function generateMap() {
  function setVal(y,x,v) {
    if (!mapLookup[y]) {
      mapLookup[y] = {};
    }
    mapLookup[y][x] = v;
  }
  for (i =0 ; i < 998; i++) {
    for (j = 0; j < 998; j++) {
      if (mapLookup[i] && mapLookup[i][j]) {
        continue;
      }
      const rn = Math.random() ;
      if (i >= 4 && i <= 12 && j >= 4 && j <= 12) {
        setVal(i,j,[3,0])
      } else if (rn > 0.9) {
        setVal(i,j,[3,0]);
      } else if (rn > 0.8) {
        setVal(i,j,[3,0]);
      } else if (rn > 0.78) {
        setVal(i,j,[3,5]);
      } else if (rn > 0.75) {
        setVal(i,j,[2,5]);
      } else if (rn > 0.67) {
        setVal(i,j,[0,5]);
      } else if (rn > 0.66) {
        setVal(i,j,[2, 9]);
        setVal(i,j+1,[3, 9]);
        setVal(i+1,j,[2, 10]);
        setVal(i+1,j+1,[3,10]);
      } else if (rn > 0.64) {
        setVal(i,j,[3,3]);
      } else if (rn > 0.62) {
        setVal(i,j,[3,4]);
      } else if (rn > 0.60) {
        setVal(i,j,[3,2]);
      } else {
        setVal(i,j,[3,0])
      }
    }
  }
}
generateMap();

function getTilePos(x, y) {
  return[ Math.floor(Math.floor(x) / 16 ), Math.floor(Math.floor(y)/16)];
}

function drawBackground(ctx, state) {

  let x = Math.floor(state.x)
  let y = Math.floor(state.y)
  let xOffset = -((x % 16 + 16) % 16);
  let yOffset = -((y % 16 + 16) % 16);
  let tileX = Math.floor(x / 16 )
  let tileY = Math.floor(y/16);

  for (let ty = -1; ty < 20; ty++) {
    for (let tx = -1; tx < 20 ; tx ++) {
      const xpos = -16 + xOffset + tx*16;
      const ypos=-16 + yOffset + ty*16;
      const [sheetX, sheetY] = getMapTileCoords(tileX+tx, tileY+ty, {});

      ctx.drawImage(state.bg, sheetX*16, sheetY*16, 16, 16, xpos, ypos, 16, 16);
    }
  }
  // 256x224
  // tile size is 24x24
    // 256/16 = 16
    // 224 / 16 = 14
  //ctx.fillStyle = '#FFF';
  //ctx.fillText("Hello world", 10, 50);
}
function getMapTileCoords(tx, ty) {
  tx = ((tx % 1000) + 1000) % 1000;
  ty = ((ty % 1000) + 1000) % 1000;
  if (mapLookup[ty]?.[tx]) {
    return mapLookup[ty][tx];
  }

  //if (tx %100 >= 0 && tx <= 2 && ty % 100 >= 0 && ty <=2) {
  //  return [tx%100, ty%100]
  //}

  //if (tx == 10) {
  //  return [2,5];
  //}
  //if (tx == 5) {
  //  return [2,5];
  //}
  //if (ty == 10) return [2,5];
  //if (ty == 5) return [2,5];


  //if ((tx*ty)% 47 === 0) {
  //  return [3,3]
  //}
  //if ((tx*ty)% 18 === 0) {
  //  return [3,2]
  //}
  return [3,0];
}

const blockingTiles = [[2,5],[0,5],[2,9],[3,9],[2,10],[3,10], [3,5]]

// Check if the next dx / dy pos is a collission.
function checkCollission(state) {
  // take all four points
  function check(x, y) {
    const [xtype, ytype] = getMapTileCoords(...getTilePos(x+state.dx, y + state.dy))
    for (const [a,b] of blockingTiles) {
      if (xtype === a && ytype === b) {
        return true;
      }
    }
    return false;
  }

  const offsets = [[1,1],[1,15],[15,1],[15,15]];
  for (const [x1, y1] of offsets) {
    if (check(state.x+136 + x1, state.y + 112+23 + y1)) {
      return true
    }
  }

  return false;
}
