<template>
  <!--    <CAlert :show.sync="dismissCountDown" closeButton :color="colorMsg" fade>-->
  <!--      <strong>{{ messagesError.header }}</strong-->
  <!--      ><br />-->
  <!--      <strong>{{ messagesError.body }}</strong>-->
  <!--    </CAlert>-->
  <CRow>
    <CCol sm="6" md="12">
      <CCard border-color="success">
        <CCardHeader>Phân quyền tài khoản</CCardHeader>
        <CCardBody>
          <CRow>
            <CCol lg="3">
              <CpmTable
                border
                hover
                :header="false"
                :items="dataBm"
                clickable-rows
                v-on:rowClick="rowsClickBm"
                :delete-row="false"
                :caption="'Tài khoản BM'"
              >
              </CpmTable>
            </CCol>
            <CCol lg="3">
              <CpmTable
                border
                hover
                :header="false"
                :items="dataAds"
                :fields="fieldsAds"
                clickable-rows
                v-on:rowClick="rowsClickAds"
                :delete-row="false"
                :caption="'Tài khoản quảng cáo'"
              >
              </CpmTable>
            </CCol>
            <CCol lg="6">
              <CpmTable
                :fields="fields"
                hover
                :header="false"
                :items="dataUser"
                clickable-rows
                :delete-row="false"
                :caption="'Phân quyền người dùng'"
              >
              </CpmTable>
            </CCol>
          </CRow>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>

<script>
import BmService from "../../../js/services/bm/bm.service";
import FbService from "../../../js/services/facebook/fb.service";
import CpmTable from "../base/table/CpmTable";
import * as constantUtils from "../../../js/constantUtils";

export default {
  name: "AccountAuthorization",
  components: { CpmTable },
  data() {
    return {
      dataBm: null,
      dataAds: null,
      dataUser: null,
      content: null,
      fieldsAds: [
        { key: "id", _classes: "d-none" },
        { key: "idMaster", _classes: "d-none" },
        { key: "token", _classes: "d-none" },
        { key: "name", _classes: "text-left", _style: { width: "40%" } },
      ],
      fields: [
        { key: "id", _classes: "d-none" },
        { key: "name", _style: { width: "40%" } },
        {
          key: "show_details",
          label: "",
          _style: { width: "1%" },
          sorter: false,
          filter: false,
        },
      ],
    };
  },
  created: async function () {
    await this.init();
  },
  methods: {
    init: async function () {
      await BmService.getListBmMaster().then(
        (response) => {
          this.dataBm = this.transportDataMasterBm(response.data);
        },
        (error) => {
          this.content =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },

    transportDataMasterBm(data) {
      let _re = [];
      data.forEach((item) => {
        _re.push({
          idBm: {
            url: item.profile_picture_uribm,
            status: "success",
            id: item.idbm,
            name: item.namebm,
            token: item.tokenbm,
            nameMaster: item.name,
          },
        });
      });

      return _re;
    },

    rowsClickBm: async function (dataBm) {
      // Clear data user
      this.dataUser = [];
      await FbService.getListBm(dataBm).then(
        (response) => {
          //data body
          this.dataAds = this.transportDataAds(dataBm, response.data.data);
        },
        (error) => {
          this.content =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },

    rowsClickAds: async function (dataAds) {

      const data = {
        id: dataAds.id,
        token: dataAds.token,
        idBm: dataAds.idMaster,
      };
      await FbService.getListUser(data).then(
        (response) => {
          //data body
          this.dataUser = this.transportDataUser(response.data.data);
        },
        (error) => {
          this.content =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },

    transportDataUser: function (dataUser) {
      let _re = [];
      // data body
      dataUser.forEach((item) => {
        _re.push({
          id: item.id,
          name: item.name,
        });
      });
      return _re;
    },

    transportDataAds(dataBm, dataAds) {
      let _re = [];

      // data body
      dataAds.forEach((item) => {
        _re.push({
          id: item.id,
          idMaster: item.business.id,
          token: dataBm.idBm.token,
          name: item.name,
        });
      });

      return _re;
    },
  },
};
</script>

<style scoped></style>
