import screenHelper from "../common/screenHelper.js";

class CookiesService {
    getListCookies() {
        return screenHelper.submitServer("post", "cookies/loadCookies");
    }
}

export default new CookiesService();
