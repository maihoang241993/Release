import axios from "axios";
import * as constantUtils from "../constantUtils"
import 'babel-polyfill'

export default function createBaseApi() {
  const token = JSON.parse(localStorage.getItem(constantUtils.userToken));
  if (token == null) {
    const url1 = constantUtils.url + constantUtils.pathAuthen
    axios
      .post(url1, {
        username: constantUtils.user,
        password: constantUtils.pass,
      })
      .then((response) => {
        localStorage.setItem(constantUtils.userToken, JSON.stringify(response.data.token));
      })
      .catch((e) => {
        this.errors.push(e);
      });
  }
  return axios.create({
    baseURL: constantUtils.url + "/",
    headers: {
      Authorization: "Bearer " + JSON.parse(localStorage.getItem(constantUtils.userToken)),
    }
  });
}
