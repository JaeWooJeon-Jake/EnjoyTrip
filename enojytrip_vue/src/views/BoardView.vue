<template>
  <router-view class="main">
  </router-view>
</template>
<script>

export default {
  name: "BoardView",
  components: {

  },
  data() {
    return {
    };
  },
  created() {
    // this.CLEAR_BOARD_LIST();
    // this.getBoardList();
  },
  methods: {
    // ...mapActions(boardStore, ["getBoardList"]),
    // ...mapMutations(boardStore,["CLEAR_BOARD_LIST"]),
  },
  computed: {
    // ...mapState(boardStore, ["boards"]),
  }
};
</script>


<style scoped>
.main {
  /* position: relative;
  margin-top: 150px;
  padding-bottom: 20px; */
  position: relative;
  min-height: calc(100vh - 260px);
  margin-left: 5%;
  margin-right: 5%;
}
</style>
