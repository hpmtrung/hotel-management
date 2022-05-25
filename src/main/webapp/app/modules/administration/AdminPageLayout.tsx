import {
  AccountCircleOutlined,
  ApiOutlined,
  CategoryOutlined,
  Dashboard,
  DashboardOutlined,
  GroupOutlined,
  Lock,
  PeopleAltOutlined,
} from '@mui/icons-material';
import { Grid, Paper } from '@mui/material';
import { CustomSection } from 'app/components/CustomSection';
import RouterBreadcrumbs, { BreadcrumbNameMapType } from 'app/components/RouterBreadcrumbs';
import SelectedList from 'app/components/SelectedList';
import InvoiceSvgIcon from 'app/components/svg/InvoiceSvgIcon';
import React from 'react';
import { Outlet } from 'react-router-dom';
import OrderDetailDialog from './order-management/OrderDetailDialog';
import AccountDetailDialog from './user-management/AccountDetailDialog';

const SideBar = () => (
  <Paper variant="outlined">
    <SelectedList
      dense
      items={[
        { icon: <DashboardOutlined />, content: 'Trang thống kê', contentKey: 'key', to: '/admin/dashboard' },
        { icon: <CategoryOutlined />, content: 'Quản lý sản phẩm', contentKey: 'key', to: '/admin/product-management' },
        { icon: <InvoiceSvgIcon />, content: 'Quản lý hóa đơn', contentKey: 'key', to: '/admin/order-management' },
        { icon: <PeopleAltOutlined />, content: 'Quản lý tài khoản', contentKey: 'key', to: '/admin/user-management' },
        { icon: <ApiOutlined />, content: 'Tài liệu API', contentKey: 'key', to: '/admin/docs' },
      ]}
    />
  </Paper>
);

const breadcrumbNameMap: BreadcrumbNameMapType = {
  '/admin/dashboard': 'Trang thống kê',
  '/admin/product-management': 'Quản lý sản phẩm',
  '/admin/order-management': 'Quản lý hóa đơn',
  '/admin/user-management': 'Quản lý tài khoản',
  '/admin/docs': 'Tài liệu API',
};

const AdminPageLayout = () => {
  return (
    <CustomSection even>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <RouterBreadcrumbs breadcrumbNameMap={breadcrumbNameMap} />
        </Grid>
        <Grid container item spacing={2}>
          <Grid item xs={3}>
            <SideBar />
          </Grid>
          <Grid item xs>
            <Outlet />
          </Grid>
          <OrderDetailDialog />
          <AccountDetailDialog />
        </Grid>
      </Grid>
    </CustomSection>
  );
};

export default AdminPageLayout;
