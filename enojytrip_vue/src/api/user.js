import { apiInstance } from "@/api/index";

const api = apiInstance();

async function login(params, success, fail) {
  await api.post(`/user/login`, JSON.stringify(params)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/user/logout/${userid}`).then(success).catch(fail);
}

function checkUserId(userid, success, fail) {
  api.get(`/user/idCheck?id=${userid}`).then(success).catch(fail);
}

function join(user, success, fail) {
  api.post(`/user/join`, JSON.stringify(user)).then(success).catch(fail);
}

function changeEmoji(index, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("access-token");
  api.put(`/user/emoji/${index}`).then(success).catch(fail);
}

function searchId(params, success, fail) {
  api.post(`/user/searchId`, JSON.stringify(params)).then(success).catch(fail);
}

function searchPwd(params, success, fail) {
  api.post(`/user/searchPwd`, JSON.stringify(params)).then(success).catch(fail);
}

function modify(params, success, fail) {
  api.put(`/user/modify`, JSON.stringify(params)).then(success).catch(fail);
}

export {
  login,
  logout,
  findById,
  tokenRegeneration,
  checkUserId,
  join,
  changeEmoji,
  searchId,
  searchPwd,
  modify,
};
