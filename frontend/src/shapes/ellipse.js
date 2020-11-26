import {getZIndex} from "../helpers.js";
import {Shape} from "./shape.js";
import gsap from 'gsap';

export class Ellipse extends Shape{
  constructor(x, y, shapeType) {
    super(x, y, shapeType);
    this.center = {x: x, y: y}
    this.radiusX = 0;
    this.radiusY = 0;
  }
  create(width, height) {
    this._changeWidthValue(width);
    this._changeHeightValue(height);

    const board = document.getElementById("board");
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const newEllipse = document.createElementNS(svgns, "ellipse");
    gsap.set(newEllipse, {
      attr: {
        cx: this.center.x, cy: this.center.y, rx: this.radiusX, ry: this.radiusY,
        fill: this.fill, 'fill-opacity': this.fillOpacity,
        stroke: this.outline, 'stroke-opacity': this.outlineOpacity,
        'stroke-width': this.outlineThickness, class: "ellipse", id: this.cssId
      }
    });
    board.appendChild(newEllipse);
    this.shape = document.getElementById(this.cssId);
    this.shape.style.zIndex = getZIndex();
    // this.addHandlers();
  }
  updateWidth(width) {
    this._changeWidthValue(width);
    this.shape.setAttribute("rx", this.radiusX);
    this.shape.setAttribute("cx", this.center.x);
  }
  updateHeight(height) {
    this._changeHeightValue(height);
    this.shape.setAttribute("ry", this.radiusY);
    this.shape.setAttribute("cy", this.center.y);
  }
  updatePos(x, y) {
    this.updateUpperLeftCorner(x, y);
    this.updateWidth(this.width);
    this.updateHeight(this.height);
  }
  _changeWidthValue(width){
    this.width = width;
    this.radiusX = this.width / 2;
    this.center.x = this.upperLeftCorner.x + this.radiusX;
  }
  _changeHeightValue(height){
    this.height = height;
    this.radiusY = this.height / 2;
    this.center.y = this.upperLeftCorner.y + this.radiusY;
  }
}












