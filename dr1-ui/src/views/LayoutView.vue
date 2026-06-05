<template>
  <el-container class="layout">
    <el-aside class="layout-aside" width="232px">
      <div class="logo">
        <span>DR1</span>
        <strong>分布式系统</strong>
      </div>
      <el-menu :default-active="activePath" router>
        <el-menu-item index="/dashboard">
          <el-icon><DataLine /></el-icon>
          <span>控制台</span>
        </el-menu-item>
        <el-menu-item index="/users">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="layout-header">
        <div>
          <h1>{{ route.meta.title || '控制台' }}</h1>
          <span>Gateway / Auth / System 服务已拆分</span>
        </div>
        <el-dropdown @command="handleCommand">
          <el-button plain>
            <el-icon><Avatar /></el-icon>
            {{ authStore.user?.nickname || '管理员' }}
            <el-icon><ArrowDown /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main class="layout-main">
        <RouterView />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const activePath = computed(() => route.path)

function handleCommand(command) {
  if (command === 'logout') {
    authStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.layout-aside {
  background: #ffffff;
  border-right: 1px solid #dbe3ef;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  height: 64px;
  padding: 0 18px;
  border-bottom: 1px solid #dbe3ef;
}

.logo span {
  display: grid;
  width: 40px;
  height: 40px;
  place-items: center;
  color: #ffffff;
  background: #2369e8;
  border-radius: 8px;
  font-weight: 800;
}

.logo strong {
  font-size: 16px;
}

.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  background: #ffffff;
  border-bottom: 1px solid #dbe3ef;
}

.layout-header h1 {
  margin: 0;
  font-size: 18px;
  letter-spacing: 0;
}

.layout-header span {
  color: #6a7488;
  font-size: 13px;
}

.layout-main {
  padding: 22px;
}

:deep(.el-menu) {
  border-right: 0;
}

@media (max-width: 760px) {
  .layout {
    display: block;
  }

  .layout-aside {
    width: 100% !important;
  }

  .layout-header {
    height: auto;
    gap: 12px;
    align-items: flex-start;
    flex-direction: column;
    padding: 14px;
  }

  .layout-main {
    padding: 14px;
  }
}
</style>

