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

  updateTokenAll(data) {
    return screenHelper.submitServer("post", "bm/updateTokenBm", data);
  }
}

export default new UserService();
