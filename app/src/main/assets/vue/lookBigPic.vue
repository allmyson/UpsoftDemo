<template>
    <div class="wrapper">
        <div class="wrapper">
            <text class="button" @click="lookBigPic">查看大图</text>
        </div>
        <div class="wrapper">
            <image :src="path" alt="" id="img" style="width: 800px;height: 800px;" resize="contain"></image>
        </div>
    </div>
</template>

<script>
    //    import test from '../static/js/test'
    var modal = weex.requireModule('modal')
    var upsoft = weex.requireModule('upsoft')
    export default {
        data() {
            return {
                weexStar: 'unknown',
                vueStar: 'unknown',
                postValue: 'unknown',
                path: 'file:///storage/emulated/0/upsoftsdk/weex/ic_weex.png',
                imageList: ['file:///storage/emulated/0/upsoftsdk/weex/ic_weex.png','http://192.168.0.67:8080/FirstWebProject/sl01.jpg','http://192.168.0.67:8080/FirstWebProject/sl02.jpg','http://192.168.0.67:8080/FirstWebProject/sl03.jpg']
            }
        },

        methods: {
            lookBigPic(event) {
//                return upsoft.lookBigPic({
//                    max: 1,
//                    mode: 2,
//                    showCamera: 'true',
//                    enablePreview: 'true',
//                    enableCrop: 'true'
//                });
                return upsoft.lookBigPic(this.imageList, 1);
            }
        },
    }
</script>


<style scoped>
    .wrapper {
        flex-direction: column;
        justify-content: center;
    }

    .button {
        font-size: 60px;
        width: 450px;
        text-align: center;
        margin-top: 30px;
        margin-left: 150px;
        padding-top: 20px;
        padding-bottom: 20px;
        border-width: 2px;
        border-style: solid;
        color: #666666;
        border-color: #DDDDDD;
        background-color: #F5F5F5
    }

    .group {
        flex-direction: row;
        justify-content: center;
        margin-bottom: 40px;
    }

    .title {
        font-size: 45px;
        color: #888888;
    }

    .count {
        font-size: 45px;
        font-weight: bold;
        margin-left: 12px;
        color: #41B883;
    }
</style>