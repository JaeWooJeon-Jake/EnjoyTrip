<template>
  <div class="main">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "PlanView",
  components: {},
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
  min-height: calc(100vh - 260px);
  margin-left: 5%;
  margin-right: 5%;
}
</style>
