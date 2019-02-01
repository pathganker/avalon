import axios from 'axios';
import store from '../store';
import vue from 'vue';
// import router from '../router';

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000,                  // 请求超时时间
  headers:{
    "Auth-Device":'001',
    "Auth-Token":'default',
  }
});

// request拦截器
service.interceptors.request.use(config => {
  console.log(config);
  if(store.getters.token){
    config.headers["Auth-Token"]=store.getters.token;
  }
  return config;
}, error => {

  console.log(error); // for debug
  Promise.reject(error);
})

// respone拦截器
service.interceptors.response.use(
  response => {
    console.log(response)
    return response
  },
  error => {
    console.log('err' + error);// for debug
    vue.$Message.error({
                    message: error.message,
                    duration: 5 * 1000,
                    closable: true
                });
    return Promise.reject(error);
  }
)

export default service;
