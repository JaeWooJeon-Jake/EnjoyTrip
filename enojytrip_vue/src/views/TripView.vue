<template>
  <div class="main">
    <trip-main />
  </div>
</template>

<script>
import tripMain from "@/components/trip/TripMain";

export default {
  name: "TripView",
  components: {
    tripMain,
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

<style scoped>
.main {
  position: relative;
  margin-left: 5%;
  margin-right: 5%;
}
</style>
