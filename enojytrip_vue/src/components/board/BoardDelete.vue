<template>
  <div class="regist">
    <h1>글 삭제</h1>
    <div>삭제중....</div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const boardStore = "boardStore";

export default {
  name: "BoardDelete",
  data() {
    return {
      articleno: Number,
    };
  },
  async created() {
    // 비동기
    await this.deleteBoard(this.$route.params.boardNo);
    this.$router.push({
      name: "boardList",
    });
  },
  methods: {
    ...mapActions(boardStore, ["deleteBoard"]),
  },
};
</script>

<style></style>
