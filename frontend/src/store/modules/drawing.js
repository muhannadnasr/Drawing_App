const state = {
  drawingMode: false,
  shapeType: null,
  shapesDrawn: [],
};

const getters = {
  isDrawing: state => state.drawingMode,
  shapeType: state => state.shapeType,
  shapesDrawn: state => state.shapesDrawn,
};

const actions = {
  enableDrawingMode: ({commit}) => commit('setDrawingMode', true),
  disableDrawingMode: ({commit}) => commit('setDrawingMode', false),
  controlShapeType: ({commit}, shapeType) => commit('setShapeType', shapeType),
  pushNewShape: ({commit}, shape) => commit('pushShapeDrawn', shape),
  clearShapesDrawn: ({commit}) => commit('clearShapes'),

}

const mutations = {
  setDrawingMode: (state, drawingMode) => state.drawingMode = drawingMode,
  setShapeType: (state, shapeType) => state.shapeType = shapeType,
  pushShapeDrawn: (state, shape) => state.shapesDrawn.push(shape),
  clearShapes : (state) => state.shapesDrawn.length = 0,
};

export default{
  state,
  getters,
  actions,
  mutations,
};