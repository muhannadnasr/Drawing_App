import { Square } from "./shapes/square.js";
import { Ellipse } from "./shapes/ellipse.js";
import { Triangle } from "./shapes/triangle.js";
import { Line } from "./shapes/line.js";
import { ShapeWrapper } from "./shapes/shapeWrapper.js";
import { LineWrapper } from "./shapes/lineWrapper.js";
import { pushShapeCopy } from "./backEndComm/shapeComm";
import { pushLineCopy } from "./backEndComm/lineComm";
import store from "./store";

const shapeTypes = {
  square: 'square',
  rectangle: 'rectangle',
  circle: 'circle',
  ellipse: 'ellipse',
  triangle: 'triangle',
  rhombus: 'rhombus',
  line: 'line',
};

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

export function stringfyPoint(x, y){
  return `${x},${y}`;
}

export function reCreateShape(shapeInfo){ // shape info passed as json
  let newShape = null;
  const shapeType = shapeInfo.type;
  const shapeId = shapeInfo.id;
  if (shapeType !== shapeTypes.line){
    const upperLeftConrner = {
      x: shapeInfo.upperLeftCorner.x,
      y: shapeInfo.upperLeftCorner.y,
    }
    const width = shapeInfo.width;
    const height = shapeInfo.height;
    const fillOpacity = shapeInfo.fillOpacity;
    const outlineColor = shapeInfo.outlineColor;

    if (shapeType === shapeTypes.square || shapeType === shapeTypes.rectangle){
      newShape = new Square(upperLeftConrner.x, upperLeftConrner.y, shapeType, shapeId);
    }
    else if(shapeType === shapeTypes.circle || shapeType === shapeTypes.ellipse){
      newShape = new Ellipse(upperLeftConrner.x, upperLeftConrner.y, shapeType, shapeId);
    }
    else if(shapeType === shapeTypes.triangle){
      newShape = new Triangle(upperLeftConrner.x, upperLeftConrner.y, shapeType, shapeId);
    }
    newShape.create(width, height);
    newShape.selector = new ShapeWrapper(newShape);
    newShape.updateFillOpacity(fillOpacity);
    newShape.updateOutlineColor(outlineColor);
  }
  else {
    const startingPoint = {
      x: shapeInfo.startingPoint.x,
      y: shapeInfo.startingPoint.y,
    }
    const endingPoint = {
      x: shapeInfo.endingPoint.x,
      y: shapeInfo.endingPoint.y,
    }
    newShape = new Line(startingPoint.x, startingPoint.y, shapeType, shapeId);
    newShape.create(startingPoint.x, startingPoint.y, endingPoint.x, endingPoint.y);
    newShape.selector = new LineWrapper(newShape);
  }

  const fillColor = shapeInfo.fillColor;
  const thickness = shapeInfo.thickness;
  newShape.updateFillColor(fillColor);
  newShape.updateThickness(thickness);
  store.commit('pushShapeDrawn', newShape);

  if(shapeType !== shapeTypes.line) pushShapeCopy(newShape, false);
  else pushLineCopy(newShape, false);

  newShape.selector.disable();
  newShape.selector.clicked = false;
  store.commit('setSelecting', false);
}