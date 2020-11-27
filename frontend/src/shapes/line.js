import {getZIndex} from "../helpers.js";
import {rgb} from "../helpers.js";
import {generateShapeId} from "../helpers.js";
import gsap from 'gsap';

export class Line {
    constructor(x, y, shpaeType) {
      this.shapeId = generateShapeId();
      this.shpaeType = shpaeType;
      this.cssId = `${shpaeType}-${this.shapeId}`;
      this.shape = null;
      this.startingPoint = { x: x, y: y};
      this.endingPoint = { x: x, y: y};
      this.color = rgb(27, 27, 27);
      this.thickness = 1;
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
          fill: 'none', stroke: this.color,
          'stroke-width': this.thickness, class: "line", id: this.cssId
        }
      });
      board.appendChild(newLine);
      this.shape = document.getElementById(this.cssId);
      this.shape.style.zIndex = getZIndex();
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
    updateColor(rgbColor) {
      this.color = rgbColor;
      this.shape.setAttribute("stroke", rgbColor);
    }
    updateThickness(thickness) {
      this.thickness = thickness;
      this.shape.setAttribute("stroke-width", thickness);
    }
    _loadPoints(){
      return `${this.startingPoint.x},${this.startingPoint.y} ${this.endingPoint.x},${this.endingPoint.y}`;
    }
}












