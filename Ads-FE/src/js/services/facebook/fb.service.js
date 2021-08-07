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
      constantUtils.FB_URL +
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

  getAllListUser(data) {
    // &fields=["email","expiry_time","first_name","finance_permission","developer_permission","ip_permission","partner_center_admin_permission","partner_center_analyst_permission","partner_center_education_permission","partner_center_marketing_permission","partner_center_operations_permission","last_name","manage_page_in_www","marked_for_removal","pending_email","role","two_fac_status","is_two_fac_blocked","sso_migration_status","business_role_request.fields(creation_source.fields(name),created_by.fields(name),created_time,updated_time)","transparency_info_seen_by","work_profile_pic","is_pending_integrity_review"]
    const api =
      constantUtils.FB_URL +
      data.id +
      "/business_users?access_token=" +
      data.token;
    // '&fields=account_id,disable_reason,name,currency,account_status,business,funding_source_details';

    return axios.get(api);
  }

  getListAccount(data) {
    let api =
      constantUtils.FB_URL +
      data.id +
      "/owned_ad_accounts?" +
      "access_token=" +
      data.token +
      "&limit=200&" +
      "fields=account_id,disable_reason,name,currency,account_status,business,funding_source_details";

    return axios.get(api);
  }

  getListPartnerAccount(data) {
    const api =
      constantUtils.FB_URL +
      data.id +
      "/client_ad_accounts?access_token=" +
      data.token +
      "&limit=200&fields=account_id,disable_reason,name,currency,account_status,business,funding_source_details";
    return axios.get(api);
  }

  updateAuthenAccountPartner(data) {
    const api =
      constantUtils.FB_URL +
      data.account_id +
      "/assigned_users?business=" +
      data.business +
      "&access_token=" +
      data.token +
      "&user=" +
      data.userId +
      "&tasks=MANAGE";
    return axios.post(api);
  }

  addPartner(data) {
    const config = {
      header: {
        "Content-Type": "multipart/form-data",
      },
    };
    const api =
      constantUtils.FB_URL +
      "act_" +
      data.account_id +
      "/agencies?access_token=" +
      data.token;

    let formData = new FormData();
    formData.append("accountId", data.account_id);
    formData.append("acting_brand_id", data.acting_brand_id);
    formData.append("business", data.business);
    formData.append(
      "permitted_tasks",
      JSON.stringify(["ADVERTISE", "ANALYZE", "DRAFT", "MANAGE"])
    );

    return axios.post(api, formData, config);
  }

  changePermissionAcount(data, upDown) {
    const permissionUpDown = upDown === "Up" ? "MANAGE" : "ANALYZE";
    const api =
      constantUtils.FB_URL +
      data.account_id +
      "/assigned_users?business=" +
      data.business +
      "&access_token=" +
      data.token +
      "&user=" +
      data.userId +
      "&tasks=" +
      permissionUpDown;

    return axios.post(api);
  }

  deletePermissionAcount(data) {
    const api =
      constantUtils.FB_URL +
      data.account_id +
      "/assigned_users?business=" +
      data.business +
      "&access_token=" +
      data.token;

    let formData = new FormData();
    formData.append("accountId", data.account_id.split("_")[1]);
    formData.append("business", data.business);
    formData.append("user", data.userId);

    const config = {
      header: {
        "Content-Type": "multipart/form-data",
      },
      data: formData,
    };

    return axios.delete(api, config);
  }

  UpdateAdminOrEmp(data, upDown) {
    const api =
      constantUtils.FB_URL + data.userId + "?access_token=" + data.token;

    const permissionUpDown = upDown === "UpAdmin" ? "ADMIN" : "EMPLOYEE";

    let formData = new FormData();
    formData.append("role", permissionUpDown);

    const config = {
      header: {
        "Content-Type": "multipart/form-data",
      },
    };

    return axios.post(api, formData, config);
  }

  changeRolePeople(data, upDown) {
    const permissionUpDown = upDown === "Up" ? "ADMIN" : "ANALYZE";
    const config = {
      header: {
        "Content-Type": "multipart/form-data",
      },
    };
    const api =
      constantUtils.FB_URL + data.user_id + "?access_token=" + data.token;

    let formData = new FormData();
    formData.append("roles", JSON.stringify([permissionUpDown]));

    return axios.post(api, formData, config);
  }
}

export default new FbService();
