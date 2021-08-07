<template>
  <CCard>
    <CCardHeader v-show="isShowCardHeader">
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

      <CButtonGroup v-show="isShowChangePermission" class="mx-1 d-sm-down-none">
        <CButton
          class="right-button"
          @click="changePermissionClick('Up')"
          size="sm"
          :disabled="isDisabelPermission"
          color="info"
          >Nâng quyền</CButton
        >

        <CButton
          class="right-button"
          @click="changePermissionClick('Down')"
          size="sm"
          :disabled="isDisabelPermission"
          color="info"
          >Hạ quyền</CButton
        >

        <CButton
          class="right-button"
          @click="changePermissionClick('Delete')"
          size="sm"
          :disabled="isDisabelPermission"
          color="info"
          >Xoá</CButton
        >
      </CButtonGroup>

      <CButtonGroup v-show="isShowChangePermission" class="mx-1 d-sm-down-none">
        <CButton
          class="right-button"
          @click="changePermissionClick('UpAdmin')"
          size="sm"
          :disabled="isDisabelPermission"
          color="info"
          >Chỉ Định Admin</CButton
        >

        <CButton
          class="right-button"
          @click="changePermissionClick('UpEmp')"
          size="sm"
          :disabled="isDisabelPermission"
          color="info"
          >Chỉ Định Nhân Viên</CButton
        >
      </CButtonGroup>
    </CCardHeader>
    <CCardBody
      :style="
        'height: ' +
        heightGrid +
        'px; display: block; overflow-y: scroll; width: 100%'
      "
    >
      <CDataTable
        class="mb-10 table-outline"
        hover
        :items="dataItems"
        :fields="tableFields"
        head-color="light"
        no-sorting
        border
        striped
        tableFilter
        outlined
        sorter
        column-filter
      >
        <td slot="infoData" slot-scope="{ item }">
          <div class="c-avatar" style="float: left">
            <img :src="item.infoData.url" class="c-avatar-img" alt="" />
            <span
              class="c-avatar-status"
              :class="`bg-${item.infoData.status || 'secondary'}`"
            ></span>
          </div>
          <div style="width: 200px; float: left; word-wrap: break-word">
            <span>ID: {{ item.infoData.id }}<br /></span>
            <span>Name: {{ item.infoData.name }}</span>
          </div>
        </td>
        <td slot="checkbox" class="text-center" slot-scope="{ item }">
          <CInputCheckbox :checked.sync="item.isCheck" />
        </td>
        <td slot="status" class="text-center" slot-scope="{ item }">
          <div class="c-avatar">
            <span
              class="c-avatar-status"
              :class="`bg-${item.status || 'secondary'}`"
            ></span>
          </div>
        </td>
        <template #addAcountPartner="{ item, index }">
          <td>
            <CCol xl class="mb-3 mb-xl-0">
              <CButton
                @click="addAccountPartnerClick(item, index)"
                block
                variant="outline"
                color="info"
                >Thêm
              </CButton>
            </CCol>
          </td>
        </template>
      </CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
import * as objectUitls from "../../../../js/utils/objectUtils";
export default {
  name: "CpmDataTable",
  props: {
    tableFields: Array,
    dataItems: Array,
    heightGrid: {
      type: String,
      default: "1210",
    },
    isShowChangePermission: {
      type: Boolean,
      default: false,
    },
    isShowCardHeader: {
      type: Boolean,
      default: true,
    },
  },
  watch: {
    dataItems: function (newVal, oldVal) {
      if (newVal !== null && newVal.length > 0) {
        this.enableButton(false);
      } else {
        this.enableButton(true);
      }
    },
  },
  data() {
    return {
      btnSelectAll: true,
      btnUnSelect: true,
      infoModal: false,

      isDisabelPermission: false,
    };
  },
  methods: {
    selectRow: function (flag) {
      console.log(flag);
      this.dataItems.forEach((item) => {
        item.isCheck = flag;
      });
      this.btnSelectAll = flag;
      this.btnUnSelect = !flag;
    },

    enableButton: function (isEnable) {
      this.btnSelectAll = isEnable;
      this.btnUnSelect = isEnable;
    },

    addAccountPartnerClick: function (item, index) {
      this.$emit("showModal", item);
    },

    changePermissionClick: function (flagChangePermission) {
      this.$emit("updatePermission", flagChangePermission);
    },
  },
};
</script>

<style scoped></style>
