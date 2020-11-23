<template>
  <div id="menu-nav">
    <div id="menu-btn-bar">
      <span @click="makeActive(fileTab)"
            @mouseover="controlHoverEffect(fileTab)" 
            @mouseleave="controlHoverEffect(fileTab)"
            class="menu-btn" id="file-tab">File</span>
      <span @click="makeActive(homeTab)"
            @mouseover="controlHoverEffect(homeTab)" 
            @mouseleave="controlHoverEffect(homeTab)"
            class="menu-btn-active" id="home-tab">Home</span>
      <span @click="makeActive(boardTab)"
            @mouseover="controlHoverEffect(boardTab)" 
            @mouseleave="controlHoverEffect(boardTab)"
            class="menu-btn" id="board-tab">Board</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'otherimp',
  props: {
  },
  data (){
    return{
      fileTab: new this.Tab('file'),
      homeTab: new this.Tab('home', true),
      boardTab: new this.Tab('board'),
      tabs: [],
      prevActive: null,
    }
  },
  methods: {
    //navigation fuctions
    makeActive(currentTab){
      this.prevActive.active = false;
      const prevActiveTabId = this.getTabId(this.prevActive.name)
      document.getElementById(prevActiveTabId).className = "menu-btn";

      currentTab.active = true;
      const currentTabId = this.getTabId(currentTab.name);
      document.getElementById(currentTabId).className = "menu-btn-active-hover";
      this.prevActive = currentTab;
    },
    controlHoverEffect(tab){
      tab.hover = !tab.hover;
      const tabId = this.getTabId(tab.name);
      const tabElement = document.getElementById(tabId);
      if (tab.active && tab.hover) tabElement.className = 'menu-btn-active-hover';
      else if (tab.active && !tab.hover) tabElement.className = 'menu-btn-active';
      else if (!tab.active && tab.hover) tabElement.className = 'menu-btn-hover';
      else tabElement.className = 'menu-btn';
    },
    //helper
    getTabId(tabName){
      return `${tabName}-tab`;
    },
    Tab(name, active=false){
      return {
        "name": name,
        "hover": false,
        "active": active,
      }
    }
  },
  created(){
    this.tabs = [this.fileTab, this.homeTab, this.boardTab];
    this.prevActive = this.homeTab;
  }
}
</script>

<style scoped>
#menu-btn-bar{
  position: relative;
  background-color: white;
  color: black;
  float: left;
  overflow: hidden;
  user-select: none;
  font-size: 14px;
  text-align: center;
  text-decoration: none;
  font-weight: normal;
}

.menu-btn{
  float: left;
  padding: 5px 15px;
  transition: 0.2s ease;
}

.menu-btn-hover{
  float: left;
  background-color: whitesmoke;
  border-bottom: 3px solid whitesmoke;
  padding: 5px 15px;
  font-weight: 800;
}

.menu-btn-active{
  float: left;
  padding: 5px 0px;
  margin-left: 15px;
  margin-right: 15px;
  border-bottom: 3px solid #4262ff;
  transition: 0.2s ease;
  font-weight: 800;
}

.menu-btn-active-hover{
  float: left;
  color: black;
  padding: 5px 15px;
  border-bottom: 3px solid #4262ff;
  transition: 0.2s ease;
  font-weight: 800;
}

</style>
