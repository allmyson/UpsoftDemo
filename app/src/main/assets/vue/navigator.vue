<template>
    <div>
        <div class="wrapper">
            <text class="button">这是存放在Assets中的js bundle</text>
        </div>
        <div class="wrapper">
            <text class="button" @click="jumpUrl">跳转远程URL</text>
        </div>
        <div class="wrapper">
            <text class="button" @click="jumpFile">跳转SDCard URL</text>
        </div>

        <div class="wrapper">
            <text class="button" @click="jumpAsset">跳转Asset URL</text>
        </div>
        <!--<div class="wrapper">-->
            <!--<text class="button" @click="jumpNative">跳转原生界面</text>-->
        <!--</div>-->
    </div>
</template>
<script>
    var navigator = weex.requireModule('navigator')
    var modal = weex.requireModule('modal')
//    var app = weex.requireModule('app')
    export default {
        methods: {
            jumpUrl(event) {
                console.log('will jump url')
                navigator.push({
                    url: 'https://h5.m.taobao.com/trip/wxc-minibar/index.html?_wx_tpl=https%3A%2F%2Fh5.m.taobao.com%2Ftrip%2Fwxc-minibar%2Fdemo%2Findex.native-min.js',
                    animated: "true"
                }, event => {
                    modal.toast({message: 'callback: ' + event})
                })
            },
            jumpFile(event) {
                console.log('will jump file')
                navigator.push({
//                    url: "sdcard:/"+app.getFilePath() + "picker.js",
                    url: "sdcard:///storage/emulated/0/upsoft/UpsoftDemo/weex/RichText.js",
                    animated: "true"
                }, event => {
                    modal.toast({message: 'callback: ' + event})
                })
            },
            jumpAsset(event) {
                console.log('will jump asset')
                navigator.push({
//                    url: 'file://assets/list.js',
                    url: 'file://assets/js/image.js',
                    animated: "true"
                }, event => {
                    modal.toast({message: 'callback: ' + event})
                })
            },
//            jumpNative(event) {
//                console.log('will jump native')
//                app.intentToNative()
//            }
        }
    };
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
</style>