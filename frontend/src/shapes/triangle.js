import {getZIndex} from "../helpers.js";
import {Shape} from "./shape.js";
import gsap from 'gsap';

export class Triangle extends Shape{
  constructor(x, y, shapeType, id=null) {
    super(x, y, shapeType, id);
    //    B
    //  /   \
    // A     C
    this.points = {
      A: {x: x, y: y},
      B: {x: x, y: y},
      C: {x: x, y: y},
    };
  }
  create(width, height) {
    this._changeWidthValue(width);
    this._changeHeightValue(height);
    this._changePointsAB();

    const board = document.getElementById("board");
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const newTriangle = document.createElementNS(svgns, "polygon");
    gsap.set(newTriangle, {
      attr: {
        points: this._loadPoints(),
        fill: this.fill, 'fill-opacity': this.fillOpacity,
        stroke: this.outline, 'stroke-opacity': this.outlineOpacity,
        'stroke-width': this.outlineThickness, class: "triangle", id: this.cssId
      }
    });
    board.appendChild(newTriangle);
    this.shape = document.getElementById(this.cssId);
    this.zIndex = getZIndex();
    this.shape.style.zIndex = this.zIndex;
  }
  updateWidth(width) {
    this._changeWidthValue(width);
    this._changePointsAB();
    this.updatePoints();
  }
  updateHeight(height) {
    this._changeHeightValue(height);
    this._changePointsAB();
    this.updatePoints();
  }
  updatePoints(){
    this.shape.setAttribute("points", this._loadPoints())
  }
  updatePos(x, y) {
    this.updateUpperLeftCorner(x, y);
    this.updateWidth(this.width);
    this.updateHeight(this.height);
  }
  _changeWidthValue(width){
    this.width = width;
    this.points.C.x = this.upperLeftCorner.x + this.width;
  }
  _changeHeightValue(height){
    this.height = height;
    this.points.C.y = this.upperLeftCorner.y + this.height;
  }
  _changePointsAB(){
    this.points.A.x = this.points.C.x - this.width;
    this.points.A.y = this.points.C.y;

    this.points.B.x = this.points.C.x - (this.width /2);
    this.points.B.y = this.points.C.y - this.height;
  }
  _loadPoints(){
    return `${this.points.A.x},${this.points.A.y} ${this.points.B.x},${this.points.B.y} ${this.points.C.x},${this.points.C.y}`
  }
}