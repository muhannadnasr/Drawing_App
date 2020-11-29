import {getZIndex} from "../helpers.js";
import {Shape} from "./shape.js";
import gsap from 'gsap';

export class Square extends Shape{
  constructor(x, y, shapeType) {
    super(x, y, shapeType);
    this.startingCoordinate = { x: x, y: y }; //upperleftCorner
    this.roundValue = 0;
  }
  create(width, height) {
    this.width = width;
    this.height = height;

    const board = document.getElementById("board");
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const newSquare = document.createElementNS(svgns, "rect");
    gsap.set(newSquare, {
      attr: {
        x: this.startingCoordinate.x, y: this.startingCoordinate.y, rx: this.roundValue, ry: this.roundValue,
        width: this.width, height: this.height,
        fill: this.fill, 'fill-opacity': this.fillOpacity,
        stroke: this.outline, 'stroke-opacity': this.outlineOpacity,
        'stroke-width': this.outlineThickness, class: "square", id: this.cssId
      }
    });
    board.appendChild(newSquare);
    this.shape = newSquare;
    this.zIndex = getZIndex();
    this.shape.style.zIndex = this.zIndex;
  }
  updateWidth(width) {
    this.width = width;
    this.shape.setAttribute("width", width);
  }
  updateHeight(height) {
    this.height = height;
    this.shape.setAttribute("height", height);
  }
  updatePos(x, y) {
    this.startingCoordinate.x = x;
    this.startingCoordinate.y = y;
    this.updateUpperLeftCorner(x, y);

    this.shape.setAttribute("x", x);
    this.shape.setAttribute("y", y);
  }
  updateRoundValue(roundValue) {
    this.roundValue = roundValue;
    this.shape.setAttribute("rx", roundValue);
    this.shape.setAttribute("ry", roundValue);
  }
}












