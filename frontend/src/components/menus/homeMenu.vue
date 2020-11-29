<template>
  <div id="home-menu">
      <undoRedo/>
      <clipboard/>
      <deleteBtn/>
      <shapeFormat/>
      <draw/>
  </div>
</template>

<script>
import clipboard from './home-menu/clipboard.vue';
import undoRedo from './home-menu/undoRedo.vue';
import shapeFormat from './home-menu/shapeFormat.vue';
import deleteBtn from './home-menu/delete.vue';
import draw from './home-menu/draw.vue';
export default {
  name: 'homeMenu',
  components: {
    undoRedo,
    clipboard,
    shapeFormat,
    deleteBtn,
    draw,
  },
  props: {
  },
}
</script>

<style scoped>
#home-menu{
  height: 80px;
  background-color: white;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  box-shadow: 0px 5px 8px -6px darkgray;
  user-select: none;
}

.editing-sec{
  display: flex;
  justify-content: center;
  align-items: center;
  /* margin-left: 5px; */
  margin-bottom: 0px;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 10px 10px;
}

.sub-elem:hover{
  background-color: whitesmoke;
}

.sub-elem-23{
  padding: 8.5px 8.5px;
}

.seperator{
  height: 32px;
  margin: 0px 4px;
  border-left: thin solid black;
}
</style>