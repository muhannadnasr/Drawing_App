<template>
  <svg  id="board" :width="boardProbs.width" :height="boardProbs.height"
        version="1.1" xmlns="http://www.w3.org/2000/svg"
        :class="[isDrawing? 'crosshair-pointer' : 'default-pointer']"
        @mousedown="mouseDownHandling()"
        @mouseover="mouseOverHandling()"
        @mouseup="mouseUpHandling()">
  </svg>
</template>

<script>
import gsap from 'gsap';
import { Square } from '../shapes/square.js';
import { mapGetters, mapActions } from 'vuex';
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
  computed: mapGetters(['isDrawing', 'shapeType']),
  methods:{
    ...mapActions(['disableDrawingMode']),
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
      if (this.isDrawing) {
        this.boardProbs.mouseDown = true;
        this.setStartingPos();
        this.createShape(0, 0);
      }
      
      const shapeSizeTracker = setInterval(() => {
        if(this.isDrawing && this.boardProbs.mouseDown) {
          const updatedX = Math.min(this.currentShape.startingPos.x, this.mousePointer.x);
          const updatedY = Math.min(this.currentShape.startingPos.y, this.mousePointer.y);
          this.currentShape.shape.updatePos(updatedX, updatedY);

          const currentWidth = Math.abs(this.currentShape.startingPos.x - this.mousePointer.x);
          const currentHeight = Math.abs(this.currentShape.startingPos.y - this.mousePointer.y);
          this.currentShape.shape.updateWidth(currentWidth);
          this.currentShape.shape.updateHeight(currentHeight);
        }
        else clearInterval(shapeSizeTracker);
      }, 10);

    },
    mouseUpHandling(){
      if(this.isDrawing) {
        if (this.currentShape.startingPos.x === this.mousePointer.x &&
            this.currentShape.startingPos.y === this.mousePointer.y) this.createShape(100, 100);
        this.boardProbs.mouseDown = false;
        this.disableDrawingMode();
      }
    },
    createShape(width, height){
      if (this.shapeType === this.shapeTypes.square){
        this.currentShape.shape= new Square(this.currentShape.startingPos.x, this.currentShape.startingPos.y);
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
</style>