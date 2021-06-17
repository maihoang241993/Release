<template>
  <CHeader fixed with-subheader light>
    <CToggler
      in-header
      class="ml-3 d-lg-none"
      @click="$store.commit('toggleSidebarMobile')"
    />
    <CToggler
      in-header
      class="ml-3 d-md-down-none"
      @click="$store.commit('toggleSidebarDesktop')"
    />
    <CHeaderBrand class="mx-auto d-lg-none" to="/"
      ><CImg
        class="c-sidebar-brand-full"
        :src="'logo_kbook.png'"
        :height="50"
      />
    </CHeaderBrand>
    <CHeaderNav class="d-md-down-none mr-auto">
      <CHeaderNavItem class="px-3">
        <CHeaderNavLink to="/dashboard"> Dashboard </CHeaderNavLink>
      </CHeaderNavItem>
      <!--      <CHeaderNavItem class="px-3">-->
      <!--        <CHeaderNavLink to="/users" exact>-->
      <!--          Users-->
      <!--        </CHeaderNavLink>-->
      <!--      </CHeaderNavItem>-->
      <!--      <CHeaderNavItem class="px-3">-->
      <!--        <CHeaderNavLink>-->
      <!--          Settings-->
      <!--        </CHeaderNavLink>-->
      <!--      </CHeaderNavItem>-->
    </CHeaderNav>
    <CHeaderNav class="mr-4">
      <CHeaderNavItem class="d-md-down-none mx-2">
        <CHeaderNavLink>
          <CIcon name="cil-bell" />
        </CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="d-md-down-none mx-2">
        <CHeaderNavLink>
          <CIcon name="cil-list" />
        </CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="d-md-down-none mx-2">
        <CHeaderNavLink>
          <CIcon name="cil-envelope-open" />
        </CHeaderNavLink>
      </CHeaderNavItem>
      <CHeaderNavItem class="d-md-down-none mx-2">
        <span>{{ username }}</span>
      </CHeaderNavItem>
      <TheHeaderDropdownAccnt />
    </CHeaderNav>
    <CSubheader class="px-3">
      <CBreadcrumbRouter class="border-0 mb-0" />
    </CSubheader>
  </CHeader>
</template>

<script>
import TheHeaderDropdownAccnt from "./TheHeaderDropdownAccnt";

export default {
  name: "TheHeader",
  data() {
    return {
      username: null,
    };
  },
  created() {
    this.username = JSON.parse(localStorage.getItem("user")).username;
  },
  components: {
    TheHeaderDropdownAccnt,
  },
};
</script>
