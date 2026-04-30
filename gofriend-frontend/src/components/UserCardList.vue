<template>
    <van-skeleton title :row="3" :loading="props.loading" v-for="user in props.userList">
        <van-card :title="`${user.username}（${user.planetCode}）`" :desc="user.profile" :thumb="user.avatarUrl">
            <template #tags>
                <van-tag plain type="danger" v-for="tag in user.tags" :key="tag"
                    style="margin-right: 8px; margin-top: 8px">
                    {{ tag }}
                </van-tag>
            </template>
            <template #footer>
                <van-button size="mini">联系我</van-button>
            </template>
        </van-card>
    </van-skeleton>
</template>

<script setup lang="ts">
import { UserType } from "../models/user";

interface UserCardListProps {
    userList: UserType[];
    loading: boolean;
}

const props = withDefaults(defineProps<UserCardListProps>(), {
    // @ts-ignore
    userList: [] as UserType[],
    loading: true,
});
</script>