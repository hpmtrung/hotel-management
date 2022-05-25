import { LogoutOutlined } from '@mui/icons-material';
import { useConfirm } from 'app/components/notification';
import NavMenuItem from 'app/components/menu/NavMenuItem';
import { useAppDispatch } from 'app/config/store';
import { logout } from 'app/modules/auth/authentication.reducer';
import { resetCart } from 'app/modules/checkout/checkout.reducer';
import React from 'react';
import { useNavigate } from 'react-router-dom';

const LogoutMenu = () => {
  const confirm = useConfirm();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    confirm({ title: 'Đăng xuất', description: 'Bạn có thật sự muốn đăng xuất không?', dialogProps: { maxWidth: 'sm' } }).then(() => {
      dispatch(logout());
      dispatch(resetCart());
      navigate('/');
    });
  };

  return <NavMenuItem icon={<LogoutOutlined fontSize="small" />} content="Thoát tài khoản" onClick={handleLogout} />;
};

export default LogoutMenu;
