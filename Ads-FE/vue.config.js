module.exports = {
  lintOnSave: false,
  runtimeCompiler: true,
  configureWebpack: {
    // devtool: 'source-map',
    devtool: 'eval',
    //Necessary to run npm link https://webpack.js.org/configuration/resolve/#resolve-symlinks
    resolve: {
       symlinks: false
    }
  },
  devServer: {
    port: 8082,
    proxy: 'http://localhost:8080'
  },
  transpileDependencies: [
    '@coreui/utils',
    '@coreui/vue'
  ]
}
