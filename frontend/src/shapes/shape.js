import {rgb} from "../helpers.js";
import {generateShapeId} from "../helpers.js";

export class Shape {
    constructor(x, y, shapeType) {
        this.shapeId = generateShapeId();
        this.cssId = `${shapeType}-${this.shapeId}`;
        this.shape = null;
        this.selector = null;
        this.zIndex = 1;
        this.shapeType = shapeType;
        this.upperLeftCorner = { x: x, y: y };
        this.width = 0;
        this.height = 0;
        this.fill = rgb(94, 202, 210);
        this.fillOpacity = 1;
        this.outline = rgb(27, 27, 27);
        this.thickness = 1;
    }
    updateUpperLeftCorner(x, y) {
        this.upperLeftCorner.x = x;
        this.upperLeftCorner.y = y;
    }
    updateFillColor(rgbColor) {
        this.fill = rgbColor;
        this.shape.setAttribute("fill", rgbColor);
    }
    updateFillOpacity(opacity) {
        this.fillOpacity = opacity;
        this.shape.setAttribute("fill-opacity", opacity);
    }
    updateOutlineColor(rgbColor) {
        this.outline = rgbColor;
        this.shape.setAttribute("stroke", rgbColor);
    }
    updateThickness(thickness) {
        this.thickness = thickness;
        this.shape.setAttribute("stroke-width", thickness);
    }
}