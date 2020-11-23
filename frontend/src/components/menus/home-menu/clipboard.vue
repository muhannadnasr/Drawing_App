<template>
  <div id="clipboard">
    <div class="sub-elem paste"><img src="../../../assets/editingSec-Icons/paste.png" width="40"></div>
    <div class="sub-elem copy"><img src="../../../assets/editingSec-Icons/copy.png" width="23"></div>
    <div class="sub-elem cut"><img src="../../../assets/editingSec-Icons/cut.png" width="23"></div>
    <div class="label">Clipboard</div>
    <div class="seperator"></div>
  </div>
</template>

<script>
export default {
  name: 'clipboard',
  data() {
    return{
      
    }
  },
  methods: {

  },
  mounted(){

  },
  created() {

  }
}
</script>

<style scoped>
#clipboard{
  display: grid;
  grid-template-columns: 60px 30px 10px;
  grid-template-rows: 30px 30px 10px;
  justify-items: center;
  align-items: center;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 3.5px 3.5px;
}
.sub-elem:hover{
  background-color: whitesmoke;
}

.paste{
  grid-row: 1/3;
  grid-column: 1/2;
  padding: 10px 10px;
}

.copy{
  grid-row: 1/2;
  grid-column: 2/3;
}

.cut{
  grid-row: 2/3;
  grid-column: 2/3;
}

.seperator{
  height: 70px;
  grid-column: 3/4;
  grid-row: 1/4;
  border-left: thin solid darkgray;
}

.label{
  text-align: center;
  font-size: 10px;
  color: darkgrey;
  grid-column: 1/3;
  grid-row: 3/4;
}
</style>