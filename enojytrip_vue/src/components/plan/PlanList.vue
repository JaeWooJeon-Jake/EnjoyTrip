<template>
  <div>
    <h1 class="mb-3">여행일정 목록</h1>
    <b-row class="table-section">
      <b-table
        id="planTable"
        striped
        outlined
        responsive
        :fields="fields"
        :items="planList"
        :per-page="perPage"
        :current-page="currentPage"
      >
        <template #cell(planName)="data">
          <router-link :to="{ name: 'planDetail', params: { planNo: data.item.planNo } }">{{
            data.value
          }}</router-link>
        </template>
        <template #cell(startDate)="data">
          {{ data.value.substr(0, 10) }}
        </template>
        <template #cell(endDate)="data">
          {{ data.value.substr(0, 10) }}
        </template>
        <template #cell(registDate)="data">
          {{ data.value.substr(0, 10) }}
        </template>
      </b-table>
    </b-row>
    <b-pagination
      class="mt-3"
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="planTable"
      align="center"
    ></b-pagination>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "planList",
  components: {},
  data() {
    return {
      fields: [
        {
          key: "planName",
          label: "여행명",
          thStyle: { width: "30%" },
        },
        {
          key: "userId",
          label: "작성자",
          thStyle: { width: "10%" },
        },
        {
          key: "period",
          label: "기간",
          thStyle: { width: "15%" },
        },
        {
          key: "startDate",
          label: "시작일",
          thStyle: { width: "15%" },
        },
        {
          key: "endDate",
          label: "종료일",
          thStyle: { width: "15%" },
        },
        {
          key: "registDate",
          label: "작성일",
          thStyle: { width: "15%" },
        },
      ],
      perPage: 10,
      currentPage: 1,
    };
  },
  computed: {
    ...mapState("planStore", ["planList"]),
    rows() {
      return this.planList.length;
    },
  },
  created() {
    this.getPlanList();
  },
  methods: {
    ...mapActions("planStore", ["getPlanList"]),
  },
};
</script>

<style scoped>
.table-section {
  height: 540px;
}
.row-title {
  font-weight: bold;
  cursor: pointer;
}
</style>
