<template>
  <div>
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
                    <CIcon name="cil-grid"/>
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
                      <CCardHeader> Tài khoản quảng cáo</CCardHeader>
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
                        <!--                        <CInput-->
                        <!--                            label="Token Full Quyền (Thêm tài khoản đối tác)"-->
                        <!--                            size="lg"-->
                        <!--                            placeholder="update token all"-->
                        <!--                            v-model="tokenFull"-->
                        <!--                            ref="testFocus"-->
                        <!--                        >-->
                        <template #append>
                          <CButton
                              type="submit"
                              @click="updateTokenAll"
                              color="primary"
                          >Cập nhật
                          </CButton
                          >
                        </template>
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
                              >Share
                              </CButton
                              >
                              <CButton color="info" :disabled="btnHistory"
                              >History
                              </CButton
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
import {freeSet} from "@coreui/icons";
import ModalAdd from "../base/modal/ModalAdd";
import AlterMessages from "@/views/common/alterMessages";
import BmService from "../../../js/services/bm/bm.service";
import FB from "../../../js/services/facebook/fb.service";
import * as objectUitls from "../../../js/utils/objectUtils";
import CpmDataTable from "@/views/screen/base/table/CpmDataTable";
import FbService from "../../../js/services/facebook/fb.service";
import {MSG_BUS_003} from "../../../js/constantUtils";

export default {
  name: "SampleOne",
  components: {CpmDataTable, AlterMessages, ModalAdd, CpmTable},
  freeSet,
  data() {
    return {
      dataDetail: null,
      tableFields: [
        {key: "checkbox", label: "Chọn", _classes: "text-center"},
        {key: "status", label: "Hoạt động", _classes: "text-center"},
        {key: "account_id", label: "ID", _classes: "text-center"},
        {key: "name", label: "Tên", _classes: "text-center"},
        {key: "idbm", label: "ID BM", _classes: "text-center"},
        {key: "currency", label: "Loại tiền", _classes: "text-center"},
        {key: "card", label: "Thẻ", _classes: "text-center"},
        {key: "active", label: "Trạng thái", _classes: "text-center"},
        {
          key: "addAcountPartner",
          label: "Thêm tài khoản (Đối tác)",
          _classes: "text-center",
        },
      ],
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

      // button
      btnShare: true,
      btnHistory: true,
      btnExport: true,

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
            this.$showMessages(
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString(), this.msg4);
          }
      );

      // Clear data bm detail
      this.dataDetail = null;
    },

    showModal: async function (item) {
      this.isShowGrid = true;
      this.dataModalGrid = null;
      const data = {
        idBm: this.dataMaster.idBm.id,
        accessToken: this.dataMaster.idBm.token,
      };

      await BmService.getAllListUser(data).then(
          (response) => {
            this.infoModal = true;
            //data body
            this.transportDataUser(item, response.data);
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

    transportDataUser: function (dataDetail, dataUser) {
      let _re = [];
      // data body
      dataUser.data.forEach(data => {
        _re.push({
          dataMaster: this.dataMaster,
          dataDetail: dataDetail,
          id: data.id,
          name: data.name,
          isCheck: false,
          statusAuthen: "",
        });
      });
      this.dataModalGrid = _re;
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
              this.$showMessages(constantUtils.MSG_BUS_002, this.msg1)
            },
            (error) => {
              this.$showMessages((error.response &&
                      error.response.data &&
                      error.response.data.message) ||
                  error.message ||
                  error.toString(), this.msg4);
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
        idBm: dataMaster.idBm.id,
        accessToken: dataMaster.idBm.token,
      };
      await BmService.getListAccount(data).then(
          (response) => {
            this.transportDataDetailBm(dataMaster, response.data);
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

    transportDataDetailBm(dataMaster, dataDetail) {
      let _re = [];
      // data body
      dataDetail.forEach((item) => {
        item.data.forEach(data => {
          let status = data.account_status % 2 ? "success" : "danger";
          _re.push({
            isCheck: false,
            status: status,
            account_id: data.account_id,
            name: data.name,
            idbm: !objectUitls.isNullOrEmpty(data.business)
                ? data.business.id
                : "",
            currency: data.currency,
            card: this.getFundingSourceDetails(data),
            active: "",
          });
        })
      });
      // data header
      this.info = {
        id: dataMaster.idBm.id,
        name: dataMaster.idBm.nameMaster,
      };
      this.dataDetail = _re;
      this.info.sumTable = _re === [] ? "" : _re.length;
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
      if (accept === true) {
        if (view === "addBm") {
          await this.UpdateBm(component.dataModal);

        } else {
          await this.setShareBm(component);
        }
      }
    },

    UpdateBm: async function (modal) {
      const data = {
        idBm: modal.id,
        name: modal.name,
        accessToken: modal.token
      };
      await BmService.addBmMaster(data).then(
          (response) => {
            response.status ?
                this.$showMessages(constantUtils.MSG_BUS_002, this.msg1) :
                this.$showMessages(constantUtils.MSG_BUS_003, this.msg4)
          },
          (error) => {
            this.$showMessages((error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString(), this.msg4);
          }
      );
      await this.init();
    },

    enableButton: function (isEnable) {
      this.btnShare = isEnable;
      this.btnHistory = isEnable;
      this.btnExport = isEnable;
    },

    setShareBm: async function (component) {
      const dataShare = component.dataModal;
      let result;
      for (let i = 0; i < this.dataDetail.length; i++) {
        if (this.dataDetail[i].isCheck === true) {
          const data = {
            account_id: this.dataDetail[i].account_id,
            token: this.dataMaster.idBm.token,
            acting_brand_id: this.dataMaster.idBm.id,
            business: dataShare.id,
          };
	  
          result = await FB.addPartner(data).then(
              (response) => {
                return true;
              },
              (error) => {
                return false;
              }
          );
          if (result) {
            this.updateStatusShare(this.dataDetail[i].account_id, "Thành công");
          } else {
            this.updateStatusShare(this.dataDetail[i].account_id, "Bỏ qua");
          }
        }
      }
      this.$showMessages(constantUtils.MSG_BUS_004, this.msg1)
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
              this.$showMessages(constantUtils.MSG_BUS_002, this.msg1);
            },
            (error) => {
              this.$showMessages((error.response &&
                      error.response.data &&
                      error.response.data.message) ||
                  error.message ||
                  error.toString(), this.msg4);
            }
        );

        await this.init();
      }
    },
  },
};
</script>
