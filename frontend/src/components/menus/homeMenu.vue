<template>
  <div id="home-menu">
      <undoRedo/>
      <clipboard/>
      <font/>
      <colorStyle/>
  </div>
</template>

<script>
import clipboard from './home-menu/clipboard.vue';
import undoRedo from './home-menu/undoRedo.vue';
import font from './home-menu/fontAndParagraph.vue';
import colorStyle from './home-menu/colorPicker.vue';
export default {
  name: 'homeMenu',
  components: {
    undoRedo,
    clipboard,
    font,
    colorStyle,
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
  box-shadow: 0px 4px 8px -6px darkgray;
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