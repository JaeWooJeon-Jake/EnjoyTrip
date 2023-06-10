<template>
  <div>
    <b-row>
      <b-col cols="9">
        <h1 class="mb-3">상세 일정</h1>
      </b-col>
      <b-col cols="3" v-if="isLogin && userInfo.id === planDetail.userId">
        <b-button pill variant="danger" @click="showDismissibleAlert = true">글삭제</b-button>
      </b-col>
    </b-row>
    <b-card class="plan-header mb-4">
      <b-row>
        <b-col cols="4">
          <span class="plan-subtitle mr-3">여행명</span>
          <span>{{ planDetail.planName }}</span>
        </b-col>
        <b-col cols="4">
          <span class="plan-subtitle mr-3">기간</span>
          <span>{{ planDetail.period }}일</span>
        </b-col>
        <b-col cols="4">
          <span class="plan-subtitle mr-3">작성자</span>
          <span>{{ planDetail.userId }}</span>
        </b-col>
      </b-row>
    </b-card>
    <b-card class="mb-4" v-for="(plan, index) in planCourse" :key="index">
      <b-card-header header-tag="header" class="p-1" role="tab">
        <b-button block v-b-toggle="`accordion-${index + 1}`">
          <h4>Day {{ index + 1 }}</h4>
        </b-button>
      </b-card-header>
      <b-collapse :id="`accordion-${index + 1}`" visible accordion="my-accordion" role="tabpanel">
        <b-card-body>
          <b-row class="mb-4" v-for="(course, index) in plan" :key="index">
            <b-col cols="6">
              <b-img
                class="attr-img"
                :src="
                  course.firstImage != ''
                    ? course.firstImage
                    : require(`@/assets/img/common/noImage.jpg`)
                "
              >
              </b-img>
            </b-col>
            <b-col cols="6">
              <p>{{ course.title }}</p>
            </b-col>
          </b-row>
        </b-card-body>
      </b-collapse>
    </b-card>
    <b-container class="like-box">
      <b-row align-h="center">
        <b-col>
          <b-icon
            v-if="isLike"
            class="like-icon"
            icon="hand-thumbs-up-fill"
            @click="dislike"
          ></b-icon>
          <b-icon v-else class="like-icon" icon="hand-thumbs-up" @click="like"></b-icon>
        </b-col>
      </b-row>
    </b-container>
    <!-- Custom Alert start -->
    <b-alert
      variant="danger"
      dismissible
      fade
      :show="showDismissibleAlert"
      @dismissed="showDismissibleAlert = false"
    >
      <span class="mr-4">정말 삭제 하시겠습니까?</span>
      <b-button variant="success" @click="deletePlan">네</b-button>
    </b-alert>
    <!-- Custom Alert end -->
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "PlanDetail",
  components: {},
  data() {
    return {
      showDismissibleAlert: false,
    };
  },
  computed: {
    ...mapState("planStore", ["planDetail", "planCourse", "isLike"]),
    ...mapState("userStore", ["isLogin", "userInfo"]),
  },
  created() {
    let params = {
      planNo: this.$route.params.planNo,
      userId: this.isLogin ? this.userInfo.id : null,
    };
    this.getPlanDetail(params);
  },
  methods: {
    ...mapActions("planStore", ["getPlanDetail", "deletePlanDetail", "modifyLike"]),
    deletePlan() {
      this.deletePlanDetail(this.planDetail.planNo);
      alert("여행 일정이 삭제 되었습니다.");
      this.$router.push({ name: "plan" });
    },
    like() {
      if (!this.isLogin) {
        alert("로그인 후에 좋아요 기능을 이용할 수 있습니다.");
        return;
      }
      let params = {
        planNo: this.planDetail.planNo,
        userId: this.userInfo.id,
        isLike: true,
      };
      this.modifyLike(params);
    },
    dislike() {
      let params = {
        planNo: this.planDetail.planNo,
        userId: this.userInfo.id,
        isLike: false,
      };
      this.modifyLike(params);
    },
  },
};
</script>

<style scoped>
.plan-header {
  max-width: 1800px;
  height: 60px;
}
.plan-subtitle {
  font-weight: bold;
  font-size: 20px;
}
.attr-img {
  width: 200px;
  height: 200px;
  border-radius: 50%;
}
.alert {
  position: absolute;
  top: 10%;
  left: 30%;
  width: 500px;
  height: 100px;
}
.like-box {
  width: 100px;
  height: 80px;
  border: 1px solid black;
  border-radius: 30%;
  cursor: pointer;
}
.like-icon {
  width: 60px;
  height: 80px;
  cursor: pointer;
}
</style>
