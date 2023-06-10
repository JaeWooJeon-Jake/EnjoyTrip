import {
  boardList,
  boardWrite,
  boardDetail,
  boardModify,
  boardDelete,
  boardCommentWrite,
} from "@/api/board.js";

const boardStore = {
  namespaced: true,

  // board안에 들어갈 comments들을 관리
  state: {
    boards: [],
    board: {},
    comments: {},
  },
  getters: {},
  mutations: {
    CLEAR_BOARD_LIST(state) {
      //   console.log("초기화");
      state.boards = [];
    },
    SET_BOARD_LIST(state, boards) {
      state.boards = boards;
    },
    SET_BOARD(state, board) {
      state.board = board;
    },
    SET_BOARD_COMMENT_LIST(state, comments) {
      state.comments = comments;
    },
  },
  actions: {
    getBoardList: ({ commit }) => {
      boardList(
        ({ data }) => {
          commit("SET_BOARD_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    writeBoard: async ({ commit }, params) => {
      await boardWrite(
        params,
        () => {},
        (error) => {
          console.log(error);
          console.log(commit);
        }
      );
    },
    getBoardDetail: ({ commit }, params) => {
      boardDetail(
        params,
        ({ data }) => {
          commit("SET_BOARD", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyBoard: async ({ commit }, params) => {
      await boardModify(
        params,
        () => {},
        ({ error }) => {
          console.log(commit);
          console.log(error);
        }
      );
    },
    deleteBoard: async ({ commit }, params) => {
      await boardDelete(
        params,
        () => {},
        ({ error }) => {
          console.log(commit);
          console.log(error);
        }
      );
    },

    // 댓글관련 액션추가
    writeBoardComment: async ({ commit }, params) => {
      await boardCommentWrite(
        params,
        () => {},
        ({ error }) => {
          console.log(commit);
          console.log(error);
        }
      );
    },
  },
};

export default boardStore;
