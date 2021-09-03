import Vue from 'vue'
import Router from 'vue-router'
import VueAxios from 'vue-axios';
import axios from 'axios';
import store from '../store';
import * as constantUtils from "../js/constantUtils";
import * as objectUitls from "../js/utils/objectUtils";

// Containers
const TheContainer = () => import('@/containers/TheContainer')

// Views
const Dashboard = () => import('@/views/Dashboard')

const Colors = () => import('@/views/theme/Colors')
const Typography = () => import('@/views/theme/Typography')

const Charts = () => import('@/views/charts/Charts')
const Widgets = () => import('@/views/widgets/Widgets')

// Views - Components
const Cards = () => import('@/views/base/Cards')
const Forms = () => import('@/views/base/Forms')
const Switches = () => import('@/views/base/Switches')
const Tables = () => import('@/views/base/Tables')
const Tabs = () => import('@/views/base/Tabs')
const Breadcrumbs = () => import('@/views/base/Breadcrumbs')
const Carousels = () => import('@/views/base/Carousels')
const Collapses = () => import('@/views/base/Collapses')
const Jumbotrons = () => import('@/views/base/Jumbotrons')
const ListGroups = () => import('@/views/base/ListGroups')
const Navs = () => import('@/views/base/Navs')
const Navbars = () => import('@/views/base/Navbars')
const Paginations = () => import('@/views/base/Paginations')
const Popovers = () => import('@/views/base/Popovers')
const ProgressBars = () => import('@/views/base/ProgressBars')
const Tooltips = () => import('@/views/base/Tooltips')

// Views - Buttons
const StandardButtons = () => import('@/views/buttons/StandardButtons')
const ButtonGroups = () => import('@/views/buttons/ButtonGroups')
const Dropdowns = () => import('@/views/buttons/Dropdowns')
const BrandButtons = () => import('@/views/buttons/BrandButtons')

// Views - Icons
const CoreUIIcons = () => import('@/views/icons/CoreUIIcons')
const Brands = () => import('@/views/icons/Brands')
const Flags = () => import('@/views/icons/Flags')

// Views - Notifications
const Alerts = () => import('@/views/notifications/Alerts')
const Badges = () => import('@/views/notifications/Badges')
const Modals = () => import('@/views/notifications/Modals')

// Views - Pages
const Page404 = () => import('@/views/pages/Page404')
const Page500 = () => import('@/views/pages/Page500')
const Login = () => import('@/views/pages/Login')
const Register = () => import('@/views/pages/Register')

// Users
const Users = () => import('@/views/users/Users')
const User = () => import('@/views/users/User')

// WorkingSpace
const StartBootApp = () => import('@/views/StartBootApp')
const SampleOne = () => import('@/views/screen/ads/SampleOne')
const AccountAuthorization = () => import('@/views/screen/account/AccountAuthorization')
const AddPartner = () => import('@/views/screen/partner/AddPartner')
const Cookies = () => import('@/views/screen/cookies/Cookies')
const PeopleAuthorization = () => import('@/views/screen/account/PeopleAuthorization.vue')
const Setting = () => import('@/views/screen/settings/Setting.vue')


Vue.use(VueAxios, axios)
Vue.use(Router)

export const router = new Router({
    mode: 'history',
    linkActiveClass: 'active',
    scrollBehavior: () => ({y: 0}),
    routes: configRoutes()
})

router.beforeEach((to, from, next) => {
    const publicPages = ['/Login', '/pages/register'];
    const authRequired = publicPages.includes(to.path);
    const user = JSON.parse(localStorage.getItem("user"))
    if (!objectUitls.isNullOrEmpty(user)) {
        store.dispatch('auth/checkAuthen', user.accessToken).then(
            (res) => {
                if (!res.status) {
                    localStorage.removeItem("user");
                    next('/Login');
                } else {
                    next()
                }
            },
            (error) => {
                Vue.prototype.$showMessages(constantUtils.MSG_ERROR_502, Vue.prototype.msg4)
                localStorage.removeItem("user");
                next()
            }
        );
    } else if (authRequired) {
        next()
    } else {
        next('/Login');
    }
})

function configRoutes() {
    return [
        {
            path: '/',
            redirect: 'Login',
            component: StartBootApp,
            children: [
                {
                    path: '/login',
                    name: 'Login',
                    component: Login
                }
            ]
        },
        {
            path: '/Home',
            redirect: 'Dashboard',
            component: TheContainer,
            children: [
                {
                    path: '/Dashboard',
                    name: 'Dashboard',
                    component: Dashboard
                },
                {
                    path: '/Sampleone',
                    name: 'Sampleone',
                    component: SampleOne
                },
                {
                    path: '/accountauthorization',
                    name: 'AccountAuthorization',
                    component: AccountAuthorization
                },
                {
                    path: '/peopleAuthorization',
                    name: 'PeopleAuthorization',
                    component: PeopleAuthorization
                },
                {
                    path: '/addPartner',
                    name: 'AddPartner',
                    component: AddPartner
                },
                {
                    path: '/cookies',
                    name: 'cookies',
                    component: Cookies
                },
                {
                    path: '/setting',
                    name: 'setting',
                    component: Setting
                },
                // {
                //   path: 'workingspace',
                //   redirect: '/screen/scans',
                //   name: 'WorkingSpace',
                //   component: {
                //     render (c) { return c('router-view') }
                //   },
                //   children: [
                //     {
                //       path: 'sampleone',
                //       name: 'SampleOne',
                //       component: SampleOne
                //     }
                //   ]
                // },
                {
                    path: 'theme',
                    redirect: '/theme/colors',
                    name: 'Theme',
                    component: {
                        render(c) {
                            return c('router-view')
                        }
                    },
                    children: [
                        {
                            path: 'colors',
                            name: 'Colors',
                            component: Colors
                        },
                        {
                            path: 'typography',
                            name: 'Typography',
                            component: Typography
                        }
                    ]
                },
                {
                    path: 'charts',
                    name: 'Charts',
                    component: Charts
                },
                {
                    path: 'widgets',
                    name: 'Widgets',
                    component: Widgets
                },
                {
                    path: 'users',
                    meta: {
                        label: 'Users'
                    },
                    component: {
                        render(c) {
                            return c('router-view')
                        }
                    },
                    children: [
                        {
                            path: '',
                            name: 'Users',
                            component: Users
                        },
                        {
                            path: ':id',
                            meta: {
                                label: 'User Details'
                            },
                            name: 'User',
                            component: User
                        }
                    ]
                },
                {
                    path: 'base',
                    redirect: '/base/cards',
                    name: 'Base',
                    component: {
                        render(c) {
                            return c('router-view')
                        }
                    },
                    children: [
                        {
                            path: 'cards',
                            name: 'Cards',
                            component: Cards
                        },
                        {
                            path: 'forms',
                            name: 'Forms',
                            component: Forms
                        },
                        {
                            path: 'switches',
                            name: 'Switches',
                            component: Switches
                        },
                        {
                            path: 'tables',
                            name: 'Tables',
                            component: Tables
                        },
                        {
                            path: 'tabs',
                            name: 'Tabs',
                            component: Tabs
                        },
                        {
                            path: 'breadcrumbs',
                            name: 'Breadcrumbs',
                            component: Breadcrumbs
                        },
                        {
                            path: 'carousels',
                            name: 'Carousels',
                            component: Carousels
                        },
                        {
                            path: 'collapses',
                            name: 'Collapses',
                            component: Collapses
                        },
                        {
                            path: 'jumbotrons',
                            name: 'Jumbotrons',
                            component: Jumbotrons
                        },
                        {
                            path: 'list-groups',
                            name: 'List Groups',
                            component: ListGroups
                        },
                        {
                            path: 'navs',
                            name: 'Navs',
                            component: Navs
                        },
                        {
                            path: 'navbars',
                            name: 'Navbars',
                            component: Navbars
                        },
                        {
                            path: 'paginations',
                            name: 'Paginations',
                            component: Paginations
                        },
                        {
                            path: 'popovers',
                            name: 'Popovers',
                            component: Popovers
                        },
                        {
                            path: 'progress-bars',
                            name: 'Progress Bars',
                            component: ProgressBars
                        },
                        {
                            path: 'tooltips',
                            name: 'Tooltips',
                            component: Tooltips
                        }
                    ]
                },
                {
                    path: 'buttons',
                    redirect: '/buttons/standard-buttons',
                    name: 'Buttons',
                    component: {
                        render(c) {
                            return c('router-view')
                        }
                    },
                    children: [
                        {
                            path: 'standard-buttons',
                            name: 'Standard Buttons',
                            component: StandardButtons
                        },
                        {
                            path: 'button-groups',
                            name: 'Button Groups',
                            component: ButtonGroups
                        },
                        {
                            path: 'dropdowns',
                            name: 'Dropdowns',
                            component: Dropdowns
                        },
                        {
                            path: 'brand-buttons',
                            name: 'Brand Buttons',
                            component: BrandButtons
                        }
                    ]
                },
                {
                    path: 'icons',
                    redirect: '/icons/coreui-icons',
                    name: 'CoreUI Icons',
                    component: {
                        render(c) {
                            return c('router-view')
                        }
                    },
                    children: [
                        {
                            path: 'coreui-icons',
                            name: 'Icons library',
                            component: CoreUIIcons
                        },
                        {
                            path: 'brands',
                            name: 'Brands',
                            component: Brands
                        },
                        {
                            path: 'flags',
                            name: 'Flags',
                            component: Flags
                        }
                    ]
                },
                {
                    path: 'notifications',
                    redirect: '/notifications/alerts',
                    name: 'Notifications',
                    component: {
                        render(c) {
                            return c('router-view')
                        }
                    },
                    children: [
                        {
                            path: 'alerts',
                            name: 'Alerts',
                            component: Alerts
                        },
                        {
                            path: 'badges',
                            name: 'Badges',
                            component: Badges
                        },
                        {
                            path: 'modals',
                            name: 'Modals',
                            component: Modals
                        }
                    ]
                }
            ]
        }

    ]
}

