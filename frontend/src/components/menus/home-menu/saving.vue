<template>
  <div id="save-load">
    <div class="sub-elem save">
      <img src="../../../assets/saving/save.png" width="40">
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: "savingControl",
  data(){
    return{

    }
  },
  methods: {
    firePopUp(message){
      const popUpBackground = document.createElement('div');
      popUpBackground.className = 'pop-up-background';
      document.body.appendChild(popUpBackground);

      const popUp = document.createElement('div');
      popUp.className = 'pop-up';
      popUpBackground.appendChild(popUp);

      const msg = document.createElement('h2');
      msg.innerText = message;
      popUp.appendChild(msg);

      const fileInput = document.createElement('input');
      fileInput.type = "file";
      fileInput.webkitdirectory = true;
      fileInput.multiple = true;
      popUp.appendChild(fileInput);

      const closeBtn = document.createElement('span');
      closeBtn.innerText = 'OK';
      closeBtn.className = 'pop-up-close-button';
      closeBtn.onclick = () => {
      //   const path = fileInput.value;
      //   const folderPath = path.slice(0, path.lastIndexOf("\\")+1);
      //   const savingPath = folderPath + "first.xml";
      //   console.log(savingPath);
      //   axios.post('http://localhost:8085/save', null, 
      //   {params :{
      //     location: savingPath,
      //   }})
      // .catch( (error) => console.log(error));
        
        document.body.removeChild(popUpBackground); 
      }
      popUp.appendChild(closeBtn);
    },
    download(filename, text) {
      var element = document.createElement('a');
      element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
      element.setAttribute('download', filename);

      element.style.display = 'none';
      document.body.appendChild(element);

      element.click();

      document.body.removeChild(element);
    },
  },
}
</script>

<style>
.save-load {
  display: grid;
  grid-template-columns: 10px 60px 60px;
  grid-template-rows: 30px 30px 13px;
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

.save{
  grid-row: 1/3;
  grid-column: 3/4;
  padding: 10px 10px;
}

.pop-up-background {
    position: fixed;
    left: 0;
    top: 0;        
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    transform: scale(1.1);
    transition: visibility 0s linear 0.25s, opacity 0.25s 0s, transform 0.25s;
    z-index: 99999999999;
}
.pop-up {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 1rem 1rem;
    width: 24rem;
    color: rgb(12, 53, 71);
    border: 3px solid #4262ff;
}

.pop-up h2 {
    margin-bottom: auto;
}

.pop-up-close-button {
    float: right;
    width: 4rem;
    line-height: 2rem;
    text-align: center;
    font-family: Arial, Helvetica, sans-serif;
    font-size: medium;
    color: white;
    cursor: pointer;
    background-color: #4262ff;
    /* border-radius: 0.25rem; */
}
</style>