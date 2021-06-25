import axios from "axios";
const https = require('https');
import * as constantUtils from "../../constantUtils";

class AuthService {
  login(user) {
    return axios
      .post(
        constantUtils.API_URL + "auth/signin",
        {
          username: user.username,
          password: user.password,
        },
        {
          httpsAgent: new https.Agent({
            rejectUnauthorized: false,
          }),
        }
      )
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
