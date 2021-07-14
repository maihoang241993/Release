import StartBootApp from "../views/StartBootApp"
import * as constantUtils from "@/js/constantUtils";

var installed = false

function install(Vue, options) {

    if (installed) {
        window.console.log("already installed.")
        return
    }

    // 1. add global method or property
    Vue.myGlobalMethod = function () {
        console.log("123 test")
    }

    Vue.prototype.msg1 = constantUtils.TYPE_MSG.SUCCESS
    Vue.prototype.msg2 = constantUtils.TYPE_MSG.INFO
    Vue.prototype.msg3 = constantUtils.TYPE_MSG.WARNING
    Vue.prototype.msg4 = constantUtils.TYPE_MSG.ERROR

    // 2. add a global asset
    Vue.directive('my-directive', {
        bind(el, binding, vnode, oldVnode) {
            // some logic ...
        }
    })

    // 3. inject some component options
    Vue.mixin({
        created: function () {
        }
    })

    // 4. add an instance method
    Vue.prototype.$showMessages = function (value, level = null) {
        switch (level) {
            case constantUtils.TYPE_MSG.SUCCESS:
                Vue.$toast.success(value);
                break;
            case constantUtils.TYPE_MSG.INFO:
                Vue.$toast.info(value);
                break;
            case constantUtils.TYPE_MSG.WARNING:
                Vue.$toast.warning(value);
                break;
            case constantUtils.TYPE_MSG.ERROR:
                Vue.$toast.error(value);
                break;
            default:
                Vue.$toast(value);
        }
    }

    Vue.prototype.$initService = function () {
        // Vue.$store.dispatch('auth/checkAuthen');
        console.log("okokokokokok")
    }

    installed = true
}

const index = {
    install: install
}

export default index
