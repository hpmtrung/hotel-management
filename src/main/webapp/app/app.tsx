import { AUTHORITIES } from 'app/config/constants';
import 'app/config/dayjs.ts';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import AppRoutes from 'app/routes';
import { hasAnyAuthority } from 'app/shared/private-route/PrivateRoute';
import ErrorBoundary from 'app/components/error-boundary/ErrorBoundary';
import Footer from 'app/layout/Footer';
import React, { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { getAppProfile } from './modules/app-profile/application-profile';
import Header from './layout/header/Header';
import { getSession } from './modules/auth/authentication.reducer';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getSession());
    dispatch(getAppProfile());
  }, [dispatch]);

  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  return (
    <BrowserRouter basename={baseHref}>
      <ErrorBoundary>
        <Header isAuthenticated={isAuthenticated} isAdmin={isAdmin} currentLocale={currentLocale} />
      </ErrorBoundary>
      <ErrorBoundary>
        <AppRoutes />
      </ErrorBoundary>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
