import axios from 'axios';

const isDev = import.meta.env.DEV;

// const myAxios: AxiosInstance = axios.create({
//     baseURL: isDev ? 'http://localhost:8080' : 'https://api.example.com',
// });

const myAxios = axios.create({
    baseURL: isDev ? 'http://localhost:8080/api' : 'https://api.example.com',
});
// 请求携带 Cookie
myAxios.defaults.withCredentials = true;

// 添加请求拦截器
myAxios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
myAxios.interceptors.response.use(function (response) {
    // 拦截是否登录
    if (response?.data?.code == 40100) {
        const redirectUrl = window.location.href;
        window.location.href = `/user/login?redirect=${redirectUrl}`;
    }
    return response.data;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});

export default myAxios;