const webpack = require('webpack');
const merge = require('webpack-merge');
const baseWebpackConfig = require('./webpack.base.conf');
const devWebpackConfig = merge(baseWebpackConfig, {
    mode: "development",
    devtool: 'cheap-eval-source-map',
    devServer: {
        contentBase: baseWebpackConfig.externals.paths.dist,
        port: 8081,
        overlay: true,
        disableHostCheck: true,
        historyApiFallback: true,
        host: '0.0.0.0',
    },
    plugins: [
        new webpack.SourceMapDevToolPlugin({
            filename: '[file].map'
        })    
    ]       
});

module.exports = new Promise((resolve, reject) => {
    resolve(devWebpackConfig)
});
