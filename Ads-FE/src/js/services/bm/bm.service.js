import axios from 'axios';
import authHeader from '../authen/auth-header';
import * as constantUtils from "../../constantUtils"

class UserService {
  getListBmMaster() {
    return axios.get(constantUtils.API_URL + 'bm/loadBm',{ headers: authHeader() });
  }

  addBmMaster(data) {
    return axios.post(constantUtils.API_URL + 'bm/updateBm',data,{ headers: authHeader() });
  }
}

export default new UserService();
