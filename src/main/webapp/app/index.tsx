// prettier-ignore-start
import { createTheme, CssBaseline, Fade, ThemeProvider } from '@mui/material';
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { bindActionCreators } from 'redux';
import AppComponent from './app';
import setupAxiosInterceptors from './config/axios-interceptor';
import getStore from './config/store';
import { registerLocale } from './config/translation';
import ErrorBoundary from './components/error-boundary/ErrorBoundary';
import { clearAuthentication } from 'app/modules/auth/authentication.reducer';
import { NotificationProvider } from './components/notification';
import { SnackbarProvider } from 'notistack';

// Font
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
// Carousel theme
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/slick.css';
import { registerCart } from './config/cart';
// prettier-ignore-end

const store = getStore();
// Register default locale
registerLocale(store);
// Register cart data
registerCart(store);

// Set axios interceptor for clearing expired authentication when fetching session is false
const actions = bindActionCreators({ clearAuthentication }, store.dispatch);
setupAxiosInterceptors(() => actions.clearAuthentication('login.error.unauthorized'));

const rootEl = document.getElementById('root');

// MUI theme
const theme = createTheme({
  palette: {
    mode: 'light',
    background: {
      default: '#F7FAFC',
      paper: '#fff',
    },
    primary: {
      main: '#118ab2',
    },
    secondary: {
      main: '#073b4c',
    },
    warning: {
      main: '#ffd166',
    },
    info: {
      main: '#3a86ff',
    },
    error: {
      main: '#ef476f',
    },
    success: {
      main: '#06d6a0',
    },
  },
});

const render = Component =>
  // eslint-disable-next-line react/no-render-return-value
  ReactDOM.render(
    <ErrorBoundary>
      <ThemeProvider theme={theme}>
        <Provider store={store}>
          <NotificationProvider>
            <SnackbarProvider
              maxSnack={1}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'center',
              }}
              TransitionComponent={Fade}
              preventDuplicate
              autoHideDuration={6000}
            >
              <CssBaseline />
              <Component />
            </SnackbarProvider>
          </NotificationProvider>
        </Provider>
      </ThemeProvider>
    </ErrorBoundary>,
    rootEl
  );

render(AppComponent);
