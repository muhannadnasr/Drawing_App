const state = {
  drawingMode: false,
  shapeType: null,
};

const getters = {
  isDrawing: state => state.drawingMode,
  shapeType: state => state.shapeType,
  maxCurrZIndex: state => state.maxCurrZIndex,
};

const actions = {
  enableDrawingMode: ({commit}) => commit('setDrawingMode', true),
  disableDrawingMode: ({commit}) => commit('setDrawingMode', false),
  controlShapeType: ({commit}, shapeType) => commit('setShapeType', shapeType),
}

const mutations = {
  setDrawingMode: (state, drawingMode) => state.drawingMode = drawingMode,
  setShapeType: (state, shapeType) => state.shapeType = shapeType,
};

export default{
  state,
  getters,
  actions,
  mutations,
};