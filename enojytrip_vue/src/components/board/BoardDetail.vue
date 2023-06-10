<template>
  <div class="mainContent">
    <!-- board Header Start -->
    <b-list-group>
      <b-list-group-item href="#"
        ><h1 class="m-1">{{ board.title }}</h1></b-list-group-item
      >
    </b-list-group>
    <b-list-group>
      <b-list-group-item href="#" disabled>
        <div class="d-flex bd-highlight">
          <div class="p-2 bd-highlight">
            <b-card-img
              v-if="userInfo && userInfo.id === board.userId"
              :src="`https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/${
                emoji.items[userInfo.emoji]
              }.png`"
              width="50"
              height="50"
            ></b-card-img>

            <b-card-img
              v-else
              :src="`https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/${
                emoji.items[board.emoji]
              }.png`"
              width="50"
              height="50"
            ></b-card-img>
          </div>
          <div class="p-2 bd-highlight">
            {{ board.userName }} <span style="font-weight: bolder">님</span>
          </div>
          <div class="ml-auto p-2 bd-highlight">
            <b-icon class="mr-2" icon="clock-fill"></b-icon
            >{{ board.registDate }}
          </div>
          <p class="m-2"><b-icon icon="eye"></b-icon> {{ board.viewCnt }}</p>
        </div>
      </b-list-group-item>
    </b-list-group>
    <!-- board Header End -->

    <!-- boarder Content Start -->
    <h3 class="m-4">{{ board.content }}</h3>

    <center>
      <div @click="boardLike">
        <img
          v-if="board.likeCnt < 10"
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Beating%20Heart.png"
          alt="Beating Heart"
          width="100"
          height="100"
        />
        <img
          v-else
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Heart%20on%20Fire.png"
          alt="Beating Heart"
          width="100"
          height="100"
        />
        <span style="color: red">{{ board.likeCnt }}</span>
      </div>
    </center>
    <div class="d-flex justify-content-end" v-if="board.userId === userInfo.id">
      <b-button class="m-1" @click="moveModify" variant="outline-secondary" pill
        >글수정</b-button
      >
      <b-button class="m-1" @click="remove" variant="outline-danger" pill
        >글삭제</b-button
      >
    </div>

    <!-- boarder Content End -->

    <!-- 댓글 입력 form start -->
    <b-input-group :prepend="userInfo.name + '님'" class="mt-3">
      <b-form-input
        placeholder="댓글을 입력하시오"
        v-model="content"
      ></b-form-input>
      <b-input-group-append>
        <b-button variant="outline-success" @click="mkComment"
          ><img
            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Left%20Speech%20Bubble.png"
            alt="Left Speech Bubble"
            width="25"
            height="25"
        /></b-button>
      </b-input-group-append>
    </b-input-group>
    <!-- 댓글 입력 form end -->

    <!-- 댓글Start -->
    <comment-list
      :comments="comments"
      @deleteComment="deleteComment"
      @likeComment="likeComment"
    ></comment-list>
    <!-- 댓글 END -->

    <div>
      <b-button class="mt-4" block variant="light" @click="moveList"
        >글목록</b-button
      >
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import {
  boardDetail,
  boardCommentList,
  boardCommentDelete,
  boardLike,
  commentLike,
} from "@/api/board";
import CommentList from "../comment/CommentList.vue";
const boardStore = "boardStore";
const userStore = "userStore";

export default {
  name: "BoardView",
  components: {
    CommentList,
  },
  data() {
    return {
      board: {},
      comments: {},
      content: "",
    };
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

    boardCommentList(
      this.$route.params.boardNo,
      ({ data }) => {
        this.comments = data;
      },
      (error) => {
        console.log(error);
      }
    );
    // console.log(this.$route.params.boardNo);
  },

  methods: {
    ...mapActions(boardStore, ["writeBoardComment"]),
    moveList() {
      this.$router.push({ name: "boardList" });
    },
    moveModify() {
      this.$router.push({
        name: "boardModify",
        params: { boardNo: this.board.boardNo },
      });
    },
    remove() {
      this.$router.push({
        name: "boardDelete",
        params: { boardNo: this.board.boardNo },
      });
    },
    // 좋아요 기능 구현
    async boardLike() {
      await boardLike(
        this.board.boardNo,
        ({ data }) => {
          // console.log(data);
          if (data !== "success") {
            alert("이미 좋아요를 누르셨습니다.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
      // console.log("hi");
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

    // 댓글관련 메소드
    async mkComment() {
      if (!this.content) return;
      await this.writeBoardComment({
        boardNo: this.board.boardNo,
        comment: {
          content: this.content,
          userId: this.userInfo.id,
          name: this.userInfo.name,
        },
      });
      // console.log("hi");
      boardCommentList(
        this.$route.params.boardNo,
        ({ data }) => {
          // console.log(data);
          this.comments = data;
        },
        (error) => {
          console.log(error);
        }
      );
      this.content = "";
    },

    async deleteComment(commentNo) {
      await boardCommentDelete(
        commentNo,
        () => {
          // console.log("delete Success");
        },
        (error) => {
          console.log(error);
        }
      );

      boardCommentList(
        this.$route.params.boardNo,
        ({ data }) => {
          this.comments = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async likeComment(commentNo) {
      await commentLike(
        commentNo,
        ({ data }) => {
          if (data != "success") {
            alert("이미 이 댓글에 좋아요를 누르셨습니다.");
          }
          // console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );

      boardCommentList(
        this.$route.params.boardNo,
        ({ data }) => {
          this.comments = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  computed: {
    ...mapState(userStore, ["userInfo", "emoji"]),
  },
};
</script>

<style scoped>
.mainContent {
  height: auto;
  min-height: 100%;
  padding-bottom: 100px;
}
</style>
