<template>
  <section>
    <div class="summary-grid">
      <div class="summary-item">
        <span>后端服务</span>
        <strong>3</strong>
        <p>Gateway / Auth / System</p>
      </div>
      <div class="summary-item">
        <span>ORM 框架</span>
        <strong>MP</strong>
        <p>MyBatis-Plus 已集成</p>
      </div>
      <div class="summary-item">
        <span>组件库</span>
        <strong>EP</strong>
        <p>Element Plus 已集成</p>
      </div>
      <div class="summary-item">
        <span>代码生成器</span>
        <strong>OK</strong>
        <p>Generator 模块可运行</p>
      </div>
    </div>

    <div class="content-panel dashboard-panel">
      <div>
        <h2>服务调用链路</h2>
        <p class="muted">前端请求通过 Vite 代理进入网关，再由网关转发到认证服务和系统服务。</p>
      </div>
      <el-steps :active="4" finish-status="success" simple>
        <el-step title="Vue 3" />
        <el-step title="Gateway" />
        <el-step title="System/Auth" />
        <el-step title="MyBatis-Plus" />
      </el-steps>
    </div>
  </section>
</template>

<style scoped>
.summary-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
  margin-bottom: 18px;
}

.summary-item {
  padding: 18px;
  background: #ffffff;
  border: 1px solid #dbe3ef;
  border-radius: 8px;
  box-shadow: 0 10px 26px rgba(41, 63, 98, 0.08);
}

.summary-item span {
  color: #6a7488;
  font-size: 13px;
}

.summary-item strong {
  display: block;
  margin: 8px 0;
  color: #2369e8;
  font-size: 32px;
  line-height: 1;
}

.summary-item p {
  margin: 0;
  color: #546078;
}

.dashboard-panel {
  display: grid;
  gap: 24px;
}

.dashboard-panel h2 {
  margin: 0 0 8px;
  font-size: 18px;
  letter-spacing: 0;
}

.dashboard-panel p {
  margin: 0;
}

@media (max-width: 980px) {
  .summary-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 560px) {
  .summary-grid {
    grid-template-columns: 1fr;
  }
}
</style>

