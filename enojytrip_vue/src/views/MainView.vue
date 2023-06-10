<template>
  <div class="main">
    <main-carousel />
    <main-content />
  </div>
</template>

<script>
import MainCarousel from "@/components/main/MainCarousel";
import MainContent from "@/components/main/MainContent";

export default {
  name: "MainView",
  components: {
    MainCarousel,
    MainContent,
  },
  data() {
    return {};
  },
  created() {},
  methods: {},
};
</script>

<style scoped>
.main {
  position: relative;
  margin-left: 15%;
  margin-right: 15%;
}
</style>
