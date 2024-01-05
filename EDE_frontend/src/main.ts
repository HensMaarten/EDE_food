import { createApp } from "vue";
import App from "./App.vue";
import vue3GoogleLogin from "vue3-google-login";
import router from "./router";
import store from "./store";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  faFilePen,
  faTrashCan,
  faCirclePlus,
  faRightFromBracket,
  faPlugCircleXmark,
  faPlugCircleCheck,
  faHouse,
} from "@fortawesome/free-solid-svg-icons";

library.add(
  faFilePen,
  faTrashCan,
  faCirclePlus,
  faRightFromBracket,
  faPlugCircleXmark,
  faPlugCircleCheck,
  faHouse
);
createApp(App)
  .component("font-awesome-icon", FontAwesomeIcon)
  .use(store)
  .use(router)
  .use(vue3GoogleLogin, {
    clientId:
      "739035229987-njjrl35i5s3fhuitf5upuif3901gvajp.apps.googleusercontent.com",
  })
  .mount("#app");
