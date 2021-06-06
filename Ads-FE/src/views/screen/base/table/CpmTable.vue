<template>
  <CCard>
    <CCardHeader>
      <slot name="header"> <CIcon name="cil-grid" /> {{ caption }} </slot>
    </CCardHeader>
    <CCardBody
      style="height: 1560px; display: block; overflow-y: scroll; width: 100%"
    >
      <CDataTable
        :hover="hover"
        :striped="striped"
        :border="border"
        :small="small"
        :fixed="fixed"
        :items="items"
        :fields="fields"
        :items-per-page="small ? 10 : 14"
        :dark="dark"
        :header="header"
        :pagination="pagination"
        :clickableRows="clickableRows"
        v-on:row-clicked="rowClicked"
      >
        <td
          slot="idBm"
          slot-scope="{ item }"
          :class="deleteRow ? 'containerHover' : ''"
        >
          <div class="c-avatar" style="float: left">
            <img :src="item.idBm.url" class="c-avatar-img" alt="" />
            <span
              class="c-avatar-status"
              :class="`bg-${item.idBm.status || 'secondary'}`"
            ></span>
          </div>
          <CLink class="overlay" @click="deleteClick(item.idBm.id)">
            <CIcon name="cil-trash" />
          </CLink>
          <div style="width: 200px; float: left; word-wrap: break-word">
            <span>ID: {{ item.idBm.id }}<br /></span>
            <span>Name: {{ item.idBm.name }}</span>
          </div>
        </td>
        <template #show_details="{ item, index }">
          <td class="py-2">
            <CButton
              color="primary"
              variant="outline"
              square
              size="sm"
              @click="toggleDetails(item, index)"
            >
              {{ Boolean(item._toggled) ? "Hide" : "Show" }}
            </CButton>
          </td>
        </template>
        <template #details="{ item }">
          <CCollapse
            :show="Boolean(item._toggled)"
            :duration="collapseDuration"
          >
            <CCardBody>
              <CCard>
                <CCardHeader>
                  <strong>Basic Form</strong> Elements
                </CCardHeader>
                <CCardBody>
                  <CForm>
                    <CRow form class="form-group">
                      <CCol tag="label" sm="5" class="col-form-label">
                        Quản lý chiến dịch
                      </CCol>
                      <CCol sm="5">
                        <CSwitch
                          class="mr-1"
                          color="info"
                          :checked="true"
                          shape="pill"
                        />
                      </CCol>
                    </CRow>
                    <CRow form class="form-group">
                      <CCol tag="label" sm="5" class="col-form-label">
                        Xem hiệu quả
                      </CCol>
                      <CCol sm="5">
                        <CSwitch
                          class="mr-1"
                          color="info"
                          :checked="true"
                          shape="pill"
                        />
                      </CCol>
                    </CRow>
                    <CRow form class="form-group">
                      <CCol tag="label" sm="5" class="col-form-label">
                        Quản lý mẫu mô phỏng trong Creative Hub
                      </CCol>
                      <CCol sm="5">
                        <CSwitch
                          class="mr-1"
                          color="info"
                          :checked="true"
                          shape="pill"
                        />
                      </CCol>
                    </CRow>
                    <CRow form class="form-group">
                      <CCol tag="label" sm="5" class="col-form-label">
                        Quản lý tài khoản quảng cáo
                      </CCol>
                      <CCol sm="5">
                        <CSwitch
                          class="mr-1"
                          color="info"
                          :checked="true"
                          shape="pill"
                        />
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
              <!--                      <CMedia :aside-image-props="{ height: 102 }">-->

              <!--                        <h4>-->
              <!--                          {{ item.username }}-->
              <!--                        </h4>-->
              <!--                        <p class="text-muted">-->
              <!--                          User since: {{ item.registered }}-->
              <!--                        </p>-->
              <!--                        <CButton size="sm" color="info" class="">-->
              <!--                          User Settings-->
              <!--                        </CButton>-->
              <!--                        <CButton size="sm" color="danger" class="ml-1">-->
              <!--                          Delete-->
              <!--                        </CButton>-->
              <!--                      </CMedia>-->
            </CCardBody>
          </CCollapse>
        </template>
      </CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
export default {
  name: "CpmTable",
  data() {
    return {
      collapseDuration: 0,
    };
  },
  props: {
    items: Array,
    fields: {
      type: Array,
      default: () => {
        return [{ key: "idBm", _classes: "text-center" }];
      },
    },
    caption: {
      type: String,
      default: "Table",
    },
    hover: Boolean,
    striped: Boolean,
    border: Boolean,
    small: Boolean,
    fixed: Boolean,
    dark: Boolean,
    header: Boolean,
    pagination: Boolean,
    clickableRows: Boolean,
    deleteRow: {
      type: Boolean,
      default: true,
    },
  },
  methods: {
    deleteClick(id) {
      this.$confirm("Bạn muốn xóa " + id, "Xác nhận", "warning")
        .then((res) => {
          this.$emit("deleteClick", true, id);
        })
        .catch((e) => {
          this.$emit("deleteClick", false, id);
        });
    },
    getBadge(status) {
      return status === "Active"
        ? "success"
        : status === "Inactive"
        ? "secondary"
        : status === "Pending"
        ? "warning"
        : status === "Banned"
        ? "danger"
        : "primary";
    },

    rowClicked(item) {
      this.$emit("rowClick", item);
    },

    toggleDetails: async function (item) {
      let itemValue;
      for (let i = 0; i < this.items.length; i++) {
        if (this.items[i].id === item.id) {
          itemValue = this.items[i];
          break;
        }
      }
      this.$set(itemValue, "_toggled", !item._toggled);
      this.collapseDuration = 300;
      this.$nextTick(() => {
        this.collapseDuration = 0;
      });
    },
  },
};
</script>

<style>
.containerHover {
  position: relative;
  width: auto;
  max-width: 300px;
}
.containerHover:hover .overlay {
  opacity: 1;
}

.overlay {
  position: inherit;
  bottom: 0;
  /*background: rgb(0, 0, 0);*/
  /*background: rgba(0, 0, 0, 0.5); !* Black see-through *!*/
  /*color: #f1f1f1;*/
  width: auto;
  transition: 0.5s ease;
  opacity: 0;
  /*color: white;*/
  text-align: right;
}
</style>
