<template>
  <div>
    <CAlert :show.sync="dismissCountDown" closeButton :color="colorMsg" fade>
      <strong>{{ messagesError.header }}</strong
      ><br/>
      <strong>{{ messagesError.body }}</strong>
    </CAlert>
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
                <CpmDataTable
                    :data-items="dataUser"
                    :table-fields="fields"
                    :is-show-change-permission="true"
                    v-on:updatePermission="updatePermission"
                >
                </CpmDataTable>
                <!--              <CpmTable-->
                <!--                :fields="fields"-->
                <!--                hover-->
                <!--                :header="false"-->
                <!--                :items="dataUser"-->
                <!--                clickable-rows-->
                <!--                :delete-row="false"-->
                <!--                :caption="'Phân quyền người dùng'"-->
                <!--              >-->
                <!--              </CpmTable>-->
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  </div>
</template>

<script>
import BmService from "../../../js/services/bm/bm.service";
import FbService from "../../../js/services/facebook/fb.service";
import CpmTable from "../base/table/CpmTable";
import * as objectUitls from "../../../js/utils/objectUtils";
import CpmDataTable from "@/views/screen/base/table/CpmDataTable";

export default {
  name: "AccountAuthorization",
  components: {CpmDataTable, CpmTable},
  data() {
    return {
      dataBm: null,
      dataAds: null,
      dataUser: null,
      content: null,
      fieldsAds: [
        {key: "id", _classes: "d-none"},
        {key: "idMaster", _classes: "d-none"},
        {key: "token", _classes: "d-none"},
        {key: "name", _classes: "text-left", _style: {width: "40%"}},
      ],
      fields: [
        {key: "id", _classes: "d-none"},
        {
          key: "checkbox",
          _style: {width: "10px"},
          label: "",
          _classes: "col-md-1",
        },
        {
          key: "name",
          label: "Name",
          _classes: "text-center",
          _style: {width: "40%"},
        },
        {key: "statusPermission", label: "Status", _classes: "text-center"},
        // {
        //   key: "show_details",
        //   label: "",
        //   _style: { width: "1%" },
        //   sorter: false,
        //   filter: false,
        // },
      ],

      messagesError: {
        header: null,
        body: null,
      },
      colorMsg: null,
      dismissCountDown: 0,
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
      const data = {
        id: dataBm.idBm.id,
        token: dataBm.idBm.token,
      };
      let result1;
      let result2;
      await FbService.getListAccount(data).then(
          (response) => {
            //data body
            result1 = this.transportDataAds(dataBm, response.data.data);
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

      await FbService.getListPartnerAccount(data).then(
          (response) => {
            //data body
            if (
                !objectUitls.isNullOrEmpty(response.data.data) &&
                response.data.data.length > 0
            ) {
              result2 = this.transportDataAds(dataBm, response.data.data);
            }
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
      let dataAfter = null;
      if (!objectUitls.isNullOrEmpty(result1) && result1.length > 0) {
        dataAfter = result1;
      }
      if (!objectUitls.isNullOrEmpty(result2) && result2.length > 0) {
        result2.forEach((obj) => {
          dataAfter.push(obj);
        });
      }
      this.dataAds = dataAfter;
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
            this.dataUser = this.transportDataUser(dataAds, response.data.data);
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

    transportDataUser: function (dataAds, dataUser) {
      let _re = [];
      // data body
      dataUser.forEach((item) => {
        _re.push({
          dataAds: dataAds,
          isCheck: false,
          id: item.id,
          name: item.name,
          statusPermission: "",
        });
      });
      return _re;
    },

    transportDataAds(dataBm, dataAds) {
      let _re = [];

      // data body
      dataAds.forEach((item) => {
        if (!objectUitls.isNullOrEmpty(item.business)) {
          _re.push({
            dataBm: dataBm,
            id: item.id,
            idMaster: item.business.id,
            token: dataBm.idBm.token,
            name: item.name,
          });
        }
      });

      return _re;
    },

    updatePermission: async function (flagChangePermission) {
      let loader = this.$loading.show({
        // Optional parameters
        container: this.$refs.formContainer,
        isFullPage: false,
        canCancel: false,
      });
      let intSuccess = 0;
      let intDanger = 0;

      for (let i = 0; i < this.dataUser.length; i++) {
        const item = this.dataUser[i];
        if (item.isCheck) {
          const data = {
            account_id: item.dataAds.id,
            business: item.dataAds.dataBm.idBm.id,
            token: item.dataAds.dataBm.idBm.token,
            userId: item.id,
          };
          let result = await FbService.changePermissionAcount(
              data,
              flagChangePermission
          ).then(
              (response) => {
                return true;
              },
              (error) => {
                return false;
                this.content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
              }
          );
          if (result) {
            await this.updateStatus(item.id, "Thành công");
            intSuccess += 1;
          } else {
            await this.updateStatus(item.id, "Thất bại");
            intDanger += 1;
          }
        }
      }
      loader.hide();
      this.showMessages(
          0,
          "Xử lý hoàn tất",
          "Thành công: " + intSuccess + ", Thất Bại: " + intDanger
      );
    },

    updateStatus: async function (accountId, active) {
      this.dataUser.forEach((item) => {
        if (item.id === accountId) {
          item.statusPermission = active;
        }
      });
    },

    showMessages: async function (code, header, body = "") {
      this.messagesError = {
        header: header,
        body: body,
      };
      this.colorMsg = code == 1 ? "danger" : "success";
      this.dismissCountDown = 5;
    },
  },
};
</script>

<style scoped></style>
