import axios from "axios";
import * as constantUtils from "../../constantUtils";
import screenHelper from "@/js/services/common/screenHelper";

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
        const config = {
            headers: {
                "Access-Control-Allow-Origin": "https://graph.facebook.com",
                'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
            }
        }
        return axios.get(api, config);
    }

    getListUser(data) {
        return screenHelper.submitServer("post", "fb/getListUser", data)
    }

    updateAuthenAccountPartner(data) {
        return screenHelper.submitServer("post", "fb/updateAuthenAccountPartner", data);
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

    changePermissionAcount(data) {
        return screenHelper.submitServer("post", "fb/changePermissionAcount", data)
    }

    deletePermissionAcount(data) {
        return screenHelper.submitServer("post", "fb/deletePermissionAcount", data)
    }

    updateAdminOrEmp(data) {
        return screenHelper.submitServer("post", "fb/updateAdminOrEmp", data)
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
