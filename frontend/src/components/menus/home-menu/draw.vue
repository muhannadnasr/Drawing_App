<template>
  <div id="draw-menu">
    <div class="sub-elem pointer" @click="disableDrawingMode()">
      <img v-if="isDrawing" src="../../../assets/drawing/blackPointer.png" width="40">
      <img v-if="!isDrawing" src="../../../assets/drawing/purplePointer.png" width="40">
    </div>
    <div class="sub-elem top-controls square" @click="enableDrawing(shapeTypes.square)">
      <img src="../../../assets/drawing/square.png" width="20">
    </div>
    <div class="sub-elem top-controls rectangle" @click="enableDrawing(shapeTypes.rectangle)">
      <img src="../../../assets/drawing/rectangle.png" width="20">
    </div>
    <div class="sub-elem top-controls circle" @click="enableDrawing(shapeTypes.circle)">
      <img src="../../../assets/drawing/circle.png" width="20">
    </div>
    <div class="sub-elem top-controls ellipse" @click="enableDrawing(shapeTypes.ellipse)">
      <img src="../../../assets/drawing/ellipse.png" width="20">
    </div>
    <div class="sub-elem down-controls triangle" @click="enableDrawing(shapeTypes.triangle)">
      <img src="../../../assets/drawing/triangle.svg" width="20">
    </div>
    <div class="sub-elem down-controls line" @click="enableDrawing(shapeTypes.line)">
      <img src="../../../assets/drawing/line.png" width="20">
    </div>
    <div class="label">Draw</div>
    <div class="seperator"></div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'draw',
  data(){
    return{
      shapeTypes: {
        square: 'square',
        rectangle: 'rectangle',
        circle: 'circle',
        ellipse: 'ellipse',
        triangle: 'triangle',
        rhombus: 'rhombus',
        line: 'line',
      }
    }
  },
  computed: mapGetters(['isDrawing', 'shapeType', 'currentSelector']),
  methods:{
    ...mapActions(['enableDrawingMode', 'disableDrawingMode', 'controlShapeType']),
    enableDrawing(shapeType){
      this.enableDrawingMode();
      this.controlShapeType(shapeType);
      if(this.currentSelector !== null) {
        this.currentSelector.disable();
        this.currentSelector.clicked = false;
      } 
    },
  },
}
</script>

<style scoped>
#draw-menu{
  display: grid;
  grid-template-columns: 40px repeat(4, 30px) 10px;
  grid-template-rows: 30px 30px 13px;
  justify-items: center;
  align-items: center;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px 5px;
}
.sub-elem:hover{
  background-color: whitesmoke;
}

.pointer{
  grid-row: 1/3;
  grid-column: 1/2;
  padding: 10px 0px;
}
.pointer:hover{
  background-color: white;
}

.top-controls{
  grid-row: 1/2;
}
.square{
  grid-column: 2/3;
}
.rectangle{
  grid-column: 3/4;
}
.circle{
  grid-column: 4/5;
}
.ellipse{
  grid-column: 5/6;
}

.down-controls{
  grid-row: 2/3;
}
.triangle{
  grid-column: 2/3;
}
.rhombus{
  grid-column: 3/4;
}
.line{
  grid-column: 3/4;
}

.label{
  text-align: center;
  font-size: 10px;
  color: darkgrey;
  grid-column: 1/6;
  grid-row: 3/4;
}

.seperator{
  height: 70px;
  grid-column: 6/7;
  grid-row: 1/4;
  border-left: thin solid darkgray;
}
</style>