<template>
    <div id="teamPage">
        <van-search v-model="searchText" placeholder="搜索队伍" @search="onSearch" />
        <van-tabs v-model:active="active" @change="onTabChange">
            <van-tab title="公开" name="public" />
            <van-tab title="加密" name="private" />
        </van-tabs>
        <div style="margin-bottom:16px" />
        <van-button class="add-button" type="primary" icon="plus" @click="toAddTeam" />
        <TeamCardList :teamList="teamList" />
        <van-empty v-if="teamList?.length < 1" description="暂无队伍" />
    </div>

</template>

<script setup lang="ts">
import TeamCardList from '@/components/TeamCardList.vue';
import myAxios from '@/plugins/myAxios';

import { showFailToast } from 'vant';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const active = ref('public');
const searchText = ref('');
const router = useRouter();

/**
 * 切换查询状态
 * @param name 
 */
const onTabChange = (name: string) => {
    //公开
    if (name === 'public') {
        getTeamList(searchText.value, 0);
    } else {
        getTeamList(searchText.value, 2);
    }

}

// 跳转到创建队伍页面
const toAddTeam = () => {
    router.push({
        path: '/team/add',
    })
}

const teamList = ref([]);

/**
 * 搜索队伍
 * @param val 
 * @param status 
 */
const getTeamList = async (val = '', status = 0) => {
    const res = await myAxios.get('/team/list', {
        params: {
            searchText: val,
            pageNum: 1,
            status,
        }
    });
    if (res?.code === 0) {
        teamList.value = res?.data;
    } else {
        showFailToast('加载队伍失败，请刷新重试');
    }
}

// 页面加载时只触发一次
onMounted(() => {
    // 获取队伍列表
    getTeamList();
});

const onSearch = (val: string) => {
    getTeamList(val);
}

</script>