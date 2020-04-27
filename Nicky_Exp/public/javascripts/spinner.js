Vue.component("spinner", {
    props: ['loading'],
    template: '<div class="loader mx-auto" v-show="loading"></div>'
});