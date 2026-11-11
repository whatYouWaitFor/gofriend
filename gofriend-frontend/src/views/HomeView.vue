<template>
  <van-cell center title="心动模式">
    <template #right-icon>
      <van-switch v-model="isMatchMode" size="24" />
    </template>
  </van-cell>
  <van-pull-refresh v-model="loading" @refresh="onRefresh">
    <UserCardList :userList="userList" :loading="loading" />
    <van-empty v-if="!userList || userList.length < 1" description="数据为空1" />
  </van-pull-refresh>  

</template>

<script setup lang="ts">
import myAxios from '@/plugins/myAxios';
import { showFailToast } from 'vant';
import { ref, onMounted, watchEffect } from 'vue'
import { type UserType } from '@/models/user';
import UserCardList from '@/components/UserCardList.vue';

const isMatchMode = ref<boolean>(false);
const userList = ref([]);
const loading = ref(true);

const loadDate = async () => {
  let userListData;
  loading.value = true;

  if (isMatchMode.value) {
    // 心动模式
    const num = 10;
    userListData = await myAxios.get('/user/match', {
      params: {
        num,
      }
    }).then(function (response) {
      return response?.data;
    }).catch(function (error) {
      showFailToast('请求失败');
    })
  } else {
    // 普通模式
    // 分页查询用户
    userListData = await myAxios.get('/user/recommend', {
      params: {
        pageSize: 8 * count.value,
        pageNum: 1,
      }
    }).then(function (response) {
      return response?.data?.records;
    }).catch(function (error) {
      showFailToast('请求失败');
    })
  }

  if (userListData) {
    userListData.forEach((user: UserType) => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    });
    userList.value = userListData;
  }
  loading.value = false;
}

const count = ref(1);
const onRefresh = () => {
   setTimeout(() => {
        //showToast('刷新成功');
        loading.value = false;
        count.value++;
        loadDate();
      }, 1000);
  
}

watchEffect(() => {
  loadDate();
})
</script>