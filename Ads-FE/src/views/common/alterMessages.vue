<template>
  <div>
    <CAlert
        :show.sync="dismissCountDown"
        closeButton
        color="warning"
        fade
    >
      Alert will dismiss after
      <strong>{{dismissCountDown}}</strong> seconds...
    </CAlert>
  </div>
</template>

<script>
export default {
  name: 'alterMessages',
  props: {
    dismissCountDown: Number,
  },
  data () {
    return {
      dismissSecs: String,
      alert1: true,
      alert2: true
    }
  }
}
</script>
