<template>
  <svg  id="board" :width="boardProbs.width" :height="boardProbs.height"
        version="1.1" xmlns="http://www.w3.org/2000/svg"
        :class="setBoardCursor()"
        @mousedown="mouseDownHandling()"
        @mouseover="mouseOverHandling()"
        @mouseup="mouseUpHandling()"
        >
  </svg>
</template>

<script>
import gsap from 'gsap';
import { Square } from '../shapes/square.js';
import { Ellipse } from '../shapes/ellipse.js';
import { Triangle } from '../shapes/triangle.js';
import { ShapeWrapper } from "../shapes/shapeWrapper.js";
import { LineWrapper } from "../shapes/lineWrapper.js";
import { Line } from '../shapes/line.js';
import { pushShape } from '../backEndComm/shapeComm.js';
import { pushLine } from '../backEndComm/lineComm.js';
import { mapGetters, mapActions } from 'vuex';
import { updateFillColor } from '../backEndComm/comm.js';
export default {
  name: 'board',
  data(){
    return{
      board: null,
      boardProbs: {
        width: 100,
        height: 100,
        mouseDown: false,
      },
      grid: {
        startingX: 30,
        startingY: 30,
      },
      currentShape: {
        shape: null,
        startingPos: {x: 0, y: 0},
      },
      shapeTypes: {
        square: 'square',
        rectangle: 'rectangle',
        circle: 'circle',
        ellipse: 'ellipse',
        triangle: 'triangle',
        rhombus: 'rhombus',
        line: 'line',
      },
      mousePointer: {
        originX: 0,
        originY: 110,
        x: 0, //current position relative to x-axis
        y: 0, //current position relative to y-axis
        updatePosition: function() {
          this.x = window.mouseX - this.originX;
          this.y = window.mouseY - this.originY;
        },
      }
    }
  },
  computed: mapGetters(['isDrawing', 'shapeType', 'isResizing', 'resizingDirection', 'currentSelector']),
  methods:{
    ...mapActions(['disableDrawingMode', 'setCurrentSelector', 'setBoardMouseDown', 'setSelecitngStatus', 'pushNewShape']),
    //Drawing Shapes
    mouseOverHandling(){
      const mousePosTracker = setInterval(() => {
        if(!this.isDrawing || !this.mouseOnBoard()) clearInterval(mousePosTracker);
        else {
          this.mousePointer.updatePosition();
        }
      }, 100);    
    },
    mouseDownHandling(){
      this.setBoardMouseDown(true);
      if(this.currentSelector !== null) {
        if(!this.currentSelector.wrapper.matches(':hover')){
          this.currentSelector.disable();
          this.currentSelector.clicked = false;
        }
        this.setSelecitngStatus(false);
      }

      if (this.isDrawing) {
        this.boardProbs.mouseDown = true;
        this.setStartingPos();
        this.createShape(0, 0);
      }
      
      const shapeSizeTracker = setInterval(() => {
        if(this.isDrawing && this.boardProbs.mouseDown) {
          if(this.isLine()){
            this.currentShape.shape.updateEndingPos(this.mousePointer.x, this.mousePointer.y)
          }
          else{
            const updatedX = Math.min(this.currentShape.startingPos.x, this.mousePointer.x);
            const updatedY = Math.min(this.currentShape.startingPos.y, this.mousePointer.y);
            this.currentShape.shape.updatePos(updatedX, updatedY);

            const currentWidth = Math.abs(this.currentShape.startingPos.x - this.mousePointer.x);
            const currentHeight = Math.abs(this.currentShape.startingPos.y - this.mousePointer.y);
            this.currentShape.shape.updateWidth(currentWidth);
            this.currentShape.shape.updateHeight(currentHeight);
          }
        }
        else clearInterval(shapeSizeTracker);
      }, 10);

    },
    mouseUpHandling(){
      if(this.isDrawing) {
        const xError = Math.abs(this.currentShape.startingPos.x - this.mousePointer.x);
        const yError = Math.abs(this.currentShape.startingPos.y - this.mousePointer.y);
        
        if (this.isAcceptableError(xError) && this.isAcceptableError(yError)){
          this.board.removeChild(this.currentShape.shape.shape);
          if(this.isSquare()) this.createShape(100, 100);
          else if(this.isRectangle()) this.createShape(200, 100);
          else if(this.isCircle()) this.createShape(100, 100);
          else if(this.isEllipse()) this.createShape(200, 100);
          else if(this.isTriangle()) this.createShape(100, 100);
          else if(this.isLine()) this.createShape(100, 100);
        } 

        this.boardProbs.mouseDown = false;
        if(!this.isLine()) {
          this.currentShape.shape.selector = new ShapeWrapper(this.currentShape.shape);
          this.setCurrentSelector(this.currentShape.shape.selector);
          pushShape(this.currentShape.shape);
        }
        else{
          this.currentShape.shape.selector = new LineWrapper(this.currentShape.shape);
          this.setCurrentSelector(this.currentShape.shape.selector);
          pushLine(this.currentShape.shape);
          updateFillColor(this.currentShape.shape);
        }

        this.pushNewShape(this.currentShape.shape);
        this.disableDrawingMode();
        this.setSelecitngStatus(true);
      }
      this.setBoardMouseDown(false);
    },
    createShape(width, height){
      if (this.isSquare() || this.isRectangle()){
        this.currentShape.shape = new Square(this.currentShape.startingPos.x, this.currentShape.startingPos.y, this.shapeType);
      }
      else if(this.isCircle() || this.isEllipse()){
        this.currentShape.shape = new Ellipse(this.currentShape.startingPos.x, this.currentShape.startingPos.y, this.shapeType);
      }
      else if(this.isTriangle()){
        this.currentShape.shape = new Triangle(this.currentShape.startingPos.x, this.currentShape.startingPos.y, this.shapeType);
      }
      else if(this.isLine()){
        this.currentShape.shape = new Line(this.currentShape.startingPos.x, this.currentShape.startingPos.y, this.shapeType);
      }
      if(this.isLine()) {
        const endingX = this.currentShape.startingPos.x + width;
        const endingY = this.currentShape.startingPos.y - height;
        this.currentShape.shape.create(this.currentShape.startingPos.x, this.currentShape.startingPos.y, endingX, endingY);
      }
      else {
        this.currentShape.shape.create(width, height);
      }
    },
    setStartingPos(){
      this.currentShape.startingPos.x = this.mousePointer.x;
      this.currentShape.startingPos.y = this.mousePointer.y;
    },
    mouseOnBoard(){
      return this.board.matches(':hover');
    },
    isSquare(){
      return this.shapeType === this.shapeTypes.square;
    },
    isRectangle(){
      return this.shapeType === this.shapeTypes.rectangle;
    },
    isCircle(){
      return this.shapeType === this.shapeTypes.circle;
    },
    isEllipse(){
      return this.shapeType === this.shapeTypes.ellipse;
    },
    isTriangle(){
      return this.shapeType === this.shapeTypes.triangle;
    },
    isLine(){
      return this.shapeType === this.shapeTypes.line;
    },
    isAcceptableError(error){
      if(error >= 0 && error <= 10) return true;
      return false;
    },
    setBoardCursor(){
      if(this.isDrawing) return "crosshair-pointer";
      if(this.isResizing) return `${this.resizingDirection}-pointer`;
      else return "default-pointer"
    },
    //grid functions
    createGrid(){
      //drawing the grid
      const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
      let newLine = null;
        //drawing vertical lines
      for(let x = this.grid.startingX; x <= this.boardProbs.width; x += 50){
        newLine = document.createElementNS(svgns, "line");
        gsap.set(newLine, {
          attr: { x1: x, y1: 0, x2: x, y2: this.boardProbs.width, 
                  stroke:"rgb(225,225,225)", 'stroke-width':1, class: "grid-lien"}
        });
        this.board.appendChild(newLine);
      }
        //drawing horizontal lines
      for(let y = this.grid.startingX; y <= this.boardProbs.height; y += 50){
        newLine = document.createElementNS(svgns, "line");
        gsap.set(newLine, {
          attr: { x1: 0, y1: y, x2: this.boardProbs.width, y2: y, 
                  stroke:"rgb(225,225,225)", 'stroke-width':1, class: "grid-lien"}
        });
        this.board.appendChild(newLine);
      }
    },
  },
  mounted(){
    //setting the height and the width of the svg board
    this.board = document.getElementById("board");
    this.boardProbs.width = window.innerWidth;
    const homeMenuHeight = document.getElementById("home-menu").offsetHeight;
    const headerHeight = document.getElementById("header").offsetHeight;
    this.boardProbs.height = window.innerHeight - homeMenuHeight - headerHeight;

    this.createGrid();

    document.onmousemove = function(e) {
      var event = e || window.event;
      window.mouseX = event.clientX;
      window.mouseY = event.clientY;
    }
  }
}
</script>

<style scoped>
#board{
  background-color: whitesmoke;
  position: relative;
  z-index: 1;
  margin-top: 1px;
}

.default-pointer{
  cursor: default;
}

.crosshair-pointer{
  cursor: crosshair;
}

.nwse-resize-pointer{
  cursor: nwse-resize;
}
.nesw-resize-pointer{
  cursor: nesw-resize;
}
.ew-resize-pointer{
  cursor: ew-resize;
}
.ns-resize-pointer{
  cursor: ns-resize;
}

</style>