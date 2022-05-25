import { updateCart } from 'app/modules/checkout/checkout.reducer';
import { IUserCart } from 'app/modules/checkout/model/cart.model';
import { Storage } from 'app/shared/util/storage-utils';

export const registerCart = store => {
  store.dispatch(updateCart(Storage.local.get('cart')));
};
