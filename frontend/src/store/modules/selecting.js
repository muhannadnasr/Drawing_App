const state = {
  currentSelector: null,
  boardMouseDown: false,
};

const getters = {
  currentSelector: state => state.currentSelector,
  boardMouseDown: state => state.boardMouseDown,
};

const actions = {
  setCurrentSelector: ({commit}, selector) => commit('setSelector', selector),
  setBoardMouseDown: ({commit}, status) => commit('setMouseDownStatus', status),
}

const mutations = {
  setSelector: (state, selector) => state.currentSelector = selector,
  setMouseDownStatus: (state, status) => state.boardMouseDown = status,
};

export default{
  state,
  getters,
  actions,
  mutations,
};