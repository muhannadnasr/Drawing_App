import store from "./store"

export function rgb(R, G, B){
  return `rgb(${R},${G},${B})`;
}

let shapeId = 0;
export function generateShapeId(){
  return shapeId++;
}

export function getZIndex(){
  const newZIndex = store.getters.maxCurrZIndex;
  store.commit("setMaxZIndex", newZIndex +1)
  return newZIndex;
}