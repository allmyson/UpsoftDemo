<template>
    <div class="wrapper">
        <div class="group">
            <text class="title">Weex Star :</text>
            <text class="count">{{weexStar}}</text>
        </div>
        <div class="group">
            <text class="title">Vue Star :</text>
            <text class="count">{{vueStar}}</text>
        </div>

        <div class="group">
            <text class="title">Post :</text>
            <text class="count">{{postValue}}</text>
        </div>
    </div>
</template>

<script>
    //    import test from '../static/js/test'
    var modal = weex.requireModule('modal')
    var stream = weex.requireModule('stream')
    export default {
        data() {
            return {
                weexStar: 'unknown',
                vueStar: 'unknown',
                postValue: 'unknown'
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
            }
        },
        mounted() {
            modal.toast({message: 'mounted执行 '})
//            loadScript('http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js',function () {
//                modal.toast({message: 'md5.js加载完成 '})
//                alert('md5.js加载完成')
//            })
        },
        created() {
            this.getStarCount('alibaba/weex', res => {
                this.weexStar = res.ok ? res.data.stargazers_count : '(network error)'
            })
            this.getStarCount('vuejs/vue', res => {
                this.vueStar = res.ok ? res.data.stargazers_count : '(network error)'
            })
            this.getUserInfo(res => {
                this.postValue = res.ok ? res.data.data.name : '(network error)';
            })
        }
    }
</script>


<style scoped>
    .wrapper {
        flex-direction: column;
        justify-content: center;
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