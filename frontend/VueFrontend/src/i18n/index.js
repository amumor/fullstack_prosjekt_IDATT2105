import { createI18n } from 'vue-i18n';
import en from './en.json';
import fr from './fr.json';
import es from './es.json';
import cmn from './cmn.json';
import ar from './ar.json';
import no from './no.json';

const i18n = createI18n({
    locale: 'en', // default locale
    fallbackLocale: 'en',
    messages: {
      en,
      fr,
      es,
      cmn,
      ar,
      no
    }
});
  
export default i18n;