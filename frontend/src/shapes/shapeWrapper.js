import gsap from 'gsap';

import store from "../store";

export class ShapeWrapper{
  constructor(shape){
    this.id = shape.shapeId;
    this.shpaeWrapped = shape;  
    this.board = document.getElementById("board");
    this.wrapper = null;
    this.corners = {
      upperLeft: null,
      upperRight: null,
      lowerLeft: null,
      lowerRight: null,
    };
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

    this.startingPos.x = this.shpaeWrapped.upperLeftCorner.x -1;
    this.startingPos.y = this.shpaeWrapped.upperLeftCorner.y -1;
    this.width = this.shpaeWrapped.width + 2;
    this.height = this.shpaeWrapped.height + 2;

    gsap.set(selectionWrapper, {
    attr: {
        x: this.startingPos.x, y: this.startingPos.y, rx: 0, ry: 0,
        width: this.width, height: this.height,
        fill: 'transparent', stroke: "#4262ff",
        'stroke-width': 1, id: `shape-selector-${this.shpaeWrapped.cssId}`
        }
    });
    this.board.appendChild(selectionWrapper);
    selectionWrapper.style.zIndex = this.shpaeWrapped.zIndex;
    selectionWrapper.style.userSelect = 'none';

    this.wrapper = selectionWrapper;

    this._createCorners();
  }

  _createCornerSelector(centerX, centerY){
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const cornerSelector = document.createElementNS(svgns, "circle");
    gsap.set(cornerSelector, {
    attr: {
        cx: centerX, cy: centerY, r: 3,
        fill: '#4262ff', stroke: "none",
        'stroke-width': 0, class: `selector-corner-${this.shpaeWrapped.cssId}`
        }
    });
    this.board.appendChild(cornerSelector);
    cornerSelector.style.zIndex = this.shpaeWrapped.zIndex;
    cornerSelector.style.userSelect = 'none';

    return cornerSelector;
  }
 
  _createCorners(){
    this.corners.upperLeft = this._createCornerSelector(this.startingPos.x, this.startingPos.y)
    this.corners.lowerLeft = this._createCornerSelector(this.startingPos.x, this.startingPos.y + this.height)
    this.corners.upperRight = this._createCornerSelector(this.startingPos.x + this.width, this.startingPos.y);
    this.corners.lowerRight = this._createCornerSelector(this.startingPos.x + this.width, this.startingPos.y + this.height);
  }

  updatePos(x, y){
    this.startingPos.x = x;
    this.startingPos.y = y;
    this.wrapper.setAttribute("x", x);
    this.wrapper.setAttribute("y", y);
    this._updateCorners();
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

  _addEventHandlers(){
    this.wrapper.onclick = () => this._clickAcion();
    this.wrapper.onmousedown = () => this._mouseDownAction();
    this.wrapper.onmouseup = () => this._mouseUpAction();

    this.corners.upperLeft.onmousedown = () => this._cornerMouseDownAction( this.shpaeWrapped.upperLeftCorner.x + this.shpaeWrapped.width,
                                                                            this.shpaeWrapped.upperLeftCorner.y + this.shpaeWrapped.height);
    this.corners.upperLeft.onmouseover = () => this._cornerMouseOverAction("nw-resize");
    this.corners.upperLeft.onmouseleave = () => this._cornerMouseLeaveAction();
    this.corners.upperLeft.onmouseup = () => this._cornerMouseUpAction();

    this.corners.upperRight.onmousedown = () => this._cornerMouseDownAction( this.shpaeWrapped.upperLeftCorner.x,
                                                                             this.shpaeWrapped.upperLeftCorner.y + this.shpaeWrapped.height);
    this.corners.upperRight.onmouseover = () => this._cornerMouseOverAction("ne-resize");
    this.corners.upperRight.onmouseleave = () => this._cornerMouseLeaveAction();
    this.corners.upperRight.onmouseup = () => this._cornerMouseUpAction();

    this.corners.lowerLeft.onmousedown = () => this._cornerMouseDownAction( this.shpaeWrapped.upperLeftCorner.x + this.shpaeWrapped.width,
                                                                            this.shpaeWrapped.upperLeftCorner.y);
    this.corners.lowerLeft.onmouseover = () => this._cornerMouseOverAction("sw-resize");
    this.corners.lowerLeft.onmouseleave = () => this._cornerMouseLeaveAction();
    this.corners.lowerLeft.onmouseup = () => this._cornerMouseUpAction();

    this.corners.lowerRight.onmousedown = () => this._cornerMouseDownAction( this.shpaeWrapped.upperLeftCorner.x,
                                                                             this.shpaeWrapped.upperLeftCorner.y);
    this.corners.lowerRight.onmouseover = () => this._cornerMouseOverAction("se-resize");
    this.corners.lowerRight.onmouseleave = () => this._cornerMouseLeaveAction();
    this.corners.lowerRight.onmouseup = () => this._cornerMouseUpAction();
  }

  _clickAcion(){
    this.clicked = !this.clicked;
    if(this.clicked){
      this.disablePrevSelector();
      this.enable();
    }
    else{
      this.disable();
      this.mouseDown = false;
    }
  }

  
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

        this.shpaeWrapped.updateWidth(currentWidth);
        this.shpaeWrapped.updateHeight(currentHeight);
        this.updateWidth(currentWidth + 2);
        this.updateHeight(currentHeight + 2);

        this.shpaeWrapped.updatePos(newUpperLeftCornerX, newUpperLeftCornerY);
        this.updatePos(newUpperLeftCornerX -1, newUpperLeftCornerY -1);
      }
    }, 10); 
    
  }

  _cornerMouseUpAction(){
    this.resizing = false;
  }
  
  _cornerMouseLeaveAction(){
    document.getElementById('board').style.cursor = "default";
  }

  _cornerMouseOverAction(mosueDirection){
    document.getElementById('board').style.cursor = mosueDirection;
  }
  //moving the shape
  _mouseDownAction(){
    this.mouseDown = true;
    this._trackMovement();
  }

  _mouseUpAction(){
    this.mouseDown = false;
  }
  _trackMovement(){
    let prevX = window.mouseX;
    let prevY = window.mouseY - 110;
    
    const tracker = setInterval(() => {
      if(!this.mouseDown || !store.getters.boardMouseDown) clearInterval(tracker);
      else {
        const updatedX = window.mouseX;
        const updatedY = window.mouseY - 110;
        const xShift = updatedX - prevX;
        const yShift = updatedY - prevY;
        const newUpperLeftCornerX = this.shpaeWrapped.upperLeftCorner.x + xShift;
        const newUpperLeftCornerY = this.shpaeWrapped.upperLeftCorner.y + yShift;
        prevX = window.mouseX;
        prevY = window.mouseY - 110;
        this.shpaeWrapped.updatePos(newUpperLeftCornerX, newUpperLeftCornerY);
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
  }
  enable(){
    this.wrapper.style.stroke = '#4262ff'; 
    this.corners.upperLeft.style.fill = '#4262ff';
    this.corners.upperRight.style.fill = '#4262ff';
    this.corners.lowerLeft.style.fill = '#4262ff';
    this.corners.lowerRight.style.fill = '#4262ff'; 
  }
  disablePrevSelector(){
    const prevSelector = store.getters.currentSelector;
    prevSelector.disable();
    prevSelector.mouseDown = false;
    if(prevSelector.id !== this.id) prevSelector.clicked = false;
    store.commit('setSelector', this);
  }
}