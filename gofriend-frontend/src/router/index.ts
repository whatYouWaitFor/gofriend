import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  //history: createWebHistory(import.meta.env.BASE_URL),
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/team',
      name: '找队伍',
      component: () => import('@/views/TeamView.vue'),
    },
    {
      path: '/team/add',
      name: '创建队伍',
      component: () => import('@/views/TeamAddView.vue'),
    },
    {
      path: '/team/update',
      name: '修改队伍',
      component: () => import('@/views/TeamUpdateView.vue'),
    }, {
      path: '/search',
      name: '找伙伴',
      component: () => import('@/views/SearchView.vue'),
    }, {
      path: '/user',
      name: '个人信息',
      component: () => import('@/views/UserView.vue'),
    }, {
      path: '/user/list',
      name: '用户列表',
      component: () => import('@/views/SearchResultView.vue'),
    }, {
      path: '/user/update',
      name: '更新信息',
      component: () => import('@/views/UserUpdateView.vue'),
    }, {
      path: '/user/edit',
      name: '编辑信息',
      component: () => import('@/views/UserEditView.vue'),
    }, {
      path: '/user/login',
      name: '登录',
      component: () => import('@/views/UserLoginView.vue'),
    }
  ],
})

export default router
