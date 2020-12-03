import Vuex from 'vuex';
import Vue from 'vue';
import drawing from './modules/drawing.js';
import selecting from './modules/selecting.js';
import loading from './modules/loading.js';
//load Vuex
Vue.use(Vuex);

//Create the store
export default new Vuex.Store({
  modules: {
    drawing,
    selecting,
    loading,
  }
});