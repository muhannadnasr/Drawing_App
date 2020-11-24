export function rgb(R, G, B){
  return `rgb(${R},${G},${B})`;
}

let shapeId = 0;
export function generateShapeId(){
  return shapeId++;
}

let zIndex = 2;
export function getZIndex(){
  return zIndex++;
}