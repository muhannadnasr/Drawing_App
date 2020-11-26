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
        this.outlineThickness = 1;
        this.outlineOpacity = 1;
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
        if (opacity > 1 || opacity < 0)
            return;
        this.fillOpacity = opacity;
        this.shape.setAttribute("fill-opacity", opacity);
    }
    updateOutlineColor(rgbColor) {
        this.outline = rgbColor;
        this.shape.setAttribute("stroke", rgbColor);
    }
    updateOutlineOpacity(opacity) {
        if (opacity > 1 || opacity < 0)
            return;
        this.outlineOpacity = opacity;
        this.shape.setAttribute("stroke-opacity", opacity);
    }
    updateOutlineThickness(thickness) {
        this.outlineThickness = thickness;
        this.shape.setAttribute("stroke-width", thickness);
    }
    addHandlers() {
        const square = this;
        let mousePosTracker = null;
        const board = document.getElementById("board");
        this.shape.onmouseover = function () {
            mousePosTracker = setInterval(() => {
                const leftEdge = square.upperLeftCorner.x;
                const rightEdge = leftEdge + square.width;
                const upperEdge = square.upperLeftCorner.y;
                const lowerEdge = upperEdge + square.height;

                const leftEdgeFromMouse = Math.abs(window.mouseX - leftEdge);
                const rightEdgeFromMosue = Math.abs(window.mouseX - rightEdge);
                const upperEdgeFromMouse = Math.abs(window.mouseY - 110 - upperEdge);
                const lowerEdgeFromMouse = Math.abs(window.mouseY - 110 - lowerEdge);

                if (square.mouseNearEdge(leftEdgeFromMouse) && square.mouseNearEdge(upperEdgeFromMouse)) {
                    board.style.cursor = "nw-resize";
                }
                else if (square.mouseNearEdge(leftEdgeFromMouse) && square.mouseNearEdge(lowerEdgeFromMouse)) {
                    board.style.cursor = "sw-resize";
                }
                else if (square.mouseNearEdge(rightEdgeFromMosue) && square.mouseNearEdge(upperEdgeFromMouse)) {
                    board.style.cursor = "ne-resize";
                }
                else if (square.mouseNearEdge(rightEdgeFromMosue) && square.mouseNearEdge(lowerEdgeFromMouse)) {
                    board.style.cursor = "se-resize";
                }
                else if (square.mouseNearEdge(leftEdgeFromMouse)) {
                    board.style.cursor = "w-resize";
                }
                else if (square.mouseNearEdge(rightEdgeFromMosue)) {
                    board.style.cursor = "e-resize";
                }
                else if (square.mouseNearEdge(upperEdgeFromMouse)) {
                    board.style.cursor = "n-resize";
                }
                else if (square.mouseNearEdge(lowerEdgeFromMouse)) {
                    board.style.cursor = "s-resize";
                }
                else
                    board.style.cursor = "default";
            }, 100);
        };
        this.shape.onmouseleave = function () {
            clearInterval(mousePosTracker);
            board.style.cursor = "default";
        };
    }
    mouseNearEdge(dist) {
        if (dist <= 15 && dist >= 0)
            return true;
    }
}












