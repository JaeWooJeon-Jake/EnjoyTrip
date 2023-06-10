<template>
  <div id="app">
    <header-view />
    <blank-space />
    <router-view />
    <footer-view />
  </div>
</template>
<script>
import HeaderView from "@/components/common/HeaderView";
import FooterView from "@/components/common/FooterView";
import BlankSpace from "@/components/common/BlankSpace";

export default {
  name: "App",
  components: {
    HeaderView,
    FooterView,
    BlankSpace,
  },
};
</script>
<style scoped>
#app {
  min-height: calc(100vh - 70px);
}
</style>
