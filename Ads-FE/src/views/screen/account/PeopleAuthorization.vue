<template>
  <div>
    <CAlert :show.sync="dismissCountDown" closeButton :color="colorMsg" fade>
      <strong>{{ messagesError.header }}</strong
      ><br />
      <strong>{{ messagesError.body }}</strong>
    </CAlert>
    <CRow>
      <CCol sm="6" md="12">
        <CCard border-color="success">
          <CCardHeader>Phân quyền người dùng</CCardHeader>
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
              <CCol lg="9">
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
  name: "PeopleAuthorization",
  components: { CpmDataTable, CpmTable },
  data() {
    return {
      dataBm: null,
      dataUser: null,
      content: null,
      fields: [
        { key: "id", _classes: "d-none" },
        {
          key: "checkbox",
          _style: { width: "10px" },
          label: "",
          _classes: "col-md-1",
        },
        {
          key: "name",
          label: "Name",
          _classes: "text-center",
          _style: { width: "40%" },
        },
        { key: "statusPermission", label: "Status", _classes: "text-center" },
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
            token: item.tokenbm,//item.token_bm_full
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
      await FbService.getAllListUser(data).then(
          (response) => {
            //data body
            result1 = this.transportDataUser(dataBm, response.data.data);
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
      this.dataUser = dataAfter;
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
            token: item.dataAds.idBm.token,
            user_id: item.id,
          };
          let result = await FbService.changeRolePeople(
              data,
              flagChangePermission
          ).then(
              (response) => {
                return true;
              },
              (error) => {
                this.content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                loader.hide();
                return false;
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
  }
}
</script>

<style scoped>

</style>
