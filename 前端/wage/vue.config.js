const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  lintOnSave: false,
  // publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
  publicPath: '/',
  // 以下为alias配置
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('assets', resolve('src/assets'))
      .set('components', resolve('src/components'))
      .set('common', resolve('src/common'))
      .set('network', resolve('src/network'))
      .set('views', resolve('src/views'))
  },
  // 由于浏览器有跨域限制,这里cli 工具提供了 启动本地代理服务器 请求
  devServer: {
    open: true,      // 是否打开浏览器;
    hotOnly: true,    // 是否热更新;

    proxy: {
      '/api': {   // 路径中有 /api 的请求都会走这个代理 , 可以自己定义一个,下面移除即可
        target: 'http://127.0.0.1:9001',    // 目标代理接口地址,实际跨域要访问的接口,这个地址会替换掉 axios.defaults.baseURL
        secure: false,
        changeOrigin: true,  // 开启代理，在本地创建一个虚拟服务端
        ws: true,       // 是否启用  websockets;
        pathRewrite: {   // 去掉 路径中的  /api  的这一截
          '^/api': ''
        }
      }
    },

  }
}