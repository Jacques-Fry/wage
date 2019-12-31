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
  }
}