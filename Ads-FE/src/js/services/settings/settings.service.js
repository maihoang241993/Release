import screenHelper from "../common/screenHelper.js";

class UserService {

    loadSetting() {
        return screenHelper.submitServer("post", "setting/loadSetting");
    }

    updateSetting(data) {
        return screenHelper.submitServer("post", "setting/updateSetting", data);
    }
}

export default new UserService();
