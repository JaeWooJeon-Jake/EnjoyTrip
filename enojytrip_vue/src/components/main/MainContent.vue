<template>
  <div>
    <div class="frequent-service">
      <h3 class="section-title">자주 이용하는 서비스</h3>
      <b-card-group deck>
        <b-card
          class="section"
          v-for="(image, index) in images"
          :key="index"
          :img-src="require(`@/assets/img/mainSection/${image.name}`)"
          @click="move(image.url)"
        >
          <b-card-text> {{ image.text }} </b-card-text>
        </b-card>
      </b-card-group>
    </div>
    <div class="recommend-video">
      <h3 class="section-title">국내 인기 여행 영상 추천</h3>
      <b-card-group deck>
        <b-card
          class="section"
          v-for="(video, index) in videos"
          :key="index"
          :img-src="require(`@/assets/img/youtube/${video.thumbnail}`)"
          @click="moveSite(video.url)"
        >
        </b-card>
      </b-card-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainContent",
  components: {},
  data() {
    return {
      images: [],
      videos: [],
    };
  },
  created() {
    this.images = [
      {
        name: "attractionSection.jpg",
        text: "여행지 검색",
        url: "trip",
      },
      {
        name: "planSection.jpg",
        text: "인기 여행코스",
        url: "planList",
      },
      {
        name: "boardSection.jpg",
        text: "인기 게시글",
        url: "board",
      },
    ];
    this.videos = [
      {
        thumbnail: "bbanibottle.jpg",
        url: "_CYQlxIkXn0",
      },
      {
        thumbnail: "kwaktube.jpg",
        url: "valwrFtlzRc",
      },
      {
        thumbnail: "chaecoje.jpg",
        url: "Txq-pV3-lfs",
      },
    ];
  },
  methods: {
    move(url) {
      this.$router.push({ name: url });
    },
    moveSite(url) {
      window.open(`https://youtu.be/${url}`);
    },
  },
};
</script>

<style scoped>
.section-title {
  font-weight: bold;
}
.card-img {
  height: 100%;
  cursor: pointer;
}
.card-text {
  position: absolute;
  top: 50%;
  left: 32%;
  font-weight: bold;
  font-size: 20px;
  text-shadow: 3px 3px 2px rgb(218, 218, 218);
}
.frequent-service {
  margin-top: 7%;
}
.recommend-video {
  margin-top: 7%;
}
</style>
