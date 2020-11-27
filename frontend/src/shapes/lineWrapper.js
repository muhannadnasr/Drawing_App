import gsap from 'gsap';

import store from "../store";

export class LineWrapper{
  constructor(line){
    this.id = line.shapeId;
    this.lineWrapped = line;  
    this.board = document.getElementById("board");
    this.wrapper = null;
    this.pointsSelectors = {
      pointA: null,
      pointB: null,
    };
    this.clicked = true;
    this.mouseDown = false;
    this._createLineWrapper();
    this._addEventHandlers();
  }

  _createLineWrapper(){
    const svgns = "http://www.w3.org/2000/svg"; //variable for the namespace
    const selectionWrapper = document.createElementNS(svgns, "ellipse");

    this._setWrapper(selectionWrapper);
    
		this.board.appendChild(selectionWrapper);
		selectionWrapper.style.zIndex = this.lineWrapped.zIndex;
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
        'stroke-width': 0, class: `selector-corner-${this.lineWrapped.cssId}`
        }
    });
    this.board.appendChild(cornerSelector);
    cornerSelector.style.zIndex = this.lineWrapped.zIndex;
    cornerSelector.style.userSelect = 'none';

    return cornerSelector;
  }

  _setWrapper(wrapper){
    const lineLength = Math.sqrt(Math.pow((this.lineWrapped.endingPoint.x - this.lineWrapped.startingPoint.x), 2) +
		Math.pow((this.lineWrapped.endingPoint.y - this.lineWrapped.startingPoint.y), 2));

		const centerX = (this.lineWrapped.startingPoint.x + this.lineWrapped.endingPoint.x) /2;
		const centerY = (this.lineWrapped.startingPoint.y + this.lineWrapped.endingPoint.y) /2;

		const width = this.lineWrapped.endingPoint.x - this.lineWrapped.startingPoint.x;
		const height = this.lineWrapped.endingPoint.y - this.lineWrapped.startingPoint.y;
		const rotationAngle = Math.atan(height/ width) *(180/ Math.PI);

		const selectorRadiusW = (lineLength/2) + 10;
    const selectorRadiusH = 7;

    gsap.set(wrapper, {
      attr: {
        cx: centerX, cy: centerY, rx: selectorRadiusW, ry: selectorRadiusH,
        fill: 'transparent',
        transform: `rotate(${rotationAngle} ${centerX} ${centerY})`,
        id: `line-selector-${this.lineWrapped.cssId}`
      }
    });
  }

  update(){
    this._setWrapper(this.wrapper);
    this.updatePointsSelectors();
  }
 
  _createPointsSelectors(){
    this.pointsSelectors.pointA = this._createPointSelector(this.lineWrapped.startingPoint.x, this.lineWrapped.startingPoint.y)
    this.pointsSelectors.pointB = this._createPointSelector(this.lineWrapped.endingPoint.x, this.lineWrapped.endingPoint.y)
  }

  updatePointsSelectors(){
    this.pointsSelectors.pointA.setAttribute("cx", this.lineWrapped.startingPoint.x);
    this.pointsSelectors.pointA.setAttribute("cy", this.lineWrapped.startingPoint.y);
    this.pointsSelectors.pointB.setAttribute("cx", this.lineWrapped.endingPoint.x);
    this.pointsSelectors.pointB.setAttribute("cy", this.lineWrapped.endingPoint.y);
  }
  removeWrapper(){
    this.wrapper.remove();
  }

  _addEventHandlers(){
    this.wrapper.onclick = () => this._clickAcion();
    this.wrapper.onmousedown = () => this._mouseDownAction();
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
        const newStartingPointX = this.lineWrapped.startingPoint.x + xShift;
        const newStartingPointY = this.lineWrapped.startingPoint.y + yShift;
        const newEndingPointX = this.lineWrapped.endingPoint.x + xShift;
        const newEndingPointY = this.lineWrapped.endingPoint.y + yShift;
        prevX = window.mouseX;
        prevY = window.mouseY - 110;
        this.lineWrapped.updateStartingPos(newStartingPointX, newStartingPointY);
        this.lineWrapped.updateEndingPos(newEndingPointX, newEndingPointY);
        this.update();
      }
    }, 1); 
  }
  //sub actions
  disable(){
    this.mouseDown = false;
  }
  enable(){
    return;
  }
  disablePrevSelector(){
    const prevSelector = store.getters.currentSelector;
    prevSelector.disable();
    if(prevSelector.id !== this.id) prevSelector.clicked = false;
    store.commit('setSelector', this);
  }
}