<template>
  <section class="content-panel">
    <div class="toolbar">
      <div>
        <h2 class="page-title">用户管理</h2>
        <p class="muted">基于系统服务的 MyBatis-Plus CRUD 示例</p>
      </div>
      <el-button type="primary" @click="openCreate">
        <el-icon><Plus /></el-icon>
        新增用户
      </el-button>
    </div>

    <el-table :data="users" :loading="loading" row-key="id">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" min-width="120" />
      <el-table-column prop="nickname" label="昵称" min-width="120" />
      <el-table-column prop="email" label="邮箱" min-width="180" />
      <el-table-column prop="phone" label="手机号" min-width="140" />
      <el-table-column label="状态" width="110">
        <template #default="{ row }">
          <span class="status-dot" :class="{ 'is-off': row.status !== 1 }"></span>
          {{ row.status === 1 ? '启用' : '停用' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pager">
      <el-pagination
        v-model:current-page="page.current"
        v-model:page-size="page.size"
        layout="total, prev, pager, next"
        :total="page.total"
        @current-change="loadUsers"
      />
    </div>

    <el-dialog v-model="dialogVisible" :title="editingUser ? '编辑用户' : '新增用户'" width="520px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="82px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { createUser, deleteUser, getUserPage, updateUser } from '../api/users'

const loading = ref(false)
const saving = ref(false)
const users = ref([])
const dialogVisible = ref(false)
const editingUser = ref(null)
const formRef = ref()
const page = reactive({
  current: 1,
  size: 10,
  total: 0
})

const form = reactive({
  username: '',
  nickname: '',
  email: '',
  phone: '',
  status: 1
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确邮箱', trigger: 'blur' }]
}

function resetForm(row = null) {
  form.username = row?.username || ''
  form.nickname = row?.nickname || ''
  form.email = row?.email || ''
  form.phone = row?.phone || ''
  form.status = row?.status ?? 1
}

async function loadUsers() {
  loading.value = true
  try {
    const result = await getUserPage({ current: page.current, size: page.size })
    users.value = result.records
    page.total = result.total
  } finally {
    loading.value = false
  }
}

function openCreate() {
  editingUser.value = null
  resetForm()
  dialogVisible.value = true
}

function openEdit(row) {
  editingUser.value = row
  resetForm(row)
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) {
    return
  }
  saving.value = true
  try {
    if (editingUser.value) {
      await updateUser(editingUser.value.id, form)
      ElMessage.success('用户已更新')
    } else {
      await createUser(form)
      ElMessage.success('用户已新增')
    }
    dialogVisible.value = false
    await loadUsers()
  } finally {
    saving.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除用户 ${row.username}？`, '删除确认', {
    type: 'warning'
  })
  await deleteUser(row.id)
  ElMessage.success('用户已删除')
  await loadUsers()
}

onMounted(loadUsers)
</script>

<style scoped>
.toolbar p {
  margin: 8px 0 0;
}

.pager {
  display: flex;
  justify-content: flex-end;
  margin-top: 18px;
}

@media (max-width: 720px) {
  .pager {
    justify-content: flex-start;
  }
}
</style>

