import axios from "axios";
import * as constantUtils from "@/js/constantUtils";
import authHeader from "../authen/auth-header";

class screenHelper {
  submitServer(type, controler, data = null) {
    const loginScope = JSON.parse(localStorage.getItem("user"));
    const afterData =
      data == null
        ? { loginScope: loginScope }
        : Object.assign({ loginScope: loginScope }, data);
    if (type === "post") {
      return axios.post(constantUtils.API_URL + controler, afterData, {
        headers: authHeader(),
      });
    } else {
      // return axios.get(constantUtils.API_URL + controler, {
      //   headers: authHeader(),
      //   data: afterData,
      // });
    }
  }
}

export default new screenHelper();
