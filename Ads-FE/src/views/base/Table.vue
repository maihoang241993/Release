<template>
  <CCard>
    <CCardHeader>
      <slot name="header"> <CIcon name="cil-grid" /> {{ caption }} </slot>
    </CCardHeader>
    <CCardBody
      style="height: 1210px; display: block; overflow-y: scroll; width: 100%"
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
        <td slot="idBm" slot-scope="{ item }">
          <div  class="mytd">
          <div  style="float: left">
            <div class="c-avatar" style="float: left">
              <img :src="item.idBm.url" class="c-avatar-img" alt="" />
              <span
                  class="c-avatar-status"
                  :class="`bg-${item.idBm.status || 'secondary'}`"
              ></span>
            </div>
            <div style="width:200px; float: left; word-wrap: break-word">
              <span>ID: {{ item.idBm.id }}<br /></span>
              <span>Name: {{ item.idBm.name }}</span>
            </div>
          </div>
          <div class="hide" style="float: right">
              <CButton
                  color="info"
                  @click="testClick"
              >Share</CButton>
          </div>

          <!--          <div class="myDIV" >Del</div>-->
          <!--          <div class="hide">I am</div>-->

          <!--          <CIcon name="cil-settings"/>-->
          </div>
        </td>
      </CDataTable>
    </CCardBody>
  </CCard>
</template>

<script>
export default {
  name: "Table",
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
    testClick(item){
      debugger
      alert(item)
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
.hide {
  display: none;
}

.mytd:hover + .hide {
  display: block;
  color: red;
}
</style>
