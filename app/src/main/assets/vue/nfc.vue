<template>
    <div class="wrapper">
        <div class="wrapper">
            <text class="button" @click="scanNfc">打开NFC</text>
        </div>
        <div class="panel">
            <text class="text" lines="3">{{scanResult}}</text>
        </div>
    </div>
</template>

<script>
    var modal = weex.requireModule('modal')
    var upsoft = weex.requireModule('upsoft')
    export default {
        data() {
            return {
                scanResult: '扫描结果：'
            }
        },

        methods: {
            scanNfc() {
                return upsoft.intentToNFC(data => {
                    modal.toast({message: 'NFC扫描结果： ' + data.data});
                    this.scanResult = 'NFC扫描结果：'+data.data;
                })
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

    .text {
        lines: 3;
        color: #666666;
        font-size: 32px;
    }

    .panel {
        width: 600px;
        margin-left: 135px;
        padding-top: 15px;
        padding-bottom: 15px;
        padding-left: 15px;
        padding-right: 15px;
        margin-bottom: 30px;
    }
</style>