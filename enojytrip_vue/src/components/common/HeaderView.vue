<template>
  <header>
    <b-navbar toggleable="lg" class="nav-bar">
      <b-navbar-brand id="logo" class="mb-0"
        ><router-link :to="{ name: 'main' }">LuckyTrip</router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'trip' }">여행떠나기</b-nav-item>
          <b-nav-item :to="{ name: 'board' }">일반게시판</b-nav-item>
          <b-nav-item :to="{ name: 'plan' }">여행일정</b-nav-item>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav v-if="isLogin" class="ml-auto">
          <b-list-group>
            <b-list-group-item class="d-flex align-items-center">
              <!-- <b-avatar></b-avatar> -->
              <img
                @click="showModal"
                v-if="userInfo != null"
                :src="`https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/${
                  emoji.items[userInfo.emoji]
                }.png`"
                alt="Ghost"
                width="25"
                height="25"
                style="margin-right: 10px"
              />
              <b-dropdown
                variant="primary"
                id="dropdown"
                v-if="userInfo != null"
                :text="userInfo.name"
                right
              >
                <b-dropdown-item :to="{ name: 'userMypage' }">마이페이지</b-dropdown-item>
                <b-dropdown-divider></b-dropdown-divider>
                <b-dropdown-item @click="logout">로그아웃</b-dropdown-item>
              </b-dropdown>
            </b-list-group-item>
          </b-list-group>
        </b-navbar-nav>
        <b-navbar-nav v-else class="ml-auto">
          <b-nav-item :to="{ name: 'userLogin' }">로그인</b-nav-item>
          <b-nav-item :to="{ name: 'userRegist' }">회원가입</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <!-- 모달창Start -->
    <div>
      <b-modal ref="my-modal" hide-footer title="Emoji 변경">
        <div class="d-block text-center">
          <b-row cols="3">
            <b-col v-for="(item, index) in emoji.items" :key="index">
              <img
                :src="`https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/${item}.png`"
                :alt="`${item}`"
                width="100%"
                height="100%"
                @click="changeEmoji(index)"
              />
            </b-col>
          </b-row>
        </div>
        <b-button class="mt-3" variant="outline-primary" block @click="hideModal"
          >Close Me</b-button
        >
      </b-modal>
    </div>
    <!-- 모달창 END -->
  </header>
</template>

<script>
import { mapState, mapActions, mapGetters,mapMutations } from "vuex";
import { changeEmoji } from "@/api/user";
export default {
  name: "HeaderView",
  components: {},
  data() {
    return {};
  },
  computed: {
    ...mapState("userStore", ["isLogin", "userInfo", "emoji"]),
    ...mapGetters(["checkUserInfo"]),
  },
  created() {},
  methods: {
    ...mapActions("userStore", ["userLogout"]),
    ...mapMutations("userStore",["SET_USER_EMOJI"]),
    logout() {
      // console.log(this.userInfo.id);
      this.userLogout(this.userInfo.id);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "main" });
    },
    changeEmoji(index) {
      changeEmoji(
        index,
        ({ data }) => {
          // console.log(data);
          this.SET_USER_EMOJI(data.emoji);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    showModal() {
      this.$refs["my-modal"].show();
    },
    hideModal() {
      this.$refs["my-modal"].hide();
    },
    // toggleModal() {
    //   // We pass the ID of the button that we want to return focus to
    //   // when the modal has hidden
    //   this.$refs['my-modal'].toggle('#toggle-btn')
    // }
  },
};
</script>

<style scoped>
.nav-bar {
  position: fixed;
  top: 0;
  width: 100%;
  box-shadow: 0px 0px 0px 1px;
  z-index: 10;
  background: #f8f8ff;
  opacity: 90%;
}
.navbar-toggler {
  background-color: #f9fbe7;
}
#logo {
  font-weight: bold;
  font-size: 50px;
  margin-right: 50px;
}

#logo > a {
  color: skyblue;
  text-decoration: none;
}

.nav-link {
  font-size: 20px;
  font-weight: bold;
  color: #00235b;
  line-height: 15px;
  padding-top: 20px;
  opacity: 100%;
}
</style>
