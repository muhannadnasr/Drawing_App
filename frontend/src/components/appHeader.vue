<template>
  <div id="header">
      <div id="file-name">Drawing App</div>
  </div>
</template>

<script>
export default {
	name: 'appHeader',
	props: {
	},
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