Vue.use(Toasted)

Vue.component("qe-card", {
    props: {
        qe: Object
    },
    template:
        '<b-card class="mb-4 w-60" no-body>' +
        '   <b-card-body class="row">' +
        '       <div class="col-sm-8"><h4><span class="badge badge-primary">QE Name:</span> {{ qe.name }}</h4></div>' +
        '       <div class="col-sm-4 d-flex flex-row justify-content-center align-items-center">' +
        '           <p class="m-0">Enabled</p>' +
        '           <label class="switch ml-3 m-0">' +
        '               <input type="checkbox" v-on:change="changeEnabled()" v-model="qe.enabled">' +
        '               <span class="slider round"></span>' +
        '           </label>' +
        '       </div>' +
        '   </b-card-body>' +
        '   <b-list-group flush>' +
        '       <b-list-group-item>' +
        '           <p class="mb-0" v-for="(value, key) in qe">' +
        '               <span class="badge badge-info">{{ key }}:</span> {{ value }}' +
        '           </p>' +
        '       </b-list-group-item>' +
        '   </b-list-group>' +
        '   <b-card-footer class="container">' +
        '       <div class="row">' +
        '           <div class="col col-sm-6"><button class="btn btn-block btn-outline-warning" v-on:click="forceExpand()" v-b-modal="expandModalId">Expands</button></div>' +
        '           <div class="col col-sm-6"><button class="btn btn-block btn-outline-info" v-b-modal="logModalId">Logs</button></div>' +
        '       </div>' +
        '   </b-card-footer>' +
        '   <qe-logs-modal :qe="qe" :id="logModalId" ref="logsModal"></qe-logs-modal>' +
        '   <qe-expand-modal :qe="qe" :id="expandModalId" ref="expandModal" v-on:update-logs="updateLogs"></qe-expand-modal>' +
        '</b-card>',
    computed: {
        logModalId: function() {
            return this.qe.name + "-log-modal"
        },
        expandModalId: function() {
            return this.qe.name + "-expand-modal"
        }
    },
    data: function() {
        return {
        }
    },
    methods: {
        isInterval: function(key) {
            return key === "interval"
        },
        forceExpand: function() {
            this.$refs.expandModal.forceExpand()
        },
        updateLogs: function() {
            this.$refs.logsModal.getExpLogs()
        },
        changeEnabled: function() {
            console.log("new " + this.qe.enabled);
            // submit update
            let xhr = new XMLHttpRequest();
            xhr.open('PUT', jsRoutes.controllers.ManageQEController.changeQEEnabled(this.qe.name, this.qe.enabled).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // update qes info
                    vm.$emit('update-qes-info');
                    let toastString = "";
                    if (vm.qe.enabled) {
                        toastString = vm.qe.name + " is enabled!";
                    } else {
                        toastString = vm.qe.name + " is disabled!";
                    }
                    Vue.toasted.show(toastString, {
                        theme: "bubble",
                        position: "bottom-center",
                        duration : 2000,
                        fitToScreen: true
                    });
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        }
    }
});

Vue.use(Toasted);

var app1 = new Vue({
    el: "#app1",
    created: function() {
        this.getQEsInfo();
    },
    data: {
        query: {},
        qes: []
    },
    computed: {

    },
    methods: {
        updateQuery: function(query) {
            this.query = query;
        },
        getQEsInfo: function() {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.ManageQEController.getQEsInfo().url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.qes = response.data;
                    // transform timestamp to string
                    for (let i = 0; i < vm.qes.length; i++) {
                        let lastExpansion = vm.qes[i].lastExpansion;
                        let nextExpansion = vm.qes[i].nextExpansion;
                        vm.qes[i].lastExpansion = vm.formatTime(lastExpansion) + vm.relativeTime(lastExpansion);
                        vm.qes[i].nextExpansion = vm.formatTime(nextExpansion) + vm.relativeTime(nextExpansion);
                    }
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        formatTime: function(ts) {
            if (ts !== -2)
                return moment.unix(ts).format('Do MMMM YYYY, h:mm:ss a');
            else
                return "N/A (System just started)";
        },
        relativeTime: function(ts) {
            if (ts !== -2)
                return " (" +  moment.unix(ts).fromNow() + ")";
            else
                return "";
        },
        navigateToIndex: function() {
            window.location.href = jsRoutes.controllers.HomeController.index().url
        },
        navigateToTrackQuery: function() {
            window.location.href = jsRoutes.controllers.TrackQueryController.trackQuery().url
        },
        navigateToManageCrawler: function() {
            window.location.href = jsRoutes.controllers.ManageCrawlerController.manageCrawler().url
        }
    }
});

