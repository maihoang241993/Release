<template>
  <div>
    <CRow>
      <CCol sm="6" md="12">
        <CCard border-color="success">
          <CCardHeader>Tài khoản BM</CCardHeader>
          <CCardBody>
            <CRow>
              <CCol>
                <CpmDataTable
                    :data-items="dataBm"
                    :table-fields="fields"
                    v-on:showModal="addPartner"
                    :sorter="false"
                    :column-filter="false"
                    :is-show-card-header="false"
                >
                </CpmDataTable>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    <ModalAdd
        :title="titleModal"
        :viewModal="viewModal"
        color="info"
        :show.sync="infoModal"
        v-on:returnData="addBm"
        :is-show-grid="isShowGrid"
        :data-modal-grid="dataModalGrid"
    >
    </ModalAdd>
  </div>
</template>

<script>
import CpmTable from "@/views/screen/base/table/CpmTable";
import * as objectUitls from "../../../js/utils/objectUtils";
import FbService from "../../../js/services/facebook/fb.service";
import BmService from "../../../js/services/bm/bm.service";
import CpmDataTable from "@/views/screen/base/table/CpmDataTable";
import ModalAdd from "@/views/screen/base/modal/ModalAdd";

export default {
  name: "AddPartner",
  components: {ModalAdd, CpmDataTable, CpmTable},
  data() {
    return {
      dataBm: null,
      fields: [
        {key: "dataObject", _classes: "d-none"},
        {key: "id", _classes: "d-none"},
        {key: "infoData", label: "Bm", _classes: "text-center"},
        {key: "statusAddPartner", label: "Status", _classes: "text-center"},
        {
          key: "addAcountPartner",
          label: "",
          _style: {width: "1%"},
          sorter: false,
          filter: false,
        },
      ],

      infoModal: false,
      dataModal: null,
      infoModalPartner: false,
      viewModal: null,
      titleModal: null,
      isShowGrid: false,
      dataModalGrid: null,
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
            this.$showMessages(
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString(), this.msg4);
          }
      );
    },

    transportDataMasterBm(data) {
      let _re = [];
      data.forEach((item) => {
        _re.push({
          dataObject: item,
          infoData: {
            url: item.profile_picture_uribm,
            status: "success",
            id: item.idbm,
            name: item.namebm,
          },
          statusAddPartner: "",
          show_details: "",
        });
      });

      return _re;
    },

    addPartner: async function (item) {
      console.log(item);
      this.isShowGrid = true;
      this.dataModalGrid = null;

      this.infoModal = true;
      this.viewModal = "addPartner";
      this.setTileModal("Tài khoản quảng cáo");

      const data = {
        idBm: item.dataObject.idbm,
        accessToken: item.dataObject.tokenbm,
      };
      await BmService.getListAccount(data).then(
          (response) => {
            //data body
            if (
                !objectUitls.isNullOrEmpty(response.data.data) &&
                response.data.data.length > 0
            ) {
              this.dataModalGrid = this.transportDataDetailBm(
                  data,
                  response.data.data
              );
            }
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

    transportDataDetailBm(data, dataDetail) {
      let _re = [];

      // data body
      dataDetail.forEach((item) => {
        _re.push({
          dataObject: {master: data, detail: item},
          id: item.account_id,
          isCheck: false,
          name: item.name,
          statusAuthen: "",
        });
      });
      return _re;
    },

    setTileModal: function (str) {
      this.titleModal = str;
      this.infoModal = true;
    },

    addBm: async function () {
    },
  },
};
</script>

<style scoped></style>
