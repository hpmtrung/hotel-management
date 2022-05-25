import { AppBar, Box, Button, Container, Link, styled, Toolbar, useScrollTrigger } from '@mui/material';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { setShowModalLogin } from 'app/modules/auth/authentication.reducer';
import LoginModal from 'app/modules/auth/LoginModal';
import { setLocale } from 'app/shared/reducers/locale';
import { Storage } from 'app/shared/util/storage-utils';
import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import AdminManagementMenu from './menu/AdminManagementMenu';
import AuthenticatedUserMenu from './menu/AuthenticatedUserMenu';
import CartMenu from './menu/CartMenu';
import { LocaleMenu } from './menu/LocaleMenu';
import MobileMenu from './MobileMenu';

const BrandStyled = styled(Link)`
  display: flex;
  justify-content: center;
  align-items: center;
  .brand-icon {
    height: 16px;
    width: auto;
  }
`;

const Brand = React.memo(() => (
  <BrandStyled component={RouterLink} to="/">
    <img src="assets/images/logo-default.svg" className="brand-icon" alt="Logo" />
  </BrandStyled>
));

const HEADER_COLOR = 'text.secondary';
const HEADER_BG_FILL = 'background.paper';
const HEADER_BG_TRANSPARENT = 'transparent';

export type IHeaderProps = {
  isAuthenticated: boolean;
  isAdmin: boolean;
  currentLocale: string;
};

const Header = ({ isAuthenticated, isAdmin, currentLocale }: IHeaderProps) => {
  const dispatch = useAppDispatch();
  // For trigger scrolling
  const trigger = useScrollTrigger({ disableHysteresis: true, threshold: 30 });
  const elevationEffect = useAppSelector(state => state.header.elevationEffect);
  const headerBgColor = elevationEffect ? (trigger ? HEADER_BG_FILL : HEADER_BG_TRANSPARENT) : HEADER_BG_FILL;

  const handleOpenLoginModal = () => {
    dispatch(setShowModalLogin(true));
  };

  return (
    <>
      <AppBar elevation={trigger ? 4 : 0} sx={{ flexGrow: 1, bgcolor: headerBgColor, color: HEADER_COLOR }}>
        <Container maxWidth="lg">
          <Toolbar disableGutters>
            {/* Logo */}
            <Brand />
            {/* Filling space */}
            <Box sx={{ flexGrow: 1 }} />
            {/* Menus */}
            <Box sx={{ alignItems: 'center', gap: 1, display: { xs: 'none', md: 'flex' } }}>
              {isAuthenticated && isAdmin && <AdminManagementMenu />}

              {isAuthenticated ? (
                <AuthenticatedUserMenu />
              ) : (
                <Button variant="text" size="small" data-cy="login" disableElevation onClick={handleOpenLoginModal}>
                  Login
                </Button>
              )}

              {/* <CartMenu /> */}
              {/* <LocaleMenu currentLocale={currentLocale} onClick={handleLocaleChange} sx={{ textTransform: 'none' }} /> */}
            </Box>
            <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
              <MobileMenu isAuthenticated isAdmin />
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
      <LoginModal />
      {elevationEffect || <Toolbar disableGutters sx={{ flexGrow: 1 }} />}
    </>
  );
};

export default Header;
