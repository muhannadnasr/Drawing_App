<template>
  <div id="delete-sec">
      <div class="sub-elem">
        <img src="../../../assets/editingSec-Icons/delete.png" width="30">
      </div>
      <div class="label">Delete</div>
      <div class="seperator"></div>
  </div>
</template>

<script>
export default {
  name: 'deleteBtn',
  data(){
    return{

    }
  },
  methods: {

  },
}
</script>

<style scoped> 
#delete-sec{
  display: grid;
  grid-template-columns: 50px 10px;
  grid-template-rows: 50px 10px 13px;
  justify-items: center;
  align-items: center;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 10px;

  grid-row: 1/2;
  grid-column: 1/2;
}

.label{
  text-align: center;
  font-size: 10px;
  color: darkgrey;
  grid-column: 1/2;
  grid-row: 3/4;
}

.seperator{
  height: 70px;
  grid-column: 2/3;
  grid-row: 1/4;
  border-left: thin solid darkgray;
}
</style>