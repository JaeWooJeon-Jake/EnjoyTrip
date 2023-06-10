<template>
  <b-container>
    <h1 class="mb-4">마이페이지</h1>
    <b-card>
      <b-form>
        <b-form-group id="input-group-1" label="아이디" label-for="userId">
          <b-form-input
            id="userId"
            v-model="myPageUserInfo.id"
            type="text"
            placeholder="아이디를 입력하세요"
            readonly
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-2" label="비밀번호" label-for="userPwd">
          <b-form-input
            id="userPwd"
            v-model="myPageUserInfo.password"
            type="password"
            placeholder="비밀번호를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-3" label="이름" label-for="userName">
          <b-form-input
            id="userName"
            v-model="myPageUserInfo.name"
            type="text"
            placeholder="이름을 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-4" label="이메일" label-for="userEmail">
          <b-form-input
            id="userEmail"
            v-model="myPageUserInfo.email"
            type="email"
            placeholder="ex) ssafy@ssafy.com"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-5" label="주소" label-for="userAddress">
          <b-form-input
            id="userAddress"
            v-model="myPageUserInfo.address"
            type="text"
            placeholder="주소를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-6" label="휴대폰 번호" label-for="userPhone">
          <b-form-input
            id="userPhone"
            v-model="myPageUserInfo.phone"
            type="text"
            placeholder="휴대폰 번호를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-button type="button" @click="modify" variant="primary" class="mr-3">수정</b-button>
        <b-button type="button" @click="move" variant="danger">돌아가기</b-button>
      </b-form>
    </b-card>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "userMyPage",
  components: {},
  data() {
    return {
      myPageUserInfo: {
        id: "",
        password: "",
        name: "",
        email: "",
        address: "",
        phone: "",
      },
    };
  },
  computed: {
    ...mapState("userStore", ["userInfo"]),
  },
  created() {
    this.myPageUserInfo.id = this.userInfo.id;
    this.myPageUserInfo.name = this.userInfo.name;
    this.myPageUserInfo.email = this.userInfo.email;
    this.myPageUserInfo.address = this.userInfo.address;
    this.myPageUserInfo.phone = this.userInfo.phone;
  },
  methods: {
    ...mapActions("userStore", ["modifyUserInfo"]),
    modify() {
      this.modifyUserInfo(this.myPageUserInfo);
      this.$router.push({ name: "main" });
    },
    move() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style scoped></style>
