import password from 'app/modules/account/password-change/password.reducer';
import settings from 'app/modules/account/settings/account-settings.reducer';
import dashboard from 'app/modules/administration/dashboard/dashboard.reducer';
import orderManagement from 'app/modules/administration/order-management/admin-order.reducer';
import accountManagement from 'app/modules/administration/user-management/account.reducer';
import applicationProfile from 'app/modules/app-profile/application-profile';
import activate from 'app/modules/auth/activate/activate.reducer';
import authentication from 'app/modules/auth/authentication.reducer';
import passwordReset from 'app/modules/auth/password-reset.reducer';
import signUp from 'app/modules/auth/sign-up.reducer';
import cart from 'app/modules/checkout/checkout.reducer';
import userOrders from 'app/modules/account/orders/order.reducer';
import header from './header';
import locale from './locale';

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  header,
  signUp,
  activate,
  passwordReset,
  password,
  cart,
  // Account
  settings,
  userOrders,
  // Administrators
  dashboard,
  orderManagement,
  accountManagement,
};

export default rootReducer;
