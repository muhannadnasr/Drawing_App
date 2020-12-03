<template>
  <div id="header">
      <div id="file-name">{{getFileName()}}</div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
	name: 'appHeader',
	props: {
	},
	computed: mapGetters(['fileName']),
	methods:{
		getFileName(){
			if(this.fileName === "") return "Drawing App";
			return this.fileName;
		}
	}
}
</script>

<style>
#header{
	display: flex;
	justify-content: center;
	align-items: center;
	background-color: #4262ff;
	font-weight: normal;
	color: white;
	height: 30px;
	user-select: none;
}

</style>