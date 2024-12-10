# Z-OJ 在线判题系统
## 项目介绍
基于 Vue + Spring Boot + Spring Cloud 的在线判题平台，实现了用户注册登录、浏览题目、创建和修改题目、管理题目、提交代码、代码评测、浏览提交记录等功能。
## GitHub仓库
前端：https://github.com/zyf127/zoj-frontend

后端：https://github.com/zyf127/zoj-backend

代码沙箱：https://github.com/zyf127/zoj-code-sandbox
## 技术栈
### 前端
Vue

Vue-CLI 脚手架

Arco Design 组件库
### 后端
Spring Boot

Nacos

OpenFeign

Spring Cloud Gateway

Docker

RabbitMQ

MySQL
## 核心功能
用户注册和登录：用户通过注册和登录账号即可使用本网站。

浏览题目：用户可以通过浏览和搜索题目，选择题目进行做题。

管理题目：管理员可以创建、修改、删除题目。

提交代码：用户编写代码完毕后，可以提交代码进行评测。

浏览提交记录：用户可以查看做题记录。
## 项目亮点
系统划分为用户模块、题目模块、负责校验结果的判题模块、负责编译执行代码的代码沙箱。

为了保证代码沙箱宿主机的安全，使用 Docker Java 库创建 Docker 容器，在容器中执行用户提交的代码。

使用 Nacos + OpenFeign 实现了各模块之间的调用，使用 RabbitMQ 实现题目服务和判题服务的异步解耦。

使用 Spring Cloud Gateway 对各服务接口进行路由，并在网关自定义 GobalFilter 配合 JWT 实现权限校验。

定义代码沙箱的抽象接口和多种实现类，通过静态工厂模式 + Spring 配置化实现对多种代码沙箱的灵活调用。
## 界面展示
### 用户注册、用户登录
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E7%94%A8%E6%88%B7%E6%B3%A8%E5%86%8C.png">
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95.png">

### 浏览题目
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E6%B5%8F%E8%A7%88%E9%A2%98%E7%9B%AE.png">

### 提交记录
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E6%8F%90%E4%BA%A4%E8%AE%B0%E5%BD%95.png">

### 创建题目
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E5%88%9B%E5%BB%BA%E9%A2%98%E7%9B%AE.png">

### 管理题目
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E7%AE%A1%E7%90%86%E9%A2%98%E7%9B%AE.png">

### 做题页面
<img src="https://github.com/zyf127/zoj-backend/blob/master/img/%E5%81%9A%E9%A2%98%E9%A1%B5%E9%9D%A2.png">
