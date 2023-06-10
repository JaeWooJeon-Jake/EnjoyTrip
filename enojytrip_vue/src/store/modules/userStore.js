import jwtDecode from "jwt-decode";
import router from "@/router";
import { login, findById, tokenRegeneration, logout, modify } from "@/api/user";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    userInfo: null,
    //   address : "서울시"
    // email :  "ssafy@ssafy.com"
    // emoji: 5
    // id :  "ssafy"
    // name : "강싸피"
    // password : null
    // phone : "010-1111-1111"
    // registDate :  null
    isLoginError: false,
    isValidToken: false,
    emoji: {
      baseURL:
        "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/",
      items: [
        "Beaming%20Face%20with%20Smiling%20Eyes",
        "Alien",
        "Angry%20Face%20with%20Horns",
        "Cowboy%20Hat%20Face",
        "Astonished%20Face",
        "Face%20with%20Medical%20Mask",
        "Face%20with%20Monocle",
        "Ghost",
        "Hear-No-Evil%20Monkey",
        "Thinking%20Face",
        "Disguised%20Face",
        "Face%20in%20Clouds",
      ],
    },
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
      // console.log("SET_IS_LOGIM >>>>>>>>>>>>>>>>> ", state.isLogin);
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_USER_EMOJI: (state, idx) => {
      state.userInfo.emoji = idx;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            // console.log(data);
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            alert("입력하신 정보가 일치하지 않습니다");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async getUserInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
      // console.log("decode된 user정보", decodeToken.userid);
      await findById(
        decodeToken.userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.userInfo);
            commit("SET_IS_LOGIN", true);
            // console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },

    async tokenRegeneration({ commit, state }) {
      console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("access-token"));
      await tokenRegeneration(
        JSON.stringify(state.userInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.userInfo.userid,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    async userLogout({ commit }, userid) {
      await logout(
        userid,
        ({ data }) => {
          // console.log("로그아웃 시도");
          // console.log(this.state.userStore);
          if (data.message === "success") {
            // console.log("서버에서 로그아웃 success받아옴");
            commit("SET_USER_INFO", null);
            commit("SET_IS_LOGIN", false);
            // console.log(this.state.userStore);
            commit("SET_IS_VALID_TOKEN", false);
            // console.log(state.isLogin);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyUserInfo({ commit }, params) {
      modify(
        params,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.user);
            alert("회원정보 수정이 완료되었습니다.");
          } else {
            alert("회원정보 수정 중 문제가 발생했습니다.");
            return;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default userStore;
