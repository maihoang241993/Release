<template>
  <div class="c-app flex-row align-items-center">
    <CContainer>
      <CAlert :show.sync="dismissCountDown" closeButton :color="colorMsg" fade>
        <strong>{{ messagesError.header }}</strong
        ><br />
        <strong>{{ messagesError.body }}</strong>
      </CAlert>
      <CRow class="justify-content-center">
        <CCol md="6">
          <CCardGroup>
            <CCard class="p-4">
              <CCardBody @submit="handleLogin">
                <CForm>
                  <h1>Login</h1>
                  <p class="text-muted">Sign In to your account</p>
                  <CInput
                    v-model="dataLogin.username"
                    placeholder="Username"
                    autocomplete="username email"
                  >
                    <template #prepend-content
                      ><CIcon name="cil-user"
                    /></template>
                  </CInput>
                  <CInput
                    placeholder="Password"
                    type="password"
                    autocomplete="curent-password"
                    v-model="dataLogin.password"
                  >
                    <template #prepend-content
                      ><CIcon name="cil-lock-locked"
                    /></template>
                  </CInput>
                  <CRow>
                    <CCol col="6" class="text-left">
                      <CButton @click="handleLogin" color="primary" class="px-4"
                        >Login</CButton
                      >
                    </CCol>
                    <CCol col="6" class="text-right">
<!--                      <CButton color="link" class="px-0"-->
<!--                        >Forgot password?</CButton-->
<!--                      >-->
                      <CButton color="link" class="d-lg-none"
                        >Register now!</CButton
                      >
                    </CCol>
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>
            <CCard
              color="primary"
              text-color="white"
              class="text-center py-5 d-md-down-none"
              body-wrapper
              hidden
            >
              <CCardBody>
                <h2>Sign up</h2>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
                  do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
                <CButton color="light" variant="outline" size="lg">
                  Register Now!
                </CButton>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      dismissCountDown: 0,
      messagesError: {
        header: null,
        body: null,
      },
      colorMsg: null,
      dataLogin: {
        username: null,
        password: null
      },
      message: null
    }
  },
  methods: {
    handleLogin: function () {
      this.$store.dispatch("auth/login", this.dataLogin).then(
          () => {
            this.$router.push("/Dashboard");
          },
          (error) => {
            this.loading = false;
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
            this.showMessages(1,this.message)
          }
      );
    },

    showMessages: async function (code, header, body = "") {
      this.messagesError = {
        header: header,
        body: body,
      };
      this.colorMsg = code == 1 ? "danger" : "success";
      this.dismissCountDown = 5;
    },
  },
};
</script>
