<template>
    <div class="wrapper">
        <div class="wrapper">
            <text class="button" @click="choosePic">选择图片</text>
        </div>
        <div class="wrapper">
            <image :src="path" alt="" id="img" style="width: 800px;height: 800px;" resize="contain"></image>
        </div>
    </div>
</template>

<script>
    //    import test from '../static/js/test'
    var modal = weex.requireModule('modal')
    var stream = weex.requireModule('stream')
    var upsoft = weex.requireModule('upsoft')
    export default {
        data() {
            return {
                weexStar: 'unknown',
                vueStar: 'unknown',
                postValue: 'unknown',
                path: 'file:///storage/emulated/0/upsoftsdk/weex/ic_weex.png',
                imageList: []
            }
        },

        methods: {
            getStarCount(repo, callback) {
                return stream.fetch({
                    method: 'GET',
                    type: 'json',
                    url: 'https://api.github.com/repos/' + repo
                }, callback)
            },
            getUserInfo(callback) {
                return stream.fetch({
                    method: 'POST',
                    type: 'json',
                    url: 'http://172.16.1.59/cgi/bp/usermgt/user/getCurrent'
                }, callback)
            },
            choosePic(event) {
                return upsoft.choosePic({
                    max: 1,
                    mode: 2,
                    showCamera: 'true',
                    enablePreview: 'true',
                    enableCrop: 'true'
                }, event => {
                    if (event.status == 1) {
                        modal.toast({message: '选择照片回调执行 ' + event.msg})
                        this.path = 'file://' + event.data[0];
                    } else if (event.status == 0) {
                        modal.toast({message: '选择照片回调执行 ' + event.msg})
                    }
                });
            }
        },
        mounted() {
//            modal.toast({message: 'mounted执行 '})
//            loadScript('http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js',function () {
//                modal.toast({message: 'md5.js加载完成 '})
//                alert('md5.js加载完成')
//            })
        },
//        created() {
//            this.getStarCount('alibaba/weex', res => {
//                this.weexStar = res.ok ? res.data.stargazers_count : '(network error)'
//            })
//            this.getStarCount('vuejs/vue', res => {
//                this.vueStar = res.ok ? res.data.stargazers_count : '(network error)'
//            })
//            this.getUserInfo(res => {
//                this.postValue = res.ok ? res.data.data.name : '(network error)';
//            })
//        }
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