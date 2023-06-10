<template>
  <div id="map"></div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "KakaoMap",
  components: {},
  data() {
    return {
      map: null,
      markers: [],
      positions: [],
      overlay: null,
    };
  },
  watch: {
    attractions: function () {
      /*
        attractions가 초기화 된 후 새로운 값이 갱신되어 2번 호출되기 때문에
        리스트가 존재할 경우에만 다음 메소드 호출 되도록 함
      */
      if (this.attractions.length > 0) {
        this.positions = [];
        this.attractions.forEach((attraction) => {
          this.positions.push({
            title: attraction.title,
            latlng: new kakao.maps.LatLng(attraction.latitude, attraction.longitude),
            img: attraction.firstImage,
            addr1: attraction.addr1,
          });
        });
        this.loadMarker();
      }
    },
  },
  computed: {
    ...mapState("tripStore", ["attractions"]),
  },
  created() {},
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" +
        process.env.VUE_APP_KAKAO_MAP_API_KEY;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(37.50126267077286, 127.03959334233862),
        level: 7,
      };

      this.map = new kakao.maps.Map(container, options);

      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    },
    loadMarker() {
      // 기존에 지도에 표시 된 마커 제거
      this.deleteMarker();
      // 마커 이미지의 이미지 주소입니다
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      this.markers = [];
      this.positions.forEach((position) => {
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);
        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커의 이미지
          clickable: true,
        });
        this.markers.push(marker);
      });
      // 지도를 이동합니다
      this.moveMap();
    },
    makeCustomOverlay(marker, position) {
      // 커스텀 오버레이에 표시할 컨텐츠 입니다
      // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
      // 별도의 이벤트 메소드를 제공하지 않습니다
      var content = `<div class="wrap">
        <div class="info">
                <div class="title">
                ${position.title}
                <div class="close" @click="${this.closeOverlay}" title="닫기"></div>
               </div>
                <div class="body">
                    <div class="img">
                        <img src="${position.img}" width="73" height="70">
                  </div>
                    <div class="desc">
                        <div class="ellipsis">${position.addr1}</div>
                    </div>
                </div>
            </div>
        </div>`;
      // 마커 위에 커스텀오버레이를 표시합니다
      // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
      var overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: this.map,
        position: marker.getPosition(),
      });
      this.overlays.push(overlay);
      // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
      kakao.maps.event.addListener(marker, "click", function () {
        overlay.setMap(this.map);
      });
    },
    // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
    closeOverlay(overlay) {
      console.log("닫기");
      overlay.setMap(null);
    },
    deleteMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
    },
    moveMap() {
      let lat = this.attractions[0].latitude;
      let lng = this.attractions[0].longitude;
      let moveLocation = new kakao.maps.LatLng(lat, lng);
      this.map.panTo(moveLocation);
    },
    // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: calc(100vh - 350px);
}
@import "../../../assets/css/style.css";
</style>
