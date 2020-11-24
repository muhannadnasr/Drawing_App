import {rgb} from "../helpers.js";
import {generateShapeId} from "../helpers.js";
import {getZIndex} from "../helpers.js";
import gsap from 'gsap';

export function Square(x, y){
  this.id = generateShapeId();
  this.cssId = `square-${this.id}`;
  this.shape = null;
  this.upperLeftCorner = {x: x, y: y};
  this.width = 0;
  this.height = 0;
  this.fill = rgb(94, 202, 210);
  this.fillOpacity = 1;
  this.outline = rgb(27, 27, 27);
  this.outlineThickness= 1;
  this.outlineOpacity = 1;
  this.roundValue = 0;
}

Square.prototype.create = function(width, height){
  this.width = width;
  this.height = height;

  const board = document.getElementById("board");
  const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
  const newSquare = document.createElementNS(svgns, "rect");
  gsap.set(newSquare, {
    attr: { x: this.upperLeftCorner.x, y: this.upperLeftCorner.y, rx: this.roundValue, ry: this.roundValue,
            width: this.width, height: this.height,
            fill: this.fill, 'fill-opacity': this.fillOpacity,
            stroke: this.outline, 'stroke-opacity': this.outlineOpacity, 
            'stroke-width': this.outlineThickness, class: "square", id: this.cssId}
    });
  board.appendChild(newSquare);
  this.shape = document.getElementById(this.cssId);
  this.shape.style.zIndex = getZIndex();
}

Square.prototype.updateWidth = function(width){
  this.width = width;
  this.shape.setAttribute("width", width);
}

Square.prototype.updateHeight = function(height){
  this.height = height;
  this.shape.setAttribute("height", height);
}

Square.prototype.updatePos = function(x, y){
  this.upperLeftCorner.x = x;
  this.upperLeftCorner.y = y;

  this.shape.setAttribute("x", x);
  this.shape.setAttribute("y", y);
}

Square.prototype.updateFillColor = function(rgbColor){
  this.fill = rgbColor;
  this.shape.setAttribute("fill", rgbColor);
}

Square.prototype.updateFillOpacity = function(opacity){
  if(opacity > 1 || opacity < 0) return;
  this.fillOpacity = opacity;
  this.shape.setAttribute("fill-opacity", opacity);
}

Square.prototype.updateOutlineColor = function(rgbColor){
  this.outline = rgbColor;
  this.shape.setAttribute("stroke", rgbColor);
}

Square.prototype.updateOutlineOpacity = function(opacity){
  if(opacity > 1 || opacity < 0) return;
  this.outlineOpacity = opacity;
  this.shape.setAttribute("stroke-opacity", opacity);
}

Square.prototype.updateOutlineThickness = function(thickness){
  this.outlineThickness = thickness;
  this.shape.setAttribute("stroke-width", thickness);
}

Square.prototype.updateRoundValue = function(roundValue){
  this.roundValue = roundValue;
  this.shape.setAttribute("rx", roundValue);
  this.shape.setAttribute("ry", roundValue);
}