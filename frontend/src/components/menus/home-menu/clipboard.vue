<template>
  <div id="clipboard">
    <div class="sub-elem paste" @click="pasteShape()">
      <img src="../../../assets/editingSec-Icons/paste.png" width="40">
    </div>
    <div class="sub-elem copy" @click="copyShape()">
      <img src="../../../assets/editingSec-Icons/copy.png" width="23">
    </div>
    <div class="sub-elem cut"><img src="../../../assets/editingSec-Icons/cut.png" width="23"></div>
    <div class="label">Clipboard</div>
    <div class="seperator"></div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import { Square } from "../../../shapes/square";
import { Ellipse } from "../../../shapes/ellipse.js";
import { Triangle } from "../../../shapes/triangle.js";
import { Line } from "../../../shapes/line.js";
import { ShapeWrapper } from "../../../shapes/shapeWrapper.js";
import { LineWrapper } from "../../../shapes/lineWrapper.js";
import { pushShape, updateFillOpacity, updateOutlineColor } from "../../../backEndComm/shapeComm";
import { pushLine } from "../../../backEndComm/lineComm";
import { updateFillColor, updateThickness } from "../../../backEndComm/comm.js";
import axios from 'axios';
export default {
  name: 'clipboard',
  data() {
    return{
      currentCopied: null,

      shapeTypes: {
        square: 'square',
        rectangle: 'rectangle',
        circle: 'circle',
        ellipse: 'ellipse',
        triangle: 'triangle',
        rhombus: 'rhombus',
        line: 'line',
      },
    }
  },
  computed: mapGetters(['currentSelector']),
  methods: {
    ...mapActions(['setCurrentSelector']),
    copyShape() {
      this.currentCopied = this.currentSelector.shapeWrapped;
    },
    pasteShape(){
      if (this.currentCopied === null) return;
      this.currentSelector.disable();
      this.currentSelector.clicked = false;
      axios.get('http://localhost:8085/getShapeData', {
        params :{
          id: this.currentCopied.shapeId,
        }
      })
      .then( (response) => {
        this.performPasting(response.data);
        this.currentCopied = this.currentSelector.shapeWrapped;
      })
      .catch( (error) => console.log(error));
    },
    performPasting(shapeInfo){
      let newShape = null;
      const shapeType = shapeInfo.type;
      const shapeId = null;
      if (shapeType !== this.shapeTypes.line){
        const upperLeftConrner = {
          x: shapeInfo.upperLeftCorner.x + 15,
          y: shapeInfo.upperLeftCorner.y + 15,
        }
        const width = shapeInfo.width;
        const height = shapeInfo.height;
        const fillOpacity = shapeInfo.fillOpacity;
        const outlineColor = shapeInfo.outlineColor;

        if (shapeType === this.shapeTypes.square || shapeType === this.shapeTypes.rectangle){
          newShape = new Square(upperLeftConrner.x, upperLeftConrner.y, shapeType, shapeId);
        }
        else if(shapeType === this.shapeTypes.circle || shapeType === this.shapeTypes.ellipse){
          newShape = new Ellipse(upperLeftConrner.x, upperLeftConrner.y, shapeType, shapeId);
        }
        else if(shapeType === this.shapeTypes.triangle){
          newShape = new Triangle(upperLeftConrner.x, upperLeftConrner.y, shapeType, shapeId);
        }

        newShape.create(width, height);
        newShape.selector = new ShapeWrapper(newShape);
        pushShape(newShape);
    
        newShape.updateFillOpacity(fillOpacity);
        updateFillOpacity(newShape);
        newShape.updateOutlineColor(outlineColor);
        updateOutlineColor(newShape);
      }
      else {
        const startingPoint = {
          x: shapeInfo.startingPoint.x + 15,
          y: shapeInfo.startingPoint.y,
        }
        const endingPoint = {
          x: shapeInfo.endingPoint.x + 15,
          y: shapeInfo.endingPoint.y,
        }
        newShape = new Line(startingPoint.x, startingPoint.y, shapeType, shapeId);
        newShape.create(startingPoint.x, startingPoint.y, endingPoint.x, endingPoint.y);
        newShape.selector = new LineWrapper(newShape);
        pushLine(newShape);
      }

      const fillColor = shapeInfo.fillColor;
      const thickness = shapeInfo.thickness;
      newShape.updateFillColor(fillColor);
      updateFillColor(newShape);
      newShape.updateThickness(thickness);
      updateThickness(newShape);

      // newShape.selector.disablePrevSelector(); 
      newShape.selector.enable();
      newShape.selector.clicked = false;
      this.setCurrentSelector(newShape.selector);
    }
  },
  mounted(){

  },
  created() {

  }
}
</script>

<style scoped>
#clipboard{
  display: grid;
  grid-template-columns: 60px 30px 10px;
  grid-template-rows: 30px 30px 13px;
  justify-items: center;
  align-items: center;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 3.5px 3.5px;
}
.sub-elem:hover{
  background-color: whitesmoke;
}

.paste{
  grid-row: 1/3;
  grid-column: 1/2;
  padding: 10px 10px;
}

.copy{
  grid-row: 1/2;
  grid-column: 2/3;
}

.cut{
  grid-row: 2/3;
  grid-column: 2/3;
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
</style>