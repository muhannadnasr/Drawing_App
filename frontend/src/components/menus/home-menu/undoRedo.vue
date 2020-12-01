<template>
  <div id="undo-redo">
    <div class="sub-elem undo" @click="undo()">
      <img src="../../../assets/editingSec-Icons/undo.png" width="20">
    </div>
    <div class="sub-elem redo" @click="redo()">
      <img src="../../../assets/editingSec-Icons/redo.png" width="20">
    </div>
    <div class="label">Undo</div>
    <div class="seperator"></div>
  </div>
</template>

<script>
import { reCreateShape } from '../../../helpers';
import { mapGetters, mapActions } from 'vuex';
import axios from 'axios';

export default {
  name: 'undoRedo',
  data() {
    return{
      
    }
  },
  computed: mapGetters(['shapesDrawn']),
  methods: {
    ...mapActions(['clearShapesDrawn']),
    undo(){
      axios.get('http://localhost:8085/undo')
      .then( (response) => {
        this.clearBoard();
        if(response.data !== "empty") this.reconstructBoard(response.data);
      })
      .catch( (error) => console.log(error));
    },
    redo(){
      axios.get('http://localhost:8085/redo')
      .then( (response) => {
        if(response.data !== "empty") {
          this.clearBoard();
          this.reconstructBoard(response.data);
        }
      })
      .catch( (error) => console.log(error));
    },
    reconstructBoard(shapesInfo){
      const shapes = shapesInfo.Shapes.Shape;
      for (let shape of shapes) {
        reCreateShape(shape);
      }
    },
    clearBoard(){
      for(let shape of this.shapesDrawn){
        shape.remove();
      }
      this.clearShapesDrawn();
    },
  },
  mounted() {

  },
  created() {

  }
}
</script>

<style scoped>
#undo-redo{
  margin-left: 5px;
  display: grid;
  grid-template-columns: 30px 10px;
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
.undo{
  grid-column: 1/2;
  grid-row: 1/2;
}
.redo{
  grid-column: 1/2;
  grid-row: 2/3;
}
.label{
  text-align: center;
  font-size: 10px;
  color: darkgrey;
  grid-column: 1/2;
  grid-row: 3/4;
}
.seperator{
  height: 70px;
  grid-column: 2/3;
  grid-row: 1/4;
  border-left: thin solid darkgray;
}
</style>>

