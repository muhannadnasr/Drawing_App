<template>
  <div id="color-style-menu">
    <div @click="activateMenu(colorFillMenu)" class="sub-elem circle-fill" id="color-fill-btn">
      <svg width="60" height="60" version="1.1" xmlns="http://www.w3.org/2000/svg">
        <circle cx="30px" cy="30px" r="20px" stroke="black" stroke-width="2" :style="{fill: colorFillMenu.selectedColor}"/>
      </svg>
    </div>

    <div @click="activateMenu(outlineMenu)" class="sub-elem circle-outline" id="color-outline-btn">
      <svg width="60" height="60" version="1.1" xmlns="http://www.w3.org/2000/svg">
        <circle cx="30px" cy="30px" r="17px" :style="{stroke: outlineMenu.selectedColor}" stroke-width="10px" fill="none"/>
      </svg>
    </div>

    <div id="color-menu" class="menu-hidden">
      <div class="color-label">Standard Colors</div>
      <div id="standard-colors-sec">
        <div id="no-fill-btn" @click="setNoFill()"><img src="../../../assets/colorStyle/noFill.png" width="24"></div>
        <div class="color-square" 
          v-for="(color, colorIndex) in colors" :key="colorIndex" 
          :style="{'background-color': `rgb(${color.R},${color.G},${color.B})`}"
          @click="setColor(colorIndex)"
      ></div>
      </div>
    </div>

    <div class="seperator"></div>
  </div>
</template>

<script>
export default {
  name: 'colorStyle',
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
    }
  },
  methods: {
    activateMenu(menu){
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

      if (menu.show) this.showColorMenu(menu.btnId);
      else this.hideColorMenu();

      this.currActiveMenu = menu;
      this.prevActiveMenuId = menu.id;
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
      this.colorFillMenu.show = false;
    },
    setColor(colorIndex){
      const color = this.colors[colorIndex];
      this.currActiveMenu.selectedColor = `rgb(${color.R},${color.G},${color.B})`;
      this.hideColorMenu();
    },
    setNoFill(){
      this.currActiveMenu.selectedColor = "transparent";
      this.hideColorMenu();
    }
  },
  mounted(){
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
  height: 200px;
  width: 150px;
  z-index: 9999999;
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

.color-label{
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
</style>