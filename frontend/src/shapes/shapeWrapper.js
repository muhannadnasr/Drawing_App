import gsap from 'gsap';

import store from "../store";

export class ShapeWrapper{
  constructor(shape){
    this.id = shape.shapeId;
    this.shapeWrapped = shape;  
    this.board = document.getElementById("board");
    this.wrapper = null;
    this.corners = {
      upperLeft: null,
      upperRight: null,
      lowerLeft: null,
      lowerRight: null,
    };
    this.edges = {
      left: null,
      right: null,
      up: null,
      down: null,
    }
    this.width = 0;
    this.height = 0;
    this.startingPos = {x: 0, y: 0};
    this.clicked = true;
    this.mouseDown = false;
    this.resizing = false;
    this._createShapeWrapper();
    this._addEventHandlers();
  }

  _createShapeWrapper(){
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const selectionWrapper = document.createElementNS(svgns, "rect");

    this.startingPos.x = this.shapeWrapped.upperLeftCorner.x -1;
    this.startingPos.y = this.shapeWrapped.upperLeftCorner.y -1;
    this.width = this.shapeWrapped.width + 2;
    this.height = this.shapeWrapped.height + 2;

    gsap.set(selectionWrapper, {
    attr: {
        x: this.startingPos.x, y: this.startingPos.y, rx: 0, ry: 0,
        width: this.width, height: this.height,
        fill: 'transparent', stroke: "#4262ff",
        'stroke-width': 1, id: `shape-selector-${this.shapeWrapped.cssId}`
        }
    });
    this.board.appendChild(selectionWrapper);
    selectionWrapper.style.zIndex = this.shapeWrapped.zIndex;
    selectionWrapper.style.userSelect = 'none';

    this.wrapper = selectionWrapper;

    this._createCorners();
    this._createEdgesSelectors();
  }

  _createCircularSelector(centerX, centerY){
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const cornerSelector = document.createElementNS(svgns, "circle");
    gsap.set(cornerSelector, {
    attr: {
        cx: centerX, cy: centerY, r: 4,
        fill: '#4262ff', stroke: "none",
        'stroke-width': 0, class: `selector-corner-${this.shapeWrapped.cssId}`
        }
    });
    this.board.appendChild(cornerSelector);
    cornerSelector.style.zIndex = this.shapeWrapped.zIndex;
    cornerSelector.style.userSelect = 'none';

    return cornerSelector;
  }
 
  _createCorners(){
    this.corners.upperLeft = this._createCircularSelector(this.startingPos.x, this.startingPos.y)
    this.corners.lowerLeft = this._createCircularSelector(this.startingPos.x, this.startingPos.y + this.height)
    this.corners.upperRight = this._createCircularSelector(this.startingPos.x + this.width, this.startingPos.y);
    this.corners.lowerRight = this._createCircularSelector(this.startingPos.x + this.width, this.startingPos.y + this.height);
  }

  _createEdgesSelectors(){
    const middlepoints = this.getEdgesMiddlePoints();
    
    this.edges.left = this._createCircularSelector(middlepoints.leftEdge.x, middlepoints.leftEdge.y);
    this.edges.right = this._createCircularSelector(middlepoints.rightEdge.x, middlepoints.rightEdge.y);
    this.edges.up = this._createCircularSelector(middlepoints.upperEdge.x, middlepoints.upperEdge.y);
    this.edges.down = this._createCircularSelector(middlepoints.lowerEdge.x, middlepoints.lowerEdge.y);
  }

  updatePos(x, y){
    this.startingPos.x = x;
    this.startingPos.y = y;
    this.wrapper.setAttribute("x", this.startingPos.x);
    this.wrapper.setAttribute("y", this.startingPos.y);
    this._updateCorners();
    this._updateEgesSelectors();
  }

  updateWidth(width){
    this.width = width;
    this.wrapper.setAttribute("width", this.width);
  }

  updateHeight(height){
    this.height = height; 
    this.wrapper.setAttribute("height", this.height);
  }

  _updateCorners(){
    this.corners.upperLeft.setAttribute("cx", this.startingPos.x);
    this.corners.upperLeft.setAttribute("cy", this.startingPos.y);

    this.corners.lowerLeft.setAttribute("cx", this.startingPos.x);
    this.corners.lowerLeft.setAttribute("cy", this.startingPos.y + this.height);

    this.corners.upperRight.setAttribute("cx", this.startingPos.x + this.width);
    this.corners.upperRight.setAttribute("cy", this.startingPos.y);

    this.corners.lowerRight.setAttribute("cx", this.startingPos.x + this.width);
    this.corners.lowerRight.setAttribute("cy",this.startingPos.y + this.height);
  }

  _updateEgesSelectors(){
    const middlepoints = this.getEdgesMiddlePoints();

    this.edges.left.setAttribute("cx", middlepoints.leftEdge.x);
    this.edges.left.setAttribute("cy", middlepoints.leftEdge.y);

    this.edges.right.setAttribute("cx", middlepoints.rightEdge.x);
    this.edges.right.setAttribute("cy", middlepoints.rightEdge.y);

    this.edges.up.setAttribute("cx" ,middlepoints.upperEdge.x);
    this.edges.up.setAttribute("cy", middlepoints.upperEdge.y);

    this.edges.down.setAttribute("cx", middlepoints.lowerEdge.x);
    this.edges.down.setAttribute("cy", middlepoints.lowerEdge.y);
  }

  _addEventHandlers(){
    this.wrapper.onmousedown = () => this._mouseDownAction();
    this.wrapper.onmouseup = () => this._mouseUpAction();

    this.corners.upperLeft.onmousedown = () => this._cornerMouseDownAction( this.shapeWrapped.upperLeftCorner.x + this.shapeWrapped.width,
                                                                            this.shapeWrapped.upperLeftCorner.y + this.shapeWrapped.height);
    this.corners.upperLeft.onmouseover = () => this._resizingMouseOVerAction("nwse-resize");
    this.corners.upperLeft.onmouseleave = () => this._resizingMouseLeaveAction();
    this.corners.upperLeft.onmouseup = () => this._resizingMouseUpAction();

    this.corners.upperRight.onmousedown = () => this._cornerMouseDownAction( this.shapeWrapped.upperLeftCorner.x,
                                                                             this.shapeWrapped.upperLeftCorner.y + this.shapeWrapped.height);
    this.corners.upperRight.onmouseover = () => this._resizingMouseOVerAction("nesw-resize");
    this.corners.upperRight.onmouseleave = () => this._resizingMouseLeaveAction();
    this.corners.upperRight.onmouseup = () => this._resizingMouseUpAction();

    this.corners.lowerLeft.onmousedown = () => this._cornerMouseDownAction( this.shapeWrapped.upperLeftCorner.x + this.shapeWrapped.width,
                                                                            this.shapeWrapped.upperLeftCorner.y);
    this.corners.lowerLeft.onmouseover = () => this._resizingMouseOVerAction("nesw-resize");
    this.corners.lowerLeft.onmouseleave = () => this._resizingMouseLeaveAction();
    this.corners.lowerLeft.onmouseup = () => this._resizingMouseUpAction();

    this.corners.lowerRight.onmousedown = () => this._cornerMouseDownAction( this.shapeWrapped.upperLeftCorner.x,
                                                                             this.shapeWrapped.upperLeftCorner.y);
    this.corners.lowerRight.onmouseover = () => this._resizingMouseOVerAction("nwse-resize");
    this.corners.lowerRight.onmouseleave = () => this._resizingMouseLeaveAction();
    this.corners.lowerRight.onmouseup = () => this._resizingMouseUpAction();

    this.edges.right.onmousedown = () => this._edgeMouseDownAction(this.shapeWrapped.upperLeftCorner.x, true)
    this.edges.right.onmouseover = () => this._resizingMouseOVerAction("ew-resize");
    this.edges.right.onmouseleave = () => this._resizingMouseLeaveAction();
    this.edges.right.onmouseup = () => this._resizingMouseUpAction();

    this.edges.left.onmousedown = () => this._edgeMouseDownAction(this.shapeWrapped.upperLeftCorner.x + this.shapeWrapped.width, true)
    this.edges.left.onmouseover = () => this._resizingMouseOVerAction("ew-resize");
    this.edges.left.onmouseleave = () => this._resizingMouseLeaveAction();
    this.edges.left.onmouseup = () => this._resizingMouseUpAction();    

    this.edges.up.onmousedown = () => this._edgeMouseDownAction(this.shapeWrapped.upperLeftCorner.y + this.shapeWrapped.height, false)
    this.edges.up.onmouseover = () => this._resizingMouseOVerAction("ns-resize");
    this.edges.up.onmouseleave = () => this._resizingMouseLeaveAction();
    this.edges.up.onmouseup = () => this._resizingMouseUpAction(); 
    
    this.edges.down.onmousedown = () => this._edgeMouseDownAction(this.shapeWrapped.upperLeftCorner.y, false)
    this.edges.down.onmouseover = () => this._resizingMouseOVerAction("ns-resize");
    this.edges.down.onmouseleave = () => this._resizingMouseLeaveAction();
    this.edges.down.onmouseup = () => this._resizingMouseUpAction(); 
  }

  _clickAcion(){
    this.clicked = !this.clicked;
    if(this.clicked){
      this.disablePrevSelector();
      this.enable();
      store.commit('setSelecting', true);
    }
    else{
      this.disable();
      this.mouseDown = false;
      store.commit('setSelecting', false);
    }
  }

  //edge resizing
  _edgeMouseDownAction(ref, lockY){
    this.resizing = true;
    this.disable();
    
    const tracker = setInterval(() => {
      if(!this.resizing || !store.getters.boardMouseDown) {
        clearInterval(tracker);
        this.enable();
      }
      else {
        if(lockY){ //change the width = x
          const updatedX = window.mouseX; 
          const newUpperLeftCornerX = Math.min(ref, updatedX);
          const currentWidth = Math.abs(updatedX - ref);
          this.shapeWrapped.updateWidth(currentWidth);
          this.updateWidth(currentWidth + 2);
          this.shapeWrapped.updatePos(newUpperLeftCornerX, this.shapeWrapped.upperLeftCorner.y);
          this.updatePos(newUpperLeftCornerX -1, this.shapeWrapped.upperLeftCorner.y -1);
        }
        else{
          const updatedY = window.mouseY - 110;
          const newUpperLeftCornerY = Math.min(ref, updatedY);
          const currentHeight = Math.abs(updatedY - ref);
          this.shapeWrapped.updateHeight(currentHeight);
          this.updateHeight(currentHeight + 2);
          this.shapeWrapped.updatePos(this.shapeWrapped.upperLeftCorner.x, newUpperLeftCornerY);
          this.updatePos(this.shapeWrapped.upperLeftCorner.x -1, newUpperLeftCornerY -1);
        }
      }
    }, 10); 
  }

  //corner resinzing
  _cornerMouseDownAction(refX, refY){
    this.resizing = true;
    this.disable();
    
    const tracker = setInterval(() => {
      if(!this.resizing || !store.getters.boardMouseDown) {
        clearInterval(tracker);
        this.enable();
      }
      else {
        const updatedX = window.mouseX 
        const updatedY = window.mouseY - 110;
        const newUpperLeftCornerX = Math.min(refX, updatedX);
        const newUpperLeftCornerY = Math.min(refY, updatedY);
        const currentWidth = Math.abs(updatedX - refX);
        const currentHeight = Math.abs(updatedY - refY);

        this.shapeWrapped.updateWidth(currentWidth);
        this.shapeWrapped.updateHeight(currentHeight);
        this.updateWidth(currentWidth + 2);
        this.updateHeight(currentHeight + 2);

        this.shapeWrapped.updatePos(newUpperLeftCornerX, newUpperLeftCornerY);
        this.updatePos(newUpperLeftCornerX -1, newUpperLeftCornerY -1);
      }
    }, 10); 
    
  }
  //resining actions
  _resizingMouseUpAction(){
    this.resizing = false;
  }
  
  _resizingMouseLeaveAction(){
    store.commit("setResizing", false);
  }

  _resizingMouseOVerAction(mosueDirection){
    store.commit("setResizing", true);
    store.commit("setDirection", mosueDirection);
  }
  //moving the shape
  _mouseDownAction(){
    this.mouseDown = true;
    this.disable();
    this._trackMovement();
  }

  _mouseUpAction(){
    this._clickAcion();
    this.mouseDown = false;
  }
  
  _trackMovement(){
    let prevX = window.mouseX;
    let prevY = window.mouseY - 110;
    
    const tracker = setInterval(() => {
      if(!this.mouseDown || !store.getters.boardMouseDown) {
        clearInterval(tracker);
      }
      else {
        const updatedX = window.mouseX;
        const updatedY = window.mouseY - 110;
        const xShift = updatedX - prevX;
        const yShift = updatedY - prevY;
        const newUpperLeftCornerX = this.shapeWrapped.upperLeftCorner.x + xShift;
        const newUpperLeftCornerY = this.shapeWrapped.upperLeftCorner.y + yShift;
        prevX = window.mouseX;
        prevY = window.mouseY - 110;
        this.shapeWrapped.updatePos(newUpperLeftCornerX, newUpperLeftCornerY);
        this.updatePos(newUpperLeftCornerX -1, newUpperLeftCornerY -1);
      }
    }, 1); 
  }
  //sub actions
  disable(){
    this.wrapper.style.stroke = 'none';  

    this.corners.upperLeft.style.fill = 'none';
    this.corners.upperRight.style.fill = 'none';
    this.corners.lowerLeft.style.fill = 'none';
    this.corners.lowerRight.style.fill = 'none';

    this.edges.left.style.fill = 'none';
    this.edges.right.style.fill = 'none';
    this.edges.up.style.fill = 'none';
    this.edges.down.style.fill = 'none';
  }
  enable(){
    this.wrapper.style.stroke = '#4262ff'; 

    this.corners.upperLeft.style.fill = '#4262ff';
    this.corners.upperRight.style.fill = '#4262ff';
    this.corners.lowerLeft.style.fill = '#4262ff';
    this.corners.lowerRight.style.fill = '#4262ff'; 

    this.edges.left.style.fill = '#4262ff';
    this.edges.right.style.fill = '#4262ff';
    this.edges.up.style.fill = '#4262ff';
    this.edges.down.style.fill = '#4262ff';
  }
  disablePrevSelector(){
    const prevSelector = store.getters.currentSelector;
    prevSelector.disable();
    prevSelector.mouseDown = false;
    if(prevSelector.id !== this.id) prevSelector.clicked = false;
    store.commit('setSelector', this);
  }

  //calculations
  getCornersCoordinates(){
    return {
      upperLeftCorner: {
        x: this.startingPos.x,
        y: this.startingPos.y,
      },

      upperRightcorner: {
        x: this.startingPos.x + this.width,
        y: this.startingPos.y,
      },

      lowerLeftCorner: {
        x: this.startingPos.x,
        y: this.startingPos.y + this.height,
      },

      lowerRightCorner: {
        x: this.startingPos.x + this.width,
        y: this.startingPos.y + this.height,
      },
    }
  }

  getEdgesMiddlePoints(){
    const corners = this.getCornersCoordinates();
    return {
      leftEdge: {
        x: (corners.upperLeftCorner.x + corners.lowerLeftCorner.x) / 2,
        y: (corners.upperLeftCorner.y + corners.lowerLeftCorner.y) / 2,
      },
  
      rightEdge: {
        x: (corners.upperRightcorner.x + corners.lowerRightCorner.x) / 2,
        y: (corners.upperRightcorner.y + corners.lowerRightCorner.y) / 2,
      },
  
      upperEdge: {
        x: (corners.upperLeftCorner.x + corners.upperRightcorner.x) / 2,
        y: (corners.upperLeftCorner.y + corners.upperRightcorner.y) / 2,
      },
  
      lowerEdge: {
        x: (corners.lowerLeftCorner.x + corners.lowerRightCorner.x) / 2,
        y: (corners.lowerLeftCorner.y + corners.lowerRightCorner.y) / 2,
      },
    }
  }
}