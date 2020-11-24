<template>
  <div id="app">
    <appHeader/>
    <homeMenu/>
    <board/>
  </div>
</template>

<script>
// import menuNav from "./components/menuNav.vue";
import appHeader from "./components/appHeader.vue";
import board from './components/board.vue';
import homeMenu from "./components/menus/homeMenu.vue";
export default {
  name: 'App',
  components: {
    appHeader,
    homeMenu,
    board,
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Open+Sans&display=swap');
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Open Sans', sans-serif;
  overflow: hidden;
}
</style>
