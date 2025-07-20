const PROXY_CONFIG = {
  "/api/*": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true,
    "logLevel": "debug",
    "bypass": function (req, res, proxyOptions) {
      console.log(`Proxying request: ${req.method} ${req.url}`);
      return null; // Continue with proxy
    }
  }
};

module.exports = PROXY_CONFIG;