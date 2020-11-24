<template>
  <div id="font">
    <div class="sub-elem top-controls bold"><img src="../../../assets/fontandparagragh/bold.png" width="18"></div>
    <div class="sub-elem top-controls italic"><img src="../../../assets/fontandparagragh/italic.png" width="18"></div>
    <div class="sub-elem top-controls underline"><img src="../../../assets/fontandparagragh/underline.png" width="18"></div>
    <div class="sub-elem top-controls align-left"><img src="../../../assets/fontandparagragh/alignLeft.png" width="18"></div>
    <div class="sub-elem top-controls align-center"><img src="../../../assets/fontandparagragh/alignCenter.png" width="18"></div>
    <div class="sub-elem top-controls align-right"><img src="../../../assets/fontandparagragh/alignRight.png" width="18"></div>
    <div class="sub-elem top-controls justify"><img src="../../../assets/fontandparagragh/justify.png" width="18"></div>
    <div class="sub-elem top-controls text-color"><img src="../../../assets/fontandparagragh/textColor.svg" width="22"></div>
    <div class="sub-elem down-controls font-up"><img src="../../../assets/fontandparagragh/fontUp.png" width="20"></div>
    <div class="sub-elem down-controls font-down"><img src="../../../assets/fontandparagragh/fontDown.png" width="20"></div>
    <div class="label">Font & paragragh</div>
    <div class="seperator"></div>
  </div>
</template>

<script>
export default {
  name: 'font',
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
#font{
  display: grid;
  grid-template-columns: repeat(8, 30px) 10px;
  grid-template-rows: 30px 30px 13px;
  justify-items: center;
  align-items: center;
}

.sub-elem{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px 5px;
}
.sub-elem:hover{
  background-color: whitesmoke;
}

.top-controls{
  grid-row: 1/2;
  padding: 6px 6px;
}
.bold{
  grid-column: 1/2;
}
.italic{
  grid-column: 2/3;
}
.underline{
  grid-column: 3/4;
}
.align-left{
  grid-column: 4/5;
}
.align-center{
  grid-column: 5/6;
}
.align-right{
  grid-column: 6/7;
}
.justify{
  grid-column: 7/8;
}
.text-color{
  grid-column: 8/9;
  padding: 4px 4px;
}

.down-controls{
  grid-row: 2/3;
}
.font-up{
  grid-column: 7/8;
}
.font-down{
  grid-column: 8/9;
}

.label{
  text-align: center;
  font-size: 10px;
  color: darkgrey;
  grid-column: 1/9;
  grid-row: 3/4;
}

.seperator{
  height: 70px;
  grid-column: 9/10;
  grid-row: 1/4;
  border-left: thin solid darkgray;
}
</style>