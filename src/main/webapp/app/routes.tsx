import { AUTHORITIES } from 'app/config/constants';
import PageNotFound from 'app/layout/PageNotFound';
import PasswordResetInit from 'app/modules/account/password-reset/init/PasswordResetInit';
import ActivatePage from 'app/modules/auth/activate/ActivatePage';
import PasswordResetFinish from 'app/modules/auth/PasswordResetFinishPage';
import PrivateRoute from 'app/shared/private-route/PrivateRoute';
import React from 'react';
import Loadable from 'react-loadable';
import { Route, Routes } from 'react-router-dom';
import ErrorBoundaryWrapper from './components/error-boundary/ErrorBoundaryWrapper';
import CheckoutPage from './modules/checkout/CheckoutPage';
import Home from './modules/home/Home';

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div></div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div></div>,
});

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<ErrorBoundaryWrapper ele={<Home />} />} />
      <Route path="/checkout" element={<ErrorBoundaryWrapper ele={<CheckoutPage />} />} />
      <Route path="/activate" element={<ErrorBoundaryWrapper ele={<ActivatePage />} />} />
      <Route path="/reset-password/request" element={<ErrorBoundaryWrapper ele={<PasswordResetInit />} />} />
      <Route path="/reset-password/finish" element={<ErrorBoundaryWrapper ele={<PasswordResetFinish />} />} />
      {/* Administrator's routes */}
      <Route path="/admin" element={<PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]} />}>
        <Route path="/admin/*" element={<Admin />} />
      </Route>
      {/* Authenticated user's routes */}
      <Route path="/account" element={<PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />}>
        <Route path="/account/*" element={<Account />} />
      </Route>
      {/* 404 Page */}
      <Route path="*" element={<ErrorBoundaryWrapper ele={<PageNotFound />} />} />
    </Routes>
  );
};

export default AppRoutes;
