<template>
  <main class="login-page">
    <section class="login-shell">
      <div class="brand-side">
        <div class="brand-mark">DR1</div>
        <h1>分布式管理系统</h1>
        <p>Spring Cloud Gateway、Nacos、MyBatis-Plus 与 Vue 3 的课程作业项目。</p>
      </div>

      <el-form ref="formRef" class="login-form" :model="form" :rules="rules" @submit.prevent>
        <h2>登录</h2>
        <el-form-item prop="username">
          <el-input v-model="form.username" size="large" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            size="large"
            type="password"
            show-password
            placeholder="密码"
            prefix-icon="Lock"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        <el-button class="login-button" type="primary" size="large" :loading="loading" @click="handleLogin">
          登录系统
        </el-button>
        <p class="login-tip">默认账号：admin / 123456</p>
      </el-form>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const formRef = ref()
const loading = ref(false)
const form = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) {
    return
  }
  loading.value = true
  try {
    await authStore.login(form)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  display: grid;
  min-height: 100vh;
  place-items: center;
  padding: 28px;
  background:
    linear-gradient(135deg, rgba(44, 124, 255, 0.12), rgba(32, 180, 134, 0.14)),
    #eef4fb;
}

.login-shell {
  display: grid;
  width: min(960px, 100%);
  min-height: 520px;
  grid-template-columns: 1.15fr 0.85fr;
  overflow: hidden;
  background: #ffffff;
  border: 1px solid #d7e0ee;
  border-radius: 8px;
  box-shadow: 0 22px 60px rgba(36, 54, 88, 0.15);
}

.brand-side {
  display: flex;
  justify-content: center;
  flex-direction: column;
  padding: 56px;
  color: #ffffff;
  background:
    linear-gradient(rgba(12, 31, 57, 0.76), rgba(12, 31, 57, 0.82)),
    url("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?auto=format&fit=crop&w=1200&q=80") center/cover;
}

.brand-mark {
  width: 72px;
  height: 72px;
  display: grid;
  place-items: center;
  margin-bottom: 24px;
  border: 1px solid rgba(255, 255, 255, 0.55);
  border-radius: 8px;
  font-size: 24px;
  font-weight: 800;
}

.brand-side h1 {
  margin: 0 0 14px;
  font-size: 38px;
  line-height: 1.2;
  letter-spacing: 0;
}

.brand-side p {
  max-width: 420px;
  margin: 0;
  color: rgba(255, 255, 255, 0.82);
  font-size: 16px;
  line-height: 1.8;
}

.login-form {
  display: flex;
  justify-content: center;
  flex-direction: column;
  padding: 44px;
}

.login-form h2 {
  margin: 0 0 28px;
  font-size: 26px;
  letter-spacing: 0;
}

.login-button {
  width: 100%;
}

.login-tip {
  margin: 16px 0 0;
  color: #6a7488;
  text-align: center;
}

@media (max-width: 800px) {
  .login-shell {
    grid-template-columns: 1fr;
  }

  .brand-side {
    min-height: 260px;
    padding: 36px;
  }

  .login-form {
    padding: 32px;
  }
}
</style>

