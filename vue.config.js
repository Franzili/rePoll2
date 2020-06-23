// vue.config.js
module.exports = {


    rules: [
        {
            test: /\.s(c|a)ss$/,
            use: [
                'vue-style-loader',
                'css-loader',
                {
                    loader: 'sass-loader',
                    // Requires sass-loader@^7.0.0
                    options: {
                        implementation: require('sass'),
                        fiber: require('fibers'),
                        indentedSyntax: true // optional
                    },
                    // Requires sass-loader@^8.0.0
                    options: {
                        implementation: require('sass'),
                        sassOptions: {
                            fiber: require('fibers'),
                            indentedSyntax: true // optional
                        },
                    },
                },
            ],
        },
    ],





    /*     proxy all webpack dev-server requests starting with /api
         to our Spring Boot backend (localhost:8088) using http-proxy-middleware
         see https://cli.vuejs.org/config/#devserver-proxy*/
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8088', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
                ws: true,
                changeOrigin: true
            }
        },
    },
    configureWebpack: {
        devtool: 'source-map'
    },
    pages: {
        index: {
            entry: 'src/main/vue/main.js',
            template: 'src/main/vue/index.html',
        },
    },

    /*     Change build paths to make them Maven compatible
         see https://cli.vuejs.org/config/*/
    outputDir: 'target/classes/public',
    assetsDir: 'static'
};
