<template>
  <CWrapper>
    <transition name="fade" mode="out-in">
      <router-view :key="$route.path"></router-view>
    </transition>
  </CWrapper>
</template>

<script>
export default {
  name: "StartBootApp",
  created() {
  }
}
</script>

<style scoped>

</style>
