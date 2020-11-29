const state = {
  drawingMode: false,
  shapeType: null,
  maxCurrZIndex: 2, 
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
  setMaxCurrZIndex: ({commit}, zIndex) => commit('setMaxZIndex', zIndex),
}

const mutations = {
  setDrawingMode: (state, drawingMode) => state.drawingMode = drawingMode,
  setShapeType: (state, shapeType) => state.shapeType = shapeType,
  setMaxZIndex: (state, zIndex) => state.zIndex = zIndex,
};

export default{
  state,
  getters,
  actions,
  mutations,
};