var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var CopyWebpackPlugin = require('copy-webpack-plugin');

var deleteBundlesRootPath = path.resolve(__dirname,
		//'../src/main/webapp/static/');
		'../src/resources/webapp/static/');
//var bundlesSrcPath = path.resolve(__dirname, '../src/main/webapp/static/dist');
var bundlesSrcPath = path.resolve(__dirname, '../src/main/resources/static/dist');
var templateSrcServerPath = path.resolve(__dirname,
		//'../src/main/webapp/templates/');
		'../src/main/resources/templates/');
const webpack = require('webpack');
module.exports = {
	entry : './src/app.js',
	devtool : 'inline-source-map',
	devServer : {
		contentBase : 'src/',
		port : 8091,
		// Send API requests on localhost to API server get around CORS.
//		proxy : {
//			'/**' : {
//				target : {
//					host : "localhost",
//					protocol : 'http:',
//					port : 8080
//				}
//			}
//		},
		hot : true,
		inline : true
	},
	plugins : [ new CopyWebpackPlugin([ {
		from : 'src/index.html',
		to : templateSrcServerPath
	} ]), new webpack.HotModuleReplacementPlugin() ],
	output : {
		path : bundlesSrcPath,
		filename : 'bundle.js',
		publicPath : '/dist/'
	},
	module : {
		loaders : [ {
			test : path.join(__dirname, '.'),
			exclude : /node_modules/,
			loader : 'babel-loader',
			query : {
				cacheDirectory : true,
				presets : [ 'es2015', 'react' ]
			}
		}, {
			test : /\.css$/,
			use : [ 'style-loader', 'css-loader' ]
		}

		]
	}
};