<template>
  <div class="regist">
    <div class="d-flex justify-content-center mb-3"><h1>글 수정</h1></div>

    <!-- 제목 새로넣을거 Start-->
    <div class="input-group input-group-lg mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text" id="inputGroup-sizing-lg">제목</span>
      </div>
      <input
        type="text"
        placeholder="제목을 입력하세요"
        v-model="board.title"
        class="form-control"
        aria-label="Large"
        aria-describedby="inputGroup-sizing-sm"
      />
    </div>
    <!-- 내용 새로넣을거 End-->

    <!-- 제목 새로넣을거 Start-->
    <b-row>
      <b-col sm="12" class="mt-3">
        <b-form-textarea
          id="textarea-auto-height"
          placeholder="내용을 입력하세요"
          rows="18"
          max-rows="8"
          v-model="board.content"
        ></b-form-textarea>
      </b-col>
    </b-row>
    <!-- 내용 새로넣을거 End-->

    <div class="d-flex justify-content-end">
      <b-button class="m-2 mt-4" variant="outline-secondary" @click="moveList"
        >목록</b-button
      >
      <b-button class="m-2 mt-4" variant="outline-primary" @click="checkValue"
        >수정</b-button
      >
    </div>
  </div>
  <!-- <div class="regist">
    <h1 class="underline">게시글 수정</h1>
    <div class="regist_form">
      <label for="title">제목</label>
      <input type="text" id="title" v-model="board.title" ref="title" /><br />
      <label for="content">내용</label>
      <br />
      <textarea
        id="content"
        v-model="board.content"
        ref="content"
        cols="35"
        rows="5"
      ></textarea
      ><br />
      <button @click="checkValue">수정</button>
      <button @click="moveList">목록</button>
    </div>
  </div> -->
</template>

<script>
import { boardDetail } from "@/api/board";
import { mapState, mapActions } from "vuex";
const userStore = "userStore";
const boardStore = "boardStore";
export default {
  name: "BoardModify",
  data() {
    return {
      board: {},
      title: "",
      content: "",
    };
  },
  methods: {
    ...mapActions(boardStore, ["modifyBoard", "getBoardDetail"]),
    // 입력값 체크하기 - 체크가 성공하면 modifyArticle 호출
    checkValue() {
      // 사용자 입력값 체크하기
      // 작성자아이디, 제목, 내용이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      err &&
        !this.board.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.board.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 modifyArticle 호출
      else this.modifyArticle();
    },
    async modifyArticle() {
      // 비동기
      await this.modifyBoard({
        title: this.board.title,
        type: "일반",
        userId: this.userInfo.id,
        userName: this.userInfo.name,
        content: this.board.content,
        boardNo: this.board.boardNo,
      });

      //완료 후 이동
      this.$router.push({
        name: "boardDetail",
        params: { boardNo: this.board.boardNo },
      });
    },

    moveList() {
      this.$router.push({ name: "boardList" });
    },
  },
  created() {
    boardDetail(
      this.$route.params.boardNo,
      ({ data }) => {
        this.board = data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
};
</script>

<style></style>
