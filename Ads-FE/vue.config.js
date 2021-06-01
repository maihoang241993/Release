module.exports = {
  lintOnSave: false,
  runtimeCompiler: true,
  configureWebpack: {
    //Necessary to run npm link https://webpack.js.org/configuration/resolve/#resolve-symlinks
    resolve: {
       symlinks: false
    }
  },
  devServer: {
    port: 8082,
    proxy: 'http://localhost:8080'
  },
  // devServer: {
  //   proxy: 'http://localhost:8082'
  // },
  transpileDependencies: [
    '@coreui/utils',
    '@coreui/vue'
  ]
}
