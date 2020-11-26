import gsap from 'gsap';

import store from "../store";

export class ShapeWrapper{
  constructor(shape){
    this.id = shape.shapeId;
    this.shpaeWrapped = shape;  
    this.board = document.getElementById("board");
    this.wrapper = this._createShapeWrapper();
    this.clicked = true;
    this.mouseDown = false;
    this._addEventHandlers();
  }

  _createShapeWrapper(){
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const selectionWrapper = document.createElementNS(svgns, "rect");
    gsap.set(selectionWrapper, {
    attr: {
        x: this.shpaeWrapped.upperLeftCorner.x -1, y: this.shpaeWrapped.upperLeftCorner.y -1, rx: 0, ry: 0,
        width: this.shpaeWrapped.width + 2,
        height: this.shpaeWrapped.height + 2,
        fill: 'transparent', stroke: "#4262ff",
        'stroke-width': 1, id: `shape-selector-${this.shpaeWrapped.cssId}`
        }
    });
    this.board.appendChild(selectionWrapper);
    selectionWrapper.style.zIndex = this.shpaeWrapped.zIndex;
    selectionWrapper.style.userSelect = 'none';
    
    return selectionWrapper;
  }

  updatePos(x, y){
    this.wrapper.setAttribute("x", x);
    this.wrapper.setAttribute("y", y);
  }

  _addEventHandlers(){
    this.wrapper.onclick = () => this._clickAcion();
    this.wrapper.onmousedown = () => this._mouseDownAction();
    this.wrapper.onmouseover = () => this._mosueOverAction();
    this.wrapper.onmouseup = () => this._mouseUpAction();
  }

  _clickAcion(){
    this.clicked = !this.clicked;
    if(this.clicked){
      this.disablePrevSelector();
      this.enable();
    }
    else{
      this.disable();
    }
  }

  _mouseDownAction(){
    // if(!this.clicked) return;
    this.mouseDown = true;
    this._trackMovement();
  }

  _mosueOverAction(){

  }

  _mouseUpAction(){
    this.mouseDown = false;
  }
  
  //moving shape
  _trackMovement(){
    let prevX = window.mouseX;
    let prevY = window.mouseY - 110;

    const tracker = setInterval(() => {
      if(!this.mouseDown || !store.getters.boardMouseDown) clearInterval(tracker);
      else {
        const updateX = window.mouseX;
        const updatedY = window.mouseY - 110;
        const xShift = updateX - prevX;
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
    this.mouseDown = false;
  }
  enable(){
    this.wrapper.style.stroke = '#4262ff';  
  }
  disablePrevSelector(){
    const prevSelector = store.getters.currentSelector;
    prevSelector.disable();
    if(prevSelector.id !== this.id) prevSelector.clicked = false;
    store.commit('setSelector', this);
  }
}