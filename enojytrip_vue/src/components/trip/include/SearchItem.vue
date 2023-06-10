<template>
  <b-card>
    <b-form-select v-model="sidoCode" :options="sidos" @change="changeSido"></b-form-select>
    <b-form-select v-model="gugunCode" :options="guguns"></b-form-select>
    <b-form-select v-model="contentTypeId" :options="contentTypeList"></b-form-select>
    <b-button @click="search">검색하기</b-button>
  </b-card>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "SearchItem",
  components: {},
  data() {
    return {
      contentTypeId: null,
      sidoCode: null,
      gugunCode: null,
    };
  },
  computed: {
    ...mapState("tripStore", ["sidos", "guguns", "contentTypeList"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_ATTRACTION_LIST();
    this.getSidos();
  },
  methods: {
    ...mapActions("tripStore", ["getSidos", "getGuguns", "getAttractions"]),
    ...mapMutations("tripStore", [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_ATTRACTION_LIST",
      "SET_CURRENT_PAGE",
    ]),
    changeSido() {
      (this.gugunCode = null), this.CLEAR_GUGUN_LIST();
      this.getGuguns(this.sidoCode);
    },
    search() {
      if (!this.sidoCode) {
        alert("지역을 선택해주세요");
        return;
      }
      if (!this.gugunCode) {
        alert("동네를 선택해주세요");
        return;
      }
      if (!this.contentTypeId) {
        alert("관광지 유형을 선택해주세요");
        return;
      }
      let params = {
        contentTypeId: this.contentTypeId,
        sidoCode: this.sidoCode,
        gugunCode: this.gugunCode,
      };
      this.CLEAR_ATTRACTION_LIST();
      this.getAttractions(params);
    },
  },
};
</script>

<style scoped>
.card {
  height: 100px;
  margin-bottom: 30px;
}
.custom-select {
  width: 25%;
  height: 50px;
  margin-right: 3%;
}
</style>
