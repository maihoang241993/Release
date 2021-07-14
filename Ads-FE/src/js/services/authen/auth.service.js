import axios from "axios";
import * as constantUtils from "../../constantUtils";

class AuthService {
  checkAuthen(accessToken) {
    return axios
        .post(constantUtils.API_URL + "auth/", {
          accessToken: accessToken,
        })
        .then((response) => {
          if (response.data.accessToken) {
            localStorage.setItem("user", JSON.stringify(response.data));
          }

          return response.data;
        });
  }

  login(user) {
    return axios
      .post(constantUtils.API_URL + "auth/signin", {
        username: user.username,
        password: user.password,
      })
      .then((response) => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(user) {
    return axios.post(constantUtils.API_URL + "auth/signup", {
      username: user.username,
      email: user.email,
      password: user.password,
    });
  }
}

export default new AuthService();
