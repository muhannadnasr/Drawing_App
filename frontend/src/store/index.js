import Vuex from 'vuex';
import Vue from 'vue';
import drawing from './modules/drawing.js';
//load Vuex
Vue.use(Vuex);

//Create the store
export default new Vuex.Store({
  modules: {
    drawing,
  }
});