<template>
  <b-card>
    <h3>여행지 목록</h3>
    <b-row class="table-section">
      <b-table
        striped
        responsive
        id="attraction-table"
        sticky-header="600px"
        :fields="fields"
        :items="attractions"
      >
        <template #cell(title)="data">
          <p class="row-title" @click="openDetailModal(data.item)">{{ data.value }}</p>
        </template>
        <template #cell(selected)="row">
          <b-img v-if="row.item.isSelected" :src="require(`@/assets/img/icons/checked.png`)" />
          <b-img
            class="plusIcon"
            v-else
            :src="require(`@/assets/img/icons/plus.png`)"
            @click="selectOneRow(row.item, row.index)"
          />
        </template>
      </b-table>
    </b-row>
    <b-row>
      <b-button v-b-toggle.sidebar variant="outline-primary">찜한 여행지 확인</b-button>
    </b-row>
    <!-- sidebar 부분 start-->
    <b-sidebar id="sidebar" title="찜한 여행지" backdrop shadow width="800px">
      <b-container fluid>
        <b-row class="mt-3 mb-3">
          <b-col sm="3">
            <label class="subtitle" for="planTitle">여행 제목</label>
          </b-col>
          <b-col sm="9">
            <b-form-input
              id="planTitle"
              v-model="plan.planName"
              placeholder="ex) 여행 가보자구~"
            ></b-form-input>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="3">
            <label class="subtitle" for="datepicker">날짜</label>
          </b-col>
          <b-col sm="9">
            <b-form-datepicker
              id="datepicker"
              v-model="plan.planDate"
              class="mb-2"
            ></b-form-datepicker>
          </b-col>
        </b-row>
        <b-row class="attr-basket">
          <b-col sm="3">
            <label class="subtitle">여행 코스</label>
          </b-col>
          <b-col sm="9">
            <b-card no-body class="tab-day">
              <b-tabs card>
                <!-- Render Tabs, supply a unique `key` to each tab -->
                <b-tab
                  v-for="i in tabs"
                  :key="'dyn-tab-' + i"
                  :title="'Day ' + (i + 1)"
                  @click="moveTab(i)"
                >
                  <b-row v-for="(course, index) in plan.courses[i]" :key="index">
                    <span class="mr-3">{{ course.title }}</span>
                    <b-img
                      class="btn-remove"
                      :src="require(`@/assets/img/icons/x-button.png`)"
                      @click="removeOneRow(index)"
                    />
                  </b-row>
                </b-tab>
                <!-- New Tab Button (Using tabs-end slot) -->
                <template #tabs-end>
                  <b-nav-item role="presentation" @click.prevent="newTab" href="#">
                    <b>+</b>
                  </b-nav-item>
                  <b-nav-item role="presentation" @click.prevent="closeTab(tabCounter)" href="#">
                    <b style="color: red">-</b>
                  </b-nav-item>
                </template>
                <!-- Render this if no tabs -->
                <template #empty>
                  <div class="text-center text-muted">탭을 추가하여 여행일정을 계획하세요~</div>
                </template>
              </b-tabs>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
      <template #footer="{ hide }">
        <b-row class="mb-3 justify-content-md-center">
          <b-col cols="3">
            <b-button variant="danger" block @click="hide">닫기</b-button>
          </b-col>
          <b-col cols="3">
            <b-button variant="primary" block @click="makePlan">계획작성</b-button>
          </b-col>
        </b-row>
      </template>
    </b-sidebar>
    <!-- sidebar 부분 end-->
    <!-- 관광지 상세 Modal창 부분 start -->
    <b-modal
      v-if="attrDetail"
      size="lg"
      id="modal-detail"
      centered
      hide-footer
      :title="attrDetail.title"
      v-model="isShowDetail"
    >
      <b-img class="attrDetailImg" :src="attrDetail.firstImage"></b-img>
      <p class="my-4">{{ attrDetail.overview }}</p>
    </b-modal>
    <!-- 관광지 상세 Modal창 부분 end -->
  </b-card>
</template>
<script>
import { mapState } from "vuex";
import { writePlan } from "@/api/plan";

export default {
  name: "TripList",
  components: {},
  data() {
    return {
      fields: [
        {
          key: "title",
          label: "장소",
          thStyle: { width: "30%" },
        },
        {
          key: "addr1",
          label: "주소",
          thStyle: { width: "55%" },
        },
        {
          key: "selected",
          label: "선택",
          thStyle: { width: "15%" },
        },
      ],
      isShowDetail: false,
      attrDetail: null,
      isPlanShow: false,
      plan: {
        planName: "",
        planDate: "",
        courses: [],
      },
      selectedCnt: 0,
      tabs: [],
      tabCounter: 0,
      currentTab: 0,
    };
  },
  watch: {
    attractions: function () {
      /* 새로 검색될 때 마다 기존 attractions값이 갱신되어 
         isSelected값이 초기화 되기 때문에 변경될때마다
         plan.courses의 값과 비교하여 값을 재설정한다. 
      */
      if (this.attractions.length > 0) {
        this.plan.courses.forEach((course) => {
          course.forEach((place) => {
            for (let i = 0; i < this.attractions.length; i++) {
              if (this.attractions[i].contentId == place.contentId) {
                this.attractions[i].isSelected = true;
              }
            }
          });
        });
      }
    },
  },
  computed: {
    ...mapState("tripStore", ["attractions"]),
    ...mapState("userStore", ["userInfo", "isLogin"]),
  },
  created() {},
  methods: {
    selectOneRow(item, index) {
      if (this.plan.courses.length == 0) {
        alert("여행일정 탭을 추가한 뒤 관광지를 선택하세요");
        return;
      }
      this.attractions[index].isSelected = true;
      this.plan.courses[this.currentTab].push(item);
      this.selectedCnt++;
    },
    removeOneRow(index) {
      let seq = this.plan.courses[this.currentTab][index].seq;
      this.plan.courses[this.currentTab].splice(index, 1);
      this.attractions[seq].isSelected = false;
      this.selectedCnt--;
    },
    openDetailModal(item) {
      this.isShowDetail = true;
      this.attrDetail = item;
    },
    makePlan() {
      if (!this.isLogin) {
        alert("로그인 후 이용 가능한 서비스입니다.");
        this.$router.push({ name: "userLogin" });
        return;
      }
      if (!this.plan.planName) {
        alert("제목을 입력하세요!!!");
        return;
      }
      if (!this.plan.planDate) {
        alert("날짜를 선택하세요!!!");
        return;
      }
      if (this.selectedCnt == 0) {
        alert("여행지를 1개 이상 추가하세요!!!");
        return;
      }

      let paramCourse = new Array();

      this.plan.courses.forEach((course, index) => {
        let seq = new Array();
        course.forEach((place) => {
          seq.push(place.contentId);
        });
        let json = {
          day: index + 1,
          seq: seq,
        };
        paramCourse.push(json);
      });

      let params = {
        planName: this.plan.planName,
        userId: this.userInfo.id,
        period: this.tabCounter,
        startDate: this.plan.planDate,
        endDate: this.plan.planDate,
        course: paramCourse,
      };

      writePlan(params, ({ data }) => {
        if (data == "success") {
          alert("여행계획 작성이 완료되었습니다.");
          this.$router.push({ name: "plan" });
        } else {
          alert("여행계획 작성 중 에러가 발생했습니다.");
          return;
        }
      });
    },
    closeTab(x) {
      if (this.tabCounter == 0) {
        alert("탭이 존재하지 않습니다.");
        return;
      }
      if (this.plan.courses[x - 1].length > 0) {
        this.plan.courses[x - 1].forEach((course) => {
          this.attractions[course.seq].isSelected = false;
          this.selectedCnt--;
        });
      }
      this.tabs.splice(x - 1, 1);
      if (x >= 0) {
        this.plan.courses.splice(x - 1, 1);
        this.tabCounter--;
      }
    },
    newTab() {
      if (this.tabCounter >= 3) {
        alert("최대 3일까지 등록가능합니다.");
        return;
      }
      this.tabs.push(this.tabCounter++);
      this.plan.courses.push(new Array());
    },
    moveTab(day) {
      this.currentTab = day;
    },
  },
};
</script>

<style scoped>
.card {
  height: 750px;
}
.table-section {
  height: 620px;
}
.b-table-sticky-header {
  width: 100%;
}
.row-title {
  font-weight: bold;
  cursor: pointer;
}
.b-icon {
  cursor: pointer;
}
.attr-basket {
  margin-top: 10px;
  height: 300px;
}
.subtitle {
  font-weight: bold;
  font-size: 20px;
}
.list-group {
  min-height: 20px;
}

.list-group-item {
  width: 100%;
  cursor: move;
}

.list-group-item i {
  cursor: pointer;
}
.btn-remove {
  cursor: pointer;
}
.attrDetailImg {
  width: 700px;
  height: 400px;
}
.plusIcon {
  cursor: pointer;
}
.tab-day {
  height: 600px;
}
</style>
