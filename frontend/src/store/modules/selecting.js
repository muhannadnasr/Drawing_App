const state = {
  isResizing: false,
  isSelecting: false,
  resizingDirection: "",
  currentSelector: null,
  boardMouseDown: false,
};

const getters = {
  currentSelector: state => state.currentSelector,
  boardMouseDown: state => state.boardMouseDown,
  isResizing: state => state.isResizing,
  isSelecting: state => state.isSelecting,
  resizingDirection: state => state.resizingDirection,
};

const actions = {
  setCurrentSelector: ({commit}, selector) => commit('setSelector', selector),
  setBoardMouseDown: ({commit}, status) => commit('setMouseDownStatus', status),
  setResizingStatus: ({commit}, status) => commit('setResizing', status),
  setSelecitngStatus: ({commit}, status) => commit('setSelecting', status),
  setResizingDirection: ({commit}, direction) => commit('setDirection', direction),
}

const mutations = {
  setSelector: (state, selector) => state.currentSelector = selector,
  setMouseDownStatus: (state, status) => state.boardMouseDown = status,
  setResizing: (state, status) => state.isResizing = status,
  setSelecting: (state, status) => state.isSelecting = status,
  setDirection: (state, direction) => state.resizingDirection = direction,
};

export default{
  state,
  getters,
  actions,
  mutations,
};