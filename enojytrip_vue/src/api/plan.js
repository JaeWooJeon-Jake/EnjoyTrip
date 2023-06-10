import { apiInstance } from "@/api/index";

const api = apiInstance();

function writePlan(plan, success, fail) {
  api.post(`/plan/write`, JSON.stringify(plan)).then(success).catch(fail);
}

function planList(success, fail) {
  api.get(`/plan/list`).then(success).catch(fail);
}

function planDetail(params, success, fail) {
  api.get(`/plan/detail/${params.planNo}?userId=${params.userId}`).then(success).catch(fail);
}

function deletePlan(planNo, success, fail) {
  api.delete(`/plan/delete/${planNo}`).then(success).catch(fail);
}

function modifyPlanLike(params, success, fail) {
  api.put(`/plan/modifyLike`, JSON.stringify(params)).then(success).catch(fail);
}

export { writePlan, planList, planDetail, deletePlan, modifyPlanLike };
