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
              <CCol lg="4">
                <CTableWrapper
                  border
                  hover
                  :header="false"
                  :items="dataBm"
                  clickable-rows
                  v-on:rowClick="rowsClick"
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
                </CTableWrapper>
              </CCol>
              <CCol sm="8">
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
                    <CCard>
                      <CCardHeader>
                        <CButtonGroup class="mx-1 d-sm-down-none">
                          <CButton
                            @click="selectRow(true)"
                            size="sm"
                            color="info"
                            :disabled="btnSelectAll"
                            >Chọn tất cả</CButton
                          >
                          <CButton
                            @click="selectRow(false)"
                            size="sm"
                            :disabled="btnUnSelect"
                            color="info"
                            >Bỏ chọn</CButton
                          >
                        </CButtonGroup>
                      </CCardHeader>
                      <CCardBody>
                        <CDataTable
                          class="mb-10 table-outline"
                          hover
                          :items="dataDetail"
                          :fields="tableFields"
                          head-color="light"
                          no-sorting
                          :pagination="{ doubleArrows: false, align: 'center' }"
                          border
                          striped
                          outlined
                        >
                          <td
                            slot="checkbox"
                            class="text-center"
                            slot-scope="{ item }"
                          >
                            <CInputCheckbox
                              :checked.sync="item.isCheck"
                            />
                          </td>
                          <td
                            slot="status"
                            class="text-center"
                            slot-scope="{ item }"
                          >
                            <div class="c-avatar">
                              <!--                          <img :src="item.url" class="c-avatar-img" alt="" />-->
                              <span
                                class="c-avatar-status"
                                :class="`bg-${item.status || 'secondary'}`"
                              ></span>
                            </div>
                          </td>
                          <!--                      <td slot="detailBm" slot-scope="{ item }">-->
                          <!--                        <div>{{ item.detailBm.id }}</div>-->
                          <!--                      </td>-->
                          <!--                      <td slot="delete" class="text-center">-->
                          <!--                        <CCol col="6" sm="4" md="2" xl class="mb-3 mb-xl-0">-->
                          <!--                          <CButton @click="" block variant="outline" color="danger"-->
                          <!--                          >Delete-->
                          <!--                          </CButton>-->
                          <!--                        </CCol>-->
                          <!--                      </td>-->
                        </CDataTable>
                      </CCardBody>
                    </CCard>
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
    >
    </ModalAdd>
  </div>
</template>

<script>
import CTableWrapper from "../../base/Table.vue";
import serverHelper from "../../../js/services/serverHelper";
import * as constantUtils from "../../../js/constantUtils";
import axios from "axios";
import { freeSet } from "@coreui/icons";
import ModalAdd from "../modal/ModalAdd";
import AlterMessages from "@/views/common/alterMessages";

export default {
  name: "SampleOne",
  components: { AlterMessages, ModalAdd, CTableWrapper },
  freeSet,
  data() {
    return {
      dataDetail: null,
      tableFields: [
        { key: "checkbox", label: "Chọn", _classes: "text-center" },
        { key: "status", label: "Trạng thái", _classes: "text-center" },
        { key: "account_id", label: "ID", _classes: "text-center" },
        { key: "name", label: "Tên", _classes: "text-center" },
        { key: "idbm", label: "ID BM", _classes: "text-center" },
        { key: "currency", label: "Loại tiền" },
      ],
      errors: [],
      data: null,
      dataBm: null,
      dataMaster: null,

      infoModal: false,
      dataModal: null,
      viewModal: null,
      titleModal: null,

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
      btnSelectAll: true,
      btnUnSelect: true,
      error: null,
    };
  },
  created: async function () {
    await this.init();
  },
  methods: {
    init: async function () {
      let scrHp = new serverHelper();
      await scrHp
        .get("authenticate/bm/loadBm")
        .then((res) => {
          this.dataBm = this.transportDataMasterBm(res.data);
        })
        .catch((error) => {
          console.error(error);
        });

      // set disable button
      this.enableButton(true);
    },

    openModalAdd: function () {
      this.viewModal = "addBm";
      this.setTileModal("Thêm BM");
    },

    setTileModal: function (str) {
      this.titleModal = str;
      this.infoModal = true;
    },

    shareBm: async function () {
      this.viewModal = "shareBm";
      this.setTileModal("Share BM");
    },

    selectRow: function (flag) {
      this.dataDetail.forEach((item) => {
        item.isCheck = flag;
      });
      this.btnSelectAll = flag;
      this.btnUnSelect = !flag;
    },

    rowsClick: async function (dataMaster) {
      this.dataMaster = dataMaster;
      let apiDetail =
        constantUtils.urlFb +
        dataMaster.idBm.id +
        "/owned_ad_accounts?" +
        "access_token=" +
        dataMaster.idBm.token +
        "&limit=200&" +
        "fields=account_id,disable_reason,name,currency,account_status,business,funding_source_details";
      await axios
        .get(apiDetail, { timeout: 500 })
        .then((res) => {
          console.log(res);

          //data body
          this.dataDetail = this.transportDataDetailBm(
            dataMaster,
            res.data.data
          );
        })
        .catch((error) => {
          return {
            title: "Error!!!",
            body: "We got an example error!",
          };
        });

      // set disable button
      this.enableButton(false);
    },

    transportDataDetailBm(dataMaster, dataDetail) {
      let _re = [];
      let flagStatus = 0;

      // data body
      dataDetail.forEach((item) => {
        let status = item.account_status % 2 ? "success" : "danger";
        _re.push({
          isCheck: false,
          status: status,
          account_id: item.account_id,
          name: item.name,
          idbm: item.business.id,
          currency: item.currency,
          // avatar: { url: item.profile_picture_uribm, status: status },
        });
        flagStatus++;
      });
      // data header
      this.info = {
        id: dataMaster.idBm.id,
        name: dataMaster.idBm.nameMaster,
        sumTable: flagStatus,
      };
      return _re;
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
        constantUtils.urlFb +
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
        .get(path, { timeout: 500 })
        .then((res) => {
          return res;
        })
        .catch((error) => {
          this.showMessages(1, "Sai thông tin vui lòng đăng ký lại.");
        });
    },

    UpdateBm: async function (dataResponse, modal) {
      let scrHp = new serverHelper();
      let data = {
        name: modal.name,
        idbm: dataResponse.id,
        namebm: dataResponse.name,
        tokenbm: modal.token,
        profile_picture_uribm: dataResponse.profile_picture_uri,
        verification_statusbm: dataResponse.verification_status,
      };

      const result = await scrHp
        .post("authenticate/bm/updateBm", data)
        .then(function (res) {
          return true;
        })
        .catch(function (err) {
          this.showMessages(1, "Đăng ký không thành công.");
        });
      if (result == true) {
        this.showMessages(0, "Đăng ký thành công.");
      }
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
      this.btnSelectAll = isEnable;
      this.btnUnSelect = isEnable;
    },

    setShareBm: async function (component) {
      const dataShare = component.dataModal;
      let config = {
        header: {
          "Content-Type": "multipart/form-data",
        },
      };
      let data = new FormData();
      data.append("accountId", this.dataMaster.idBm.id);
      data.append("acting_brand_id", this.dataMaster.idBm.id);
      data.append("business", dataShare.id);
      data.append("permitted_tasks", [
        "ADVERTISE",
        "ANALYZE",
        "DRAFT",
        "MANAGE",
      ]);
      let result;
      for (let i = 0; i < this.dataDetail.length; i++) {
        if (this.dataDetail[i].isCheck == true) {
          let apiShare =
              "https://graph.facebook.com/v9.0/" +
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
        }
      }
      if (result == true) {
        this.showMessages(0, "Share thành công.");
      } else {
        this.showMessages(
          1,
          "Share thất bại.",
          JSON.stringify(this.error)
        );
      }
    },
  },
};
</script>
