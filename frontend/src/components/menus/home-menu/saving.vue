<template>
  <div id="save-load">
    <div class="sub-elem save" @click="saveMenu()">
      <img src="../../../assets/saving/save.png" width="40">
    </div>
    <div class="sub-elem load" @click="loadMenu()">
      <span id="load-title">load</span> <img src="../../../assets/saving/choosePath.png" width="23">
    </div>
    <div class="label-saving">Save & Load</div>
    <div class="seperator-saving"></div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters, mapActions } from 'vuex';
import { Square } from "../../../shapes/square";
import { Ellipse } from "../../../shapes/ellipse.js";
import { Triangle } from "../../../shapes/triangle.js";
import { Line } from "../../../shapes/line.js";
import { ShapeWrapper } from "../../../shapes/shapeWrapper.js";
import { LineWrapper } from "../../../shapes/lineWrapper.js";
import { pushShapeCopy } from "../../../backEndComm/shapeComm";
import { pushLineCopy } from "../../../backEndComm/lineComm";
import store from "../../../store";

export default {
  name: "savingControl",
  data(){
    return{
      fileNameToBeSaved: "",
      saveAs: "xml",
      savingAllowed: false,
      savingOptions: {
        xml: "xml",
        json: "json",
      },
      loadedExtension: "",
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
  computed: mapGetters(['shapesDrawn']),
  methods: {
    ...mapActions(['clearShapesDrawn', 'changeFileName']),
    saveMenu(){
      if(this.shapesDrawn.length === 0) {
          return;
      }

      const popUpBackground = document.createElement('div');
      popUpBackground.className = 'pop-up-background';
      document.body.appendChild(popUpBackground);

      const popUp = document.createElement('div');
      popUp.className = 'pop-up';
      popUpBackground.appendChild(popUp);

      const msg = document.createElement('h2');
      msg.innerText = "save as";
      popUp.appendChild(msg);

      const typeSelection = document.createElement('div');
      typeSelection.className = 'container';

      const savaAsXmlConatiner = document.createElement('div')
      savaAsXmlConatiner.className = "radio-selector"
      const saveAsXmlRadioButton = document.createElement('input');
      saveAsXmlRadioButton.type = 'radio';
      saveAsXmlRadioButton.id = 'xml-option';
      saveAsXmlRadioButton.name = 'selector';
      saveAsXmlRadioButton.checked = true;
      savaAsXmlConatiner.appendChild(saveAsXmlRadioButton);
      const saveXmlLabel  = document.createElement('label');
      saveXmlLabel.for = 'xml-option';
      saveXmlLabel.innerHTML = '.Xml';
      savaAsXmlConatiner.appendChild(saveXmlLabel);
      typeSelection.appendChild(savaAsXmlConatiner);

      saveAsXmlRadioButton.onclick = () => {
        this.saveAs = this.savingOptions.xml;
      };

      const saveAsJsonContainer = document.createElement('div')
      saveAsJsonContainer.className = "radio-selector"
      const saveAsJsonRadioButton = document.createElement('input');
      saveAsJsonRadioButton.type = 'radio';
      saveAsJsonRadioButton.id = 's-option';
      saveAsJsonRadioButton.name = 'selector';
      saveAsJsonContainer.appendChild(saveAsJsonRadioButton);
      const saveJsonLabel  = document.createElement('label');
      saveJsonLabel.for = 's-option';
      saveJsonLabel.innerHTML = '.Json';
      saveAsJsonContainer.appendChild(saveJsonLabel);
      typeSelection.appendChild(saveAsJsonContainer);

      saveAsJsonRadioButton.onclick = () => {
        this.saveAs = this.savingOptions.json;
      };

      const fileName = document.createElement('input');
      fileName.type = 'text';
      fileName.placeholder = "file name";
      fileName.className = 'file-name';

      let fileNameTracker;
      fileName.onmousedown = () => {
        fileNameTracker = setInterval( () => {
          if(fileName.value.includes('.')) {
            fileName.className = 'not-allowed';
            this.savingAllowed = false;
          }
          else{
            fileName.className = "file-name";
            this.savingAllowed = true;
            this.fileNameToBeSaved = fileName.value;
          }
        }, 100)
      };

      const cancelBtn = document.createElement('span');
      cancelBtn.innerText = 'Cancel';
      cancelBtn.className = 'pop-up-close-button cancel-btn';

      const saveBtn = document.createElement('span');
      saveBtn.innerText = 'Save';
      saveBtn.className = 'pop-up-close-button save-btn';   

      cancelBtn.onclick = () => {    
        clearInterval(fileNameTracker);
        document.body.removeChild(popUpBackground); 
      }

      saveBtn.onclick = () => {
        clearInterval(fileNameTracker);
        
        if(this.fileNameToBeSaved === ""){
          fileName.className = "not-allowed";
        }
        else{
          this.changeFileName(this.fileNameToBeSaved);
          if(this.saveAs === this.savingOptions.xml){
            this.fileNameToBeSaved += ".xml";
            this.saveAsXMl(this.fileNameToBeSaved);
          }
          else{
            this.fileNameToBeSaved += ".json";
            this.saveAsJson(this.fileNameToBeSaved);
          }
          document.body.removeChild(popUpBackground); 
        }
      }

      popUp.appendChild(cancelBtn);
      popUp.appendChild(saveBtn);
      popUp.appendChild(typeSelection);
      popUp.appendChild(fileName);
    },

    loadMenu(){
      if (this.shapesDrawn.length > 0){
        alert(" all changes will be deleted");
      }

      const popUpBackground = document.createElement('div');
      popUpBackground.className = 'pop-up-background';
      document.body.appendChild(popUpBackground);

      const popUp = document.createElement('div');
      popUp.className = 'pop-up';
      popUpBackground.appendChild(popUp);

      const msg = document.createElement('h2');
      msg.innerText = "choose a file";
      popUp.appendChild(msg);

      const fileInput = document.createElement('input');
      fileInput.type = "file";
      fileInput.className = "load-file";
      fileInput.accept = "application/JSON, application/XML";
      popUp.appendChild(fileInput);

      fileInput.onchange = () => {
        if ('files' in fileInput && fileInput.files.length > 0){
          const file = fileInput.files[0];
          this.loadedExtension = fileInput.value.split('.')[1];
          this.readFileContent(file)
          .then( content => {
            const fullPath = fileInput.value;
            const startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
            let filename = fullPath.substring(startIndex);
            if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
                filename = filename.substring(1).split('.')[0];
            }
            this.changeFileName(filename)
            this.loadBoard(content);
            document.body.removeChild(popUpBackground); 
          })
          .catch( error => console.log(error))
        }
      }

      const cancelBtn = document.createElement('span');
      cancelBtn.innerText = 'Cancel';
      cancelBtn.className = 'pop-up-close-button load-cancel-btn';

      cancelBtn.onclick = () => {  
        document.body.removeChild(popUpBackground); 
      }

      popUp.appendChild(cancelBtn);
      popUp.appendChild(fileInput);
    },

    download(filename, dataString) {
      var element = document.createElement('a');
      if(this.saveAs === this.savingOptions.xml){
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(dataString));
        // element.setAttribute('href', 'data:Application/octet-stream,' + encodeURIComponent(dataString));
      }
      else{
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(JSON.stringify(dataString, null, 1)));
      }
      element.setAttribute('download', filename);

      element.style.display = 'none';
      document.body.appendChild(element);

      element.click();

      document.body.removeChild(element);
    },
    //saving
    saveAsXMl(fileName){
      axios.get('http://localhost:8085/saveXml')
      .then( response => this.download(fileName, response.data))
      .catch( (error) => console.log(error));
    },
    saveAsJson(fileName){
      axios.get('http://localhost:8085/saveJson')
      .then( response => this.download(fileName, response.data))
      .catch( (error) => console.log(error));
    },
    // loading
    loadBoard(inputString){
      //reset the board
      axios.post('http://localhost:8085/reset')
      .then( () => {
        this.clearBoard();
        if(this.loadedExtension === this.savingOptions.xml){
          axios.get('http://localhost:8085/xmlToJson', {params:{xmlString: inputString}})
          .then( response => {
            this.recreateShapes(response.data)
            })
          .catch( (error) => console.log(error));
        }
        else{
          this.recreateShapes(JSON.parse(inputString));
        }
      })
      .catch( (error) => console.log(error));      
    },
    recreateShapes(shapesInfo){
      const shapes = shapesInfo.Shapes.Shape;
      if(shapes.length === undefined){
        this.reconstructShape(shapes, true);
        return;
      }
      for (let i = 0; i < shapes.length; i++) {
        if(i === shapes.length -1) this.reconstructShape(shapes[i], true);
        else this.reconstructShape(shapes[i], false);
      }
    },
    clearBoard(){
      for(let shape of this.shapesDrawn){
        shape.remove();
      }
      this.clearShapesDrawn();
    },
    //helpers
    readFileContent(file) {
      const reader = new FileReader()
      return new Promise((resolve, reject) => {
        reader.onload = event => resolve(event.target.result)
        reader.onerror = error => reject(error)
        reader.readAsText(file)
      })
    },
    reconstructShape(shapeInfo, lastElement){ // shape info passed as json
      let newShape = null;
      const shapeType = shapeInfo.type;
      const shapeId = shapeInfo.id;
      if (shapeType !== this.shapeTypes.line){
        let upperLeftConrner;
        if(this.loadedExtension === this.savingOptions.xml){
          upperLeftConrner = {
            x: +(shapeInfo.upperLeftCorner.split(',')[0]),
            y: +(shapeInfo.upperLeftCorner.split(',')[1]),
          }
        }
        else{
          upperLeftConrner = {
            x: shapeInfo.upperLeftCorner.x,
            y: shapeInfo.upperLeftCorner.y,
          }
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
        newShape.updateFillOpacity(fillOpacity);
        newShape.updateOutlineColor(outlineColor);
      }
      else {
        let startingPoint;
        let endingPoint;
        if(this.loadedExtension === this.savingOptions.xml){
          startingPoint = {
            x: +(shapeInfo.startingPoint.split(',')[0]),
            y: +(shapeInfo.startingPoint.split(',')[1]),
          };
          endingPoint = {
            x: +(shapeInfo.endingPoint.split(',')[0]),
            y: +(shapeInfo.endingPoint.split(',')[1]),
          }
        }
        else{
          startingPoint = {
            x: shapeInfo.startingPoint.x,
            y: shapeInfo.startingPoint.y,
          };
          endingPoint = {
            x: shapeInfo.endingPoint.x,
            y: shapeInfo.endingPoint.y,
          }
        }
        newShape = new Line(startingPoint.x, startingPoint.y, shapeType, shapeId);
        newShape.create(startingPoint.x, startingPoint.y, endingPoint.x, endingPoint.y);
        newShape.selector = new LineWrapper(newShape);
      }

      const fillColor = shapeInfo.fillColor;
      const thickness = shapeInfo.thickness;
      newShape.updateFillColor(fillColor);
      newShape.updateThickness(thickness);
      store.commit('pushShapeDrawn', newShape);

      if(shapeType !== this.shapeTypes.line) {
        if (lastElement) pushShapeCopy(newShape, true);
        else pushShapeCopy(newShape, false);
      }
      else {
        if (lastElement) pushLineCopy(newShape, true);
        else pushLineCopy(newShape, false);
      }

      newShape.selector.disable();
      newShape.selector.clicked = false;
      store.commit('setSelecting', false);
    }
  },
  mounted(){

  }
}
</script>

<style>
#save-load {
  display: grid;
  grid-template-columns: 10px 60px 60px;
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

.save{
  grid-row: 1/3;
  grid-column: 3/4;
  padding: 10px 10px;
}
.load{
  grid-row: 1/2;
  grid-column: 2/3;
}

.pop-up-background {
    position: fixed;
    left: 0;
    top: 0;        
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    transform: scale(1.1);
    transition: visibility 0s linear 0.25s, opacity 0.25s 0s, transform 0.25s;
    z-index: 99999999999;
}
.pop-up {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 1rem 1rem;
    width: 24rem;
    height: 8rem;
    border: 3px solid #4262ff;

    display: grid;
    align-items: center;
    grid-template-rows: repeat(3, 1fr);
    grid-template-columns: 12rem 5rem 5rem;
}

.pop-up h2 {
    font-size: 19px;
    margin-bottom: auto;
}

.pop-up-close-button {
    float: right;
    width: 4rem;
    height: 1.4rem;
    font-size: 13px;
    line-height: 1.3rem;
    text-align: center;
    color: white;
    cursor: pointer;
    background-color: #4262ff;
    grid-row: 3/4;
}

.cancel-btn{
  grid-column: 2/3;
}
.save-btn{
  grid-column: 3/4;
}

.container{
  grid-row: 2/3;
  grid-column: 2/4;
  display: flex;
	align-items: center;
  justify-content: space-evenly;
  position: relative;
  height: auto;
}

.radio-selector{
  margin: 0;
  padding: 0;	
	color: black;
  position: relative;
}

.file-name{
  grid-row: 2/3;
  grid-column: 1/2;
  box-sizing: border-box;
  border: none;
  width: 10rem;
  height: 30px;
  border-bottom: 2px solid #4262ff;
}

input[type=text]:focus {
  outline-style: none;
  border-bottom: 2px solid #4262ff;
}

input[type=text]::placeholder {
  font-size: medium;
}

.not-allowed{
  grid-row: 2/3;
  grid-column: 1/2;
  box-sizing: border-box;
  border: none;
  width: 10rem;
  height: 30px;
  background: rgba(226, 83, 83, 0.6);
}

.load-file{
  grid-row: 2/3;
  grid-column: 1/2;
}
.load-cancel-btn{
  grid-row: 3/4;
  grid-column: 3/4;
}

#load-title{
  margin-right: 4px;
}

.label-saving{
  grid-row: 3/4;
  grid-column: 2/4;
  text-align: center;
  font-size: 10px;
  color: darkgrey;
}

.seperator-saving{
  border-left: thin solid darkgray;
  height: 70px;
  grid-row: 1/4;
  grid-column: 1/2;
}
</style>