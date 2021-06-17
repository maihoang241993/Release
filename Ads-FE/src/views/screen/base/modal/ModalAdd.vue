<template>
  <div>
    <div
      :class="modalClasses"
      tabindex="-1"
      role="dialog"
      @click="modalClick($event)"
    >
      <div :class="dialogClasses" role="document">
        <div :class="contentClasses">
          <slot name="header-wrapper">
            <header class="modal-header">
              <slot name="header">
                <h5 class="modal-title">
                  {{ title }}
                </h5>
                <CButtonClose @click="hide($event)" />
              </slot>
            </header>
          </slot>
          <slot name="body-wrapper">
            <div class="modal-body">
              <div v-if="isShowGrid">
                <CInput
                  v-if="viewModal == 'addPartner'"
                  placeholder="ID Bm"
                  v-model="dataModal.id"
                  :is-valid="validator"
                >
                  <template #prepend-content
                    ><CIcon name="cil-settings"
                  /></template>
                </CInput>
                <CpmDataTable
                  :table-fields="tableFields"
                  :data-items="dataModalGrid"
                  :heightGrid="'600'"
                >
                </CpmDataTable>
              </div>
              <div v-if="!isShowGrid">
                <CForm>
                  <CInput
                    v-if="viewModal == 'addBm'"
                    :is-valid="validator"
                    placeholder="Name bm"
                    v-model="dataModal.name"
                  >
                    <template #prepend-content>
                      <CIcon name="cil-user"
                    /></template>
                  </CInput>
                  <CInput
                    placeholder="Id bm"
                    v-model="dataModal.id"
                    :is-valid="validator"
                  >
                    <template #prepend-content
                      ><CIcon name="cil-settings"
                    /></template>
                  </CInput>
                  <CInput
                    v-if="viewModal == 'addBm'"
                    placeholder="Token"
                    v-model="dataModal.token"
                    :is-valid="validator"
                  >
                    <template #prepend-content
                      ><CIcon name="cil-settings"
                    /></template>
                  </CInput>
                </CForm>
              </div>
            </div>
          </slot>
          <slot name="footer-wrapper">
            <footer class="modal-footer">
              <slot name="footer">
                <button
                  type="button"
                  class="btn btn-secondary"
                  @click="hide($event)"
                >
                  Cancel
                </button>
                <button
                  type="button"
                  :class="btnClasses"
                  @click="hide($event, true)"
                >
                  OK
                </button>
              </slot>
            </footer>
          </slot>
        </div>
      </div>
    </div>
    <div
      v-if="backdrop && (visible || isTransitioning)"
      :class="backdropClasses"
    ></div>
  </div>
</template>

<script>
import CButtonClose from "./BtnClose.vue";
import CpmDataTable from "@/views/screen/base/table/CpmDataTable";
import FB from "../../../../js/services/facebook/fb.service";

export default {
  name: "ModalAdd",
  components: {
    CpmDataTable,
    CButtonClose,
  },
  props: {
    dataModalGrid: Array,
    isShowGrid: {
      type: Boolean,
      default: false,
    },
    viewModal: String,
    show: Boolean,
    centered: Boolean,
    scrollable: Boolean,
    title: String,
    size: {
      type: String,
      validator: (val) => ["", "sm", "lg", "xl"].includes(val),
    },
    color: String,
    borderColor: String,
    fade: {
      type: Boolean,
      default: true,
    },
    backdrop: {
      type: Boolean,
      default: true,
    },
    closeOnBackdrop: {
      type: Boolean,
      default: true,
    },
    addContentClasses: [String, Array, Object],
    onKey: Function,
  },
  data() {
    return {
      tableFields: [
        { key: "dataMaster", _classes: "d-none" },
        { key: "datadataDetail", _classes: "d-none" },
        {
          key: "checkbox",
          _style: { width: "10px" },
          label: "",
          _classes: "col-md-1",
        },
        { key: "account_id", label: "ID", _classes: "d-none" },
        { key: "name", label: "Tên", _classes: "text-center" },
        { key: "statusAuthen", label: "Status", _classes: "text-center" },
      ],
      visible: this.show,
      isTransitioning: false,
      timeout: null,
      dataModal: {
        name: null,
        id: null,
        token: null,
      },
      showSpinner: false,
    };
  },
  computed: {
    backdropClasses() {
      return {
        "modal-backdrop": true,
        fade: this.fade,
        show: this.visible || !this.fade,
      };
    },
    modalClasses() {
      return [
        "modal overflow-auto",
        {
          fade: this.fade,
          show: this.visible,
          "d-block": this.visible || this.isTransitioning,
          [`modal-${this.color}`]: this.color,
        },
      ];
    },
    dialogClasses() {
      return [
        "modal-dialog",
        {
          "modal-dialog-centered": this.centered,
          "modal-dialog-scrollable": this.scrollable,
          [`modal-${this.size}`]: this.size,
        },
      ];
    },
    contentClasses() {
      return [
        this.addContentClasses,
        "modal-content",
        {
          [`border-${this.borderColor}`]: this.borderColor,
        },
      ];
    },
    btnClasses() {
      return [`btn btn-${this.color || "primary"}`];
    },
  },
  watch: {
    show(val) {
      this.toggle(val);
    },
  },
  methods: {
    modalClick(e) {
      if (e.target === this.$el.firstElementChild && this.closeOnBackdrop) {
        this.hide(e);
      }
    },
    hide: async function (e, accept = false) {
      if (this.isShowGrid && accept) {
        let loader = this.$loading.show({
          // Optional parameters
          container: this.$refs.formContainer,
          isFullPage: false,
          canCancel: false,
        });
        for (let i = 0; i < this.dataModalGrid.length; i++) {
          const item = this.dataModalGrid[i];
          if (item.isCheck) {
            if (this.viewModal === "addPartner") {
              await this.addPartner(item);
            } else {
              await this.updateAccountPartner(item);
            }
          }
        }
        loader.hide();
        return;
      }
      this.$emit("update:show", false, e, accept);
      this.$emit("returnData", this, accept, this.viewModal);
      if (this.visible) {
        window.removeEventListener("keydown", this.hideEsc);
      }
    },
    hideEsc(event) {
      if (typeof this.onKey != "undefined") {
        if (this.onKey("", event.keyCode) !== false) {
          this.hide(event);
        }
      } else {
        if (event.keyCode == "27") {
          this.hide(event);
        }
      }
    },
    toggle(newVal) {
      setTimeout(() => {
        this.visible = newVal;
      }, 0);
      if (newVal) {
        window.addEventListener("keydown", this.hideEsc);
      }
      if (this.fade) {
        this.isTransitioning = true;
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          this.isTransitioning = false;
        }, 150);
      }
    },
    validator(val) {
      return val ? val.length >= 1 : false;
    },

    updateStatus: async function (accountId, active) {
      this.dataModalGrid.forEach((item) => {
        if (item.id === accountId) {
          item.statusAuthen = active;
        }
      });
    },

    addPartner: async function (dataObject) {
      const data = {
        account_id: dataObject.dataObject.detail.account_id,
        token: dataObject.dataObject.master.token,
        acting_brand_id: dataObject.dataObject.master.id, //TODO: get id bm
        business: this.dataModal.id,
      };
      const result = await FB.addPartner(data).then(
        (response) => {
          return true;
        },
        (error) => {
          return false;
        }
      );
      if (result) {
        this.updateStatus(
          dataObject.dataObject.detail.account_id,
          "Thành công"
        );
      } else {
        this.updateStatus(dataObject.dataObject.detail.account_id, "Bỏ qua");
      }
    },

    updateAccountPartner: async function (item) {
      const data = {
        account_id: "act_" + item.dataDetail.account_id,
        business: item.dataMaster.idBm.id,
        token: item.dataMaster.idBm.token,
        userId: item.id,
      };
      let result = await FB.updateAuthenAccountPartner(data).then(
        (response) => {
          return true;
        },
        (error) => {
          return false;
          // this.updateStatus(1, "Đăng ký không thành công.");
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
      } else {
        await this.updateStatus(item.id, "Thất bại");
      }
    },
  },
  mounted: function () {
    if (this.show) {
      window.addEventListener("keydown", this.hideEsc);
    }
  },
};
</script>
