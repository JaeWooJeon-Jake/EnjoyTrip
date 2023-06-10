import { apiInstance } from "@/api/index";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/attraction/sido`).then(success).catch(fail);
}

function gugunList(sidoCode, success, fail) {
  api
    .get(`/attraction/gugun?sidoCode=` + sidoCode)
    .then(success)
    .catch(fail);
}

function getAttractionList(params, success, fail) {
  api
    .post(`/attraction/list`, JSON.stringify(params))
    .then(success)
    .catch(fail);
}

export { sidoList, gugunList, getAttractionList };
