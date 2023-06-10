<template>
  <router-view class="main"></router-view>
</template>

<script>
export default {
  name: "UserView",
  components: {},
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
  min-height: calc(100vh - 260px);
}
</style>
