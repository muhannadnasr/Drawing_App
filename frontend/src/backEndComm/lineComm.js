import axios from 'axios';
import {stringfyPoint} from '../helpers.js';

function pushLine(line){
  axios.post('http://localhost:8085/createLine', null, 
  {params :{
    id: line.shapeId,
    startingPoint: stringfyPoint(line.startingPoint.x, line.startingPoint.y),
    endingPoint: stringfyPoint(line.endingPoint.x, line.endingPoint.y),
  }})
  .catch( () => console.log("error"));
}

function updateLinePos(line){
  axios.post('http://localhost:8085/updateLinePos', null, 
  {params :{
    id: line.shapeId,
    startingPoint: stringfyPoint(line.startingPoint.x, line.startingPoint.y),
    endingPoint: stringfyPoint(line.endingPoint.x, line.endingPoint.y),
  }})
  .then( () => console.log("pos updated"))
  .catch( () => console.log("error"));
}

export {pushLine, updateLinePos }