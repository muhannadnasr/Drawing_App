import axios from 'axios';
import {stringfyPoint} from '../helpers.js';

function pushShape(shape){
  axios.post('http://localhost:8085/createShape', null, 
  {params :{
    id: shape.shapeId,
    type: shape.shapeType,
    upperLeftCorner: stringfyPoint(shape.upperLeftCorner.x, shape.upperLeftCorner.y),
    width: shape.width,
    height: shape.height,
  }})
  .catch( (error) => console.log(error));
}

function updateFillOpacity(shape){
  axios.post('http://localhost:8085/updateFillOpacity', null, 
  {params :{
    id: shape.shapeId,
    opacity: shape.fillOpacity,
  }})
  .catch( (error) => console.log(error));
}

function updateOutlineColor(shape){
  axios.post('http://localhost:8085/updateOutlineColor', null, 
  {params :{
    id: shape.shapeId,
    outlineColor: shape.outline,
  }})
  .catch( (error) => console.log(error));
}


export { pushShape, updateFillOpacity, updateOutlineColor }