import gsap from 'gsap';
import { updateLinePos } from '../backEndComm/lineComm.js';
import store from "../store";

export class LineWrapper{
  constructor(line){
    this.id = line.shapeId;
    this.shapeWrapped = line;  
    this.board = document.getElementById("board");
    this.wrapper = null;
    this.pointsSelectors = {
      startingPoint: null,
      endingPoint: null,
    };
    this.clicked = true;
    this.mouseDown = false;
    this.resizing = false;
    this._createLineWrapper();
    this._addEventHandlers();
  }

  _createLineWrapper(){
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const selectionWrapper = document.createElementNS(svgns, "ellipse");

    this._setWrapper(selectionWrapper);
    
		this.board.appendChild(selectionWrapper);
		selectionWrapper.style.zIndex = this.shapeWrapped.zIndex;
    selectionWrapper.style.userSelect = 'none';

    this._createPointsSelectors();

    this.wrapper = selectionWrapper;
  }

  _createPointSelector(centerX, centerY){
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

  _setWrapper(wrapper){
    const lineLength = Math.sqrt(Math.pow((this.shapeWrapped.endingPoint.x - this.shapeWrapped.startingPoint.x), 2) +
		Math.pow((this.shapeWrapped.endingPoint.y - this.shapeWrapped.startingPoint.y), 2));

		const centerX = (this.shapeWrapped.startingPoint.x + this.shapeWrapped.endingPoint.x) /2;
		const centerY = (this.shapeWrapped.startingPoint.y + this.shapeWrapped.endingPoint.y) /2;

		const width = this.shapeWrapped.endingPoint.x - this.shapeWrapped.startingPoint.x;
		const height = this.shapeWrapped.endingPoint.y - this.shapeWrapped.startingPoint.y;
		const rotationAngle = Math.atan(height/ width) *(180/ Math.PI);

		const selectorRadiusW = (lineLength/2) + 10;
    const selectorRadiusH = 7 + (this.shapeWrapped.thickness/2);

    gsap.set(wrapper, {
      attr: {
        cx: centerX, cy: centerY, rx: selectorRadiusW, ry: selectorRadiusH,
        fill: 'transparent',
        transform: `rotate(${rotationAngle} ${centerX} ${centerY})`,
        id: `line-selector-${this.shapeWrapped.cssId}`
      }
    });
  }

  update(){
    this._setWrapper(this.wrapper);
    this.updatePointsSelectors();
  }
 
  _createPointsSelectors(){
    this.pointsSelectors.startingPoint = this._createPointSelector(this.shapeWrapped.startingPoint.x, this.shapeWrapped.startingPoint.y)
    this.pointsSelectors.endingPoint = this._createPointSelector(this.shapeWrapped.endingPoint.x, this.shapeWrapped.endingPoint.y)
  }

  updatePointsSelectors(){
    this.pointsSelectors.startingPoint.setAttribute("cx", this.shapeWrapped.startingPoint.x);
    this.pointsSelectors.startingPoint.setAttribute("cy", this.shapeWrapped.startingPoint.y);
    this.pointsSelectors.endingPoint.setAttribute("cx", this.shapeWrapped.endingPoint.x);
    this.pointsSelectors.endingPoint.setAttribute("cy", this.shapeWrapped.endingPoint.y);
  }
  removeWrapper(){
    this.wrapper.remove();
  }

  _addEventHandlers(){
    this.wrapper.onmousedown = () => this._mouseDownAction();
    this.wrapper.onmouseup = () => this._mouseUpAction();

    this.pointsSelectors.startingPoint.onmousedown = () => this._changeStartingPoint();
    this.pointsSelectors.startingPoint.onmouseover = () => this._pointSelectorMouseOverAction();
    this.pointsSelectors.startingPoint.onmouseup = () => this._pointSelectorMouseUpAction();
    this.pointsSelectors.startingPoint.onmouseleave = () => this._piontSelectorMouseLeaveAction();

    this.pointsSelectors.endingPoint.onmousedown = () => this._changeEndingPoint();
    this.pointsSelectors.endingPoint.onmouseover = () => this._pointSelectorMouseOverAction();
    this.pointsSelectors.endingPoint.onmouseup = () => this._pointSelectorMouseUpAction();
    this.pointsSelectors.endingPoint.onmouseleave = () => this._piontSelectorMouseLeaveAction();
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
  //Resizing shape
  _changeStartingPoint(){
    this.resizing = true;
    this.disable();
    
    const tracker = setInterval(() => {
      if(!this.resizing || !store.getters.boardMouseDown) {
        clearInterval(tracker);
        this.enable();
        updateLinePos(this.shapeWrapped);
      }
      else {
        const updatedX = window.mouseX 
        const updatedY = window.mouseY - 110;
        this.shapeWrapped.updateStartingPos(updatedX, updatedY);
        this.update();
      }
    }, 10); 
  }

  _changeEndingPoint(){
    this.resizing = true;
    this.disable();
    
    const tracker = setInterval(() => {
      if(!this.resizing || !store.getters.boardMouseDown) {
        clearInterval(tracker);
        this.enable();
        updateLinePos(this.shapeWrapped);
      }
      else {
        const updatedX = window.mouseX 
        const updatedY = window.mouseY - 110;
        this.shapeWrapped.updateEndingPos(updatedX, updatedY);
        this.update();
      }
    }, 10); 
  }

  _pointSelectorMouseUpAction(){    
    this.resizing = false;
  }
  
  _piontSelectorMouseLeaveAction(){
    store.commit("setResizing", false);
  }

  _pointSelectorMouseOverAction(mosueDirection){
    store.commit("setResizing", true);
    store.commit("setDirection", mosueDirection);
  }

  //moving Shape
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
    if(store.getters.isDrawing) return;

    let prevX = window.mouseX;
    let prevY = window.mouseY - 110;

    const oldStartingPoint = {
      x: this.shapeWrapped.startingPoint.x,
      y: this.shapeWrapped.startingPoint.y,
    }
    const oldEndingPoint = {
      x: this.shapeWrapped.endingPoint.x,
      y: this.shapeWrapped.endingPoint.y,
    }
    const tracker = setInterval(() => {
      if(!this.mouseDown || !store.getters.boardMouseDown) {
        if( this.shapeWrapped.startingPoint.x !== oldStartingPoint.x ||
          this.shapeWrapped.startingPoint.y !== oldStartingPoint.y ||
          this.shapeWrapped.endingPoint.x !== oldEndingPoint.x ||
          this.shapeWrapped.endingPoint.y !== oldEndingPoint.y) {
            updateLinePos(this.shapeWrapped);
          }
        clearInterval(tracker);
      }
      else {
        const updateX = window.mouseX;
        const updatedY = window.mouseY - 110;
        const xShift = updateX - prevX;
        const yShift = updatedY - prevY;
        const newStartingPointX = this.shapeWrapped.startingPoint.x + xShift;
        const newStartingPointY = this.shapeWrapped.startingPoint.y + yShift;
        const newEndingPointX = this.shapeWrapped.endingPoint.x + xShift;
        const newEndingPointY = this.shapeWrapped.endingPoint.y + yShift;
        prevX = window.mouseX;
        prevY = window.mouseY - 110;
        this.shapeWrapped.updateStartingPos(newStartingPointX, newStartingPointY);
        this.shapeWrapped.updateEndingPos(newEndingPointX, newEndingPointY);
        this.update();
      }
    }, 1); 
  }
  //sub actions
  disable(){
    this.pointsSelectors.startingPoint.style.fill = 'none';
    this.pointsSelectors.endingPoint.style.fill = 'none';
  }
  enable(){
    this.pointsSelectors.startingPoint.style.fill = '#4262ff';
    this.pointsSelectors.endingPoint.style.fill = '#4262ff';
  }
  disablePrevSelector(){
    const prevSelector = store.getters.currentSelector;
    prevSelector.mouseDown = false;
    if(prevSelector.id !== this.id) prevSelector.clicked = false;
    store.commit('setSelector', this);
  }
}