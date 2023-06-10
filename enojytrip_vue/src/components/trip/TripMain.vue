<template>
  <b-row>
    <b-col md="6">
      <search-item />
      <kakao-map />
    </b-col>
    <b-col md="6">
      <trip-list />
    </b-col>
  </b-row>
</template>
<script>
import kakaoMap from "@/components/trip/include/KakaoMap";
import SearchItem from "@/components/trip/include/SearchItem";
import TripList from "@/components/trip/include/TripList";

export default {
  name: "TripMain",
  components: {
    kakaoMap,
    SearchItem,
    TripList,
  },
  data() {
    return {
      message: "",
    };
  },
  created() {},

  methods: {},
};
</script>

<style scoped></style>
