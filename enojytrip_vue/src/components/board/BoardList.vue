<template>
  <div class="main">
    <div class="d-flex justify-content-between"><h1 class="mb-3" >리스트 목록</h1><button  type="button" class="btn btn-primary" @click="create" style="
    height: 43px;
">글쓰기</button>
    </div>
    <b-row class="table-section">
      <b-table
        id="boardTable"
        striped
        outlined
        responsive
        :fields="fields"
        :items="boards"
        :per-page="perPage"
        :current-page="currentPage"
      >
        <template #cell(content)="data">
          <router-link :to="{ name: 'boardDetail', params: { boardNo: data.item.boardNo } }">{{
            data.value
          }}</router-link>
        </template>
        <!-- <template #cell(startDate)="data">
          {{ data.value.substr(0, 10) }}
        </template>
        <template #cell(endDate)="data">
          {{ data.value.substr(0, 10) }}
        </template>
        <template #cell(registDate)="data">
          {{ data.value.substr(0, 10) }}
        </template> -->
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
import { boardList } from "@/api/board";
export default {
  name: "boardLists",
  components: {},
  data() {
        return {
          boards: [],
      fields: [
        {
          key: "boardNo",
          label: "번호",
          thStyle: { width: "5%" },
        },
        {
          key: "title",
          label: "제목",
          thStyle: { width: "10%" },
        },
        {
          key: "content",
          label: "내용",
          thStyle: { width: "20%" },
        },
        {
          key: "userName",
          label: "작성자",
          thStyle: { width: "10%" },
        },
        {
          key: "likeCnt",
          label: "좋아요",
          thStyle: { width: "5%" },
        },
        {
          key: "commentCnt",
          label: "댓글",
          thStyle: { width: "5%" },
        },
        {
          key: "viewCnt",
          label: "조회수",
          thStyle: { width: "15%" },
        },
        {
          key: "registDate",
          label: "시간",
          thStyle: { width: "15%" },
        },
        
      ],
      perPage: 10,
      currentPage: 1,
    };
  },
  computed: {
    rows() {
      return this.boards.length;
    },
  },
  created() {
  boardList(({ data }) => { 
    this.boards = data;
  }, ( error => { console.log(error) }));
},
  methods: {
  create() {
    this.$router.push({
      name: "boardCreate",
    });
  },
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
