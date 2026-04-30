import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      // 本地请求 /api/xxx 时，自动代理到后端
      '/api': {
        target: 'http://localhost:8080', // 比如 http://192.168.1.100:8080
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '/api') // 路径要不要改看你后端
      }
    }
  }
})
