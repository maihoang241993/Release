import axios from "axios";
import * as constantUtils from "../../constantUtils";

class FbService {
  getListBm(data) {
    const api =
      constantUtils.FB_URL +
      data.idBm.id +
      "/owned_ad_accounts?" +
      "access_token=" +
      data.idBm.token +
      "&limit=200&" +
      "fields=account_id,disable_reason,name,currency,account_status,business,funding_source_details";

    return axios.get(api);
  }

  getListUser(data) {
    const api =
      "https://graph.facebook.com/v10.0/" +
      data.id +
      "/assigned_users?business=" +
      data.idBm +
      "&access_token=" +
      data.token +
      '&fields=["id","name","user_type",' +
      '"permitted_tasks","can_be_removed",' +
      '"is_profile_core_app_admin","is_system_user"]';

    return axios.get(api);
  }

  // addBmMaster(data) {
  //     return axios.post(constantUtils.API_URL + 'bm/updateBm',data,{ headers: authHeader() });
  // }
  //
  // deleleBmMaster(data) {
  //     return axios.post(constantUtils.API_URL + 'bm/deleteBm',data,{ headers: authHeader() });
  // }
}

export default new FbService();
