import { Paper, Stack, Typography, useTheme } from '@mui/material';
import CircularLoadingIndicator from 'app/components/CircularLoadingIndicator';
import { ResponsiveTab } from 'app/components/ResponsiveTab';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ORDER_STATUSES } from 'app/modules/account/orders/model/order-status.model';
import { getSortState } from 'app/shared/util/pagination-utils';
import React, { useCallback } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useLocation, useNavigate } from 'react-router-dom';
import { getAccountOrderDetails, getAccountOrdersByStatus, getAllAccountOrders, reset } from './order.reducer';
import OverviewOrderPaper from './OverviewOrderPaper';

const ORDERS_PER_PAGE = 4;

const AccountOrdersPage = () => {
  const theme = useTheme();
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useAppDispatch();
  const [selectedStatusIndex, setSelectedStatusIndex] = React.useState(0);
  const { loading, orders, links } = useAppSelector(state => state.userOrders);

  const [ordersPagination, setOrdersPagination] = React.useState(getSortState(location, ORDERS_PER_PAGE, 'id'));

  React.useEffect(() => {
    if (selectedStatusIndex === 0) {
      dispatch(getAllAccountOrders({ page: ordersPagination.activePage, size: ordersPagination.itemsPerPage }));
    } else {
      dispatch(
        getAccountOrdersByStatus({
          statusId: selectedStatusIndex,
          params: { page: ordersPagination.activePage, size: ordersPagination.itemsPerPage },
        })
      );
    }
  }, [dispatch, ordersPagination, selectedStatusIndex]);

  const handleChangeSelectedStatusIndex = (e: React.SyntheticEvent, statusIndex: number) => {
    dispatch(reset());
    setSelectedStatusIndex(statusIndex);
    setOrdersPagination({
      ...ordersPagination,
      activePage: 0,
    });
  };

  const handleShowDetails = (orderId: number) => {
    navigate(`${orderId}`);
  };

  const handleLoadMore = useCallback(() => {
    if (window.pageYOffset > 0) {
      setOrdersPagination({
        ...ordersPagination,
        activePage: ordersPagination.activePage + 1,
      });
    }
  }, [ordersPagination]);

  return (
    <Stack direction="column" spacing={1}>
      <Paper elevation={0}>
        <ResponsiveTab
          breakpoint={theme.breakpoints.up('md')}
          value={selectedStatusIndex}
          onChange={handleChangeSelectedStatusIndex}
          tabItems={Object.values(ORDER_STATUSES).map(status => ({ label: status.name }))}
        />
      </Paper>
      {loading ? (
        <Paper elevation={0}>
          <CircularLoadingIndicator />
        </Paper>
      ) : orders.length > 0 ? (
        <Stack direction="column" spacing={1}>
          <InfiniteScroll
            dataLength={orders.length}
            next={handleLoadMore}
            hasMore={ordersPagination.activePage < links.next}
            loader={<CircularLoadingIndicator />}
          >
            {orders.map(order => (
              <OverviewOrderPaper key={order.id} order={order} handleShowDetails={handleShowDetails} />
            ))}
          </InfiniteScroll>
        </Stack>
      ) : (
        <Paper elevation={0} sx={{ py: 5, textAlign: 'center' }}>
          <Typography variant="body1" color="text.secondary">
            Không tìm thấy hóa đơn
          </Typography>
        </Paper>
      )}
    </Stack>
  );
};

export default AccountOrdersPage;
