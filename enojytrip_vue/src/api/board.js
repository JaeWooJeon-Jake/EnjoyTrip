import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function boardDelete(params, success, fail) {
  await api.delete(`/board/${params}`).then(success).catch(fail);
}

async function boardWrite(params, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("access-token");
  await api.post(`/board`, JSON.stringify(params)).then(success).catch(fail);
}

async function boardModify(params, success, fail) {
  await api.put(`/board`, JSON.stringify(params)).then(success).catch(fail);
}
function boardList(success, fail) {
  // console.log("board에서 확인");
  api.get(`/board/list`).then(success).catch(fail);
}
function boardDetail(params, success, fail) {
  api.get(`/board/${params}`).then(success).catch(fail);
}

async function boardLike(boardNo, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("access-token");
  await api.post(`/board/like/${boardNo}`).then(success).catch(fail);
}

// 댓글관련 메소드
async function boardCommentWrite(params, success, fail) {
  await api
    .post(`/board/${params.boardNo}/comment`, JSON.stringify(params.comment))
    .then(success)
    .catch(fail);
}

function boardCommentList(params, success, fail) {
  api.get(`/board/${params}/commentList`).then(success).catch(fail);
}

async function boardCommentDelete(params, success, fail) {
  await api.delete(`/board/${params}/comment`).then(success).catch(fail);
}

async function commentLike(commentNo, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("access-token");
  await api.post(`/board/comment/like/${commentNo}`).then(success).catch(fail);
}

export {
  boardWrite,
  boardList,
  boardDetail,
  boardModify,
  boardDelete,
  boardCommentList,
  boardCommentWrite,
  boardCommentDelete,
  boardLike,
  commentLike,
};
