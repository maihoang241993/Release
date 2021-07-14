<template>
  <div>
    <CRow>
      <CCol sm="6" md="12">
        <CCard border-color="success">
          <CCardHeader>Danh sách Cookies</CCardHeader>
          <CCardBody>
            <CRow>
              <CCol>
                <CpmDataTable
                    :data-items="dataCookies"
                    :table-fields="tableFields"
                    :is-show-card-header="false"
                >
                </CpmDataTable>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  </div>
</template>

<script>
import CpmDataTable from "@/views/screen/base/table/CpmDataTable";
import CookiesService from "../../../js/services/cookies/cookies.service";

export default {
  name: "Cookies",
  components: {CpmDataTable},
  data() {
    return {
      dataCookies: null,
      tableFields: [
        {
          key: "no",
          label: "No",
          _classes: "text-center",

          _style: {width: "5px"},
        },
        {
          key: "user_id",
          label: "Account Id",
          _classes: "text-center",

          _style: {width: "10px"},
        },
        {key: "cookie", label: "Cookie", _classes: "text-center"},
      ],
    };
  },
  created() {
    this.init();
  },
  methods: {
    init: async function () {
      await CookiesService.getListCookies().then(
          (response) => {
            this.dataCookies = this.transportDataCookies(response.data);
          },
          (error) => {
            this.$showMessages(
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString(), this.msg4);
          }
      );
    },

    transportDataCookies(data) {
      let _arr = [];
      let i = 1;
      data.forEach((item) => {
        _arr.push({
          no: i,
          user_id: item.user_id,
          cookie: item.cookie,
        });
        i++;
      });
      return _arr;
    },
  },
};
</script>

<style scoped></style>
