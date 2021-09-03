<template>
  <div>
    <CRow>
      <CCol sm="6" md="12">
        <CCard border-color="success">
          <CCardHeader>
            <strong>Setting</strong>
          </CCardHeader>
          <CCardBody>
            <CForm>
              <template v-for="(name, key) in radioNames">
                <CRow form class="form-group" :key="name">
                  <CCol sm="3">
                    {{ name }}
                  </CCol>
                  <CInputRadioGroup
                      class="col-sm-9"
                      :options="options"
                      :custom="key > 1"
                      :checked.sync="checked"
                      :inline="true"
                  />
                </CRow>
              </template>
            </CForm>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
  </div>
</template>

<script>
import SettingsService from "../../../js/services/settings/settings.service";
import * as objectUitls from "@/js/utils/objectUtils";
import * as constans from "../../../js/constantUtils";

export default {
  name: "Setting",
  data() {
    return {
      options: ['Token SU', 'Token Full'],
      radioNames: ['Token'],
      checked: null
    }
  },
  created: async function () {
    await this.init();
  },
  watch: {
    checked: async function (newVal, oldVal) {
      if (!objectUitls.isNullOrEmpty(oldVal)) {
        await this.updateSettings();
      }
    }
  },
  methods: {
    init: async function () {
      await SettingsService.loadSetting().then(
          (response) => {
            this.dataBm = this.transportDataSetting(response.data);
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

    transportDataSetting: function (data) {
      if (!objectUitls.isNullOrEmpty(data)) {
        this.checked = data[0].token === 0 ? 'Token SU' : 'Token Full';
      }
    },

    updateSettings: async function () {
      const data = {
        token: this.checked === 'Token SU' ? 0 : 1
      }
      await SettingsService.updateSetting(data).then(
          (response) => {
            this.$showMessages(constans.MSG_BUS_005, this.msg1);
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
    }
  }
}
</script>

<style scoped>

</style>