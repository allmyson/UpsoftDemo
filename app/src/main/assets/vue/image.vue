<template>
  <div>
    <scroller class="scroller" append="tree" offset-accuracy="10" @scroll="scrollHandler">
      <div>
        <text style='font-family:iconfont4;font-size:50px;color:green'>加载网络图片(https)</text>
        <image class="image" src="https://img.alicdn.com/tps/TB1z.55OFXXXXcLXXXXXXXXXXXX-560-560.jpg"></image>
        <text style='font-family:iconfont4;font-size:50px;color:green'>加载网络图片(http)</text>
        <image class="image" src="http://ov62vgsq0.bkt.clouddn.com/15037343027147299"></image>
        <text style='font-family:iconfont4;font-size:50px;color:green'>加载drawable图片</text>
        <image class="image" src="drawable://ic_drawable"></image>
        <text style='font-family:iconfont4;font-size:50px;color:green'>加载mipmap图片</text>
        <image class="image" src="mipmap://ic_mipmap"></image>
        <text style='font-family:iconfont4;font-size:50px;color:green'>加载assets图片</text>
        <image class="image" src="assets://img/ic_assets.png"></image>
        <text style='font-family:iconfont4;font-size:50px;color:green'>加载sdcard图片</text>
        <image class="image" src="file:///storage/emulated/0/upsoft/UpsoftDemo/weex/ic_weex.png"></image>
    </div>
    </scroller>
  </div>
</template>

<script>

</script>

<style scoped>
  .body {
    background-color: #ffffff;
  }
  .image {
    width:600px;
    background-color:#FFFFDF;
    height:400px;
  }
  .scroller {
    width: 750px;
    flex:1;
  }

</style>

