import { defineConfig } from 'vite'; import react from '@vitejs/plugin-react';
export default defineConfig({plugins:[react()],server:{host:'0.0.0.0',proxy:{'/api':'http://localhost:8080','/v3':'http://localhost:8080','/swagger-ui':'http://localhost:8080'}},test:{environment:'jsdom',setupFiles:'./src/test/setup.ts',exclude:['e2e/**','node_modules/**']}});
