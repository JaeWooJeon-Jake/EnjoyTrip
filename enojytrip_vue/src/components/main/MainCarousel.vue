<template>
  <b-carousel
    id="carousel"
    v-model="slide"
    :interval="4000"
    controls
    indicators
    background="#ababab"
    style="text-shadow: 1px 1px 2px #333"
  >
    <b-carousel-slide v-for="(image, index) in images" :key="index">
      <template #img>
        <img
          class="d-block img-fluid w-100"
          :src="require(`@/assets/img/carousel/${image.name}`)"
        />
      </template>
      <h1>{{ image.title }}</h1>
    </b-carousel-slide>
  </b-carousel>
</template>

<script>
export default {
  name: "MainCarousel",
  components: {},
  data() {
    return {
      images: [],
      slide: 0,
      sliding: null,
    };
  },
  created() {
    this.images = [
      {
        name: "kwanghwa-gate.jpg",
        title: "광화문",
      },
      {
        name: "kwangan-bridge.jpg",
        title: "광안대교",
      },
      {
        name: "dokdo.jpg",
        title: "독도",
      },
    ];
  },
  methods: {},
};
</script>

<style scoped>
img {
  width: 1024px;
  height: 720px;
  object-fit: cover;
}
</style>
