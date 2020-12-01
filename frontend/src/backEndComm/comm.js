import axios from 'axios';

function updateFillColor(shape){
  axios.post('http://localhost:8085/updateFillColor', null, 
  {params :{
    id: shape.shapeId,
    fillColor: shape.fill,
  }})
  .catch( (error) => console.log(error));
}

function updateThickness(shape){
  axios.post('http://localhost:8085/updateThickness', null, 
  {params :{
    id: shape.shapeId,
    thickness: shape.thickness,
  }})
  .catch( (error) => console.log(error));
}

function del(id){
  axios.post('http://localhost:8085/delete', null, 
  {params :{
    id: id,
  }})
  .catch( (error) => console.log(error));
}
export { updateFillColor, updateThickness, del }