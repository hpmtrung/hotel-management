import { ApiOutlined, CategoryOutlined, DashboardOutlined, PeopleAltOutlined } from '@mui/icons-material';
import InvoiceSvgIcon from 'app/components/svg/InvoiceSvgIcon';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import NavMenuItem from '../../../components/menu//NavMenuItem';
import NavMenu from '../../../components/menu/NavMenu';
import NavMenuItemCollapse from '../../../components/menu/NavMenuItemCollapse';

export const AdminManagementMenu = ({ collapse = false, ...other }) => {
  const navigate = useNavigate();
  let AdminMenu = null;
  let props = { ...other };
  if (collapse) {
    AdminMenu = NavMenuItemCollapse;
    props = { ...props, content: 'Management' };
  } else {
    AdminMenu = NavMenu;
    props = { ...props, tooltipText: 'Management' };
  }
  return (
    <AdminMenu {...props} icon={<CategoryOutlined />}>
      <NavMenuItem icon={<DashboardOutlined fontSize="small" />} content="Thống kê" onClick={() => navigate('/admin/dashboard')} />
      <NavMenuItem icon={<CategoryOutlined fontSize="small" />} content="Sản phẩm" onClick={() => navigate('/admin/product-management')} />
      <NavMenuItem
        icon={<InvoiceSvgIcon fontSize="small" />}
        content="Hóa đơn"
        onClick={() => navigate('/admin/order-management')}
        divider
      />
      <NavMenuItem icon={<PeopleAltOutlined fontSize="small" />} content="User" onClick={() => navigate('/admin/user-management')} />
      <NavMenuItem icon={<ApiOutlined fontSize="small" />} content="Tài liệu API" onClick={() => navigate('/admin/docs')} />
    </AdminMenu>
  );
};

export default AdminManagementMenu;
