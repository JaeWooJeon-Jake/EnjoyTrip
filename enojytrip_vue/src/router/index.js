import Vue from "vue";
import VueRouter from "vue-router";

import store from "@/store";
Vue.use(VueRouter);
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const checkToken = store.getters["userStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  // console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    // console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("userStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "userLogin" });
  } else {
    // console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "main",
    component: () => import(/* webpackChunkName: "main" */ "@/views/MainView"),
  },
  {
    path: "/user",
    name: "user",
    component: () => import(/* webpackChunkName: "user" */ "@/views/UserView"),
    children: [
      {
        path: "login",
        name: "userLogin",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/LoginForm"),
      },
      {
        path: "regist",
        name: "userRegist",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/RegistForm"),
      },
      {
        path: "mypage",
        name: "userMypage",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/MypageForm"),
      },
    ],
  },
  {
    path: "/trip",
    name: "trip",
    component: () => import(/* webpackChunkName: "trip" */ "@/views/TripView"),
  },
  {
    path: "/plan",
    name: "plan",
    component: () => import(/* webpackChunkName: "plan" */ "@/views/PlanView"),
    redirect: "/plan/list",
    children: [
      {
        path: "list",
        name: "planList",
        component: () => import(/* webpackChunkName: "board" */ "@/components/plan/PlanList"),
      },
      {
        path: "detail/:planNo",
        name: "planDetail",
        component: () => import(/* webpackChunkName: "board" */ "@/components/plan/PlanDetail"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import(/* webpackChunkName: "board" */ "@/views/BoardView"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardList"),
      },
      {
        path: "detail/:boardNo",
        name: "boardDetail",
        component: () => import("@/components/board/BoardDetail"),
      },
      {
        path: "create",
        name: "boardCreate",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardCreate"),
      },
      {
        path: "modify/:boardNo",
        name: "boardModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify"),
      },
      {
        path: "delete/:boardNo",
        name: "boardDelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDelete"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    // 화면 이동 시 스크롤 맨위로 이동 시키기 위해 추가
    return { x: 0, y: 0 };
  },
});

export default router;
