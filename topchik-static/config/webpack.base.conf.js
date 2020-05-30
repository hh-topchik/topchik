const path = require("path");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const PATHS = {
  src: path.join(__dirname, "./../src"),
  dist: path.join(__dirname, "./../build")
};

module.exports = {
  externals: {
    paths: PATHS
  },
  entry: ["@babel/polyfill", PATHS.src],
  output: {
    filename: "[name].js",
    path: PATHS.dist,
    publicPath: "/"
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: ["babel-loader"]
      },
      {
        test: /\.less$/,
        exclude: /(node_modules)/,
        use: [
          "style-loader",
          MiniCssExtractPlugin.loader,
          {
            loader: "css-loader",
            options: { sourceMap: true }
          },
          {
            loader: "postcss-loader",
            options: { sourceMap: true, config: {path: './config/postcss.config.js'} }
          },
          {
            loader: "less-loader",
            options: { sourceMap: true }
          }
        ]
      },
      {
        test: /\.(png|jpg|svg)$/,
        use: [
          {
            loader: "file-loader",
            options: {
              name: "[name].[ext]",
              esModule: false
            }
          },
        ]
      }
    ]
  },
  plugins: [
    new MiniCssExtractPlugin(),
    new HtmlWebpackPlugin({
      template: "./src/index.html"
    })
  ]
};
