import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

// module import
import tripStore from "@/store/modules/tripStore";
import boardStore from "@/store/modules/boardStore";
import userStore from "@/store/modules/userStore";
import planStore from "@/store/modules/planStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    tripStore,
    boardStore,
    userStore,
    planStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
