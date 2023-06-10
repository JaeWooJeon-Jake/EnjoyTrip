<template>
  <b-container>
    <h1 class="mb-3">회원가입</h1>
    <b-card>
      <b-form @submit.stop.prevent>
        <b-form-group id="input-group-1" label="아이디" label-for="userId">
          <b-row>
            <b-col cols="10">
              <b-form-input
                id="userId"
                v-model="user.id"
                type="text"
                placeholder="아이디를 입력하세요"
                :state="isDuplicate"
                required
              ></b-form-input>
            </b-col>
            <b-col cols="2">
              <b-button variant="success" @click="checkId">중복체크</b-button>
            </b-col>
          </b-row>
          <b-form-invalid-feedback :state="isDuplicate">
            중복된 아이디 입니다.
          </b-form-invalid-feedback>
          <b-form-valid-feedback :state="isDuplicate">
            사용가능한 아이디 입니다.
          </b-form-valid-feedback>
        </b-form-group>
        <b-form-group id="input-group-2" label="비밀번호" label-for="userPwd">
          <b-form-input
            id="userPwd"
            v-model="user.password"
            type="password"
            placeholder="비밀번호를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-3" label="이름" label-for="userNmae">
          <b-form-input
            id="userNmae"
            v-model="user.name"
            type="text"
            placeholder="이름을 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-4" label="이메일" label-for="userEmail">
          <b-form-input
            id="userEmail"
            v-model="user.email"
            type="email"
            placeholder="ex) ssafy@ssafy.com"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-5" label="주소" label-for="userAddress">
          <b-form-input
            id="userAddress"
            v-model="user.address"
            type="text"
            placeholder="주소를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="input-group-6" label="휴대폰 번호" label-for="userPhone">
          <b-form-input
            id="userPhone"
            v-model="user.phone"
            type="text"
            placeholder="휴대폰 번호를 입력하세요"
            required
          ></b-form-input>
        </b-form-group>
        <b-button class="mr-3" type="button" variant="primary" @click="regist">회원가입</b-button>
        <b-button type="button" variant="danger" @click="move">돌아가기</b-button>
      </b-form>
    </b-card>
  </b-container>
</template>

<script>
import { checkUserId, join } from "@/api/user";

export default {
  name: "userRegist",
  components: {},
  data() {
    return {
      selected: "first",
      options: [
        { text: "First radio", value: "first" },
        { text: "Second radio", value: "second" },
        { text: "Third radio", value: "third" },
      ],
      user: {
        id: "",
        password: "",
        name: "",
        email: "",
        address: "",
        phone: "",
      },
      isDuplicate: null,
      isShow: false,
    };
  },
  created() {},
  methods: {
    checkId() {
      if (!this.user.id) {
        alert("아이디를 입력하세요!!!");
        return;
      }
      checkUserId(
        this.user.id,
        ({ data }) => {
          this.isDuplicate = data;
          this.isShow = true;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    regist() {
      if (!this.user.id) {
        alert("아이디를 입력하세요!!!");
        return;
      }
      if (!this.user.password) {
        alert("비밀번호를 입력하세요!!!");
        return;
      }
      if (!this.user.name) {
        alert("이름을 입력하세요!!!");
        return;
      }
      if (!this.isDuplicate) {
        alert("아이디 중복체크 하세요!!!");
        return;
      }
      join(
        this.user,
        ({ data }) => {
          if (data === "success") {
            alert("회원가입 되었습니다!!!");
            this.$router.push({ name: "main" });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    move() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style scoped></style>
