<template>
  <div id="app">
    <appHeader/>
    <homeMenu/>
  </div>
</template>

<script>
// import menuNav from "./components/menuNav.vue";
import appHeader from "./components/appHeader.vue";
import homeMenu from "./components/menus/homeMenu.vue";
export default {
  name: 'App',
  components: {
    appHeader,
    homeMenu,
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
}
</style>
