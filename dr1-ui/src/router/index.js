import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import LoginView from '../views/LoginView.vue'
import LayoutView from '../views/LayoutView.vue'
import DashboardView from '../views/DashboardView.vue'
import UserView from '../views/UserView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/',
      component: LayoutView,
      redirect: '/dashboard',
      children: [
        {
          path: 'dashboard',
          name: 'dashboard',
          component: DashboardView,
          meta: { title: '控制台' }
        },
        {
          path: 'users',
          name: 'users',
          component: UserView,
          meta: { title: '用户管理' }
        }
      ]
    }
  ]
})

router.beforeEach(to => {
  const authStore = useAuthStore()
  if (to.name !== 'login' && !authStore.token) {
    return { name: 'login' }
  }
  if (to.name === 'login' && authStore.token) {
    return { name: 'dashboard' }
  }
  return true
})

export default router

