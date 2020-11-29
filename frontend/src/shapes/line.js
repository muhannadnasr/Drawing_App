import {getZIndex} from "../helpers.js";
import {rgb} from "../helpers.js";
import {generateShapeId} from "../helpers.js";
import gsap from 'gsap';

export class Line {
    constructor(x, y, shapeType) {
      this.shapeId = generateShapeId();
      this.shapeType = shapeType;
      this.cssId = `${shapeType}-${this.shapeId}`;
      this.shape = null;
      this.startingPoint = { x: x, y: y};
      this.endingPoint = { x: x, y: y};
      this.fill = rgb(27, 27, 27);
      this.thickness = 1;
      this.selector = null;
      this.zIndex = 1;
    }
    create(startingX, startingY, endingX, endingY) {
      this.startingPoint.x = startingX;
      this.startingPoint.y = startingY;
      this.endingPoint.x = endingX;
      this.endingPoint.y = endingY;
    
      const board = document.getElementById("board");
      const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
      const newLine = document.createElementNS(svgns, "polyline");
      gsap.set(newLine, {
        attr: {
          points: this._loadPoints(),
          fill: 'none', stroke: this.fill,
          'stroke-width': this.thickness, class: "line", id: this.cssId
        }
      });
      board.appendChild(newLine);
      this.shape = document.getElementById(this.cssId);
      this.zIndex = getZIndex();
      this.shape.style.zIndex = this.zIndex;
      // this.addHandlers();
    }
    updatePoints(){
      this.shape.setAttribute("points", this._loadPoints())
    }
    updateStartingPos(x, y) {
      this.startingPoint.x = x;
      this.startingPoint.y = y;
      this.updatePoints();
    }
    updateEndingPos(x, y) {
      this.endingPoint.x = x;
      this.endingPoint.y = y;
      this.updatePoints();
    }
    updateFillColor(rgbColor) {
      if (rgbColor === 'transparent') return;
      this.fill = rgbColor;
      this.shape.setAttribute("stroke", rgbColor);
    }
    updateThickness(thickness) {
      if(thickness < 1) return;
      this.thickness = thickness;
      this.shape.setAttribute("stroke-width", thickness);
    }
    _loadPoints(){
      return `${this.startingPoint.x},${this.startingPoint.y} ${this.endingPoint.x},${this.endingPoint.y}`;
    }
}












