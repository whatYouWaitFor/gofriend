<template>
  <!-- <van-cell center title="心动模式">
    <template #right-icon>
      <van-switch v-model="isMatchMode" size="24" />
    </template>
</van-cell> -->
  <UserCardList :userList="userList" :loading="false" />
  <van-empty v-if="!userList || userList.length < 1" description="数据为空" />
</template>

<script setup lang="ts">
import myAxios from '@/plugins/myAxios';
import { showFailToast } from 'vant';
import { ref, onMounted, watchEffect } from 'vue'
import { UserType } from '@/models/user';
import UserCardList from '@/components/UserCardList.vue';


const userList = ref([]);
const loading = ref(true);

const loadDate = async () => {
  let userListData;
  loading.value = true;

  // 分页查询用户
  userListData = await myAxios.get('/user/recommend', {
    params: {
      pageSize: 8,
      pageNum: 1,
    }
  }).then(function (response) {
    return response?.data?.records;
  }).catch(function (error) {
    showFailToast('请求失败');
  })

  if (userListData) {
    userListData.forEach((user: UserType) => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    });
    userList.value = userListData;
  }

}

watchEffect(() => {
  loadDate();
})
</script>