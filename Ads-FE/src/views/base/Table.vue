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
        <td slot="idBm" slot-scope="{ item }" class="containerHover">
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
      </CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
export default {
  name: "Table",
  data() {
    return {};
  },
  props: {
    items: Array,
    fields: [{ key: "idBm", _classes: "text-center" }],
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
