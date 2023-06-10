<template>
  <div class="regist">
    <div class="d-flex justify-content-center mb-3"><h1>글 쓰기</h1></div>

    <!-- 제목 새로넣을거 Start-->
    <div class="input-group input-group-lg mb-3">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-lg" >제목</span>
  </div>
  <input type="text" placeholder="제목을 입력하세요" v-model="title" class="form-control" aria-label="Large" aria-describedby="inputGroup-sizing-sm">
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
        v-model="content"
      ></b-form-textarea>
    </b-col>
  </b-row>
    <!-- 내용 새로넣을거 End-->

    <div class="d-flex justify-content-end">
      <b-button class="m-2 mt-4" variant="outline-secondary" @click="moveList">목록</b-button>
      <b-button class="m-2 mt-4" variant="outline-primary" @click="checkValue">등록</b-button>
    </div>
  </div>
</template>

<script>
import { mapState,mapActions } from "vuex";
const boardStore = "boardStore";
const userStore = "userStore";
export default {
  name: "BoardWrite",
  data() {
    return {
      title: null,
      content: null,
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(boardStore, ["writeBoard"]),
    // 입력값 체크하기 - 체크가 성공하면 registArticle 호출
    checkValue() {
      // 사용자 입력값 체크하기
      // 작성자아이디, 제목, 내용이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      err &&
        !this.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registArticle 호출
      else this.registArticle();
    },
    async registArticle() {
      // TODO : 글번호에 해당하는 글정보 등록.
      await this.writeBoard({
        title: this.title,
        type: "일반",
        userId: this.userInfo.id,
        userName: this.userInfo.name,
        content: this.content,
      });
      //글 보러가기
      //DB에서 번호받는 방법 생각해보기
      // this.$router.push({
      //   name: "boardDetail",
      //   params: { boardNo },
      // });
      this.moveList();
      // this.$router.push({ path: "list" });
    },

    moveList() {
      this.$router.push({ path: "list" });
    },
  },
  created() {
  }
};
</script>

<style></style>
