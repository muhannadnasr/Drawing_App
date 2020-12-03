const state = {
    fileName: "",
  };
  
  const getters = {
    fileName: state => state.fileName,
  };
  
  const actions = {
    changeFileName: ({commit}, name) => commit('setFileName', name),  
  }
  
  const mutations = {
    setFileName: (state, fileName) => state.fileName = fileName,
  };
  
  export default{
    state,
    getters,
    actions,
    mutations,
  };