import TranslatorContext from 'app/shared/language/translator-context';
import { setLocale } from 'app/shared/reducers/locale';
import { Storage } from 'app/shared/util/storage-utils';

TranslatorContext.setDefaultLocale('vi');
TranslatorContext.setRenderInnerTextForMissingKeys(false);

export const languages: any = {
  en: { name: 'EN' },
  vi: { name: 'VI' },
};

export const locales = Object.keys(languages).sort();

export const registerLocale = store => {
  store.dispatch(setLocale(Storage.session.get('locale', 'vi')));
};
