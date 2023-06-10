import { sidoList, gugunList, getAttractionList } from "@/api/trip";

const tripStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "도시를 선택하세요" }],
    guguns: [{ value: null, text: "동네를 선택하세요" }],
    contentTypeList: [
      {
        value: null,
        text: "관광지 유형을 선택하세요",
      },
      {
        value: 12,
        text: "관광지",
      },
      {
        value: 14,
        text: "문화시설",
      },
      {
        value: 15,
        text: "축제공연행사",
      },
      {
        value: 25,
        text: "여행코스",
      },
      {
        value: 28,
        text: "레포츠",
      },
      {
        value: 32,
        text: "숙박",
      },
      {
        value: 38,
        text: "쇼핑",
      },
      {
        value: 39,
        text: "음식점",
      },
    ],
    attractions: [],
    plans: [],
  },
  getters: {},
  mutations: {
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "지역을 선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "동네를 선택하세요" }];
    },
    CLEAR_ATTRACTION_LIST(state) {
      state.attractions = [];
    },
    SET_PLAN_LIST(state, plans) {
      state.plans = plans;
    },
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_ATTRACTION_LIST(state, attractions) {
      attractions.forEach((attraction, index) => {
        let attr = {
          ...attraction,
          isSelected: false,
          seq: index,
        };
        state.attractions.push(attr);
      });
    },
  },
  actions: {
    getSidos({ commit }) {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGuguns({ commit }, sidoCode) {
      gugunList(
        sidoCode,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getAttractions({ commit }, params) {
      getAttractionList(
        params,
        ({ data }) => {
          commit("SET_ATTRACTION_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default tripStore;
