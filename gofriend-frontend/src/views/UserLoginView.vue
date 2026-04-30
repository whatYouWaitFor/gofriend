<template>
    <van-form @submit="onSubmit">
        <van-cell-group inset>
            <van-field v-model="userAccount" name="userAccount" label="账号" placeholder="请输入账号"
                :rule="[{ required: true, message: '请填写用户名' }]" />

            <van-field v-model="userPassword" type="password" name="userPassword" label="密码" placeholder="请输入密码"
                :rule="[{ required: true, message: '请填写密码' }]" />
        </van-cell-group>
        <div style="margin: 16px">
            <van-button round block type="primary" native-type="submit">登录</van-button>
        </div>
    </van-form>
</template>
<script lang="ts" setup>
import myAxios from '@/plugins/myAxios';
import { showFailToast, showSuccessToast, showToast } from 'vant';
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
const userAccount = ref('');
const userPassword = ref('');

const onSubmit = async () => {
    //showSuccessToast('登录成功');
    //showFailToast('登录成功');
    const res = await myAxios.post('/user/login', {
        userAccount: userAccount.value,
        userPassword: userPassword.value,
    })
    if (res.code === 0 && res.data) {
        //showToast('登录成功');
        showSuccessToast('登录成功');
        // 跳转到之前的页面
        const redirectUrl = route.query?.redirect as string ?? '/';
        window.location.href = redirectUrl;
    } else {
        showFailToast('登录失败');
    }
}

</script>