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
          <CCardHeader>Tài khoản ADS</CCardHeader>
          <CCardBody>
            <CRow>
              <CCol lg="3">
                <CpmTable
                  border
                  hover
                  :header="false"
                  :items="dataBm"
                  clickable-rows
                  v-on:rowClick="rowsClick"
                  v-on:deleteClick="deleteClick"
                >
                  <template #header>
                    <CIcon name="cil-grid" />
                    Tài khoản BM
                    <div class="card-header-actions">
                      <CButton
                        @click="openModalAdd"
                        size="sm"
                        block
                        variant="outline"
                        color="primary"
                        >Thêm BM
                      </CButton>
                    </div>
                  </template>
                </CpmTable>
              </CCol>
              <CCol sm="9">
                <CRow>
                  <CCol lg="12">
                    <CCard>
                      <CCardHeader> Thông tin BM </CCardHeader>
                      <CCardBody>
                        <CInput
                          label="ID"
                          size="lg"
                          horizontal
                          plaintext
                          v-model="info.id"
                        />
                        <CInput
                          label="Tên"
                          size="lg"
                          horizontal
                          plaintext
                          v-model="info.name"
                        />
                        <CInput
                          label="Tổng"
                          size="lg"
                          horizontal
                          plaintext
                          v-model="info.sumTable"
                        />
                        <CInput
                          label="Token Full Quyền (Thêm tài khoản đối tác)"
                          size="lg"
                          placeholder="update token all"
                          v-model="tokenFull"
                          ref="testFocus"
                        >
                          <template #append>
                            <CButton
                              type="submit"
                              @click="updateTokenAll"
                              color="primary"
                              >Cập nhật</CButton
                            >
                          </template>
                        </CInput>
                      </CCardBody>
                      <CCardFooter>
                        <div style="float: right">
                          <CButtonToolbar
                            aria-label="Toolbar with button groups and dropdown menu"
                          >
                            <CButtonGroup class="mx-1 d-sm-down-none">
                              <CButton
                                color="info"
                                :disabled="btnShare"
                                @click="shareBm()"
                                >Share</CButton
                              >
                              <CButton color="info" :disabled="btnHistory"
                                >History</CButton
                              >
                            </CButtonGroup>
                            <CDropdown
                              toggler-text="Export"
                              color="danger"
                              class="mx-1"
                              placement="bottom-end"
                              button-content="Menu"
                              :disabled="btnExport"
                            >
                              <CDropdownItem>Export dòng đã chọn</CDropdownItem>
                              <CDropdownItem>Export tất cả</CDropdownItem>
                            </CDropdown>
                          </CButtonToolbar>
                        </div>
                      </CCardFooter>
                    </CCard>
                  </CCol>
                </CRow>
                <CRow>
                  <CCol lg="12">
                    <CpmDataTable
                      :data-items="dataDetail"
                      :table-fields="tableFields"
                      v-on:showModal="showModal"
                    >
                    </CpmDataTable>
                  </CCol>
                </CRow>
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
import CpmTable from "../base/table/CpmTable";
import * as constantUtils from "../../../js/constantUtils";
import axios from "axios";
import { freeSet } from "@coreui/icons";
import ModalAdd from "../base/modal/ModalAdd";
import AlterMessages from "@/views/common/alterMessages";
import BmService from "../../../js/services/bm/bm.service";
import FB from "../../../js/services/facebook/fb.service";
import * as objectUitls from "../../../js/utils/objectUtils";
import CpmDataTable from "@/views/screen/base/table/CpmDataTable";

export default {
  name: "SampleOne",
  components: { CpmDataTable, AlterMessages, ModalAdd, CpmTable },
  freeSet,
  data() {
    return {
      dataDetail: null,
      tableFields: [
        { key: "checkbox", label: "Chọn", _classes: "text-center" },
        { key: "status", label: "Hoạt động", _classes: "text-center" },
        { key: "account_id", label: "ID", _classes: "text-center" },
        { key: "name", label: "Tên", _classes: "text-center" },
        { key: "idbm", label: "ID BM", _classes: "text-center" },
        { key: "currency", label: "Loại tiền", _classes: "text-center" },
        { key: "card", label: "Thẻ", _classes: "text-center" },
        { key: "active", label: "Trạng thái", _classes: "text-center" },
        {
          key: "addAcountPartner",
          label: "Thêm tài khoản (Đối tác)",
          _classes: "text-center",
        },
      ],
      errors: [],
      data: null,
      dataBm: null,
      dataMaster: null,

      infoModal: false,
      dataModal: null,
      infoModalPartner: false,
      viewModal: null,
      titleModal: null,
      isShowGrid: false,
      dataModalGrid: null,

      info: {
        id: null,
        name: null,
        sumTable: null,
      },
      dismissCountDown: 0,
      messagesError: {
        header: null,
        body: null,
      },
      colorMsg: null,

      // button
      btnShare: true,
      btnHistory: true,
      btnExport: true,
      error: null,

      tokenFull: null,
    };
  },
  watch: {
    dataDetail: function (newVal, oldVal) {
      if (newVal !== null && newVal.length > 0) {
        this.enableButton(false);
      } else {
        this.enableButton(true);
      }
    },
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

      // Clear data bm detail
      this.dataDetail = null;
    },

    showModal: async function (item) {
      this.isShowGrid = true;
      this.dataModalGrid = null;
      const data = {
        id: this.dataMaster.idBm.id,
        token: this.dataMaster.idBm.tokenFull,
      };
      await FB.getAllListUser(data).then(
        (response) => {
          this.infoModal = true;
          //data body
          this.dataModalGrid = this.transportDataUser(item, response.data.data);
        },
        (error) => {
          this.showMessages(1, "Lỗi", error.response.data.error.message);
        }
      );
    },

    transportDataUser: function (dataDetail, dataUser) {
      let _re = [];
      // data body
      dataUser.forEach((item) => {
        _re.push({
          dataMaster: this.dataMaster,
          dataDetail: dataDetail,
          id: item.id,
          name: item.name,
          isCheck: false,
          statusAuthen: "",
        });
      });
      return _re;
    },

    openModalAdd: function () {
      this.viewModal = "addBm";
      this.isShowGrid = false;
      this.setTileModal("Thêm BM");
    },

    setTileModal: function (str) {
      this.titleModal = str;
      this.infoModal = true;
    },

    shareBm: async function () {
      this.viewModal = "shareBm";
      this.isShowGrid = false;
      this.setTileModal("Share BM");
    },

    deleteClick: async function (confirm, id) {
      if (confirm) {
        let data = {
          idbm: id,
        };
        await BmService.deleleBmMaster(data).then(
          (response) => {
            this.showMessages(0, "Xóa thành công.");
            // this.dataBm = this.transportDataMasterBm(response.data);
          },
          (error) => {
            this.showMessages(1, "Xóa không thành công.");
            this.content =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
          }
        );
        await this.init();
      }
    },

    rowsClick: async function (dataMaster) {
      this.dataMaster = dataMaster;
      this.dataDetail = null;
      this.info = {
        id: null,
        name: null,
        sumTable: null,
      };
      const data = {
        id: dataMaster.idBm.id,
        token: dataMaster.idBm.token,
      };
      let result1;
      let result2;
      await FB.getListAccount(data).then(
        (response) => {
          //data body
          result1 = this.transportDataDetailBm(dataMaster, response.data.data);
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

      await FB.getListPartnerAccount(data).then(
        (response) => {
          //data body
          if (
            !objectUitls.isNullOrEmpty(response.data.data) &&
            response.data.data.length > 0
          ) {
            result2 = this.transportDataDetailBm(
              dataMaster,
              response.data.data
            );
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
      this.dataDetail = dataAfter;
      this.info.sumTable = dataAfter == null ? "" : dataAfter.length;
    },

    transportDataDetailBm(dataMaster, dataDetail) {
      let _re = [];

      // data body
      dataDetail.forEach((item) => {
        let status = item.account_status % 2 ? "success" : "danger";
        _re.push({
          isCheck: false,
          status: status,
          account_id: item.account_id,
          name: item.name,
          idbm: !objectUitls.isNullOrEmpty(item.business)
            ? item.business.id
            : "",
          currency: item.currency,
          card: this.getFundingSourceDetails(item),
          active: "",
          // avatar: { url: item.profile_picture_uribm, status: status },
        });
      });
      // data header
      this.info = {
        id: dataMaster.idBm.id,
        name: dataMaster.idBm.nameMaster,
      };
      return _re;
    },

    getFundingSourceDetails(item) {
      let _reStr = "";
      if (!objectUitls.isNullOrEmpty(item.funding_source_details)) {
        if (
          !objectUitls.isNullOrEmpty(item.funding_source_details.display_string)
        ) {
          _reStr = item.funding_source_details.display_string;
        }
      }
      return _reStr;
    },

    shuffleArray(array) {
      for (let i = array.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1));
        let temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
      return array;
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
            tokenFull: item.token_bm_full,
            nameMaster: item.name,
          },
        });
      });

      return _re;
    },

    addBm: async function (component, accept, view) {
      if (accept == true) {
        if (view == "addBm") {
          const result = await this.getBmFb(component);
          if (result != null || result !== undefined) {
            await this.UpdateBm(result.data, component.dataModal);
          }
        } else {
          await this.setShareBm(component);
        }
      }
    },

    getBmFb: async function (component) {
      let data = component.dataModal;
      let path =
        constantUtils.FB_URL +
        data.id +
        "?" +
        constantUtils.access_token +
        "=" +
        data.token +
        "&fields=" +
        constantUtils.paramFb.verification_status +
        "," +
        constantUtils.paramFb.profile_picture_uri +
        "," +
        constantUtils.paramFb.id +
        "," +
        constantUtils.paramFb.name +
        "," +
        constantUtils.paramFb.primary_page;
      return await axios
        .get(path)
        .then((res) => {
          return res;
        })
        .catch((error) => {
          this.showMessages(1, "Sai thông tin vui lòng đăng ký lại.");
        });
    },

    UpdateBm: async function (dataResponse, modal) {
      let data = {
        name: modal.name,
        idbm: dataResponse.id,
        namebm: dataResponse.name,
        tokenbm: modal.token,
        profile_picture_uribm: dataResponse.profile_picture_uri,
        verification_statusbm: dataResponse.verification_status,
      };

      await BmService.addBmMaster(data).then(
        (response) => {
          this.showMessages(0, "Đăng ký thành công.");
          // this.dataBm = this.transportDataMasterBm(response.data);
        },
        (error) => {
          this.showMessages(1, "Đăng ký không thành công.");
          this.content =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
      await this.init();
    },

    showMessages: async function (code, header, body = "") {
      this.messagesError = {
        header: header,
        body: body,
      };
      this.colorMsg = code == 1 ? "danger" : "success";
      this.dismissCountDown = 5;
    },

    enableButton: function (isEnable) {
      this.btnShare = isEnable;
      this.btnHistory = isEnable;
      this.btnExport = isEnable;
    },

    setShareBm: async function (component) {
      const dataShare = component.dataModal;
      let config = {
        header: {
          "Content-Type": "multipart/form-data",
        },
      };
      const task = ["ADVERTISE", "ANALYZE", "DRAFT", "MANAGE"];
      let result;
      for (let i = 0; i < this.dataDetail.length; i++) {
        if (this.dataDetail[i].isCheck == true) {
          let data = new FormData();
          data.append("accountId", this.dataDetail[i].account_id);
          data.append("acting_brand_id", this.dataMaster.idBm.id);
          data.append("business", dataShare.id);
          data.append("permitted_tasks", JSON.stringify(task));
          let apiShare =
            constantUtils.FB_URL +
            "act_" +
            this.dataDetail[i].account_id +
            "/agencies?" +
            "access_token=" +
            this.dataMaster.idBm.token;
          result = await axios
            .post(apiShare, data, config)
            .then(function (res) {
              console.log(res);

              return true;
            })
            .catch(function (err) {
              console.log(err.response);
              return false;
            });
          if (result) {
            this.updateStatusShare(this.dataDetail[i].account_id, "Thành công");
          } else {
            this.updateStatusShare(this.dataDetail[i].account_id, "Bỏ qua");
          }
        }
      }
      this.showMessages(0, "Xử lý hoàn tất");
    },

    updateStatusShare: async function (accountId, active) {
      this.dataDetail.forEach((item) => {
        if (item.account_id === accountId) {
          item.active = active;
        }
      });
    },

    updateTokenAll: async function () {
      if (!objectUitls.isNullOrEmpty(this.dataMaster.idBm.id)) {
        let data = {
          idbm: this.dataMaster.idBm.id,
          token_bm_full: this.tokenFull,
        };
        await BmService.updateTokenAll(data).then(
          (response) => {
            this.showMessages(0, "Cập nhật thành công.");
          },
          (error) => {
            this.showMessages(1, "Cập nhật không thành công.");
            this.content =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
          }
        );
      }
    },
  },
};
</script>
