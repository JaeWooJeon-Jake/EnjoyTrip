import { planList, planDetail, deletePlan, modifyPlanLike } from "@/api/plan";

const planStore = {
  namespaced: true,
  state: {
    planList: [],
    planDetail: {},
    planCourse: [],
    isLike: false,
  },
  getters: {},
  mutations: {
    SET_PLAN_LIST(state, list) {
      state.planList = list;
    },
    SET_PLAN_DETAIL(state, plan) {
      state.planDetail = plan;
    },
    SET_PLAN_COURSE(state, course) {
      state.planCourse = course;
    },
    SET_PLAN_LIKE(state, isLike) {
      state.isLike = isLike;
    },
  },
  actions: {
    getPlanList({ commit }) {
      planList(
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_PLAN_LIST", data.planList);
          } else {
            alert("리스트 호출 중 문제가 발생했습니다.");
            return;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getPlanDetail({ commit }, params) {
      planDetail(
        params,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_PLAN_DETAIL", data.plan);
            commit("SET_PLAN_COURSE", data.course);
            commit("SET_PLAN_LIKE", data.isLike);
          } else {
            alert("리스트 호출 중 문제가 발생했습니다.");
            return;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deletePlanDetail({ commit }, planNo) {
      deletePlan(
        planNo,
        ({ data }) => {
          if (data === "success") {
            commit("SET_PLAN_DETAIL", {});
            commit("SET_PLAN_COURSE", []);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyLike({ commit }, params) {
      modifyPlanLike(
        params,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_PLAN_LIKE", data.isLike);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default planStore;
