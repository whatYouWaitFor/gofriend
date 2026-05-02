<template>
    <UserCardList :userList="userList" :loading="false" />
    <van-empty v-if="!userList || userList.length < 1" description="搜索结果为空" />
</template>

<script setup lang="ts">
import { showToast } from 'vant';
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router';
import myAxios from '@/plugins/myAxios';
//import qs from 'qs';
import UserCardList from '@/components/UserCardList.vue';
import { type UserType } from '@/models/user';

const userList = ref([]);

const route = useRoute();
const { tags } = route.query;


onMounted(async () => {
    const userListDate = await myAxios.get('/user/search/tags', {
        params: {
            tagNameList: tags
        }
    }).then(function (response) {
        return response?.data;
    }).catch(function (error) {
        console.log(error);
        showToast('请求失败');
    });

    if (userListDate) {
        userListDate.forEach((user: UserType) => {
            if (user.tags) {
                user.tags = JSON.parse(user.tags);
            }
            userList.value = userListDate;
        });
        //userList.value = userListDate;
    }
});

// const mockUser = {
//   id: 12345,
//   username: '鱼皮',
//   userAccount: '12314',
//   profile: '一名精神小伙，目前还有头发，谢谢大家，阿爸爸阿爸爸阿巴阿巴阿巴',
//   avatarUrl: 'https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png',
//   gender: 0,
//   phone: '13113113111',
//   email: '592342843721987@xzcxzczxcz.com',
//   userRole: 0,
//   planetCode: '1234',
//   tags: ['java', 'emo', '打工中', 'emo', '打工中'],
//   createTime: new Date(),
// }
</script>