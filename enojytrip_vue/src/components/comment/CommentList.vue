<template>
  <b-list-group>
    <b-list-group-item
      v-for="(comment, index) in comments"
      :key="index"
      href="#"
      class="flex-column align-items-start"
    >
      <div class="d-flex w-100 justify-content-between">
        <h5 class="mb-1">
          <img
            v-if="userInfo && userInfo.id === comment.userId"
            :src="`https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/${
              emoji.items[userInfo.emoji]
            }.png`"
            :alt="emoji.items[userInfo.emoji]"
            width="25"
            height="25"
          />
          <img
            v-else
            :src="`https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/${
              emoji.items[comment.emoji]
            }.png`"
            :alt="emoji.items[comment.emoji]"
            width="25"
            height="25"
          />
          {{ comment.userName }}님
        </h5>
        <small class="text-muted">
          <b-button-group>
            <b-button
              variant="danger"
              v-if="comment.userId !== userInfo.id"
              @click="likeComment(comment.commentNo)"
              ><b-icon icon="heart" animation="throb" font-scale="1"></b-icon
            ></b-button>

            <!-- <b-button variant="info"
              ><b-icon
                icon="chat-text"
                animation="throb"
                font-scale="1"
              ></b-icon
            ></b-button> -->

            <b-button
              v-if="comment.userId === userInfo.id"
              @click="deleteComment(comment)"
              variant="warning"
              ><b-icon icon="x-lg" animation="throb" font-scale="1"></b-icon
            ></b-button> </b-button-group
        ></small>
      </div>

      <p class="mb-2">{{ comment.content }}</p>
      <small class="mr-2">{{ comment.registDate }}</small>

      <small class="text-muted" v-if="comment.likeCnt > 0">
        <img
          v-if="comment.likeCnt < 10"
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Red%20Heart.png"
          alt="Heart"
          width="25"
          height="25"
        />
        <img
          v-else
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Heart%20on%20Fire.png"
          alt="Fire_Heart"
          width="25"
          height="25"
        />
        <span style="color: red">{{ comment.likeCnt }}</span>
      </small>
    </b-list-group-item>
  </b-list-group>
</template>
<script>
import { mapState } from "vuex";
const userStore = "userStore";
export default {
  name: "CommentList",
  props: {
    comments: [],
  },
  computed: {
    ...mapState(userStore, ["userInfo", "emoji"]),
  },
  methods: {
    deleteComment(comment) {
      console.log(comment);
      this.$emit("deleteComment", comment.commentNo);
    },
    likeComment(commentNo) {
      // console.log(commentNo);
      this.$emit("likeComment", commentNo);
    },
  },
};
</script>
<style lang=""></style>
