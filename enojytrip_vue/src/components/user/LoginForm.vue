<template>
  <b-container>
    <h1 class="mb-3">로그인</h1>
    <b-card>
      <b-form>
        <b-form-group id="input-group-1" label="아이디">
          <b-form-input id="userId" v-model="user.id" placeholder="ID" required></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-2" label="비밀번호">
          <b-form-input
            type="password"
            id="userPwd"
            v-model="user.password"
            placeholder="PASSWORD"
            required
          ></b-form-input>
        </b-form-group>
        <b-button type="button" @click="confirm" variant="primary" align-self="end"
          >로그인</b-button
        >
      </b-form>
    </b-card>
    <b-row class="mt-3">
      <b-col cols="8">
        <span>아직 회원이 아니신가요?</span>
      </b-col>
      <b-col cols="4">
        <router-link :to="{ name: 'userRegist' }">회원가입 하러가기</router-link>
      </b-col>
    </b-row>
    <b-row class="mt-3">
      <b-col cols="12">
        <b-button v-b-modal.modal-center variant="outline-primary">ID/PW 찾기</b-button>
      </b-col>
    </b-row>
    <!-- ID/PW 찾기 모달 START -->
    <b-modal id="modal-center" hide-footer centered title="ID/PW 찾기">
      <!-- <b-nav tabs>
        <b-nav-item
          v-for="(tab, index) in tabs"
          :key="index"
          :class="{ active: currentTab === index }"
          @click="currentTab = index"
          >{{ tab.name }}
        </b-nav-item>
      </b-nav> -->
      <b-tabs content-class="mt-3">
        <b-tab title="ID찾기" active>
          <b-row class="my-1">
            <b-col sm="4">
              <label for="input-default">이름:</label>
            </b-col>
            <b-col sm="8">
              <b-form-input
                id="input-default"
                placeholder="김싸피"
                v-model="searchInfo.name"
              ></b-form-input>
            </b-col>
          </b-row>
          <b-row class="my-1">
            <b-col sm="4">
              <label for="input-default">전화번호:</label>
            </b-col>
            <b-col sm="8">
              <b-form-input
                id="input-default"
                placeholder="010-0000-0000"
                v-model="searchInfo.phone"
              ></b-form-input>
            </b-col>
            <b-button variant="outline-primary" @click="searchUserId">찾기</b-button>
          </b-row>
          <b-row class="my-1">
            <p>결과: {{ searchResult != null ? searchResult : "회원이 존재하지 않습니다." }}</p>
          </b-row>
        </b-tab>
        <b-tab title="PW찾기">
          <b-row class="my-1">
            <b-col sm="4">
              <label for="input-default">아이디:</label>
            </b-col>
            <b-col sm="8">
              <b-form-input
                id="input-default"
                placeholder="김싸피"
                v-model="searchInfo.id"
              ></b-form-input>
            </b-col>
          </b-row>
          <b-row class="my-1">
            <b-col sm="4">
              <label for="input-default">이름:</label>
            </b-col>
            <b-col sm="8">
              <b-form-input
                id="input-default"
                placeholder="홍길동"
                v-model="searchInfo.name"
              ></b-form-input>
            </b-col>
            <b-button variant="outline-primary" @click="searchUserPwd">찾기</b-button>
          </b-row>
          <b-row class="my-1">
            <p>결과: {{ searchResult != null ? searchResult : "회원이 존재하지 않습니다." }}</p>
          </b-row>
        </b-tab>
      </b-tabs>
    </b-modal>
    <!-- ID/PW 찾기 모달 END -->
  </b-container>
</template>

<script>
import { mapActions, mapState } from "vuex";
import { searchId, searchPwd } from "@/api/user";

const userStore = "userStore";
export default {
  name: "userLogin",
  components: {},
  data() {
    return {
      user: {
        id: null,
        password: null,
      },
      tabs: [{ name: "ID찾기" }, { name: "PW찾기" }],
      currentTab: 0,
      searchInfo: {
        id: "",
        name: "",
        phone: "",
      },
      searchResult: "",
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  created() {},
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      // console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        this.$router.push({ name: "main" });
      }
    },
    movePage() {
      this.$router.push({ name: "join" });
    },
    searchUserId() {
      let params = {
        name: this.searchInfo.name,
        phone: this.searchInfo.phone,
      };
      searchId(
        params,
        ({ data }) => {
          if (data.message === "success") {
            this.searchResult = data.userId;
          } else {
            alert("ID찾기 중 오류가 발생했습니다.");
            return;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchUserPwd() {
      let params = {
        id: this.searchInfo.id,
        name: this.searchInfo.name,
      };
      searchPwd(
        params,
        ({ data }) => {
          if (data.message === "success") {
            this.searchResult = data.userPwd;
          } else {
            alert("PWD찾기 중 오류가 발생했습니다.");
            return;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style scoped>
.container {
  padding-top: 100px;
}
.card {
  max-width: 1024px;
  height: 250px;
}
</style>
