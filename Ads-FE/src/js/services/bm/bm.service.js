import axios from "axios";
import authHeader from "../authen/auth-header";
import * as constantUtils from "../../constantUtils";
import screenHelper from "../common/screenHelper.js";

class UserService {
  getListBmMaster() {
    return screenHelper.submitServer("post", "bm/loadBm");
  }

  addBmMaster(data) {
    return screenHelper.submitServer("post", "bm/updateBm", data);
  }

  deleleBmMaster(data) {
    return screenHelper.submitServer("post", "bm/deleteBm", data);
  }
}

export default new UserService();
