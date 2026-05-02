<template>
    <van-nav-bar :title="title" left-text="返回" left-arrow @click-left="onClickLeft" @click-right="onClickRight">
        <template #right>
            <van-icon name="search" size="18" />
        </template>
    </van-nav-bar>
    <div id="content">
        <router-view />
    </div>

    <van-tabbar route v-model="active">
        <van-tabbar-item replace to="/" icon="home-o" name="index">主页</van-tabbar-item>
        <van-tabbar-item replace to="/team" icon="search" name="team">队伍</van-tabbar-item>
        <van-tabbar-item replace to="/user" icon="setting-o" name="user">个人</van-tabbar-item>
    </van-tabbar>

</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter();

const title = ref('伙伴匹配');

watch(() => router.currentRoute.value.name, (newVal) => {
    title.value = String(newVal || '伙伴匹配');
});

const onClickLeft = () => history.back();

const onClickRight = () => {
    router.push('/search')
}

const active = ref('/');

</script>

<style scoped>
#content {
    padding-bottom: 50px;
}
</style>