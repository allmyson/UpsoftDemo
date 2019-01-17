<template>
    <div class="wrapper">
        <div class="panel">
            <text class="text" lines="3">这是来自Assets的文件</text>
        </div>
    </div>
</template>
<style scoped>
    .wrapper {
        flex-direction: column;
        justify-content: center;
    }
    .panel {
        width: 600px;
        margin-left: 75px;
        border-width: 2px;
        border-style: solid;
        border-color: #BBB;
        padding-top: 15px;
        padding-bottom: 15px;
        padding-left: 15px;
        padding-right: 15px;
        margin-bottom: 30px;
    }
    .text {
        lines: 3;
        color: #666666;
        font-size: 32px;
    }
</style>