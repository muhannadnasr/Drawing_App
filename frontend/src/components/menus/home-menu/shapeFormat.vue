<template>
  <div id="color-style-menu">
    <div @click="activateMenu(colorFillMenu)" class="sub-elem circle-fill" id="color-fill-btn">
      <svg width="60" height="60" version="1.1" xmlns="http://www.w3.org/2000/svg">
        <circle cx="30px" cy="30px" r="20px" stroke="black" stroke-width="2" :style="{fill: selectedShapeColor()}"/>
      </svg>
    </div>

    <div @click="activateMenu(outlineMenu)" class="sub-elem circle-outline" id="color-outline-btn">
      <svg width="60" height="60" version="1.1" xmlns="http://www.w3.org/2000/svg">
        <circle cx="30px" cy="30px" r="17px" :style="{stroke: selectedShapeOutlineColor()}" stroke-width="10px" fill="none"/>
      </svg>
    </div>

    <div id="color-menu" class="menu-hidden">
      <div :class="['info-label', showOpacitySlider()]">Opacity</div>
      <div :class="['slider-wrap', showOpacitySlider()]">
        <input id="opacity-adj" type="range" min="0" max="100" value="100" step="1" @mousedown="setOpacity()">
        <div class="range-value" id="opacityValue">{{selectedShapeOpacity()}}</div>
      </div>
      <div :class="['info-label', showShapeThicknessSlider()]">Thickness</div>
      <div :class="['slider-wrap', showShapeThicknessSlider()]">
        <input id="shapeThickness-adj" type="range" min="0" max="30" value="1" step="1" 
        @mousedown="setThickness()"
        @mouseup="thicknessMouseUpAction()">
        <div class="range-value" id="opacityValue">{{selectedShapeThickness()}}</div>
      </div>
      <div :class="['info-label', showLineThicknessSlider()]">Thickness</div>
      <div :class="['slider-wrap', showLineThicknessSlider()]">
        <input id="lineThickness-adj" type="range" min="1" max="30" value="1" step="1" 
        @mousedown="setThickness()"
        @mouseup="thicknessMouseUpAction()">
        <div class="range-value" id="opacityValue">{{selectedLineThickness()}}</div>
      </div>
      <div class="info-label">Standard Colors</div>
      <div id="standard-colors-sec">
        <div id="no-fill-btn" @click="setNoFill()"><img src="../../../assets/colorStyle/noFill.png" width="24"></div>
        <div class="color-square" 
          v-for="(color, colorIndex) in colors" :key="colorIndex" 
          :style="{'background-color': `rgb(${color.R},${color.G},${color.B})`}"
          @click="setColor(colorIndex)">
        </div>
      </div>
    </div>
    <div class="seperator"></div>
    <div class="label"> Shape Format</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { rgb } from '../../../helpers.js';
import { updateFillColor, updateThickness } from '../../../backEndComm/comm.js';
import { updateFillOpacity, updateOutlineColor } from '../../../backEndComm/shapeComm.js';
export default {
  name: 'shapeFormat',
  props: {

  },
  data(){
    return{
      prevActiveMenuId: null,
      currActiveMenu: null,
      colors: [
        {R: '255', G: '255', B: '255'},
        {R: '230', G: '230', B: '230'},
        {R: '128', G: '128', B: '128'},
        {R: '27', G: '27', B: '27'},
        {R: '243', G: '700', B: '71'},
        {R: '224', G: '85', B: '54'},
        {R: '201', G: '44', B: '99'},
        {R: '79', G: '173', B: '91'},
        {R: '76', G: '165', B: '138'},
        {R: '113', G: '224', B: '225'},
        {R: '94', G: '202', B: '210'},
        {R: '78', G: '155', B: '233'},
        {R: '66', G: '78', B: '172'},
        {R: '137', G: '40', B: '166'},
        {R: '94', G: '53', B: '172'},
      ],
      recentColors: {

      },
      colorFillMenu:{
        show: false,
        btnId: "color-fill-btn",
        selectedColor: "black",
        id: 0,
      },
      outlineMenu: {
        show: false,
        btnId: "color-outline-btn",
        selectedColor: "black",
        id: 1,
      },
      opacity: 1,
      opacityText: '100%',
      thickness: 1,
      thicknessText: '1px',
    }
  },
  computed: {
    ...mapGetters(['currentSelector', 'isSelecting', 'maxCurrZIndex']),
  },
  methods: {
    activateMenu(menu){
      if(!this.isSelecting) return;
      if(this.prevActiveMenuId === null) {//first time to active a menu
        this.prevActiveMenuId = menu.id;
        this.toggleColorMenu(menu);
      }
      else{
        if(menu.id === this.prevActiveMenuId) this.toggleColorMenu(menu)
        else{
          if(menu.id === 0) this.outlineMenu.show = false;
          else this.colorFillMenu.show = false;
          this.toggleColorMenu(menu);
        }
      }
    },
    toggleColorMenu(menu){
      menu.show = !menu.show;

      this.currActiveMenu = menu;
      this.prevActiveMenuId = menu.id;

      if(menu.id === 1 && this.currentSelector.shapeWrapped.shapeType === 'line') this.hideColorMenu();

      if (menu.show) this.showColorMenu(menu.btnId);
      else this.hideColorMenu();
    },
    showColorMenu(btnId){
      const parentButton = document.getElementById(btnId);
      const parentMenu = parentButton.parentNode.parentNode;

      const parentButtonPos = parentButton.getBoundingClientRect();
      const parentMenuPos = parentMenu.getBoundingClientRect();

      const menu = document.getElementById("color-menu");

      const left = parentButtonPos.left;
      const bottom = parentMenuPos.bottom;
      menu.style.left = left + "px";
      menu.style.top = bottom +2+ "px"; 

      menu.className = "menu-active";
    },
    hideColorMenu(){
      const menu = document.getElementById("color-menu");
      menu.className = "menu-hidden";
      this.currActiveMenu.show = false;
    },
    setColor(colorIndex){
      if(!this.isSelecting) return;

      const color = this.colors[colorIndex];
      this.currActiveMenu.selectedColor = rgb(color.R, color.G, color.B);
      if (this.currActiveMenu.id === 0) this.setShapeFillColor();
      else if (this.currActiveMenu.id === 1) this.setShapeOutlineColor();
    },
    setOpacity(){
      if(!this.isSelecting) return;
      if(this.currActiveMenu === null) return;

      const range = document.getElementById("opacity-adj");
      const opacityTracker = setInterval( () => {
        if(!this.isSelecting) {
          clearInterval(opacityTracker);
          updateFillOpacity(this.currentSelector.shapeWrapped);
        }
        this.opacityText = `${range.value}%`;
        this.opacity = range.value / 100;
        if (this.currActiveMenu.id == 0) {
          this.setShapeFillOpacity();
        }
      }, 50);
    },
    setNoFill(){
      this.currActiveMenu.selectedColor = "transparent";
      if(this.currentSelector.shapeWrapped.shapeType === 'line') return;
      if (this.currActiveMenu.id == 0) this.setShapeFillColor();
      else if(this.currActiveMenu.id == 1) {
        this.setShapeOutlineColor();
      }
    },
    setThickness(){
      if(!this.isSelecting) return;
      if(this.currActiveMenu === null) return;

      let range = null;
      if (this.currentSelector.shapeWrapped.shapeType === 'line'){
        range = document.getElementById("lineThickness-adj");
      }else{
        range = document.getElementById("shapeThickness-adj");
      }
      this.currentSelector.disable();
      const thicknessTracker = setInterval( () => {
        if(!this.isSelecting) {
          clearInterval(thicknessTracker);
          updateThickness(this.currentSelector.shapeWrapped);
        }
        this.thickness = range.value; 
        this.thicknessText = `${range.value}px`;
        this.setShapeThickness();
      }, 50);
    },
    thicknessMouseUpAction(){
      this.currentSelector.enable();
    },

    setShapeFillColor(){
      if(this.isSelecting){
        this.currentSelector.shapeWrapped.updateFillColor(this.colorFillMenu.selectedColor);
        updateFillColor(this.currentSelector.shapeWrapped);
      }
    },
    setShapeOutlineColor(){
      if(this.isSelecting){
        this.currentSelector.shapeWrapped.updateOutlineColor(this.outlineMenu.selectedColor);
        updateOutlineColor(this.currentSelector.shapeWrapped);
      }
    },
    setShapeFillOpacity(){
      if(this.isSelecting){
        this.currentSelector.shapeWrapped.updateFillOpacity(this.opacity);
      }
    },
    setShapeThickness(){
      if(this.isSelecting){
        this.currentSelector.shapeWrapped.updateThickness(this.thickness);
      }
    },

    selectedShapeColor(){
      if(this.currentSelector === null) return rgb(27, 27, 27);
      return this.currentSelector.shapeWrapped.fill;
    },
    selectedShapeOutlineColor(){
      if(this.currentSelector === null) return rgb(27, 27, 27);
      if (this.currentSelector.shapeWrapped.shapeType === 'line') return rgb(153, 154, 158);
      return this.currentSelector.shapeWrapped.outline;
    },
    selectedShapeOpacity(){
      if(this.currentSelector === null) return "100%";
      document.getElementById("opacity-adj").value = Math.trunc(this.currentSelector.shapeWrapped.fillOpacity *100);
      return `${Math.trunc(this.currentSelector.shapeWrapped.fillOpacity * 100)}%`; 
    },
    selectedShapeThickness(){
      if(this.currentSelector === null) return "1px";
      document.getElementById("shapeThickness-adj").value = this.currentSelector.shapeWrapped.thickness;
      return `${this.currentSelector.shapeWrapped.thickness}px`;
    },
    selectedLineThickness(){
      if(this.currentSelector === null) return "1px";
      document.getElementById("lineThickness-adj").value = this.currentSelector.shapeWrapped.thickness;
      return `${this.currentSelector.shapeWrapped.thickness}px`;
    },

    showLineThicknessSlider(){
      if(this.currentSelector === null) return;
      if(this.currActiveMenu === null) return
      if( this.currentSelector.shapeWrapped.shapeType === 'line' &&
          this.currActiveMenu.id === 0) return "slider-active";
      return "slider-hidden"
    },
    showShapeThicknessSlider(){
      if(this.currentSelector === null) return;
      if(this.currActiveMenu === null) return
      if(this.currActiveMenu.id === 1) return "slider-active";
      return "slider-hidden"
    },
    showOpacitySlider(){
      if(this.currentSelector === null) return;
      if(this.currActiveMenu === null) return
      if( this.currentSelector.shapeWrapped.shapeType !== 'line' && 
          this.currActiveMenu.id === 0) return "slider-active";
      return "slider-hidden";
    },
  },
  mounted(){
    setInterval(() => {
      if(!this.isSelecting && this.currActiveMenu !== null){
        this.hideColorMenu();
      }
    }, 100);
  }
}
</script>

<style>
#color-style-menu{
  display: grid;
  grid-template-columns: repeat(2, 60px) 10px;
  grid-template-rows: 30px 30px 10px;
  justify-items: center;
  align-items: center;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
}
.sub-elem:hover{
  background-color: whitesmoke;
}

.circle-fill{
  grid-row: 1/3;
  grid-column: 1/2;
}
.circle-outline{
  grid-row: 1/3;
  grid-column: 2/3;
}

#color-menu{
  background-color: white;
  position: absolute;
  height: 185px;
  width: 150px;
  z-index: 99999999;
}

#standard-colors-sec{
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;  
}
.color-square{
  border-radius: 20px;
  height: 25px;
  width: 25px;
  margin: 2px 5px;
  border: 1px solid black;
}

.menu-hidden{
  display: none;
  visibility: hidden;
}

.menu-active{
  display: block;
  visibility: visible;
  border: 1px solid whitesmoke;
  border-radius: 2px;
  box-shadow: 0px 5px 30px -3px whitesmoke;
}

.info-label{
  margin: 5px;
  font-size: 9px;
  color: black;
}

#no-fill-btn{
  border-radius: 20px;
  height: 25px;
  width: 25px;
  margin: 2px 5px;
}

.seperator{
  height: 70px;
  grid-column: 3/4;
  grid-row: 1/4;
  border-left: thin solid darkgray;
}
.label{
  text-align: center;
  font-size: 10px;
  color: darkgrey;
  grid-column: 1/3;
  grid-row: 3/4;
}

/* sliders */
input[type=range] {
	-webkit-appearance: none;
	width: 110px;
  height: 10px;
}
input[type=range]:focus {
	outline: none;
}
input[type=range]::-webkit-slider-runnable-track {
	width: 100%;
	height: 2px;
	cursor: pointer;
	background: black;
	border-radius: 25px;
  margin: 5px 0px;
}
input[type=range]:focus::-webkit-slider-runnable-track {
	background: black;
}
input[type=range]::-webkit-slider-thumb {
	height: 10px;
	width: 10px;
	border-radius: 50%;
	background: black;
	cursor: pointer;
	-webkit-appearance: none;
	margin-top: -4px;
}

.slider-wrap{
	width: 150px;
	position: relative;
  display: flex;
  margin: 5px;
  align-items: center;
}

.range-value{
	position: relative;
  width: 30px;
	height: 14px;
  text-align: center;
	background: white;
	color: black;
	font-size: 10px;
}

.slider-hidden{
  visibility: hidden;
  display: none;
}
.slider-active{
  visibility: visible;
  display: flex;
}
</style>